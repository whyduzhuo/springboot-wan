package com.duzhuo.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class HttpUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static String get(String httpUrl){
        StringBuilder inputLine = new StringBuilder("");
        try {
            URL url = new URL(httpUrl);
            URLConnection rulConnection = url.openConnection();
            HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
            httpUrlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream(), "UTF-8"));
            String line;
            while((line = reader.readLine()) != null){
                inputLine .append(line);
            }
        }catch (MalformedURLException e){
            logger.error(e.getMessage());
        }catch (IOException e){
            logger.error(e.getMessage());
        }
        return inputLine.toString();
    }
}
