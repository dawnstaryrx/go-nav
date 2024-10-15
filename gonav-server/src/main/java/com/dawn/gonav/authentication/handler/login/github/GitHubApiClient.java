package com.dawn.gonav.authentication.handler.login.github;

import com.dawn.gonav.exception.ExceptionTool;
import com.dawn.gonav.util.JSONUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class GitHubApiClient {
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${login.GitHub.clientId}")
    private String clientId;

    @Value("${login.GitHub.clientSecret}")
    private String clientSecret;

    @Value("${login.GitHub.redirectUri}")
    private String redirectUri;

    public String getTokenByCode(String code) {
        String tokenEndpoint = "https://github.com/login/oauth/access_token";
        try {
            // 构建请求体
            StringBuilder params = new StringBuilder();
            params.append("client_id=").append(URLEncoder.encode(clientId, StandardCharsets.UTF_8));
            params.append("&client_secret=").append(URLEncoder.encode(clientSecret, StandardCharsets.UTF_8));
            params.append("&code=").append(URLEncoder.encode(code, StandardCharsets.UTF_8));
            params.append("&redirect_uri=").append(URLEncoder.encode(redirectUri, StandardCharsets.UTF_8));
            // 如果使用了 state 参数，可以在这里添加
    //         params.append("&state=").append(URLEncoder.encode(state, "UTF-8"));

            // 创建URL和连接
            URL url = new URL(tokenEndpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // 设置请求头
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Accept", "application/json"); // 请求 JSON 响应

            // 发送请求体
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = params.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // 读取响应
            int status = conn.getResponseCode();
            InputStream is = (status >= 200 && status < 300) ? conn.getInputStream() : conn.getErrorStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            // 解析响应
            String responseJson = response.toString();
            Map<String, Object> responseMap = JSONUtil.parseToMap(responseJson);

            if (status >= 200 && status < 300) {
                Object accessToken = responseMap.get("access_token");
                if (accessToken == null) {
                    String error = (String) responseMap.get("error");
                    String errorDescription = (String) responseMap.get("error_description");
                    ExceptionTool.throwException("获取GitHub Token失败！错误信息：" + error + " - " + errorDescription);
                }
                System.out.println("GitHub access_token: " + accessToken);
                return accessToken.toString();
            } else {
                String error = (String) responseMap.get("error");
                String errorDescription = (String) responseMap.get("error_description");
                ExceptionTool.throwException("获取GitHub Token失败！错误信息：" + error + " - " + errorDescription);
                return null; // 这行通常不会执行，因为上面已经抛出异常
            }
        } catch (Exception e) {
            // 处理异常
            throw new RuntimeException("获取GitHub Token失败！", e);
        }
    }

    public Map<String, Object> getThirdUserInfo(String token) {
        String url = "https://api.github.com/user";
        try {
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("GET");

            // 设置请求头
            conn.setRequestProperty("Authorization", "token " + token); // 使用 "token" 而非 "Bearer"
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("User-Agent", "Your-App-Name"); // 替换为您的应用名称

            // 获取响应码
            int responseCode = conn.getResponseCode();
            InputStream is = (responseCode >= 200 && responseCode < 300) ? conn.getInputStream() : conn.getErrorStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            // 解析响应
            String responseJson = response.toString();
            Map<String, Object> responseMap = JSONUtil.parseToMap(responseJson);

            // 检查 "id" 字段是否存在
            Object id = responseMap.get("id");
            if (id == null) {
                // 可以根据需要抛出异常或返回特定错误信息
                throw new RuntimeException("获取用户信息失败，未找到id字段。");
            }

            // 构建第三方用户信息
            HashMap<String, Object> thirdUser = new HashMap<>();
            thirdUser.put("openId", id);
            thirdUser.put("nickname", responseMap.get("login")); // GitHub 的用户名字段是 "login"
            // 可选：添加更多字段，例如 "name" 或 "email"
            thirdUser.put("name", responseMap.get("name")); // 用户的真实姓名
            thirdUser.put("email", responseMap.get("email")); // 用户的邮箱（如果公开）

            return thirdUser;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取GitHub用户信息失败！", e);
        }
    }

}
