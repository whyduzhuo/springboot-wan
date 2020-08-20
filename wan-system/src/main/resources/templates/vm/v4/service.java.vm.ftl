package ${data.servicepackage};

import ${data.entityPackages};
import org.springframework.stereotype.Service;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.core.BaseService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * ${data.module}--Service
 * @author: ${data.author}
 * @date: ${data.createDateStr}
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ${data.entityName}Service extends BaseService<${data.entityName}, Long>{

    @Resource
    private ${data.entityName}Dao ${data.lowEntityName}Dao;

    @Resource
    public void setBaseDao(${data.entityName}Dao ${data.lowEntityName}Dao){
        super.setBaseDao(${data.lowEntityName}Dao);
    }

    /**
     * ${data.module}--数据校验
     * @param ${data.lowEntityName}VO
     */
    private void check(${data.entityName} ${data.lowEntityName}VO){
        throw new ServiceException("功能暂未完成！");
    }

    /**
     * ${data.module}--新增
     * @param ${data.lowEntityName}VO
     * @return
     */
    public Message addData(${data.entityName} ${data.lowEntityName}VO) {
        this.check(${data.lowEntityName}VO);
        super.save(${data.lowEntityName}VO);
        return Message.success("添加成功!");
    }


    /**
     * ${data.module}--修改
     * @param ${data.lowEntityName}VO
     * @return
     */
    public Message edit(${data.entityName} ${data.lowEntityName}VO) {
        this.check(${data.lowEntityName}VO);
        ${data.entityName} ${data.lowEntityName} = super.find(${data.lowEntityName}VO.getId());
        super.update(${data.lowEntityName});
        return Message.success("修改成功!");
    }

    /**
     * ${data.module}--删除
     * @param id
     * @return
     */
    public Message del(Long id) {
        if (id==null){
            throw new ServiceException("请选择数据！");
        }
        super.delete(id);
        return Message.success("删除成功！");
    }
}
