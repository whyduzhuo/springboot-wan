package com.duzhuo.common.utils.IO;

import com.duzhuo.common.enums.FileType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * 判断文件类型
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/12/23 11:49
 */

@Slf4j
public  class FileTypeJudge {

    /**
     * 将文件头转换成16进制字符串
     *
     * @param src 原生byte
     * @return 16进制字符串
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 得到文件头
     *
     * @param is 文件路径
     * @return 文件头
     * @throws IOException
     */
    private static String getFileContent(InputStream is) throws IOException {
        byte[] b = new byte[28];
        try {
            is.read(b, 0, 28);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
        return bytesToHexString(b);
    }

    /**
     * 获取文件类型类
     *
     * @param is 文件输入流
     * @return 文件类型
     */
    public static FileType getType(InputStream is) throws IOException {
        String fileHead = getFileContent(is);
        if (fileHead == null || fileHead.length() == 0) {
            return null;
        }
        fileHead = fileHead.toUpperCase();
        FileType[] fileTypes = FileType.values();
        for (FileType type : fileTypes) {
            if (fileHead.startsWith(type.getValue())) {
                return type;
            }
        }
        return null;
    }

    /**
     * 获取文件类型
     *
     * @param is
     * @return
     * @throws Exception
     */
    public static String getFileType(InputStream is) throws Exception {
        FileType fileType = getType(is);
        if (fileType != null) {
            return fileType.getValue();
        }
        return null;
    }

}