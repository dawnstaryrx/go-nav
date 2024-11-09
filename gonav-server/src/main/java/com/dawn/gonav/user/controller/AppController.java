package com.dawn.gonav.user.controller;

import com.alibaba.excel.EasyExcel;
import com.dawn.gonav.exception.ExceptionTool;
import com.dawn.gonav.model.dto.UserLoginDTO;
import com.dawn.gonav.model.po.User;
import com.dawn.gonav.model.vo.AppXlsxVO;
import com.dawn.gonav.model.vo.PageBeanVO;
import com.dawn.gonav.user.service.AppService;
import com.dawn.gonav.model.dto.AppDTO;
import com.dawn.gonav.model.po.Result;
import com.dawn.gonav.model.vo.AppVO;
import com.dawn.gonav.user.service.UserService;
import com.dawn.gonav.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AppController {
    private final AppService appService;
    private final UserService userService;
    @PutMapping("/user/app")
    public Result addApp(@RequestBody AppDTO appDTO){
        appService.addApp(appDTO);
        return Result.success();
    }

    // TODO Excel批量导入
    @PutMapping("/user/apps/upload/xlsx")
    public Result addApps(@RequestParam("file") MultipartFile file){
        try {
            if (file.isEmpty()) {
                ExceptionTool.throwException("上传文件为空！");
            }
            // 处理文件
            List<AppDTO> dataList = readXlsx(file);
            log.info("数据存储中" + dataList);
            // TODO 存储数据到数据库
            appService.addAppXlsx(dataList);
            return Result.success();
        } catch (Exception e) {
            log.error("上传失败！", e);
            ExceptionTool.throwException("上传失败！"+e);
            return Result.error("上传失败！");
        }
    }

    @DeleteMapping("/user/app")
    public Result deleteApp(@RequestParam Long id){
        appService.deleteApp(id);
        return Result.success();
    }

    @PostMapping("/user/app")
    public Result updateApp(@RequestBody AppDTO appDTO){
        appService.updateApp(appDTO);
        return Result.success();
    }

    @GetMapping("/user/app/page")
    public Result<PageBeanVO<AppVO>> getAppsPageNowUser(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String searchContent
    ){
        return Result.success(appService.getAppsPageNowUser(pageNum, pageSize, categoryId, status, searchContent));
    }

    @GetMapping("/public/app")
    public Result<List<AppVO>> getAppsByUsernamePublic(@RequestParam String username, @RequestParam(required = false) Long categoryId){
        return Result.success(appService.getAppsByUsername(username, categoryId));
    }
    @GetMapping("/user/app")
    public Result<List<AppVO>> getAppsByUsernameUser(@RequestParam String username, @RequestParam(required = false) Long categoryId){
        return Result.success(appService.getAppsByUsername(username, categoryId));
    }
    @GetMapping("/public/app/hot")
    public Result<List<AppVO>> getHotApps(@RequestParam String username){
        return Result.success(appService.getHotApps(username));
    }

    @GetMapping("/public/app/search")
    public Result<List<AppVO>> searchApps(@RequestParam String searchContent, @RequestParam String username){
        return Result.success(appService.searchApps(searchContent, username));
    }

    @GetMapping("/public/app/index")
    public Result<List<AppVO>> getAppsIndexPublic(@RequestParam(required = false) Long categoryId){
        User user = userService.getUserByUsername("admin");
        if (user == null || user.getId() == null){
            ExceptionTool.throwException("当前无ADMIN用户！");
        }
        assert user != null;
        return getAppsByUsernamePublic(user.getUsername(), categoryId);
    }

    @GetMapping("/user/app/index")
    public Result<List<AppVO>> getAppsIndexUser(@RequestParam(required = false) Long categoryId){
        User user = userService.getUserByUsername("admin");
        if (user == null || user.getId() == null){
            ExceptionTool.throwException("当前无ADMIN用户！");
        }
        assert user != null;
        return getAppsByUsernameUser(user.getUsername(), categoryId);
    }

    @PostMapping("/public/app/click")
    public Result clickApp(@RequestParam Long id){
        appService.clickApp(id);
        return Result.success();
    }

    private List<AppDTO> readXlsx(MultipartFile file) throws IOException {
        List<AppDTO> appXlsxDTOS = new ArrayList<>();
        // 使用 Apache POI 读取
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet = workbook.getSheetAt(0); // 获取第一个sheet
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue; // 跳过表头
            }
            // 创建实体对象
            AppDTO appDTO = new AppDTO();
            // 设置数据
//
            if (row.getCell(0) != null){
                appDTO.setName(row.getCell(0).getStringCellValue());
            }
            if (row.getCell(1) != null){
                appDTO.setUrl(row.getCell(1).getStringCellValue());
            }
            if (row.getCell(2) != null){
                appDTO.setIconUrl(row.getCell(2).getStringCellValue());
            }
            appDTO.setCategoryId((long) row.getCell(3).getNumericCellValue());
            if (row.getCell(4) != null){
                appDTO.setDescription(row.getCell(4).getStringCellValue());
            }
            if (row.getCell(5) != null){
                appDTO.setWeight((int) row.getCell(5).getNumericCellValue());
            }
            if (row.getCell(6) != null){
                appDTO.setStatus((int) row.getCell(6).getNumericCellValue());
            }
            if (appDTO.getUrl() != null && !appDTO.getUrl().isEmpty()){
                appXlsxDTOS.add(appDTO);
            }
        }
        workbook.close();
        return appXlsxDTOS;
    }

    @PostMapping("/user/app/download")
    public ResponseEntity<InputStreamResource> downLoadAppsXlsx(){
        UserLoginDTO nowUser = CurrentUserUtil.getCurrentUser();
        List<AppXlsxVO> appXlsxVOS = appService.downLoadAppsXlsx(nowUser.getId());
        // 创建临时文件用于存储 Excel 内容
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // 使用 EasyExcel 写入数据到输出流
        EasyExcel.write(outputStream, AppXlsxVO.class)
                .sheet("卡片数据")
                .doWrite(appXlsxVOS);
        // 将数据写入本地文件
//        try (FileOutputStream fileOutputStream = new FileOutputStream("apps.xlsx")) {
//            outputStream.writeTo(fileOutputStream);
//        } catch (IOException e) {
//            log.error("保存 Excel 文件到本地失败", e);
//        }

        // 构建 HTTP 响应返回 Excel 文件
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(outputStream.toByteArray()));

        // 设置下载文件的响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=apps.xlsx");
        headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // 确保正确的 Content-Type
        log.info("Excel 文件下载成功");
        log.info("Excel 文件大小为: " + outputStream.size());
        log.info(appXlsxVOS.toString());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(outputStream.size())
                .body(resource);
    }
}
