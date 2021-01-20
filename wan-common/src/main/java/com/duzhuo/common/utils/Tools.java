package com.duzhuo.common.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/4 11:53
 */

public class Tools {
    /**
     * @param
     * @Description: 参数验证
     */
    public static boolean vaildeParam(Object... args) {
        for (int i = 0; i < args.length; i++) {
            if (null == args[i]) {
                return false;
            } else {
                if (args[i].getClass().equals(String.class)) {
                    if ("".equals(args[i])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 获取文件MD5值
     * @param file
     */
    public static String getMD5(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        InputStream inputStream = file.getInputStream();
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inputStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return getMD5(in2b);
    }

    /**
     * 获取文件MD5值
     * @param file
     */
    public static String getMD5(File file) throws IOException, NoSuchAlgorithmException {
        return getMD5(FileUtils.readFileToByteArray(file));
    }

    public static String getMD5(byte[] bytes) throws NoSuchAlgorithmException {
        StringBuilder sb = new StringBuilder("");
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(bytes);
        byte b[] = md.digest();
        int d;
        for (int i = 0; i < b.length; i++) {
            d = b[i];
            if (d < 0) {
                d = b[i] & 0xff;
                // 与上一行效果等同
                // i += 256;
            }
            if (d < 16) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(d));
        }
        return sb.toString();
    }

    /**
     * 获取随机位数的字符串
     * @param length 随机位数
     */
    public static String getRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            // 获取ascii码中的字符 数字48-57 小写65-90 大写97-122
            int range = random.nextInt(75)+48;
            range = range<97?(range<65?(range>57?114-range:range):(range>90?180-range:range)):range;
            sb.append((char)range);
        }
        return sb.toString();
    }

}
