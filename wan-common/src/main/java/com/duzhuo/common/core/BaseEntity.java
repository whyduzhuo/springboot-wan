package com.duzhuo.common.core;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.apache.lucene.document.DateTools;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: wanhy
 * @date: 2020/1/1 16:58
 */
@EntityListeners(EntityListener.class)
@MappedSuperclass
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class BaseEntity implements Serializable{

    private static final long serialVersionUID = -67188388306700736L;
    public static final String ID_PROPERTY_NAME = "id";
    public static final String CREATE_DATE_PROPERTY_NAME = "createDate";
    public static final String MODIFY_DATE_PROPERTY_NAME = "modifyDate";

    private Long id;
    private Date createDate;
    private Date modifyDate;

    @JsonProperty
    @ApiModelProperty(value = "主键",example = "100")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty
    @ApiModelProperty(value = "创建时间")
    @Column(nullable = false)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @ApiModelProperty(value = "修改时间")
    @JsonProperty
    @Column(nullable = false)
    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!BaseEntity.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        BaseEntity other = (BaseEntity) obj;
        return getId() != null ? getId().equals(other.getId()) : false;
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode += null == getId() ? 0 : getId().hashCode() * 31;
        return hashCode;
    }


}
