package com.duzhuo.wansystem.test;

import java.io.*;
import java.util.Arrays;

/**
 * 文件加密
 * @author: wanhy
 * @date: 2020/8/10 14:10
 */

public class FileNick {

    public static final byte PASSWORD = 26;

    public static void main(String[] args){
        File dic = new File("D:\\360安全浏览器下载\\桃谷");
        File[] files = dic.listFiles();
        System.out.println("一共："+files.length);
        Arrays.stream(files).forEach(FileNick::change);
        System.out.println("结束");
    }

    public static void change(File file){
        System.err.println(file.getAbsolutePath());
        if (file.isDirectory()){
            return;
        }
        String newFieName =  nameNick(file,PASSWORD);
        File newFile = new File(newFieName);
        if (!newFile.exists()){
            try {
                copy(file,newFile,PASSWORD);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static String nameNick(File file, byte passworld){
        String oldName = file.getName();
        String dic = file.getParentFile().getAbsolutePath();
        return dic+"\\26\\"+oldName;
    }

    /**
     * 加密复制
     * @param oldFile
     * @param newFile
     * @param password
     */
    public static void copy(File oldFile,File newFile,byte password) throws IOException {
        try (InputStream in = new FileInputStream(oldFile);OutputStream out = new FileOutputStream(newFile)){
            byte[] buffer = new byte[1024*1024];
            int len;
            while ((len = in.read(buffer)) > 0) {
                out.write(jiami(buffer,password), 0, len);
            }
        }
    }

    /**
     * 对字节数组加密
     * @param buffer
     * @param password
     * @return
     */
    public static byte[] jiami(byte[] buffer,byte password){
        byte[] bytes = new byte[buffer.length];
        for (int i =0;i<buffer.length;i++){
            byte b = buffer[i];
            byte b2 = (byte) (b^password);
            bytes[i] = b2;
        }
        return bytes;
    }
}
