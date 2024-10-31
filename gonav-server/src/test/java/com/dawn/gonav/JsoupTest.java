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
        String url = "https://fonts.google.com/";

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new Callable<String>() {
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
            favImage = future.get(3, TimeUnit.SECONDS); // 设置超时为3秒
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

        // 输出图标链接
        System.out.println("Favicon URL: " + favImage);
    }
}