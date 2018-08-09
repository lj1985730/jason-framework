package com.yoogun.auth.application.service;

import com.yoogun.auth.application.dto.DepartmentPostTreeview;
import com.yoogun.auth.domain.model.Account;
import com.yoogun.auth.domain.model.Department;
import com.yoogun.auth.domain.model.Person;
import com.yoogun.auth.domain.model.Post;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthService;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.core.infrastructure.EntityUtils;
import com.yoogun.core.infrastructure.exception.BusinessException;
import com.yoogun.utils.application.dto.BsTreeview;
import com.yoogun.utils.infrastructure.BsTreeviewUtils;
import com.yoogun.utils.infrastructure.EhCacheUtils;
import org.apache.ibatis.jdbc.SQL;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 权限-岗位-业务层
 * @author Liu Jun at 2016-8-14 22:28:14
 * @version v1.0.0
 */
@Service
@CacheConfig(cacheNames = { EhCacheUtils.SYSTEM_CACHE })
public class PostService extends BaseAuthService<Post> {

	@Resource
	private PersonPostService personPostService;

	@Resource
	private DepartmentService departmentService;

	/**
	 *  查询全部部门下的岗位ID
	 * @param departmentIds	部门ID集合
	 * @return 全部部门下的岗位ID
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<String> searchPostIdsByDepartments(String[] departmentIds) {
		if(departmentIds == null || departmentIds.length == 0) {
			return null;
		}
		SQL sql = new SQL().SELECT_DISTINCT("ID").FROM("AUTH_POST POST")
				.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("DEPARTMENT_ID IN (" + EntityUtils.inSql(departmentIds) + ")");
		List<Map<String, Object>> posts = this.sqlSearchNoMapped(sql.toString());
		List<String> postIds = new ArrayList<>();
		posts.forEach(post -> postIds.add(post.get("ID").toString()));
		return postIds;
	}

	/**
	 * 获取部门下的岗位
	 * <p>缓存(''post)
	 * @param departmentId 部门Id
	 * @return 部门下的岗位
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Post> searchPostByDepartment(String departmentId) {

		SQL sql = new SQL();
		sql.SELECT("*").FROM(tableName)
				.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("DEPARTMENT_ID = '" + departmentId + "'");
		sql.ORDER_BY("IS_LEADER DESC, NAME ASC");

		return this.search(sql);
	}

	/**
	 * 查询部门岗位拼接treeview
	 * @return 部门岗位拼接treeview
	 */
	@Cacheable(key = "'departmentPostTree'")
	public List<DepartmentPostTreeview> searchTreeview() {
		List<Department> departments = departmentService.searchAll();	//查询出全部department

		List<DepartmentPostTreeview> treeviews =	//转换department节点成DTO
				BsTreeviewUtils.load(DepartmentPostTreeview.class, departments);

		this.appendPostToDepartment(treeviews);	//增加post节点

		return treeviews;
	}

	/**
	 * 查询管辖的部门岗位拼接treeview
	 * @return 部门岗位拼接treeview
	 */
	@Cacheable(key = "'departmentPostTreePermission'")
	public List<DepartmentPostTreeview> searchSubordinateTreeview() {
		List<Department> departments = departmentService.searchAll();	//查询出全部department

		List<DepartmentPostTreeview> treeviews =	//转换department节点成DTO
				BsTreeviewUtils.load(DepartmentPostTreeview.class, departments);

		this.appendPostToDepartment(treeviews, true);	//增加post节点

		this.setUnSelectable(treeviews);

		return treeviews;
	}

	/**
	 * 递归设置树全部节点不可选中
	 * @param nodes	树节点集合
	 */
	private <E extends BsTreeview> void setUnSelectable(List<E> nodes) {

		if(nodes == null || nodes.isEmpty()) {
			return;
		}

		for(E node : nodes) {	//全部节点不可选，使用节点上的checkbox操作
			node.setSelectable(false);

			if(!(node instanceof DepartmentPostTreeview)) {
				continue;
			}

			DepartmentPostTreeview departmentNode = (DepartmentPostTreeview)node;
			this.setUnSelectable(departmentNode.getNodes());
		}
	}

	/**
	 * 循环查询部门的岗位，将岗位保存到部门子节点下
	 * @param departmentNodes 部门节点
	 * @param showPermission 是否显示选择权限区域，权限包括数据权限和审批权限，默认为false
	 */
	private <E extends BsTreeview> void appendPostToDepartment(List<E> departmentNodes, Boolean... showPermission) {

		if(departmentNodes == null || departmentNodes.isEmpty()) {
			return;
		}

		for(E node : departmentNodes) {	//遍历department，处理post

			if(!(node instanceof DepartmentPostTreeview)) {
				continue;
			}

			DepartmentPostTreeview departmentNode = (DepartmentPostTreeview)node;

			if(departmentNode.getType() == 2) {	//不处理post节点
				continue;
			}
			List<Post> posts = this.searchPostByDepartment(departmentNode.getId());
			for(Post post : posts) {	//循环将post加入到department子节点中
				departmentNode.getNodes().add(new DepartmentPostTreeview(post, showPermission));
			}
			appendPostToDepartment(departmentNode.getNodes(), showPermission);	//递归处理岗位
		}
	}

	/**
	 *  对post的操作会清空departmentPost树的缓存，下同
	 *  DepartmentService中有相同配置
	 * @param entity	实体对象
	 */
	@Caching(evict = {
			@CacheEvict(key = "'departmentPostTree'"),
			@CacheEvict(key = "'departmentPostTreePermission'")
	})
	@Override
	public void create(Post entity) {
		super.create(entity);
	}

	@Caching(evict = {
			@CacheEvict(key = "'departmentPostTree'"),
			@CacheEvict(key = "'departmentPostTreePermission'")
	})
	@Override
	public void modify(Post entity) {
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
	public void remove(Post entity) {
		super.remove(entity);
	}

	@Override
	protected void beforeRemove(Post entity) {
		Account account = AuthCache.account();
		if(account == null) {
			throw new AuthenticationException("用户未登录");
		}
		entity.setLastModifyAccountId(account.getId());
		entity.setTenantId(AuthCache.tenantId());

		List<Person> persons = personPostService.searchPersonByPost(entity.getId());

		if (persons != null && !persons.isEmpty()) {
			throw new BusinessException("该岗位下存在人员");
		}
	}
}