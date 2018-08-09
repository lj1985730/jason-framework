package com.yoogun.auth.application.service;

import com.yoogun.auth.domain.model.Person;
import com.yoogun.auth.domain.model.PersonPost;
import com.yoogun.auth.domain.model.Post;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthService;
import com.yoogun.auth.repository.PersonDao;
import com.yoogun.auth.repository.PostDao;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.core.infrastructure.EntityUtils;
import com.yoogun.utils.infrastructure.EhCacheUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 权限-人员岗位关系-业务层
 * @author Liu Jun at 2016-8-16 22:30:34
 * @since v1.0.0
 */
@Service
public class PersonPostService extends BaseAuthService<PersonPost> {

	@Resource
	private PostDao postDao;

	@Resource
	private PersonDao personDao;

	/**
	 * 获取岗位下人员
	 * @param postId 岗位Id
	 * @return 岗位下人员
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Person> searchPersonByPost(String postId) {

		SQL sql = new SQL();
		sql.SELECT("A.*")
				.FROM("AUTH_PERSON A", "AUTH_PERSON_POST B", "AUTH_POST C")
				.WHERE("A.DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("A.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("A.ID = B.PERSON_ID")
				.WHERE("B.DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("B.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("B.POST_ID = C.ID")
				.WHERE("C.DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("C.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("C.ID = '" + postId + "'");

		return personDao.search(sql);
	}

	/**
	 * 获取人员的岗位
	 * @param personId 人员Id
	 * @return 人员的岗位
	 */
	@Cacheable(value = EhCacheUtils.SYSTEM_CACHE, key = "'personPost|'.concat(#personId)")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Post> searchPostByPerson(String personId) {

		SQL sql = new SQL();
		sql.SELECT("A.*")
				.FROM("AUTH_POST A", "AUTH_PERSON_POST B", "AUTH_PERSON C")
				.WHERE("A.DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("A.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("A.ID = B.POST_ID")
				.WHERE("B.DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("B.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("B.PERSON_ID = C.ID")
				.WHERE("C.DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("C.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("C.ID = '" + personId + "'");

		return postDao.search(sql);
	}

	/**
	 * 设置人员的岗位
	 * @param personId 人员Id
	 * @param postIds 岗位Id集合
	 */
	@CacheEvict(value = EhCacheUtils.SYSTEM_CACHE, key = "'personPost|'.concat(#personId)")
	@Transactional(propagation = Propagation.REQUIRED)
	public void setPersonPost(String personId, String[] postIds) {
		List<String> removedIdList = this.removeAllPost(personId);	//先删除全部已绑定的数据

		List<String> newIdList = Arrays.asList(postIds);	//需要绑定的数据

		List<String> updateIdList = new ArrayList<>();	//绑定时需要更新的数据
		List<String> createIdList = new ArrayList<>();	//绑定时需要新增的数据

		newIdList.forEach(newId -> {	//saveOrUpdate分发
			if(removedIdList.contains(newId)) {
				updateIdList.add(newId);
			} else {
				createIdList.add(newId);
			}
		});

		this.updatePersonPost(personId, updateIdList);

		this.createPersonPost(personId, createIdList);
	}

	/**
	 * 删除人员的全部岗位
	 * @param personId 人员Id
	 * @return 被删除的岗位Id
	 */
	private List<String> removeAllPost(String personId) {

		SQL sql = new SQL().SELECT("*").FROM(tableName);
		sql.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'");
		sql.WHERE("PERSON_ID = '" + personId + "'");
		// 构造条件
		List<PersonPost> list = this.search(sql);

		List<String> removedId = new ArrayList<>();

		list.forEach(roleMenu -> {
			this.remove(roleMenu);
			removedId.add(roleMenu.getPostId());
		});

		return removedId;
	}

	/**
	 * 恢复人员的岗位
	 * @param personId 人员Id
	 * @param postIdList 待操作岗位Id集合
	 */
	private void updatePersonPost(String personId, List<String> postIdList) {

		if(StringUtils.isBlank(personId) || postIdList.isEmpty()) {
			return;
		}

		SQL sql = new SQL().SELECT("*").FROM(tableName);
		// 构造条件
		sql.WHERE("PERSON_ID = '" + personId + "'");
		sql.WHERE("POST_ID IN (" + EntityUtils.inSql(postIdList) + ")");
		sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_TRUE + "'");
		sql.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'");
		// 构造条件
		List<PersonPost> list = this.search(sql);

		list.forEach(personPost -> {
			personPost.setDeleted(false);
			this.modify(personPost);
		});
	}

	/**
	 * 新增人员的岗位
	 * @param personId 人员Id
	 * @param postIdList 待操作岗位Id集合
	 */
	private void createPersonPost(String personId, List<String> postIdList) {
		PersonPost personPost;
		for(String postId : postIdList) {
			personPost = new PersonPost();
			personPost.setPersonId(personId);
			personPost.setPostId(postId);
			this.create(personPost);
		}
	}
}