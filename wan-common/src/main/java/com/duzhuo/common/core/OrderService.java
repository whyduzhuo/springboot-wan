package com.duzhuo.common.core;

import com.duzhuo.common.exception.ServiceException;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/1 16:58
 */
@Transactional(rollbackFor = Exception.class)
public class OrderService<T extends OrderEntity, ID extends Serializable> extends BaseService<T,ID>{
    @Resource
    protected EntityManager entityManager;

    /**
     * 排序 -- 向上进1
     * @param id
     * @return
     */
    public void up(ID id) {
         T t = super.find(id);
        Integer o = t.getOrder();
        T uper = getUper(o);
        if (uper==null){
            throw new ServiceException("已经到顶了!");
        }
        t.setOrder(uper.getOrder());
        uper.setOrder(o);
        super.update(uper);
        super.update(t);
    }

    /**
     * 排序 -- 向下退1
     * @param id
     * @return
     */
    public void down(ID id) {
        T t = super.find(id);
        Integer o = t.getOrder();
        T downer = getDowner(o);
        if (downer==null){
            throw new ServiceException("已经到底了!");
        }
        t.setOrder(downer.getOrder());
        downer.setOrder(o);
        super.update(downer);
        super.update(t);
    }

    /**
     * 查询最大排序
     * @return
     */
    public Integer getMaxOrder() {
        return getMaxOrder(null);
    }

    /**
     * 小于max的最大值
     * @param max
     * @return
     */
    public Integer getMaxOrder(Integer max){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        // 查询结果
        CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);
        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        // 在哪个表查
        Root<T> root = cq.from(tClass);
        if (max!=null){
            cq.where(cb.lessThan(root.get(OrderEntity.ORDER_PROPERTY_NAME),max));
        }
        cq.select(cb.greatest((Path)root.get(OrderEntity.ORDER_PROPERTY_NAME)));
        // 结果
        TypedQuery<Integer> typedQuery = entityManager.createQuery(cq);
        Integer i = typedQuery.getSingleResult();
        return i;
    }

    /**
     * 查询最大排序
     * @return
     */
    public Integer getMinOrder() {
        return getMinOrder(null);
    }

    /**
     * 大于某数的最小排序
     * @return
     */
    public Integer getMinOrder(Integer min){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        // 查询结果
        CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);
        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        // 在哪个表查
        Root<T> root = cq.from(tClass);
        if (min!=null){
            cq.where(cb.greaterThan(root.get(OrderEntity.ORDER_PROPERTY_NAME),min));
        }
        cq.select(cb.least((Path)root.get(OrderEntity.ORDER_PROPERTY_NAME)));
        // 结果
        TypedQuery<Integer> typedQuery = entityManager.createQuery(cq);
        Integer i = typedQuery.getSingleResult();
        return i;
    }

    /**
     * 获取某个排序的上一个实体
     * @return
     */
    public <T> T getUper(Integer order){
        Integer o = getMaxOrder(order);
        if (o==null){
            return null;
        }
        List<Filter> filters = new ArrayList<>();
        filters.add(Filter.eq(OrderEntity.ORDER_PROPERTY_NAME,o));
        List<T> list = (List<T>) super.searchList(filters, Sort.by(Sort.Direction.ASC, BaseEntity.CREATE_DATE_PROPERTY_NAME));
        return list.get(0);
    }

    /**
     * 获取某个排序的上一个实体
     * @return
     */
    public <T> T getDowner(Integer order){
        Integer o = getMinOrder(order);
        if (o==null){
            return null;
        }
        List<Filter> filters = new ArrayList<>();
        filters.add(Filter.eq(OrderEntity.ORDER_PROPERTY_NAME,o));
        List<T> list = (List<T>) super.searchList(filters, Sort.by(Sort.Direction.ASC, BaseEntity.CREATE_DATE_PROPERTY_NAME));
        return list.get(0);
    }

}
