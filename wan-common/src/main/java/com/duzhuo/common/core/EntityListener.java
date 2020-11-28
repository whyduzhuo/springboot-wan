
package com.duzhuo.common.core;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * @author 万宏远
 * @email: 1434495271@qq.com
 * @date 2020-01-05
 */
public class EntityListener {

	@PrePersist
	public void prePersist(BaseEntity entity) {
		if(null == entity.getCreateDate()){
			entity.setCreateDate(new Date());
		}
		if(null == entity.getModifyDate()){
			entity.setModifyDate(new Date());
		}
	}

	@PreUpdate
	public void preUpdate(BaseEntity entity) {
		entity.setModifyDate(new Date());
	}

}