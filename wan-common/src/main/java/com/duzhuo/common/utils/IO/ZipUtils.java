package com.duzhuo.common.utils.IO;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/12/11 16:35
 */
@Slf4j
public class ZipUtils {

    /**
     * 文件打包压缩并下载
     * @param request
     * @param response
     * @param fileList
     * @param zipName
     */
    public static void zipPackageDownload(HttpServletRequest request, HttpServletResponse response, List<File> fileList,String zipName){
        //响应头的设置
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");

        //设置压缩包的名字
        //解决不同浏览器压缩包名字含有中文时乱码的问题
        String downloadName = zipName + ".zip";
        //返回客户端浏览器的版本号、类型
        String agent = request.getHeader("USER-AGENT");
        try {
            //针对IE或者以IE为内核的浏览器：
            if (agent.contains("MSIE") || agent.contains("Trident")) {
                downloadName = java.net.URLEncoder.encode(downloadName, "UTF-8");
            } else {
                //非IE浏览器的处理：
                downloadName = new String(downloadName.getBytes("UTF-8"), "ISO-8859-1");
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        response.setHeader("Content-Disposition", "attachment;fileName=\"" + downloadName + "\"");

        //设置压缩流：直接写入response，实现边压缩边下载
        ZipOutputStream zipos = null;
        try {
            zipos = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()));
            //设置压缩方法
            zipos.setMethod(ZipOutputStream.DEFLATED);
            zipos.setEncoding("GBK");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        //循环将文件写入压缩流
        DataOutputStream os = null;
        //从数据库中取出要下载的图片路径、并循环写入压缩
        for (File file : fileList) {
            if (file.exists()) {
                try {
                    //添加ZipEntry，并ZipEntry中写入文件流
                    //这里，加上i是防止要下载的文件有重名的导致下载失败
                    zipos.putNextEntry(new ZipEntry(file.getName()));
                    os = new DataOutputStream(zipos);
                    InputStream is = new FileInputStream(file);
                    byte[] b = new byte[8192];
                    int length = 0;
                    while ((length = is.read(b)) != -1) {
                        os.write(b, 0, length);
                    }
                    is.close();
                    zipos.closeEntry();
                } catch (IOException e) {
                    log.error(e.getMessage(),e);
                }
            }
        }
        //关闭流
        try {
            if (os != null) {
                os.flush();
                os.close();
            }
            if (zipos != null) {
                zipos.close();
            }
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
    }


    /**
     * 文件解压
     * @param zipFile 压缩包
     * @param path 解压后存放的文件夹
     * @return
     */
    public static List<File> zipUnpackage(File zipFile, String path) throws IOException {
        ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFile));
        BufferedInputStream bin = new BufferedInputStream(zin);
        List<File> list = new ArrayList<>();
        java.util.zip.ZipEntry entry;
        while((entry = zin.getNextEntry())!=null){
            File file = new File(path,entry.getName());
            file.getParentFile().mkdirs();
            FileOutputStream out=new FileOutputStream(file);
            BufferedOutputStream bout=new BufferedOutputStream(out);
            int b;
            while((b=bin.read())!=-1){
                bout.write(b);
            }
            bout.close();
            out.close();
            //保存图片名
            if(entry.getName() != null && !"".equals(entry.getName())){
                list.add(file);
            }
        }
        bin.close();
        zin.close();
        return list;
    }
}
