/*
 * AuthrolerlatService.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.auth.application.service;

import com.yoogun.auth.application.vo.RoleRlatVo;
import com.yoogun.auth.domain.model.Account;
import com.yoogun.auth.domain.model.RoleRlat;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthService;
import com.yoogun.core.application.dto.Page;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.core.infrastructure.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 角色关系-应用业务<br/>
 * 主要业务逻辑代码
 * @author Wang Chong at 2018-05-01 13:27:08
 * @since v1.0.0
 */
@Service
public class RoleRlatService extends BaseAuthService<RoleRlat> {

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<RoleRlat> pageSearch(RoleRlatVo vo) {
		SQL sql = new SQL().SELECT("*").FROM(tableName);
		sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
		sql.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'");
		
		//过滤条件
		
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
			sql.ORDER_BY("LAST_MODIFY_TIME");
		}
		
		return this.pageSearch(sql, vo.getOffset(), vo.getLimit());
	}

	@Override
	protected void beforeCreate(RoleRlat entity) {
		super.beforeCreate(entity);
		Account account = AuthCache.account();
		if(account == null){
			throw new BusinessException("请先登陆！");
		}
		entity.setTenantId(account.getTenantId());
		entity.setLastModifyAccountId(account.getId());
	}

	/**
	 * 设置角色的下级角色
	 * @param roleId 角色id
	 * @param roleRlats 待设置下级角色
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void setRoleRlat(String roleId, List<RoleRlat> roleRlats) {
		if(StringUtils.isBlank(roleId)){
			throw new BusinessException("请指定角色！");
		}
		//根据角色id,全部逻辑删除该角色的下级角色
		//待设置角色是否包含已删除的下级角色，如果包含，则恢复，否则新增
		List<RoleRlat> removedRoleRlats = this.removeAllRoleRlat(roleId);
		roleRlats.forEach(roleRlat -> {
			if (removedRoleRlats.contains(roleRlat)) {
				roleRlat.setDeleted(false);
				roleRlat.setId(removedRoleRlats.get(removedRoleRlats.indexOf(roleRlat)).getId());
				this.modify(roleRlat);
			} else {
				this.create(roleRlat);
			}
		});
	}

	/**
	 * 根据角色id,删除该角色的所有下级角色
	 * @param roleId 角色id
	 * @return 已经删除的下级角色对象
	 */
	private List<RoleRlat> removeAllRoleRlat(String roleId){
		if(StringUtils.isBlank(roleId)){
			throw new BusinessException("请指定角色！");
		}
		List<RoleRlat> removedRoleRlats = this.searchByProp("superiorId", roleId, AuthCache.tenantId());
		removedRoleRlats.forEach(this::remove);
		return removedRoleRlats;
	}

}