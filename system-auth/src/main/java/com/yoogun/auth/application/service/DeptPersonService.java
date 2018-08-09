/*
 * DeptPersonService.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.auth.application.service;

import com.yoogun.auth.application.vo.DeptPersonVo;
import com.yoogun.auth.domain.model.Account;
import com.yoogun.auth.domain.model.DeptPerson;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthService;
import com.yoogun.core.application.dto.Page;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.core.infrastructure.EntityUtils;
import com.yoogun.core.infrastructure.exception.BusinessException;
import com.yoogun.utils.infrastructure.EhCacheUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 部门-人员-应用业务<br/>
 * 主要业务逻辑代码
 * @author Wang Chong at 2018-04-19 11:29:59
 * @since v1.0.0
 */
@Service
public class DeptPersonService extends BaseAuthService<DeptPerson> {

	@Resource
	private DepartmentService departmentService;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<DeptPerson> pageSearch(DeptPersonVo vo) {
		SQL sql = new SQL().SELECT("DP.*, GENDER.NAME AS GENDER_NAME,STATE.NAME AS STATE_NAME, NATURE.NAME AS NATURE_NAME")
				.FROM(tableName + " DP");
		sql.WHERE("DP." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
		sql.WHERE("DP.TENANT_ID = '" + AuthCache.tenantId() + "'");

		//部门id过滤条件:支持显示子部门人员
		if(StringUtils.isNotBlank(vo.getDeptId())){
			String[] deptIds = departmentService.getAllSubNodeId(vo.getDeptId());
			if(deptIds != null && deptIds.length > 0){
				sql.WHERE("DP.DEPARTMENT_ID IN (" + EntityUtils.inSql(deptIds) + ")");
			}else {
				sql.WHERE("DP.DEPARTMENT_ID = '" + vo.getDeptId() + "'");
			}
		}

		//关联人员表
		sql.LEFT_OUTER_JOIN("AUTH_PERSON PERSON ON DP.PERSON_ID = PERSON.ID " +
				"AND PERSON." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "' " +
				"AND PERSON.TENANT_ID = '" + AuthCache.tenantId() + "'");
		//性别
		sql.LEFT_OUTER_JOIN("SYS_DICT GENDER ON PERSON.GENDER = GENDER.CODE " +
				"AND GENDER.CATEGORY_KEY = 'COM_PSN_GENDER' " +
				"AND GENDER." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "' " +
				"AND GENDER.TENANT_ID IN ('" + AuthCache.tenantId() + "', '" + PermissionService.superTenantId + "')");
		//状态
		sql.LEFT_OUTER_JOIN("SYS_DICT STATE ON PERSON.STATE = STATE.CODE " +
				"AND STATE.CATEGORY_KEY = 'PERSON_STATE' " +
				"AND STATE." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "' " +
				"AND STATE.TENANT_ID IN ('" + AuthCache.tenantId() + "', '" + PermissionService.superTenantId + "')");
		//性质
		sql.LEFT_OUTER_JOIN("SYS_DICT NATURE ON PERSON.NATURE = NATURE.CODE " +
				"AND NATURE.CATEGORY_KEY = 'COM_PSN_NATURE' " +
				"AND NATURE." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "' " +
				"AND NATURE.TENANT_ID IN ('" + AuthCache.tenantId() + "', '" + PermissionService.superTenantId + "')");
		
		//模糊查询,增加了AND，放在条件最后。否则后续条件还需要先加AND
		if(StringUtils.isNotBlank(vo.getSearch())) {
			sql.AND();
			sql.WHERE("PERSON.NAME like '%" + vo.getSearch() + "%' " +
			"OR PERSON.EN_NAME like '%" + vo.getSearch() + "%' ");
		}
		
		//排序
		if(StringUtils.isNotBlank(vo.getSort()) && StringUtils.isNotBlank(vo.getOrder())) {
			try {
				sql.ORDER_BY("DP." + this.entityClass.newInstance().getColName(vo.getSort()) + " " + vo.getOrder());
			} catch (Exception e) {
				throw new BusinessException("获取'" + this.entityClass + "'的实例出错！", e);
			}
		} else {
			sql.ORDER_BY("PERSON.NAME ASC");
		}
		
		return this.pageSearch(sql, vo.getOffset(), vo.getLimit());
	}

	/**
	 *  查询兼职部门
	 * @param personId 人员ID
	 * @return 兼职部门ID串，逗号分隔
	 */
	@Cacheable(value = EhCacheUtils.SYSTEM_CACHE, key = "'personParttimeDepartment|'.concat(#personId)")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public String searchPartTimeDepartments(String personId) {
		if(StringUtils.isBlank(personId)) {
			return null;
		}
		SQL sql = new SQL().SELECT("DEPARTMENT_PERSON.DEPARTMENT_ID")
				.FROM("AUTH_DEPARTMENT_PERSON DEPARTMENT_PERSON, AUTH_PERSON PERSON")
				.WHERE("DEPARTMENT_PERSON.PERSON_ID = PERSON.ID")
				.WHERE("DEPARTMENT_PERSON.DEPARTMENT_ID <> PERSON.DEPARTMENT_ID")
				.WHERE("PERSON.DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("PERSON.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("DEPARTMENT_PERSON.DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("DEPARTMENT_PERSON.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("PERSON_ID = '" + personId + "'");
		List<Map<String, Object>> departmentIds = dao.sqlSearchNoMapped(sql.toString());
		StringBuilder departmentIdStr = new StringBuilder();
		departmentIds.forEach(departmentId -> departmentIdStr.append(departmentId.get("DEPARTMENT_ID")).append(","));
		return StringUtils.removeEnd(departmentIdStr.toString(), ",");
	}

	/**
	 * 设置人员部门
	 * @param personId 人员id
	 * @param deptIds 需要绑定部门id数组
	 */
	@CacheEvict(value = EhCacheUtils.SYSTEM_CACHE, key = "'personParttimeDepartment|'.concat(#personId)")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void setPersonDept(String personId, String[] deptIds){
		//1.根据personId逻辑删除部门人员信息,并返回删除数据的ids
		List<String> removedDeptIds = this.removeAllDept(personId);
		List<String> updateDeptIds = new ArrayList<>();	//需更新的数据
		List<String> createDeptIds = new ArrayList<>();	//需新增的数据
		//2.遍历需要绑定的数据，如果包含旧数据，则恢复逻辑删除，否则新增
		if (deptIds != null && deptIds.length > 0){
			Arrays.stream(deptIds).forEach(deptId -> {
				if (removedDeptIds.contains(deptId)) {
					updateDeptIds.add(deptId);
				} else {
					createDeptIds.add(deptId);
				}
			});
		}

		//恢复删除的数据
		if(!updateDeptIds.isEmpty()){
			this.updateDeptPerson(personId, updateDeptIds);
		}
		//新增删除的数据
		if (!createDeptIds.isEmpty()){
			this.createDeptPerson(personId, createDeptIds);
		}
	}

	/**
	 * 根据人员id删除人员下的所有部门
	 * @param personId 人员id
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public ArrayList<String> removeAllDept(String personId){
		if(StringUtils.isBlank(personId)){
			throw new BusinessException("请指定人员！");
		}
		List<DeptPerson> deptPeoples = this.searchByProp("personId",personId,AuthCache.tenantId());
		ArrayList<String> removedIds = new ArrayList<>();
		for(DeptPerson deptPerson : deptPeoples){
			removedIds.add(deptPerson.getDepartmentId());
			this.remove(deptPerson);
		}
		return removedIds;
	}

	private void updateDeptPerson(String personId,List<String> deptIds){
		if(StringUtils.isBlank(personId)){
			throw new BusinessException("请选择人员！");
		}
		SQL sql = new SQL().SELECT("*").FROM("AUTH_DEPARTMENT_PERSON");
		sql.WHERE("PERSON_ID = '" + personId + "'");
		if (deptIds != null && deptIds.size() > 0){
			sql.WHERE("DEPARTMENT_ID IN (" + EntityUtils.inSql(deptIds) + ")");
		}
		sql.WHERE(BaseEntity.DELETE_PARAM + "= '" + BooleanTypeHandler.BOOL_TRUE + "'");
		sql.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'");

		List<DeptPerson> deptPersons = this.search(sql);
		if (deptPersons !=null && deptPersons.size() > 0){
			for (DeptPerson deptPerson : deptPersons){
				deptPerson.setDeleted(false);
				this.modify(deptPerson);
			}
		}
	}

	private void createDeptPerson(String personId,List<String> deptIds){
		if(StringUtils.isBlank(personId)){
			throw new BusinessException("请选择人员！");
		}

		if (deptIds != null && deptIds.size() > 0){
			for (String deptId : deptIds){
				DeptPerson deptPerson = new DeptPerson();
				deptPerson.setPersonId(personId);
				deptPerson.setDepartmentId(deptId);
				this.create(deptPerson);
			}
		}
	}

	@Override
	protected void beforeCreate(DeptPerson entity){
		Account account = AuthCache.account();
		if(account == null) {
			throw new AuthenticationException("用户未登录");
		}
		entity.setLastModifyAccountId(account.getId());
		entity.setTenantId(AuthCache.tenantId());
	}
}