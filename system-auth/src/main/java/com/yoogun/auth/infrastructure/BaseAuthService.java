package com.yoogun.auth.infrastructure;

import com.yoogun.auth.domain.model.Account;
import com.yoogun.core.application.dto.Page;
import com.yoogun.core.application.service.BaseService;
import com.yoogun.core.domain.model.BaseEntity;
import org.apache.ibatis.jdbc.SQL;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 权限-抽象业务层<br/>
 * 封装业务层权限相关内容
 * @author Liu Jun at 2018-1-29 10:17:40
 * @version v1.0.0s
 */
public abstract class BaseAuthService<E extends BaseEntity> extends BaseService<E> {

	/**
	 *  SQL分页查询
	 *  代理父类，增加缓存功能
	 * @param sql Mybatis SQL对象
	 * @param offset 开始数据位置
	 * @param limit 数据个数
	 * @param hasTotal 是否有合计行，不传或传false为无，传true为有
	 * @return 分页数据
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<E> pageSearch(SQL sql, Integer offset, Integer limit, Boolean... hasTotal) {
		return super.pageSearch(sql, offset, limit, hasTotal);
	}

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

	/**
	 *  逻辑删除前置方法，检查当前是否登录
	 * @param id 需要处理的对象
	 */
	@Override
	protected void beforeRemove(String id) {
		Account account = AuthCache.account();
		if(account == null) {
			throw new AuthenticationException("用户未登录");
		}
	}

	/**
	 * <p>删除单个实体对象（逻辑删除）
	 * <p>DELETE和LAST_MODIFY_TIME自动写入
	 * <p>带有beforeRemove(id)切入点
	 * @param id 待删除实体ID
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(String id) {
		super.remove(id, AuthCache.accountId());
	}
}