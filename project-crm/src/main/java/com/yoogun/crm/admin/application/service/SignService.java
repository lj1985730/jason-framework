/*
 * SignService.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.application.service;

import com.yoogun.auth.domain.model.Account;
import com.yoogun.auth.domain.model.Department;
import com.yoogun.auth.domain.model.Person;
import com.yoogun.auth.domain.model.Tenant;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthService;
import com.yoogun.auth.repository.DepartmentDao;
import com.yoogun.auth.repository.PersonDao;
import com.yoogun.auth.repository.TenantDao;
import com.yoogun.core.application.dto.Page;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.core.infrastructure.exception.BusinessException;
import com.yoogun.crm.admin.application.vo.SignVo;
import com.yoogun.crm.admin.domain.model.Sign;
import com.yoogun.crm.admin.repository.SignDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


/**
 * 签到管理-应用业务<br/>
 * 主要业务逻辑代码
 * @author Wang chong at 2018-03-06 15:39:52
 * @since v1.0.0
 */
@Service
public class SignService extends BaseAuthService<Sign> {

	@Resource
	private PersonDao personDao;

	@Resource
	private TenantDao tenantDao;

	@Resource
	private DepartmentDao departmentDao;

	@Resource
	private SignDao signDao;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<Sign> pageSearch(SignVo vo) {
		SQL sql = new SQL().SELECT("*").FROM(tableName);
		sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
		sql.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'");
		
		//过滤条件
		if (StringUtils.isNotBlank(vo.getSignDate())){
			sql.AND();
			sql.WHERE("LEFT(SIGN_DATE,7) = '" + vo.getSignDate() + "'");
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
			sql.ORDER_BY("SIGN_DATE DESC");
		}
		
		return changeSign(this.pageSearch(sql, vo.getOffset(), vo.getLimit()));
	}

	/**
	 * 查询 签到人、所属公司、所属部门的实体类存放到sign实体中
	 * @param page 分页数据
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<Sign> changeSign (Page<Sign> page){

		if (page == null || page.getRows() == null || page.getRows().size() < 1){
			return page;
		}

		for (Sign sign : page.getRows()){
			sign.setPerson(personDao.searchById(Person.class,sign.getSignUser()));
			sign.setDepartment(departmentDao.searchById(Department.class,sign.getSignDept()));
			sign.setTenant(tenantDao.searchById(Tenant.class,sign.getSignCorp()));
		}
		return page;
	}

	/**
	 * 获取当前用户的公司、部门、签到人、当前时间
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Sign getSignInfo(SignVo vo){
		Sign sign = new Sign();
		Tenant tenant = (Tenant) AuthCache.get(AuthCache.LoginInfo.TENANT);
		sign.setTenant(tenant);
		Department department = (Department)AuthCache.get(AuthCache.LoginInfo.DEPARTMENT);
		sign.setDepartment(department);
		Person person = (Person)AuthCache.get(AuthCache.LoginInfo.PERSON);
		sign.setPerson(person);
		sign.setSignDate(String.valueOf(LocalDate.now()));
		if ("0".equals(vo.getInOrOut())){
			sign.setSignInTime(String.valueOf(LocalTime.now().withNano(0)));
		}
		if ("1".equals(vo.getInOrOut())){
			sign.setSignOutTime(String.valueOf(LocalTime.now().withNano(0)));
		}
		return sign;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Sign isSigned(SignVo vo) {
		Tenant tenant = (Tenant) AuthCache.get(AuthCache.LoginInfo.TENANT);
		List<Sign> signs = signDao.searchByProp(Sign.class,"signDate",vo.getSignDate(),tenant.getId());
		if (signs != null && signs.size() > 0){
			return signs.get(0);
		}
		return null;
	}

	/**
	 *  更新前置方法，自动写入修改人和租户ID
	 * @param sign 需要处理的对象
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	protected void beforeModify(Sign sign) {
		Account account = AuthCache.account();
		if(account == null) {
			throw new AuthenticationException("用户未登录");
		}
		sign.setLastModifyAccountId(account.getId());
		sign.setTenantId(AuthCache.tenantId());
		List<Sign> signs = signDao.searchByProp(Sign.class,"signDate",sign.getSignDate());
		if (signs != null && signs.size() > 0){
			sign.setId(signs.get(0).getId());
		}
		changeProperty(sign);
	}

	/**
	 * 属性为空字符串的属性值变为null
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	 void changeProperty(Sign sign){
		try {
			Class userCla = sign.getClass();
			Field[] fs = userCla.getDeclaredFields();
			for (Field f : fs) {
				f.setAccessible(true);
				String type = f.getType().toString();
				if (type.endsWith("String")) {
					if ("".equals(f.get(sign))){
						f.set(sign,null);
					}
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}