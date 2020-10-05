package com.duzhuo.common.core;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author: wanhy
 * @date: 2020/1/1 16:58
 */
@Transactional(rollbackFor = Exception.class)
public class BaseService<T, ID extends Serializable> {

    private BaseDao<T, ID> baseDao;

    public void setBaseDao(BaseDao<T, ID> baseDao) {
        this.baseDao = baseDao;
    }

    @PersistenceContext
    protected EntityManager entityManager;

    public void clear() {
        entityManager.clear();
    }

    @Transactional(readOnly = true)
    public T find(ID id) {
        if (null == id) {
            return null;
        }
        return baseDao.findById(id).get();
    }

    /**
     * 保存
     *
     * @param entity
     * @return
     */
    protected T save(T entity) {
        return baseDao.save(entity);
    }


    /**
     * 删除
     *
     * @param entity
     */
    public void delete(T entity) {
        baseDao.delete(entity);
    }
    /**
     * 删除
     *
     * @param id
     */
    public void delete(ID id) {
        baseDao.deleteById(id);
    }

    /**
     * 批量删除
     * @param ids
     */
    protected void delete(ID... ids) {
        if (ids != null) {
            for (ID id : ids) {
                delete(id);
            }
        }
    }

    protected void delete(List<T> list){
        baseDao.deleteAll(list);
    }

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    protected T update(T entity) {
        return baseDao.save(entity);
    }

    @SuppressWarnings("unchecked")
    public ID getIdentifier(T entity) {
        Assert.notNull(entity,"entity can not be null");
        return (ID) entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
    }

    /**
     * 查找全部
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return baseDao.findAll();
    }

    /**
     * 通过id查找
     *
     * @param ids
     * @return
     */
    @Transactional(readOnly = true)
    public List<T> findAll(Iterable<ID> ids) {
        return baseDao.findAllById(ids);
    }


    /**
     * 通过id查找
     *
     * @param ids
     * @return
     */
    @Transactional(readOnly = true)
    public List<T> findList(ID... ids) {
        List<T> result = new ArrayList<>();
        if (ids != null) {
            for (ID id : ids) {
                T entity = find(id);
                if (entity != null) {
                    result.add(entity);
                }
            }
        }
        return result;
    }

    /**
     * 查找全部并排序
     *
     * @param sort
     * @return
     */
    @Transactional(readOnly = true)
    public List<T> findAll(Sort sort) {
        return baseDao.findAll(sort);
    }

    /**
     * 将实体和底层数据库进行同步
     */
    public void flush() {
        baseDao.flush();
    }

    /**
     * 计算数量
     *
     * @param spec
     * @return
     */
    @Transactional(readOnly = true)
    public long count(Specification<T> spec) {
        return baseDao.count(spec);
    }

    /**
     * 计算数量
     *
     * @param filters
     * @return
     */
    @Transactional(readOnly = true)
    public long count(List<Filter> filters) {
        Specification<T> spec = generareSpecification(filters, null);
        return baseDao.count(spec);
    }

    /**
     *
     * @param spec
     * @return
     */
    @Transactional(readOnly = true)
    public List<T> findAll(Specification<T> spec) {
        return baseDao.findAll(spec);
    }

    /**
     *
     * @param spec
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        return baseDao.findAll(spec, pageable);
    }

    /**
     *
     * @param spec
     * @param sort
     * @return
     */
    @Transactional(readOnly = true)
    public List<T> findAll(Specification<T> spec, Sort sort) {
        return baseDao.findAll(spec, sort);
    }

    /**
     *
     * @param spec
     * @return
     */
    @Transactional(readOnly = true)
    public T findOne(Specification<T> spec) {
        return baseDao.findOne(spec).get();
    }

