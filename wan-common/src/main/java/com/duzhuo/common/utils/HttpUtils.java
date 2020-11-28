package com.duzhuo.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author: wanhy
 * @date: 2020/8/18 9:47
 */
@Slf4j
public class HttpUtils {

    public static String get(String httpUrl,String charset) {
        StringBuilder inputLine = new StringBuilder("");
        try {
            URL url = new URL(httpUrl);
            URLConnection rulConnection = url.openConnection();
            HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
            httpUrlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream(), charset));
            String line;
            while((line = reader.readLine()) != null){
                inputLine .append(line);
            }
        }catch (MalformedURLException e){
            log.error(e.getMessage());
        }catch (IOException e){
            log.error(e.getMessage());
        }
        return  inputLine.toString();
    }

    public static String get(String httpUrl) {
        return get(httpUrl,"UTF-8");
    }
}
