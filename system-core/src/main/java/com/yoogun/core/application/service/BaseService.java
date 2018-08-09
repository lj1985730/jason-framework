package com.yoogun.core.application.service;

import com.yoogun.core.application.dto.Page;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.Reflections;
import com.yoogun.core.repository.BaseDao;
import com.yoogun.core.repository.BaseEntityDao;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.Table;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;


/**
 * 业务层-基础抽象类<br>
 * 使用泛型约束，如无必须实体类对应，可以用BaseEntity充当<br>
 * 用于业务Service层继承，提供了简单对实体类的CURD操作
 * @author Liu Jun
 * @version 2015-12-26 14:28:54
 */
@Transactional(transactionManager = "transactionManager")
public abstract class BaseService<E extends BaseEntity> {

	/**
	 * 统一注入DAO层
	 */
	@Autowired    //此处需要使用Autowired来保证按照类型选择Dao
	protected BaseDao<E> dao;

	@Resource
	protected BaseEntityDao baseEntityDao;

	/**
	 * 继承类泛型类型
	 */
	protected Class<E> entityClass;

	/**
	 * 泛型类型对应表名
	 */
	protected String tableName;

	/**
	 * 反射获取泛型Class
	 */
	@SuppressWarnings("unchecked")
	public BaseService() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entityClass = (Class<E>) params[0];
		Object tableNameObj = Reflections.readClassAnnotationProp(entityClass, Table.class, "name");
		tableName = tableNameObj == null ? "" : tableNameObj.toString();
	}

	/**
     * 查询单个实体对象
     * @param id 主键
	 * @param tenantId 租户ID
     * @return 数据对象
     */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
    public E searchById(Serializable id, String... tenantId) {
		return dao.searchById(entityClass, id, tenantId);
    }

	/**
	 * 按照属性查询
	 * @param propName 属性名
	 * @param value 属性值
	 * @param tenantId 租户ID
	 * @return 数据对象
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<E> searchByProp(String propName, Object value, String... tenantId) {
		return dao.searchByProp(entityClass, propName, value, tenantId);
	}

	/**
	 * 根据PO查询数据，PO中的非null内容均会被当做查询条件
	 * @param entity 数据PO
	 * @param tenantId 租户ID
	 * @return 查询对象集合
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<E> searchByPo(E entity, String... tenantId) {
		return dao.searchByPo(entity, tenantId);
	}

	/**
	 * 查询全部对象
	 * @param tenantId 租户ID
	 * @return 查询对象集合
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<E> searchAll(String... tenantId) {
		return dao.searchAll(entityClass, tenantId);
	}

	/**
	 * 排序查询全部对象
	 * @param orderColumn 排序属性名
	 * @param order ASC or DESC
	 * @param tenantId 租户ID
	 * @return 查询对象集合
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<E> searchAllSortable(String orderColumn, String order, String... tenantId) {
		return dao.searchAllSortable(entityClass, orderColumn, order, tenantId);
	}

	/**
	 * Mybatis SQL查询
	 * @param sql Mybatis SQL对象
	 * @return 数据集合
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<E> search(final SQL sql) {
		return dao.search(sql);
	}

	/**
	 * Mybatis SQL查询
	 * @param sql Mybatis SQL对象
	 * @param offset 开始数据位置
	 * @param limit 数据个数
	 * @param hasTotal 是否有合计行，不传或传false为无，传true为有
	 * @return 数据集合
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<E> pageSearch(SQL sql, Integer offset, Integer limit, Boolean... hasTotal) {
		Page<E> page = new Page<>(offset, limit);
		Integer total = dao.countSearch(sql);
		// 执行查询
		List<E> pageData = dao.pageSearch(sql, offset, limit);
		// 为pager绑定数据和总数
		page.setRows(pageData);	// 单页数据
		page.setTotal(total);	// 当前条件下的总数

		if(hasTotal != null && hasTotal.length != 0 && hasTotal[0]) {
			page.setFooter(this.genTotalRow());// 合计列
		}

		return page;
	}

	/**
	 * Mybatis SQL查询
	 * @param sql 查询SQL语句
	 * @return 数据集合
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<E> sqlSearch(final String sql) {
		return dao.sqlSearch(sql);
	}

	/**
	 * 根据原生sql获取数据
	 * @param sql 查询sql
	 * @return 查询结果
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Map<String, Object>> sqlSearchNoMapped(String sql) {
		return dao.sqlSearchNoMapped(sql);
	}

	/**
	 * 处理合计行，各实现类自己实现
	 * footerProp.put("houseCode", "合计"); footerProp.put("useArea", "15");
	 * @return 合计行集合
	 */
	protected List<Map<String, String>> genTotalRow() {
		return null;
	}

	/**
	 * 保存数据预处理<br>
	 * 可以对保存的数据进行部分默认字段的处理<br>
	 * @param entity 需要处理的对象
	 */
	protected abstract void beforeCreate(E entity);

	/**
     * <p>新增单个实体对象
	 * <p>DELETE和LAST_MODIFY_TIME自动写入
     * @param entity	实体对象
     */
	@Transactional(propagation = Propagation.REQUIRED)
    public void create(E entity) {
		beforeCreate(entity);	//前置处理
		dao.insert(entity);
    }

	/**
	 * 更新数据预处理<br>
	 * 可以对更新的数据进行部分删除前处理<br>
	 * @param entity 需要处理的对象
	 */
	protected abstract void beforeModify(E entity);

	/**
	 * <p>更新单个实体对象
	 * <p>DELETE和LAST_MODIFY_TIME自动写入
	 * @param entity 待更新实体对象
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void modify(E entity) {
		beforeModify(entity);	//前置处理
		dao.update(entity);
	}

	/**
	 * <p>逻辑删除数据预处理
	 * <p>可以对删除的数据进行部分删除前处理
	 * @param id 待删除实体ID
	 */
	protected void beforeRemove(String id) {
		//do nothing
	}

	/**
	 * <p>逻辑删除数据预处理
	 * <p>可以对删除的数据进行部分删除前处理
	 * @param entity 待删除实体PO
	 */
	protected abstract void beforeRemove(E entity);

	/**
	 * <p>删除单个实体对象（逻辑删除）
	 * <p>DELETE和LAST_MODIFY_TIME自动写入
	 * <p>带有beforeRemove(id)切入点
	 * @param id 待删除实体ID
	 * @param accountId 账户ID
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(String id, String accountId) {
		beforeRemove(id);	//前置处理
		dao.delete(entityClass, id, accountId);
	}

	/**
	 * <p>删除单个实体对象（逻辑删除）
	 * <p>DELETE和LAST_MODIFY_TIME自动写入
	 * <p>带有beforeRemove(entity)切入点
	 * @param entity 待删除实体
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(E entity) {
		beforeRemove(entity);	//前置处理
		dao.delete(entityClass, entity.getId(), entity.getLastModifyAccountId());
	}

	/**
	 * <p>批量删除实体对象（逻辑删除）
	 * <p>DELETE和LAST_MODIFY_TIME自动写入
	 * @param ids 待删除实体id集合
	 * @param accountId 操作账户ID
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeAll(List<String> ids, String accountId) {
		dao.deleteAll(entityClass, ids, accountId);
	}

	/**
     * <p>删除单个实体对象（物理删除）
     * @param id 主键
     */
	@Transactional(propagation = Propagation.REQUIRED)
    public void destroy(Serializable id) {
		dao.destroy(entityClass, id);
    }

	/**
	 * <p>批量删除实体对象（物理删除）<br>
	 * @param idArray 待删除实体
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void destroyAll(String[] idArray) {
		dao.destroyAll(entityClass, idArray);
	}

}