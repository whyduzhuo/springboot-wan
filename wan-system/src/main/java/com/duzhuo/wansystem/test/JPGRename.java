package com.duzhuo.wansystem.test;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: wanhy
 * @date: 2020/9/10 14:18
 */

public class JPGRename {
    public static void main(String[] args){
        List<String> stringList = new ArrayList(20);
        stringList.parallelStream().forEach(r->{
            System.err.println(Thread.currentThread());
        });
    }
}
