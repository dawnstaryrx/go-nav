package com.dawn.gonav;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@SpringBootTest
public class JsoupTest {
    public static void main(String[] args) {
        String favImage = "Not Found";
        String title = "Not Found";
        String description = "Not Found";
        String url = "https://www.zhihu.com/hot";

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Document document = Jsoup.connect(url).get();

                // 获取 favicon 图标链接
                Element iconElement = document.head().select("link[href~=.*\\.(ico|png|jpg|jpeg|svg)]").first();
                String favIconUrl = iconElement != null ? iconElement.attr("href") : "Not Found";

                // 获取 title
                String pageTitle = document.title();

                // 获取 description
                Element descriptionElement = document.select("meta[name=description], meta[property=og:description]").first();
                String pageDescription = descriptionElement != null ? descriptionElement.attr("content") : "Not Found";

                // 拼接所有结果，以便在 main 方法中通过 future.get() 获取
                return favIconUrl + "|" + pageTitle + "|" + pageDescription;
            }
        });

        try {
            String result = future.get(3, TimeUnit.SECONDS); // 设置超时为3秒
            String[] parts = result.split("\\|");
            favImage = parts[0];
            title = parts[1];
            description = parts[2];
        } catch (TimeoutException e) {
            System.out.println("Operation timed out. Returning default value.");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

        // 处理相对路径
        if (!favImage.equals("Not Found") && !favImage.startsWith("http")) {
            favImage = url + favImage; // 将相对路径转换为绝对路径
        }

        // 输出结果
        System.out.println("Favicon URL: " + favImage);
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
    }
}