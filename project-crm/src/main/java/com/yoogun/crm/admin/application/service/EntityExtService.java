/*
 * CustomerExtService.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.application.service;

import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthService;
import com.yoogun.core.application.dto.Page;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.core.infrastructure.exception.BusinessException;
import com.yoogun.crm.admin.application.vo.EntityExtVo;
import com.yoogun.crm.admin.domain.model.EntityExt;
import com.yoogun.crm.admin.repository.EntityExtDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;



/**
 * 客户附加信息-应用业务<br/>
 * 主要业务逻辑代码
 * @author Wang Chong at 2018-03-22 10:13:42
 * @since v1.0.0
 */
@Service
public class EntityExtService extends BaseAuthService<EntityExt> {

	@Resource
	private EntityExtDao dao;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<EntityExt> pageSearch(EntityExtVo vo) {
		SQL sql = new SQL().SELECT("*").FROM(tableName);
		sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
		sql.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'");
		
		//过滤条件
		if (vo != null && StringUtils.isNotBlank(vo.getEntityId())){
			sql.WHERE("ENTITY_ID = '" + vo.getEntityId() + "'");
		}
		
		//模糊查询,增加了AND，放在条件最后。否则后续条件还需要先加AND
		if(StringUtils.isNotBlank(vo.getSearch())) {
			sql.AND();
			sql.WHERE("XXX like '%" + vo.getSearch() + "%' " +
			"OR XXX like '%" + vo.getSearch() + "%' ");
		}
		
		//排序
		if(StringUtils.isNotBlank(vo.getSort()) && StringUtils.isNotBlank(vo.getOrder())) {
			try {
				sql.ORDER_BY(this.entityClass.newInstance().getColName(vo.getSort()) + " " + vo.getOrder());
			} catch (Exception e) {
				throw new BusinessException("获取'" + this.entityClass + "'的实例出错！", e);
			}
		} else {
			//默认排序
			sql.ORDER_BY("EXT_ID");
		}
		
		return this.pageSearch(sql, vo.getOffset(), vo.getLimit());
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void create(List<EntityExt> entityExts){
		if (entityExts != null && entityExts.size() > 0){
			for (EntityExt entityExt : entityExts){
				this.beforeCreate(entityExt);
				dao.insert(entityExt);
			}
		}
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void modify(List<EntityExt> entityExts){
		if (entityExts != null && entityExts.size() > 0){
			for (EntityExt entityExt : entityExts){
				this.beforeModify(entityExt);
				dao.update(entityExt);
			}
		}
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<EntityExt> searchByCusId (String entityId){
		return dao.searchByEntityId(entityId);
	}

}