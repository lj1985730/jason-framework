package com.yoogun.auth.application.service;

import com.yoogun.auth.application.vo.ButtonVo;
import com.yoogun.auth.domain.model.Button;
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
 * 权限-按钮-业务层
 * @author Liu Jun at 2016-8-8 23:38:18
 * @since v1.0.0
 */
@Service
public class ButtonService extends BaseAuthService<Button> {

	/**
	 * Table分页查询
	 * @param vo 查询参数VO
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<Button> pageSearch(ButtonVo vo) {

		SQL sql = new SQL().SELECT("*").FROM(tableName);
		sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
		sql.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'");

		//过滤条件
		if(StringUtils.isNotBlank(vo.getMenuId())) {
			sql.WHERE("MENU_ID = '" + vo.getMenuId() + "'");
		}

		if(StringUtils.isNotBlank(vo.getSearch())) {
			sql.AND();
			sql.WHERE("NAME like '%" + vo.getSearch() + "%' " +
					"OR PERMISSION like '%" + vo.getSearch() + "%'" +
					" OR ELEMENT_ID like '%" + vo.getSearch() + "%'");
			sql.AND();
		}

		//排序
		if(StringUtils.isNotBlank(vo.getOrder()) && StringUtils.isNotBlank(vo.getSort())) {
			try {
				sql.ORDER_BY(this.entityClass.newInstance().getColName(vo.getSort()) + " " + vo.getOrder());
			} catch (Exception e) {
				throw new BusinessException("获取'" + this.entityClass + "'的实例出错！", e);
			}
		} else {	//默认排序
			sql.ORDER_BY("PERMISSION ASC");
		}

		return this.pageSearch(sql, vo.getOffset(), vo.getLimit());
	}
	
	/**
	 * 根据用户权限获取按钮-非管理员登录时按钮权限过滤使用
	 * 关系链路Account -> AccountRole -> Role -> RoleButton -> Button
	 * @param accountId 用户Id
	 * @return	用户按钮
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	List<Button> searchPermissionButtons(String accountId) {

		SQL sql = new SQL();
		sql.SELECT("A.*")
				.FROM("AUTH_BUTTON A", "AUTH_ROLE_BUTTON B",
						"AUTH_ROLE C", "AUTH_ACCOUNT_ROLE D",
						"ACT_ID_USER E")
				.WHERE("A.DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("A.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("A.ID = B.BUTTON_ID")
				.WHERE("B.DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("B.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("B.ROLE_ID = C.ID")
				.WHERE("C.DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("C.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("C.ID = D.ROLE_ID")
				.WHERE("D.DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("D.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("D.ACCOUNT_ID = E.ID")
				.WHERE("E.DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("E.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("E.ID = '" + accountId + "'");
		return this.search(sql);
	}

	/**
	 * 获取菜单的按钮
	 * @param menuId 账户Id
	 * @return 角色的菜单
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Button> searchButtonByMenu(String menuId) {

		SQL sql = new SQL();
		sql.SELECT("A.*")
				.FROM("AUTH_BUTTON A")
				.WHERE("A.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("A.DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("A.ENABLED = '" + BooleanTypeHandler.BOOL_TRUE + "'")
				.WHERE("A.MENU_ID = '" + menuId + "'");
		sql.ORDER_BY("A.NAME ASC");

		return dao.search(sql);
	}

	/**
	 * 切换按钮是否启用状态
	 * @param id 按钮ID
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void toggleEnable(String id) {
		Button button = this.searchById(id);
		button.setEnabled(button.getEnabled() == null || !button.getEnabled());
		this.modify(button);
	}
}