    /**
     *
     * @param filters
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<T> search(List<Filter> filters, Pageable pageable) {
        Specification<T> spec = generareSpecification(filters, null);
        Page<T> page = findAll(spec, pageable);
        return page;
    }


    /**
     *
     * @param filters
     * @param pageNumber
     * @param pageSize
     * @param sort
     * @return
     */
    @Transactional(readOnly = true)
    public Page<T> search(List<Filter> filters, int pageNumber, int pageSize, Sort sort) {
        return search(filters, pageNumber, pageSize, sort, null);
    }

    /**
     *
     * @param filters
     * @param pageNumber
     * @param pageSize
     * @param sort
     * @param criteriaQuery
     * @return
     */
    public Page<T> search(List<Filter> filters, int pageNumber, int pageSize, Sort sort, CriteriaQuery<T> criteriaQuery) {
        Specification<T> spec = generareSpecification(filters, criteriaQuery);
        if (null == sort) {
            //默认创建时间排序
            sort = Sort.by(Sort.Direction.DESC, "createDate");
        }
        Page<T> page = findAll(spec, PageRequest.of(pageNumber - 1, pageSize, sort));
        return page;
    }

    /**
     *
     * @param searchParams
     * @param customSearch
     * @return
     */
    @Transactional(readOnly = true)
    public Page<T> search(Map<String, Object> searchParams, CustomSearch customSearch) {

        //构造排序列表
        List<Sort.Order> orderList = new ArrayList<>();
        if (StringUtils.isNotBlank(customSearch.getOrderProperty())) {
            orderList.add(new Sort.Order(customSearch.getOrderDirection(), customSearch.getOrderProperty()));
        }
        List<Sort.Order> orders = customSearch.getOrders();
        if (null != orders && orders.size() > 0) {
            orderList.addAll(orders);
        }
        orderList.add(new Sort.Order(Sort.Direction.DESC, "createDate"));

        /**
         * 排序
         */
        Sort sort = Sort.by(orderList);

        //搜索参数
        Map<String, Filter> filters = parseSearchParams(searchParams);
        List<Filter> searchParamsFilters = new ArrayList<>(filters.values());
        searchParamsFilters.addAll(customSearch.getFilters());

        return search(searchParamsFilters, customSearch.getPageNumber(), customSearch.getPageSize(), sort);
    }


    @Transactional(readOnly = true)
    public List<T> searchList(List<Filter> filters, Sort sort) {
        Specification<T> spec = generareSpecification(filters, null);
        if (null == sort) {
            //默认创建时间排序
            sort = Sort.by(Sort.Direction.ASC,BaseEntity.CREATE_DATE_PROPERTY_NAME);
        }
        return findAll(spec, sort);
    }


    /**
     * searchParams转Filter
     * @param searchParams
     * @return
     */
    public Map<String, Filter> parseSearchParams(Map<String, Object> searchParams) {
        Map<String, Filter> filters = Maps.newHashMap();
        if (null == searchParams) {
            searchParams = Maps.newHashMap();
        }

        for (Map.Entry<String, Object> entry : searchParams.entrySet()) {
            // 过滤掉空值
            String key = entry.getKey();
            Object value = entry.getValue();

            if (org.apache.commons.lang3.StringUtils.isNotEmpty(key)) {
                if (!key.contains("isNotNull") && !key.contains("isNull")) {
                    if (key.contains("in_") && (value instanceof Object[] || value instanceof Collection<?>)) {
                        // 拆分operator与filedAttribute
                        String[] names = org.apache.commons.lang3.StringUtils.split(key, "_");
                        if (names.length != 2) {
                            throw new IllegalArgumentException(key + " is not a valid search filter name");
                        }
                        String filedName = names[1];
                        Filter.Operator operator = Filter.Operator.valueOf(names[0]);

                        // 创建searchFilter
                        Filter filter = new Filter(filedName, operator, value);
                        filters.put(key, filter);
                        continue;
                    }
                    if (value instanceof String && org.apache.commons.lang3.StringUtils.isBlank((String) value)) {
                        continue;
                    }
                }
            }

            // 拆分operator与filedAttribute
            String[] names = org.apache.commons.lang3.StringUtils.split(key, "_");
            if (names.length != 2) {
                throw new IllegalArgumentException(key + " is not a valid search filter name");
            }
            String filedName = names[1];
            Filter.Operator operator = Filter.Operator.valueOf(names[0]);

            // 创建searchFilter
            Filter filter = new Filter(filedName, operator, value);
            filters.put(key, filter);
        }

        return filters;
    }

