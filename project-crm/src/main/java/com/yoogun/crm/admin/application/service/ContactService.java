/*
 * ContactService.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.application.service;

import com.yoogun.auth.application.service.PermissionService;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthService;
import com.yoogun.core.application.dto.Page;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.core.infrastructure.EntityUtils;
import com.yoogun.core.infrastructure.exception.BusinessException;
import com.yoogun.crm.admin.application.vo.ContactVo;
import com.yoogun.crm.admin.domain.model.Contact;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 联系人-应用业务<br/>
 * 主要业务逻辑代码
 * @author Sheng Baoyu at 2018-02-25 14:44:35
 * @since v1.0.0
 */
@Service
public class ContactService extends BaseAuthService<Contact> {

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<Contact> pageSearch(ContactVo vo) {

		SQL sql = new SQL().SELECT("A.*, ROLE.NAME AS ROLE_NAME").FROM(tableName + " A");
		sql.WHERE("A." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
		sql.WHERE("A.TENANT_ID = '" + AuthCache.tenantId() + "'");

		if(StringUtils.isNotBlank(vo.getCustomerId())) {	//客户过滤
			sql.WHERE("A.CUSTOMER_ID = '" + vo.getCustomerId() + "'");
		}

		//项目角色
		sql.LEFT_OUTER_JOIN("SYS_DICT ROLE ON A.ROLE = ROLE.CODE " +
				"AND ROLE.CATEGORY_KEY = 'CRM_PROJ_ROLE' " +
				"AND ROLE." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "' " +
				"AND ROLE.TENANT_ID = '" + AuthCache.tenantId() + "'");

		//模糊查询,增加了AND，放在条件最后。否则后续条件还需要先加AND
		if(StringUtils.isNotBlank(vo.getSearch())) {
			sql.AND();
			sql.WHERE("A.CONTACT_NAME like '%" + vo.getSearch() + "%'");
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
			sql.ORDER_BY("A.LAST_MODIFY_TIME ASC");
		}

		return super.pageSearch(sql, vo.getOffset(), vo.getLimit());
	}

	/**
	 * 根据客户ID查客户联系人
	 * @param customerId 客户ID
	 * @return 客户联系人
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Contact> pageSearch(String customerId) {
		//根据客户ID查客户联系人
		SQL sql = new SQL().SELECT("*").FROM(tableName);
		sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
		sql.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'");
		sql.WHERE("CUSTOMER_ID = '" + customerId + "'");

		return dao.search(sql);
	}

	/**
	 * 根据PO查询数据，PO中的非null内容均会被当做查询条件
	 * @param customerId 客户id
	 * @return 查询对象集合
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<Contact> searchByCustomer(String customerId) {
		SQL sql = new SQL().SELECT("CONTACT.*," +
				"GENDER.NAME AS GENDER_TEXT, " +
				"DEPARTMENT.NAME AS DEPARTMENT_TEXT, " +
				"POST.NAME AS POST_TEXT," +
				"ACCOUNT.FIRST_ AS MODIFIER_ACCOUNT," +
				"PERSON.NAME AS MODIFIER_NAME"
		).FROM(tableName + " CONTACT");

		//性别
		sql.LEFT_OUTER_JOIN("SYS_DICT GENDER ON CONTACT.GENDER = GENDER.CODE " +
				"AND GENDER.CATEGORY_KEY = 'COM_PSN_GENDER' " +
				"AND GENDER." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "' " +
				"AND GENDER.TENANT_ID IN ('" + AuthCache.tenantId() + "', '" + PermissionService.superTenantId + "')");

		//部门
		sql.LEFT_OUTER_JOIN("SYS_DICT DEPARTMENT ON CONTACT.DEPARTMENT = DEPARTMENT.CODE " +
				"AND DEPARTMENT.CATEGORY_KEY = 'CRM_CONTACT_DEPT' " +
				"AND DEPARTMENT." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "' " +
				"AND DEPARTMENT.TENANT_ID IN ('" + AuthCache.tenantId() + "', '" + PermissionService.superTenantId + "')");

		//岗位
		sql.LEFT_OUTER_JOIN("SYS_DICT POST ON CONTACT.POST = POST.CODE " +
				"AND POST.CATEGORY_KEY = 'CRM_CONTACT_POST' " +
				"AND POST." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "' " +
				"AND POST.TENANT_ID IN ('" + AuthCache.tenantId() + "', '" + PermissionService.superTenantId + "')");

		//最后修改人账户
		sql.LEFT_OUTER_JOIN("ACT_ID_USER ACCOUNT ON CONTACT.LAST_MODIFY_ACCOUNT_ID = ACCOUNT.ID " +
				"AND ACCOUNT.TENANT_ID = '" + AuthCache.tenantId() + "'");

		//最后修改人
		sql.LEFT_OUTER_JOIN("AUTH_PERSON PERSON ON ACCOUNT.PERSON_ID = PERSON.ID " +
				"AND PERSON.TENANT_ID = '" + AuthCache.tenantId() + "'");

		sql.WHERE("CONTACT." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
		sql.WHERE("CONTACT.TENANT_ID = '" + AuthCache.tenantId() + "'");
		sql.WHERE("CONTACT.CUSTOMER_ID = '" + customerId + "'");

		List<Contact> contacts = this.search(sql);

		Page<Contact> page = new Page<>(0, 500);
		// 为pager绑定数据和总数
		page.setRows(contacts);	// 单页数据
		page.setTotal(contacts.size());	// 当前条件下的总数

		return page;
	}

	/**
	 * 更新多个联系人
	 * @param contacts 待更新联系人集合
	 * @param customerId 客户ID
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void modifyAll(List<Contact> contacts, String customerId) {
		List<String> removedIdList = this.removeByCustomer(customerId, AuthCache.accountId());		//先删除该客户原来的联系人

		contacts.forEach(contact -> {	//saveOrUpdate分发
			if(removedIdList != null && removedIdList.contains(contact.getId())) {
				this.modify(contact);
			} else {
				contact.setCustomerId(customerId);
				this.create(contact);
			}
		});
	}

	/**
	 * 根据客户ID删除全部联系人（逻辑删除）<br>
	 * @param accountId 账户ID
	 * @return 被删除的ID
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> removeByCustomer(String customerId, String accountId) {
		Page<Contact> contacts = this.searchByCustomer(customerId);
		List<String> contactIds = EntityUtils.getAllId(contacts.getRows());//获得所有联系人的Id
		if(contactIds != null) {
			this.removeAll(contactIds, accountId);//逻辑删除
		}
		return contactIds;
	}

}