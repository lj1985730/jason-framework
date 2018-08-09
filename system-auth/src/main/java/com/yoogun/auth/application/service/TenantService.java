package com.yoogun.auth.application.service;

import com.yoogun.auth.application.vo.TenantVo;
import com.yoogun.auth.domain.model.Account;
import com.yoogun.auth.domain.model.Tenant;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthService;
import com.yoogun.core.application.dto.Page;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.core.infrastructure.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * 权限-租户-业务层
 * @author Liu Jun at 2017-11-8 18:45:57
 * @version v1.0.0
 */
@Service
public class TenantService extends BaseAuthService<Tenant> {

	/**
	 * Table分页查询
	 * @param vo 查询参数VO
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<Tenant> pageSearch(TenantVo vo) {

		SQL sql = new SQL().SELECT("*").FROM(tableName);
		sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
//		sql.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'");

		//过滤条件
		if(StringUtils.isNotBlank(vo.getName())) {
			sql.WHERE("NAME = '" + vo.getName() + "'");
		}
		if(StringUtils.isNotBlank(vo.getUscc())) {
			sql.WHERE("USCC = '" + vo.getUscc() + "'");
		}

		//模糊查询,增加了AND，放在条件最后。否则后续条件还需要先加AND
		if(StringUtils.isNotBlank(vo.getSearch())) {
			sql.AND();
			sql.WHERE("NAME like '%" + vo.getSearch() + "%' " +
					"OR SHORT_NAME like '%" + vo.getSearch() + "%' " +
					"OR USCC like '%" + vo.getSearch() + "%'");
		}

		//排序
		if(StringUtils.isNotBlank(vo.getSort()) && StringUtils.isNotBlank(vo.getOrder())) {
			try {
				sql.ORDER_BY(this.entityClass.newInstance().getColName(vo.getSort()) + " " + vo.getOrder());
			} catch (Exception e) {
				throw new BusinessException("获取'" + this.entityClass + "'的实例出错！", e);
			}
		} else {	//默认排序
			sql.ORDER_BY("USCC ASC");
		}

		return this.pageSearch(sql, vo.getOffset(), vo.getLimit());
	}

	/**
	 * 切换租户是否启用状态
	 * @param tenantId 租户ID
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void toggleEnable(String tenantId) {
		Tenant tenant = this.searchById(tenantId);
		tenant.setEnabled(tenant.getEnabled() == null || !tenant.getEnabled());
		this.modify(tenant);
	}

	/**
	 * 按照主键查询租户信息
	 * @param id 主键
	 * @return 租户信息
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Tenant searchById(Serializable id, String... tenantId) {
		SQL sql = new SQL()
				.SELECT("A.*, B.NAME AS CATEGORY_TEXT")	//关联查询企业分类
				.FROM(tableName + " A")
				.LEFT_OUTER_JOIN("SYS_DICT B ON B.CATEGORY_KEY = 'COM_ENT_NATURE' AND A.CATEGORY = B.CODE")
				.WHERE("A.ID = '" + id + "'");
		List<Tenant> tenants = dao.search(sql);
		return (tenants == null || tenants.isEmpty()) ? null : tenants.get(0);
	}

	@Override
	protected void beforeCreate(Tenant entity) {
		Account account = AuthCache.account();
		if(account == null) {
			throw new AuthenticationException("用户未登录");
		}

		entity.setEnabled(true);
		entity.setLastModifyAccountId(account.getId());

		String uuid = UUID.randomUUID().toString().toUpperCase();	//生成租户ID
		entity.setId(uuid);
		entity.setTenantId(uuid);

	}

	@Override
	protected void beforeModify(Tenant entity) {
		Account account = AuthCache.account();
		if(account == null) {
			throw new AuthenticationException("用户未登录");
		}
		entity.setLastModifyAccountId(account.getId());
	}

	@Override
	protected void beforeRemove(Tenant entity) {
		Account account = AuthCache.account();
		if(account == null) {
			throw new AuthenticationException("用户未登录");
		}
		if(!account.isSuperAdmin()) {
			throw new AuthorizationException("非超管禁止删除企业");
		}
		entity.setLastModifyAccountId(account.getId());
	}
}