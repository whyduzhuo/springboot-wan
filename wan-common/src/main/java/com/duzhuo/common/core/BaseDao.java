package com.duzhuo.common.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author: wanhy
 * @date: 2020/1/1 16:58
 */
public interface BaseDao<T extends BaseEntity, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

}
