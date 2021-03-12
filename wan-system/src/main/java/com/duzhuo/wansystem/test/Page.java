package com.duzhuo.wansystem.test;

import com.duzhuo.wansystem.entity.Jubao;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2021/3/12 15:46
 */
@Getter
@Setter
public class Page {
    String total;
    List<Jubao> list;
}
