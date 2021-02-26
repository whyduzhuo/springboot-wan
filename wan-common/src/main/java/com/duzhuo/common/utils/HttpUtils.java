package com.duzhuo.common.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/8/18 9:47
 */
@Slf4j
public class HttpUtils {
    private static final String ALLOWED_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.!~*'()";

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

    public static JSONObject doGetJson(String url) throws Exception, IOException {
        JSONObject jsonObject=null;
        //初始化httpClient
        DefaultHttpClient client=new DefaultHttpClient();
        //用Get方式进行提交
        HttpGet httpGet=new HttpGet(url);
        //发送请求
        HttpResponse response= client.execute(httpGet);
        //获取数据
        HttpEntity entity=response.getEntity();
        //格式转换
        if (entity!=null) {
            String result= EntityUtils.toString(entity,"UTF-8");
            jsonObject=JSONObject.parseObject(result);
        }
        //释放链接
        httpGet.releaseConnection();
        return jsonObject;
    }

    public static <T> T getForObject(String url,Class<T> clazz) throws Exception {
        JSONObject jsonObject = HttpUtils.doGetJson(url);
        return jsonObject.toJavaObject(clazz);
    }

    public static String encodeURIComponent(String input) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(input)) {
            return input;
        }

        int l = input.length();
        StringBuilder o = new StringBuilder(l * 3);
        for (int i = 0; i < l; i++) {
            String e = input.substring(i, i + 1);
            if (!ALLOWED_CHARS.contains(e)) {
                byte[] b = e.getBytes(StandardCharsets.UTF_8);
                o.append(getHex(b));
                continue;
            }
            o.append(e);
        }
        return o.toString();
    }

    private static String getHex(byte[] buf) {
        StringBuilder o = new StringBuilder(buf.length * 3);
        for (byte aBuf : buf) {
            int n = aBuf & 0xff;
            o.append("%");
            if (n < 0x10) {
                o.append("0");
            }
            o.append(Long.toString(n, 16).toUpperCase());
        }
        return o.toString();
    }

    public static String httpPostMethod(String url,
                                        Map<String, String> headerMap, Map<String, String> params)
            throws IOException {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        Headers headers = Headers.of(headerMap);
        params.keySet().stream().forEach(key -> {
            formBody.add(key, params.get(key));
        });
        Request request = new Request.Builder().url(url).headers(headers)
                .post(formBody.build()).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static Map<String,Object> httpPostMethod(String url, Map<String, String> headers,
                                        String requestBodys) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType
                .parse(headers.get("Content-Type") == null ? "application/json"
                        : headers.get("Content-Type"));
        RequestBody requestBody = RequestBody.create(mediaType, requestBodys);
        Headers headerMap = okhttp3.Headers.of(headers);
        Request request = new Request.Builder().url(url).headers(headerMap)
                .post(requestBody).build();
        Response response = client.newCall(request).execute();
        String result =  response.body().string();
        return JSONObject.parseObject(result,Map.class);
    }
}
