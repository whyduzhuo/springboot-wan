package com.duzhuo.common.utils.IO;

import com.deepoove.poi.XWPFTemplate;
import com.duzhuo.common.utils.Tools;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/12/11 16:47
 */
@Slf4j
public class DocUtils {

    /**
     * worldMap转File并保存到本地
     * @param filePath 文件路径 ege: D:/wan/upload/tzz/haha.doc
     * @param templeteFile 模板文件
     * @param map
     * @return
     */
    public static File wordMapToFile(String filePath, File templeteFile, Map<String, Object> map) throws IOException {
        if (!Tools.vaildeParam(map)) {
            map = new HashMap<String, Object>() {{
                put("title", "Poi-tl 模板引擎");
            }};
        }
        //获得替换参数之后的docx文件
        File file = new File(filePath);
        if (!file.getParentFile().exists()){
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                log.error("文件创建失败",e);
            }
        }
        if (templeteFile==null || !templeteFile.exists()){
            throw new FileNotFoundException("模板文件不存在！");
        }
        XWPFTemplate template = XWPFTemplate.compile(templeteFile);
        if (template==null){
            throw new FileNotFoundException("模板不存在！");
        }
        template.render(map);
        try {

            OutputStream os = new FileOutputStream(file);
            template.write(os);
            template.close();
        } catch (FileNotFoundException e) {
            log.error("文件不存在！");
            throw e;
        } catch (IOException e) {
            log.error("文件保存失败！");
            throw e;
        }
        return file;
    }
}
