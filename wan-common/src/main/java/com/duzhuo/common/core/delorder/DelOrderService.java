package com.duzhuo.common.core.delorder;

import com.duzhuo.common.core.Filter;
import com.duzhuo.common.core.base.BaseEntity;
import com.duzhuo.common.core.del.DeleteEntity;
import com.duzhuo.common.core.del.DeleteService;
import com.duzhuo.common.core.order.OrderEntity;
import com.duzhuo.common.exception.ServiceException;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/12/4 10:34
 */
@Transactional(rollbackFor = Exception.class)
public class DelOrderService<T extends DelOrderEntity, ID extends Serializable> extends DeleteService<T,ID> {
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
        T uper = getUper(o,t.getDelTime());
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
        T downer = getDowner(o,t.getDelTime());
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
        return getMaxOrder(null,0L);
    }

    /**
     * 小于max的最大值
     * @param max
     * @return
     */
    public Integer getMaxOrder(Integer max,Long delTime){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        // 查询结果
        CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);
        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        // 在哪个表查
        Root<T> root = cq.from(tClass);
        List<Predicate> predicates = new ArrayList<>();
        if (max!=null){
            predicates.add(cb.lessThan(root.get(OrderEntity.ORDER_PROPERTY_NAME),max));
        }
        if (delTime==0L){
            predicates.add(cb.equal(root.get(DeleteEntity.DEL_TIME_PROPERTY_NAME),0L));
        }else {
            predicates.add(cb.notEqual(root.get(DeleteEntity.DEL_TIME_PROPERTY_NAME),0L));
        }
        Predicate[] predicateArr = new Predicate[predicates.size()];
        cq.where(predicates.toArray(predicateArr));
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
        return getMinOrder(null,0L);
    }

    /**
     * 大于某数的最小排序
     * @return
     */
    public Integer getMinOrder(Integer min,Long delTime){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        // 查询结果
        CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);
        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        // 在哪个表查
        Root<T> root = cq.from(tClass);
        List<Predicate> predicates = new ArrayList<>();
        if (min!=null){
            predicates.add(cb.greaterThan(root.get(OrderEntity.ORDER_PROPERTY_NAME),min));
        }
        if (delTime==0L){
            predicates.add(cb.equal(root.get(DeleteEntity.DEL_TIME_PROPERTY_NAME),0L));
        }else {
            predicates.add(cb.notEqual(root.get(DeleteEntity.DEL_TIME_PROPERTY_NAME),0L));
        }
        Predicate[] predicateArr = new Predicate[predicates.size()];
        cq.where(predicates.toArray(predicateArr));
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
    public <T> T getUper(Integer order,Long delTime){
        Integer o = getMaxOrder(order,delTime);
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
    public <T> T getDowner(Integer order,Long delTime){
        Integer o = getMinOrder(order,delTime);
        if (o==null){
            return null;
        }
        List<Filter> filters = new ArrayList<>();
        filters.add(Filter.eq(OrderEntity.ORDER_PROPERTY_NAME,o));
        if (delTime==0L){
            filters.add(Filter.eq(DeleteEntity.DEL_TIME_PROPERTY_NAME,0L));
        }else {
            filters.add(Filter.ne(DeleteEntity.DEL_TIME_PROPERTY_NAME,0L));
        }
        List<T> list = (List<T>) super.searchList(filters, Sort.by(Sort.Direction.ASC, BaseEntity.CREATE_DATE_PROPERTY_NAME));
        return list.get(0);
    }
}
