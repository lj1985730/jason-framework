package com.yoogun.auth.infrastructure;

import com.yoogun.auth.domain.model.Account;
import com.yoogun.core.application.service.TreeService;
import com.yoogun.core.domain.model.TreeEntity;
import org.apache.shiro.authc.AuthenticationException;

/**
 * 权限-抽象树形业务层<br/>
 * 封装树状业务层权限相关内容
 * @author Liu Jun at 2018-1-29 10:17:36
 * @version v1.0.0
 */
public abstract class BaseAuthTreeService<E extends TreeEntity> extends TreeService<E> {

	/**
	 *  保存前置方法，自动写入修改人和租户ID
	 * @param entity 需要处理的对象
	 */
	@Override
	protected void beforeCreate(E entity) {
		Account account = AuthCache.account();
		if(account == null) {
			throw new AuthenticationException("用户未登录");
		}
		entity.setLastModifyAccountId(account.getId());
		entity.setTenantId(AuthCache.tenantId());
	}

	/**
	 *  更新前置方法，自动写入修改人和租户ID
	 * @param entity 需要处理的对象
	 */
	@Override
	protected void beforeModify(E entity) {
		Account account = AuthCache.account();
		if(account == null) {
			throw new AuthenticationException("用户未登录");
		}
		entity.setLastModifyAccountId(account.getId());
		entity.setTenantId(AuthCache.tenantId());
	}

	/**
	 *  逻辑删除前置方法，自动写入修改人和租户ID
	 * @param entity 需要处理的对象
	 */
	@Override
	protected void beforeRemove(E entity) {
		Account account = AuthCache.account();
		if(account == null) {
			throw new AuthenticationException("用户未登录");
		}
		entity.setLastModifyAccountId(account.getId());
		entity.setTenantId(AuthCache.tenantId());
	}
}