    /**
     * 将 Filter 转换成 Specification
     *
     * @param filters
     * @param <T>
     * @return
     */
    private <T> Specification<T> generareSpecification(final List<Filter> filters, final CriteriaQuery<T> criteriaQuery) {
        return (Specification<T>) (root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (null != criteriaQuery) {
                CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
                Predicate restrictions = criteriaQuery.getRestriction() != null ? criteriaQuery.getRestriction() : criteriaBuilder.conjunction();
                predicates.add(restrictions);
            }

            if (null != filters && filters.size() > 0) {

                for (Filter filter : filters) {
                    // nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
                    String[] names = StringUtils.split(filter.getProperty(), ".");
                    Path expression = root.get(names[0]);
                    for (int i = 1; i < names.length; i++) {
                        expression = expression.get(names[i]);
                    }
                    switch (filter.getOperator()) {
                        case eq:
                            predicates.add(builder.equal(expression, filter.getValue()));
                            break;
                        case has:
                            predicates.add(builder.isMember(filter.getValue(), expression));
                            break;
                        case notHas:
                            predicates.add(builder.isNotMember(filter.getValue(), expression));
                            break;
                        case isEmpty:
                            predicates.add(builder.isEmpty(expression));
                            break;
                        case isNotEmpty:
                            predicates.add(builder.isNotEmpty(expression));
                            break;
                        case like:
                            predicates.add(builder.like(expression, "%" + filter.getValue() + "%"));
                            break;
                        case parentlike:
                            predicates.add(builder.like(expression, filter.getValue() + "%"));
                            break;
                        case gt:
                            predicates.add(builder.greaterThan(expression, (Comparable) filter.getValue()));
                            break;
                        case lt:
                            predicates.add(builder.lessThan(expression, (Comparable) filter.getValue()));
                            break;
                        case ge:
                            predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.getValue()));
                            break;
                        case le:
                            predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.getValue()));
                            break;
                        case ne:
                            predicates.add(builder.notEqual(expression, filter.getValue()));
                            break;
                        case between:
                            Object[] values = (Object[]) filter.getValue();
                            predicates.add(builder.between(expression, (Comparable) values[0], (Comparable) values[1]));
                            break;
                        case in:
                            Object value = filter.getValue();

                            // 防止数组或集合传递给可变参时，当作单个参数，导致类型转换异常
                            if (value instanceof Object[]) {
                                predicates.add(expression.in((Object[]) value));
                            } else if (value instanceof Collection) {
                                predicates.add(expression.in(((Collection<?>) value).toArray()));
                            } else {
                                predicates.add(expression.in(value));
                            }
                            break;
                        case isNotNull:
                            predicates.add(expression.isNotNull());
                            break;
                        case isNull:
                            predicates.add(expression.isNull());
                            break;
                        case notIn:
                            Object val = filter.getValue();
                            // 防止数组或集合传递给可变参时，当作单个参数，导致类型转换异常
                            if (val instanceof Object[]) {
                                predicates.add(builder.not(expression.in((Object[]) val)));
                            } else if (val instanceof Collection) {
                                predicates.add(builder.not(expression.in(((Collection<?>) val).toArray())));
                            } else {
                                predicates.add(builder.not(expression.in(val)));
                            }
                            break;
                        default:
                            break;
                    }
                }

                // 将所有条件用 and 联合起来
                if (predicates.size() > 0) {
                    return builder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            }

            return builder.conjunction();
        };
    }

}
