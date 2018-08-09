package com.yoogun.auth.application.service;

import com.yoogun.auth.application.dto.MenuBtnTreeview;
import com.yoogun.auth.application.vo.RoleVo;
import com.yoogun.auth.domain.model.Account;
import com.yoogun.auth.domain.model.Button;
import com.yoogun.auth.domain.model.Menu;
import com.yoogun.auth.domain.model.Role;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthService;
import com.yoogun.auth.infrastructure.event.RoleChangedEvent;
import com.yoogun.core.application.dto.Page;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.core.infrastructure.EntityUtils;
import com.yoogun.core.infrastructure.exception.BusinessException;
import com.yoogun.utils.infrastructure.BsTreeviewUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 权限-角色-业务层
 * @author Liu Jun at 2016-8-14 22:36:02
 * @version v1.0.0
 */
@Service
public class RoleService extends BaseAuthService<Role> {

	@Resource
	private MenuService menuService;

	@Resource
	private ButtonService buttonService;

	@Resource
	private AccountRoleService accountRoleService;

	@Resource
	private RoleMenuService roleMenuService;

	@Resource
	private RoleButtonService roleButtonService;

	@Resource
	private ApplicationEventPublisher publisher;

	/**
	 * Table分页查询
	 * @param vo 查询参数VO
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<Role> pageSearch(RoleVo vo) {

		SQL sql = new SQL().SELECT("*").FROM(tableName);
		sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
		sql.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'");

		//过滤条件
		if(StringUtils.isNotBlank(vo.getCode())) {
			sql.WHERE("CODE = '" + vo.getCode() + "'");
		}
		if(StringUtils.isNotBlank(vo.getName())) {
			sql.WHERE("NAME = '" + vo.getName() + "'");
		}

		//模糊查询，放在过滤条件最后，否则需要判定是否加and
		if(StringUtils.isNotBlank(vo.getSearch())) {
			sql.AND();
			sql.WHERE("NAME like '%" + vo.getSearch() + "%' " +
					"OR CODE like '%" + vo.getSearch() + "%' " );
		}

		//排序
		if(StringUtils.isNotBlank(vo.getSort()) && StringUtils.isNotBlank(vo.getOrder())) {
			try {
				sql.ORDER_BY(this.entityClass.newInstance().getColName(vo.getSort()) + " " + vo.getOrder());
			} catch (Exception e) {
				throw new BusinessException("获取'" + this.entityClass + "'的实例出错！", e);
			}
		} else {	//默认排序
			sql.ORDER_BY("IS_SYSTEM ASC, CODE ASC, NAME ASC");
		}

		return this.pageSearch(sql, vo.getOffset(), vo.getLimit());
	}

	/**
	 * 切换角色是否启用状态
	 * @param roleId 角色ID
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void toggleEnable(String roleId) {
		Role role = this.searchById(roleId);
		role.setEnabled(role.getEnabled() == null || !role.getEnabled());
		this.modify(role);
	}

	/**
	 * 检验角色名是否重复<br/>
	 * @param name 待验证名称
	 * @param id 待验证主键（非必须）
	 * @return true 重复；false 不重复
	 */
	private Boolean nameExist(final String name, String id) {
		SQL sql = new SQL().SELECT("1").FROM(tableName);
		sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
		sql.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'");
		sql.WHERE("NAME = '" + name + "'");
		if(StringUtils.isNotBlank(id)) {
			sql.WHERE("ID <> '" + id + "'");
		}
		return dao.countSearch(sql) > 0;
	}

	/**
	 *  查询全部可分配的菜单和按钮，拼接成树对象
	 * @return 菜单按钮树对象
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<MenuBtnTreeview> searchAllAssignableMenuAndBtn() {
		List<Menu> menus = menuService.searchAllAssignableMenu(null);    //查询树形实体集合

		this.appendBtnToMenu(menus);	//增加按钮数据

		return BsTreeviewUtils.load(MenuBtnTreeview.class, menus);

	}

	/**
	 * 循环查询菜单的按钮，将按钮保存到菜单中
	 */
	private void appendBtnToMenu(List<Menu> menus) {

		if(menus == null || menus.isEmpty()) {
			return;
		}

		for(Menu menu : menus) {	//遍历menu，处理button
			List<Button> btns = buttonService.searchButtonByMenu(menu.getId());
			menu.setButtons(btns);
		}
	}

	/**
	 * 根据角色获取菜单和按钮
	 * @param roleId 角色ID
	 * @return 角色权限绑定的菜单和按钮
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Map<String, List<String>> searchMenuAndButtonByRole(String roleId) {
		//获取角色绑定的菜单
		List<Menu> menus = roleMenuService.searchLeafMenuByRole(roleId);
		List<String> menuIds = EntityUtils.getAllId(menus);
		//获取角色绑定的按钮
		List<Button> buttons = roleButtonService.searchButtonByRole(roleId);
		List<String> buttonIds = EntityUtils.getAllId(buttons);

		Map<String, List<String>> res = new HashMap<>();
		res.put("menus", menuIds);
		res.put("buttons", buttonIds);

		return res;
	}

	/**
	 *  绑定菜单和按钮到角色上
	 * @param roleId	角色ID
	 * @param menuIds	菜单ID集合
	 * @param buttonIds	按钮ID集合
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void bindMenuAndButtonToRole(String roleId, List<String> menuIds, List<String> buttonIds) {
		roleMenuService.bindMenuToRole(roleId, menuIds);	//绑定菜单
		roleButtonService.bindButtonToRole(roleId, buttonIds);	//绑定按钮
	}

	@Override
	public void create(Role entity) {
		super.create(entity);
		//广播新增角色的事件
		publisher.publishEvent(new RoleChangedEvent(entity, RoleChangedEvent.ChangeType.CREATE));
	}

	@Override
	public void modify(Role entity) {
		super.modify(entity);
		//广播修改角色的事件
		publisher.publishEvent(new RoleChangedEvent(entity, RoleChangedEvent.ChangeType.MODIFY));
	}

	@Override
	public void remove(String id, String accountId) {
		super.remove(id, accountId);
		Role entity = new Role();
		entity.setId(id);
		entity.setLastModifyAccountId(accountId);
		//广播删除角色的事件
		publisher.publishEvent(new RoleChangedEvent(entity, RoleChangedEvent.ChangeType.REMOVE));
	}

	@Override
	protected void beforeCreate(Role entity) {
		if(this.nameExist(entity.getName(), entity.getId())) {
			throw new AuthenticationException("角色名“" + entity.getName() + "”已存在！");
		}
		entity.setEnabled(entity.getEnabled() == null ? true : entity.getEnabled());	//默认启用
		entity.setIsSystem(false);	//只能编辑非系统角色

		entity.setId(UUID.randomUUID().toString().toUpperCase());	//预先设置ID，为了保证同步到工作流业务时有ID

		super.beforeCreate(entity);
	}

	@Override
	protected void beforeModify(Role entity) {
		if(this.nameExist(entity.getName(), entity.getId())) {
			throw new AuthenticationException("角色名“" + entity.getName() + "”已被使用！");
		}

		super.beforeModify(entity);
	}

	@Override
	protected void beforeRemove(Role entity) {
		Account account = AuthCache.account();
		if(account == null) {
			throw new AuthenticationException("用户未登录");
		}
		entity.setLastModifyAccountId(account.getId());
		entity.setTenantId(AuthCache.tenantId());

		List<Account> accounts = accountRoleService.searchAccountByRole(entity.getId());

		if (accounts != null && !accounts.isEmpty()) {
			throw new BusinessException("该角色下存在账户，不可删除！");
		}
	}
}