/*
 * BaseDao.java Copyright 2015 LandSea, Inc. All rights reserved.
 */
package com.yoogun.core.repository;

import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.repository.sqlBuilder.BaseSqlBuilder;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *  核心-基础-Hibernate DAO层
 * @author liujun
 */
@Component
public interface BaseDao<E extends BaseEntity> {

	/**
	 * 按ID查询
	 * @param clazz 数据实体类型
	 * @param id 查询对象ID
	 * @param tenantId 租户ID
	 * @return 查询对象
	 */
	@SelectProvider(type = BaseSqlBuilder.class, method = "searchById")
	E searchById(final Class<E> clazz, final Serializable id, final String... tenantId);

	/**
	 * 按照属性查询
	 * @param clazz 数据实体类型
	 * @param propName 属性名
	 * @param value 属性值
	 * @param tenantId 租户ID
	 * @return 查询对象集合
	 */
	@SelectProvider(type = BaseSqlBuilder.class, method = "searchByProp")
	List<E> searchByProp(final Class<E> clazz, final String propName, final Object value, final String... tenantId);

	/**
	 * 根据PO查询数据，PO中的非null内容均会被当做查询条件
	 * @param entity 数据PO
	 * @param tenantId 租户ID
	 * @return 查询对象集合
	 */
	@SelectProvider(type = BaseSqlBuilder.class, method = "searchByPo")
	List<E> searchByPo(final E entity, final String... tenantId);

	/**
	 * 查询全部对象
	 * @param clazz 实体类型
	 * @param tenantId 租户ID
	 * @return 查询对象集合
	 */
	@SelectProvider(type = BaseSqlBuilder.class, method = "searchAll")
	List<E> searchAll(final Class<E> clazz, final String... tenantId);
	
	/**
	 * 排序查询全部对象
	 * @param orderColumn 排序属性名
	 * @param order ASC or DESC
	 * @param tenantId 租户ID
	 * @return 查询对象集合
	 */
	@SelectProvider(type = BaseSqlBuilder.class, method = "searchAllSortable")
	List<E> searchAllSortable(final Class<E> clazz, final String orderColumn, final String order, final String... tenantId);

	/**
	 * 封装根据Mybatis SQL查询
	 * @param sql	Mybatis SQL对象
	 * @return 查询对象集合
	 */
	@SelectProvider(type = BaseSqlBuilder.class, method = "search")
	List<E> search(final SQL sql);

	/**
	 * 封装根据Mybatis SQL查询（分页）
	 * @param sql	Mybatis SQL对象
	 * @param offset 起始数据位置
	 * @param limit	数据个数
	 * @return 分页查询对象集合
	 */
	@SelectProvider(type = BaseSqlBuilder.class, method = "pageSearch")
	List<E> pageSearch(SQL sql, final Integer offset, final Integer limit);

	/**
	 * 封装根据sql查询记录数
	 * @param sql	查询sq
	 * @return 查询记录数
	 */
	@SelectProvider(type = BaseSqlBuilder.class, method = "countSearch")
	Integer countSearch(SQL sql);

	/**
	 * 封装根据SQL查询
	 * @param sql	SQL语句
	 * @return 查询对象集合
	 */
	@SelectProvider(type = BaseSqlBuilder.class, method = "nativeSqlSearch")
	List<E> sqlSearch(final String sql);

	/**
	 * 根据原生sql查询非绑定对象
	 * @param sql 查询SQL
	 * @return List 查询对象集合
	 */
	@SelectProvider(type = BaseSqlBuilder.class, method = "nativeSqlSearch")
	List<Map<String, Object>> sqlSearchNoMapped(final String sql);
	
	/**
	 * 保存
	 * <p>1、如果参数PO无ID，则随机生成UUID
	 * <p>2、赋值DELETED为false
	 * <p>3、自动写入最后编辑时间
	 * @param entity 待保存对象
	 */
	@InsertProvider(type = BaseSqlBuilder.class, method = "insert")
	void insert(E entity);

	/**
	 * 更新
	 * <p>1、只更新属性不为null的内容
	 * <p>2、赋值DELETED为false
	 * <p>3、自动写入最后编辑时间
	 * @param entity 待更新对象
	 */
	@UpdateProvider(type = BaseSqlBuilder.class, method = "update")
	void update(E entity);

	/**
	 * 按ID逻辑删除
	 * <p>1、赋值DELETED为true
	 * <p>2、自动写入最后编辑时间
	 * <p>3、accountId不为空则更新最后编辑人
	 * @param id 待删除对象ID
	 */
	@UpdateProvider(type = BaseSqlBuilder.class, method = "delete")
	void delete(final Class<E> clazz, final String id, final String accountId);

	/**
	 * 按ID数组批量逻辑删除
	 * <p>1、赋值DELETED为true
	 * <p>2、自动写入最后编辑时间
	 * <p>3、accountId不为空则更新最后编辑人
	 * @param ids 待删除对象ID集合
	 */
	@UpdateProvider(type = BaseSqlBuilder.class, method = "deleteAll")
	void deleteAll(final Class<E> clazz, final List<String> ids, final String accountId);

	/**
	 * 按ID物理删除
	 * @param clazz 操作实体类型
	 * @param id 待删除对象ID
	 */
	@DeleteProvider(type = BaseSqlBuilder.class, method = "destroy")
	void destroy(final Class<E> clazz, final Serializable id);

	/**
	 * 按ID数组批量物理删除
	 * @param clazz 操作实体类型
	 * @param idArray 要删除的主键集合
	 */
	@DeleteProvider(type = BaseSqlBuilder.class, method = "destroyAll")
	void destroyAll(final Class<E> clazz, final String[] idArray);

}