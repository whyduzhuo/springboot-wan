package com.duzhuo.wansystem.entity.house;

import com.duzhuo.common.annotation.Unique;
import com.duzhuo.common.annotation.UniqueColumn;
import com.duzhuo.common.core.base.BaseEntity;
import com.duzhuo.wansystem.service.house.ExcelDemoService;
import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/12/16 17:56
 */
@Getter
@Setter
@Entity
@ApiModel(value = "用户")
@Table(name = "T_EXCEL_DEMO")
@EqualsAndHashCode(callSuper = true,exclude = "roleSet")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
@Unique(service = ExcelDemoService.class,message = "姓名重复",uniqueColumns = @UniqueColumn("name"))
public class ExcelDemo extends BaseEntity{

    private static final long serialVersionUID = 7772140120958145383L;
    @NotBlank(message = "姓名不可为空！")
    @Length(max=15,min = 2,message = "姓名长度2-15字符")
    private String name;

    @NotBlank(message = "性别")
    private String sex;

    @NotBlank(message = "民族不可为空！")
    private String mz;

    @NotBlank(message = "学院不可为空！")
    private String org;

}
