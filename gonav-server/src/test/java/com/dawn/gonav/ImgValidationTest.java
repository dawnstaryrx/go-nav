package com.dawn.gonav;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class ImgValidationTest {
    public static void main(String[] args) {
        String imageUrl = "https://gooodh.com/11222.png";
        boolean isValid = isValidImage(imageUrl);
        System.out.println("Image URL is valid: " + isValid);
    }
    public static boolean isValidImage(String imageUrl) {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse response = httpClient.execute(new HttpGet(imageUrl));
            int statusCode = response.getStatusLine().getStatusCode();
            return statusCode == 200;
        } catch (Exception e) {
            return false;
        }
    }
}
