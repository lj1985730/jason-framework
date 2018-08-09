package com.yoogun.auth.application.service;

import com.yoogun.auth.domain.model.Account;
import com.yoogun.auth.domain.model.Department;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthTreeService;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.core.infrastructure.exception.BusinessException;
import com.yoogun.utils.infrastructure.EhCacheUtils;
import org.apache.ibatis.jdbc.SQL;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * 权限-部门-业务层
 * @author Liu Jun at 2016-8-11 23:58:24
 * @since v1.0.0
 */
@Service
@CacheConfig(cacheNames = { EhCacheUtils.SYSTEM_CACHE })
public class DepartmentService extends BaseAuthTreeService<Department> {

	/**
	 * 查询最大序号
	 * @param parentId	父节点Id
	 * @return 最大序号
     */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Integer searchMaxSort(String parentId) {

		SQL sql = new SQL();
		sql.SELECT("MAX(SORT_NUMBER) AS SORT_NUMBER")
				.FROM("AUTH_DEPARTMENT")
				.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("PARENT_ID = '" + parentId + "'");

		List<Map<String, Object>> res = dao.sqlSearchNoMapped(sql.toString());
		if(res.isEmpty()) {
			return 0;
		} else {
			return Integer.valueOf(res.get(0).get("SORT_NUMBER").toString());
		}
	}

	@Override
	protected List<Map<String, String>> genTotalRow() {
		return null;
	}


	/**
	 *  对department的操作会清空departmentPost树的缓存，下同
	 *  PostService中有相同配置
	 * @param entity	实体对象
	 */
	@Caching(evict = {
			@CacheEvict(key = "'departmentPostTree'"),
			@CacheEvict(key = "'departmentPostTreePermission'")
	})
	@Override
	public void create(Department entity) {
		super.create(entity);
	}

	@Caching(evict = {
			@CacheEvict(key = "'departmentPostTree'"),
			@CacheEvict(key = "'departmentPostTreePermission'")
	})
	@Override
	public void modify(Department entity) {
		super.modify(entity);
	}

	@Caching(evict = {
			@CacheEvict(key = "'departmentPostTree'"),
			@CacheEvict(key = "'departmentPostTreePermission'")
	})
	@Override
	public void remove(String id, String accountId) {
		super.remove(id, accountId);
	}

	@Caching(evict = {
			@CacheEvict(key = "'departmentPostTree'"),
			@CacheEvict(key = "'departmentPostTreePermission'")
	})
	@Override
	public void remove(Department entity) {
		super.remove(entity);
	}

	@Override
	protected void beforeRemove(String id) {
		Account account = AuthCache.account();
		if(account == null) {
			throw new AuthenticationException("用户未登录");
		}

		if(!isLeaf(id)) {
			throw new BusinessException("该部门下存在子部门, 无法删除");
		}

		//删除校验：部门-人员
		SQL sql = new SQL().SELECT("ID").FROM("AUTH_DEPARTMENT_PERSON");
		sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
		sql.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'");
		sql.WHERE("DEPARTMENT_ID = '" + id + "'");
		if(dao.countSearch(sql) > 0){
			throw new BusinessException("该部门已绑定人员,无法删除");
		}
	}
}