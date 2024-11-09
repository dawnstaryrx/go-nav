package com.dawn.gonav.user.service.impl;

import com.dawn.gonav.model.po.User;
import com.dawn.gonav.model.vo.AppXlsxVO;
import com.dawn.gonav.model.vo.PageBeanVO;
import com.dawn.gonav.user.mapper.AppMapper;
import com.dawn.gonav.user.mapper.UserMapper;
import com.dawn.gonav.user.service.AppService;
import com.dawn.gonav.exception.ExceptionTool;
import com.dawn.gonav.model.dto.AppDTO;
import com.dawn.gonav.model.dto.UserLoginDTO;
import com.dawn.gonav.model.enums.AppStatusEnum;
import com.dawn.gonav.model.enums.UserTypeEnum;
import com.dawn.gonav.model.po.App;
import com.dawn.gonav.model.vo.AppVO;
import com.dawn.gonav.user.service.UserService;
import com.dawn.gonav.util.CopyUtil;
import com.dawn.gonav.util.CurrentUserUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppServiceImpl implements AppService {
    private final AppMapper appMapper;
    private final UserService userService;
    @Override
    public void addApp(AppDTO appDTO) {
        // 不需要添加分类名称
//        if (appDTO.getCategoryId() == null){
//            throw new RuntimeException("分类不能为空");
//        }
        // TODO 检查 分类需要是本人的分类
        App app = CopyUtil.copy(appDTO, App.class);
        if (app.getUrl() == null || app.getUrl().trim().isEmpty()){
            throw new RuntimeException("应用地址不能为空");
        }
        UserLoginDTO nowUser = CurrentUserUtil.getCurrentUser();
        if (nowUser == null){
            throw new RuntimeException("用户未登录");
        }
        String url = app.getUrl();
        ExecutorService executor = Executors.newFixedThreadPool(3);
        if (app.getIconUrl() == null || app.getIconUrl().trim().isEmpty()){
            String favImage = "";
//            ExecutorService executor = Executors.newFixedThreadPool(3);
            Future<String> faviconFuture = executor.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    Document document = Jsoup.connect(url).get();
                    Element iconElement = document.head().select("link[href~=.*\\.(ico|png|jpg|jpeg|svg)]").first();
                    if (iconElement != null) {
                        return iconElement.attr("href");
                    } else {
                        iconElement = document.head().select("meta[itemprop=image], meta[property=og:image]").first();
                        return iconElement != null ? iconElement.attr("content") : "Not Found";
                    }
                }
            });
            try {
                favImage = faviconFuture.get(3, TimeUnit.SECONDS); // 设置超时为3秒
            } catch (TimeoutException e) {
                System.out.println("Operation timed out. Returning default value.");
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            // 处理相对路径
            if (!favImage.equals("") && !favImage.startsWith("http")) {
                favImage = url + favImage; // 将相对路径转换为绝对路径
            }

            // 得到图标链接
            app.setIconUrl(favImage);
        }
        if (app.getName() == null || app.getName().trim().isEmpty()){
            String title = "";
            // Callable 任务2：爬取 title
            Future<String> titleFuture = executor.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    Document document = Jsoup.connect(url).get();
                    return document.title();
                }
            });
            try {
                // 获取 title 结果
                title = titleFuture.get(3, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                System.out.println("Operation timed out. Returning default values.");
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            app.setName(title);
        }
        if (app.getDescription() == null || app.getDescription().trim().isEmpty()){
            String description = "";
            // Callable 任务3：爬取 description
            Future<String> descriptionFuture = executor.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    Document document = Jsoup.connect(url).get();
                    Element descriptionElement = document.select("meta[name=description], meta[property=og:description]").first();
                    return descriptionElement != null ? descriptionElement.attr("content") : "";
                }
            });
            try {
                description = descriptionFuture.get(3, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                System.out.println("Operation timed out. Returning default values.");
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            app.setDescription(description);
        }
        if ( !executor.isTerminated() ){
            executor.shutdown();
        }
        assert nowUser != null;
        app.setCreateUser(nowUser.getId());
        app.setUpdateUser(nowUser.getId());
        app.setCreateTime(LocalDateTime.now());
        app.setUpdateTime(LocalDateTime.now());
        app.setClickCount(0);
        appMapper.addApp(app);
    }

    @Override
    public void deleteApp(Long id) {
        App app = getAppById(id);
        if (app == null){
            ExceptionTool.throwException("应用不存在");
        }
        appMapper.deleteApp(id);
    }

    @Override
    public void updateApp(AppDTO appDTO) {
        App app = CopyUtil.copy(appDTO, App.class);
        UserLoginDTO nowUser = CurrentUserUtil.getCurrentUser();
        if (nowUser == null){
            ExceptionTool.throwException("用户未登录");
        }
        assert nowUser != null;
        app.setUpdateUser(nowUser.getId());
        app.setUpdateTime(LocalDateTime.now());
        appMapper.updateApp(app);
    }

    @Override
    public List<AppVO> getAppsByUsername(String username, Long categoryId) {
        Integer status = AppStatusEnum.ENABLE.getCode();
        UserLoginDTO nowUser = CurrentUserUtil.getCurrentUser();
        log.info("----------------status 启用");
        if (nowUser != null && nowUser.getRole() == 1) {
            log.info("----------------status 隐藏");
            status = AppStatusEnum.HIDE.getCode();
        }
        if (nowUser != null && Objects.equals(nowUser.getUsername(), username)){
            log.info("----------------status 私有");
            status = AppStatusEnum.PRIVATE.getCode();
        }
        if (nowUser != null && Objects.equals(nowUser.getRole(), UserTypeEnum.ADMIN.getCode())){
            log.info("----------------status 私有");
            status = AppStatusEnum.PRIVATE.getCode();
        }
        User user = userService.getUserByUsername(username);
        List<AppVO> appVOS = appMapper.getAppVOsByUserId(user.getId(), status, categoryId);
        return appVOS;
    }

    @Override
    public List<AppXlsxVO> downLoadAppsXlsx(Long userId) {
        return appMapper.downLoadAppXlsx(userId);
    }

    @Override
    public List<AppVO> getHotApps(String username) {
        User user = userService.getUserByUsername(username);
        return appMapper.getHotAppVOs(user.getId());
    }

    @Override
    public List<AppVO> searchApps(String searchContent, String username) {
        User user = userService.getUserByUsername(username);
        return appMapper.searchAppVOs(searchContent, user.getId());
    }

    public App getAppById(Long id){
        return appMapper.getAppById(id);
    }

    @Override
    public PageBeanVO<AppVO> getAppsPageNowUser(Integer pageNum, Integer pageSize, Long categoryId, Integer status, String searchContent) {
        PageBeanVO<AppVO> pageBeanVO = new PageBeanVO<>();
        PageHelper.startPage(pageNum, pageSize);
        UserLoginDTO nowUser = CurrentUserUtil.getCurrentUser();
        assert nowUser != null;
        List<AppVO> appVOS = appMapper.getAppVOsPageNowUser(nowUser.getId(), status, categoryId, searchContent);
        Page<AppVO> page = (Page<AppVO>) appVOS;
        pageBeanVO.setTotal(page.getTotal());
        pageBeanVO.setItems(page.getResult());
        return pageBeanVO;
    }

    @Override
    public void clickApp(Long id) {
        App app = getAppById(id);
        app.setClickCount(app.getClickCount() + 1);
        appMapper.updateApp(app);
    }

    @Override
    public void addAppXlsx(List<AppDTO> dataList) {
        for (AppDTO appDTO : dataList){
            addApp(appDTO);
        }
    }
}
