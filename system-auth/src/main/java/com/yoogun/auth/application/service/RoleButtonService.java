package com.yoogun.auth.application.service;

import com.yoogun.auth.domain.model.Button;
import com.yoogun.auth.domain.model.RoleButton;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthService;
import com.yoogun.auth.repository.ButtonDao;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.core.infrastructure.EntityUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限-角色按钮关系-业务层
 * @author Liu Jun
 * @version 2016-8-14 22:28:14
 */
@Service
public class RoleButtonService extends BaseAuthService<RoleButton> {

	@Resource
	private ButtonDao buttonDao;

	/**
	 * 获取角色的按钮
	 * @param roleId 账户Id
	 * @return 角色的菜单
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Button> searchButtonByRole(String roleId) {

		SQL sql = new SQL();
		sql.SELECT("A.*")
				.FROM("AUTH_BUTTON A", "AUTH_ROLE_BUTTON B", "AUTH_ROLE C")
				.WHERE("A.DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("A.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("A.ID = B.BUTTON_ID")
				.WHERE("B.DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("B.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("B.ROLE_ID = C.ID")
				.WHERE("C.DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("C.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("C.ID = '" + roleId + "'");

		return buttonDao.search(sql);
	}

	/**
	 * 设置角色的按钮
	 * @param roleId 角色Id
	 * @param buttonIds 按钮Id集合
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void bindButtonToRole(String roleId, List<String> buttonIds) {
		List<String> removedIdList = this.removeAllButton(roleId);	//先删除全部已绑定的数据

		List<String> updateIdList = new ArrayList<>();	//绑定时需要更新的数据
		List<String> createIdList = new ArrayList<>();	//绑定时需要新增的数据

		buttonIds.forEach(buttonId -> {	//saveOrUpdate分发
			if(removedIdList.contains(buttonId)) {
				updateIdList.add(buttonId);
			} else {
				createIdList.add(buttonId);
			}
		});

		this.updateRoleButton(roleId, updateIdList);

		this.createRoleButton(roleId, createIdList);
	}

	/**
	 * 删除角色的全部按钮
	 * @param roleId 角色Id
	 * @return 被删除的按钮Id
	 */
	private List<String> removeAllButton(String roleId) {

		SQL sql = new SQL().SELECT("*").FROM(tableName);
		sql.WHERE("ROLE_ID = '" + roleId + "'");
		sql.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'");
		List<RoleButton> list = this.search(sql);

		List<String> removedButtonId = new ArrayList<>();

		list.forEach(roleMenu -> {
			this.remove(roleMenu);
			removedButtonId.add(roleMenu.getButtonId());
		});

		return removedButtonId;
	}

	/**
	 * 恢复角色的按钮
	 * @param roleId 角色Id
	 * @param buttonIdList 待操作按钮Id集合
	 */
	private void updateRoleButton(String roleId, List<String> buttonIdList) {

		if(StringUtils.isBlank(roleId) || buttonIdList.isEmpty()) {
			return;
		}

		SQL sql = new SQL().SELECT("*").FROM(tableName);
		// 构造条件
		sql.WHERE("ROLE_ID = '" + roleId + "'");
		sql.WHERE("BUTTON_ID IN (" + EntityUtils.inSql(buttonIdList) + ")");
		sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_TRUE + "'");
		sql.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'");
		List<RoleButton> list = this.search(sql);

		list.forEach(roleButton -> {
			roleButton.setDeleted(false);
			this.modify(roleButton);
		});
	}

	/**
	 * 新增角色的按钮
	 * @param roleId		角色Id
	 * @param buttonIdList 待操作按钮Id集合
	 */
	private void createRoleButton(String roleId, List<String> buttonIdList) {
		RoleButton roleButton;
		for(String buttonId : buttonIdList) {
			roleButton = new RoleButton();
			roleButton.setRoleId(roleId);
			roleButton.setButtonId(buttonId);
			this.create(roleButton);
		}
	}
}