package com.duzhuo.common.core;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

/**
 * 删除标志
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/11/9 17:49
 */

@MappedSuperclass
@Getter
@Setter
public  class DeleteEntity extends BaseEntity{
    private static final long serialVersionUID = 7976667564090073247L;
    /**
     * 删除标志，0未未删除
     * 已删除=删除时间
     */
    private Long delTime=0L;

}
