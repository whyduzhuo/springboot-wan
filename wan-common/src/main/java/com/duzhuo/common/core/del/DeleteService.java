package com.duzhuo.common.core.del;

import com.duzhuo.common.core.base.BaseService;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/1 16:58
 */
@Transactional(rollbackFor = Exception.class)
public class DeleteService<T extends DeleteEntity, ID extends Serializable> extends BaseService<T,ID> {

    /**
     * 假删除
     *
     * @param entity
     */
    @Override
    public void delete(T entity) {
        entity.setDelTime(System.currentTimeMillis());
        super.update(entity);
    }

    /**
     * 假删除
     *
     * @param id
     */
    @Override
    public void delete(ID id){
        this.delete(super.find(id));
    }

    /**
     * 批量假删除
     * @param ids
     */
    @Override
    protected void delete(ID... ids) {
        if (ids != null) {
            Arrays.stream(ids).forEach(this::delete);
        }
    }

    @Override
    protected void delete(List<T> list){
        list.forEach(this::delete);
    }

    /**
     * 恢复
     * @param entity
     */
    public void recovery(T entity) {
        entity.setDelTime(0L);
        super.update(entity);
    }

    public void openClose(T entity){
        if (entity.getDelTime()==0L){
            this.delete(entity);
        }else {
            this.recovery(entity);
        }
    }

}
