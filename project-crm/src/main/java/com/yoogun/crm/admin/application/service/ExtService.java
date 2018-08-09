/*
 * ExtService.java
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
import com.yoogun.crm.admin.application.vo.ExtVo;
import com.yoogun.crm.admin.domain.model.Ext;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

;


/**
 * 附加信息-应用业务<br/>
 * 主要业务逻辑代码
 * @author Wang Chong at 2018-03-22 10:11:47
 * @since v1.0.0
 */
@Service
public class ExtService extends BaseAuthService<Ext> {

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<Ext> pageSearch(ExtVo vo) {
		SQL sql = new SQL().SELECT("*").FROM(tableName);
		sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
		sql.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'");
		
		//过滤条件
		if (vo != null && StringUtils.isNotBlank(vo.getModule())){
			sql.WHERE("MODULE = '" + vo.getModule() + "'") ;
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
			sql.ORDER_BY("ID");
		}
		
		return this.pageSearch(sql, vo.getOffset(), vo.getLimit());
	}
}