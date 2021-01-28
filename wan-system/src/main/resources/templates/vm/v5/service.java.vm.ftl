package ${data.servicepackage};

import com.jxyunge.exception.BusinessException;
import ${data.entityPackages};
import ${data.daopackage}.${data.entityName}Mapper;
import org.springframework.stereotype.Service;
import com.jxyunge.mybatis.service.BaseService;
import com.jxyunge.exception.BusinessException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * ${data.module}--Service
 * @author: ${data.author}
 * @date: ${data.createDateStr}
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ${data.entityName}Service extends BaseService<${data.entityName}Mapper, ${data.entityName}>{

    /**
     *
     * @param page
     * @param searchParams
     * @return
     */
    public IPage<${data.entityName}Dto> pageDto(IPage<${data.entityName}Dto> page, Map<String, Object> searchParams) {
        return baseMapper.pageDto(page, searchParams);
    }

    /**
    *
    * @param id
    * @return
    */
    public ${data.entityName}Dto findDtoById(Long id){
        return baseMapper.findDtoById(id);
    }

    /**
    *
    * @param searchParams
    * @return
    */
    public List<${data.entityName}Dto> ListDto(Map<String, Object> searchParams) {
        return baseMapper.pageDto(searchParams);
    }

    /**
     * ${data.module}--数据校验
     * @param ${data.lowEntityName}VO
     */
    private void check(${data.entityName} ${data.lowEntityName}VO){
        throw new BusinessException("功能暂未完成！");
    }

    /**
     * ${data.module}--新增
     * @param ${data.lowEntityName}VO
     * @return
     */
    public boolean add(${data.entityName} ${data.lowEntityName}VO) {
        super.validation(${data.lowEntityName}VO);
        this.check(${data.lowEntityName}VO);
        return super.save(${data.lowEntityName}VO);
    }


    /**
     * ${data.module}--修改
     * @param ${data.lowEntityName}VO
     * @return
     */
    public boolean edit(${data.entityName} ${data.lowEntityName}VO) {
        super.validation(${data.lowEntityName}VO);
        this.check(${data.lowEntityName}VO);
        ${data.entityName} ${data.lowEntityName} = super.getById(${data.lowEntityName}VO.getId());

        return super.update(${data.lowEntityName});
    }

    /**
     * ${data.module}--删除
     * @param id
     * @return
     */
    public void del(Long id) {
        if (id==null){
            throw new BusinessException("请选择数据！");
        }
        super.delete(id);
    }

    /**
     * ${data.module}--批量删除
     * @param ids
     * @return
     */
    public void del(Long... ids) {
        if (ids==null || ids.length==0){
            throw new BusinessException("请勾选数据！");
        }
        Arrays.stream(ids).forEach(this::del);
    }

    /**
    * ${data.module}--下载导入模板
    * @param ids
    * @return
    */
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        String fileName = "${data.module}导入模板";
        //首行固定表头
        List<String> titleList = new ArrayList<>();
        titleList.add("字段1");
        titleList.add("字段2");
        titleList.add("字段3");
        List<List<String>> parpamtsList = new ArrayList<>(titleList.size());
        ExcelCommonUtil.exportExcelTemplet(fileName, titleList, parpamtsList, response);
    }

    /**
    * ${data.module}--数据导入
    * @param ids
    * @return
    */
    public ApproveResult importData(MultipartFile file, Boolean isUpload) throws IOException {
        List<Map<String,String>> dataListStr = ExcelCommonUtil.readExelDataMap(file,1,2,50,"字段1,字段2,字段3");
        ApproveResult approveResult = new ApproveResult(isUpload?"导入":"检查");
        for (int i = 0;i<dataListStr.size();i++){
            try {
                ${data.entityName} ${data.lowEntityName} = this.mapToEntity(dataListStr.get(i));
                if (isUpload) {
                    this.add(${data.lowEntityName});
                } else {
                    this.check(${data.lowEntityName});
                }
                approveResult.addSuccess();
            }catch (BusinessException e){
                approveResult.addFailed("第"+(i+2)+"行"+(isUpload?"导入":"检查")+"失败:"+e.getMessage());
            }
        }
        return approveResult;
    }

    /**
    * 字段1,字段2,字段3
    * @param map
    * @return
    */
    private ${data.entityName} mapToEntity(Map<String, String> map) {
        ${data.entityName} ${data.lowEntityName} = new ${data.entityName}();

        return ${data.lowEntityName};
    }
}
