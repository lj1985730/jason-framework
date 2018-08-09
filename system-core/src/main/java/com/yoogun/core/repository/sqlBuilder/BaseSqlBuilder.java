package com.yoogun.core.repository.sqlBuilder;

import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.*;
import com.yoogun.core.infrastructure.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * 核心-基本SQL构建器
 * @author Liu jun at 2017-11-10 17:07:59
 * @since v1.0.0
 */
public class BaseSqlBuilder {

    public static final String CURRENT_TIME = "CURRENT_TIMESTAMP";

    /**
     * 新增SQL增加固定内容
     * @param sql 新增脚本
     */
    protected void addInsertCommonColumns(SQL sql) {
        sql.VALUES("ID", UUID.randomUUID().toString().toUpperCase())
                .VALUES("TENANT_ID", "#{tenantId}")
                .VALUES("DELETED", BooleanTypeHandler.BOOL_FALSE)
                .VALUES("LAST_MODIFY_ACCOUNT_ID", "#{lastModifyAccountId}")
                .VALUES("LAST_MODIFY_TIME", "CURRENT_TIMESTAMP");
    }

    /**
     * 修改SQL增加固定内容
     * @param entity 修改实体
     * @param sql 修改脚本
     */
    protected void addUpdateCommonColumns(BaseEntity entity, SQL sql) {
        if(entity.getDeleted() != null) {
            sql.SET("DELETE = #{delete}");
        }
        if(StringUtils.isNotBlank(entity.getTenantId())) {
            sql.SET("TENANT_ID = #{tenantId}");
        }
        if(StringUtils.isNotBlank(entity.getLastModifyAccountId())) {
            sql.SET("LAST_MODIFY_ACCOUNT_ID = #{lastModifyAccountId}");
        }
        if(entity.getLastModifyTime() != null) {
            sql.SET("LAST_MODIFY_TIME = #{lastModifyTime}");
        } else {
            sql.SET("LAST_MODIFY_TIME = CURRENT_TIMESTAMP");
        }
    }

    /**
     * 根据ID查询单个数据脚本
     * @param clazz 数据实体类型
     * @param id 数据ID
     * @param tenantId 租户ID
     */
    public <E extends BaseEntity> String searchById(final Class<E> clazz, final Serializable id, final String... tenantId) {
        //拼接SQL
        SQL sql = new SQL().SELECT("*").FROM(readTableName(clazz))
                .WHERE("DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'")
                .WHERE("ID = '" + id + "'");

        //企业过滤
        if(tenantId.length != 0 && StringUtils.isNotBlank(tenantId[0])) {
            sql.WHERE("TENANT_ID = '" + tenantId[0] + "'");
        }

        return sql.toString();
    }

    /**
     * 根据属性值查询数据脚本，只支持String/数值/Boolean类型
     * @param clazz 数据实体类型
     * @param propName 属性名
     * @param value 属性值
     * @param tenantId 租户ID
     */
    public <E extends BaseEntity> String searchByProp(final Class<E> clazz, final String propName, final Object value, final String... tenantId) {

        //获取全部关联数据库的属性
        try {

            BaseEntity obj = clazz.newInstance();

            List<Property> props = obj.loadProperties();
            //拼接SQL
            SQL sql = new SQL().SELECT(EntityUtils.selectSql(clazz)).FROM(readTableName(clazz))
                    .WHERE("DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'");

            if(value instanceof String) {
                sql.WHERE(obj.getColName(propName) + " = '" + value + "'");
            } else if(value instanceof Integer || value instanceof Long
                    || value instanceof Float || value instanceof Double) {
                sql.WHERE(obj.getColName(propName) + " = " + value + "");
            } else if(value instanceof Boolean) {
                sql.WHERE(obj.getColName(propName)
                        + " = '" + ((Boolean)value ? BooleanTypeHandler.BOOL_TRUE : BooleanTypeHandler.BOOL_FALSE) + "'");
            }

            //企业过滤
            if(tenantId.length != 0 && StringUtils.isNotBlank(tenantId[0])) {
                sql.WHERE("TENANT_ID = '" + tenantId[0] + "'");
            }

            return sql.toString();
        } catch (Exception e) {
            throw new BusinessException("实体类型：" + clazz.getName() + "不是BaseEntity的子类！");
        }
    }

    /**
     * 根据数据PO查询数据脚本，PO中的非null内容均会被当做查询条件
     * @param entity 数据PO
     * @param tenantId 租户ID
     */
    public <E extends BaseEntity> String searchByPo(final E entity, final String... tenantId) {
        //获取表名
        String tableName = entity.getTableName();

        //拼接SQL
        SQL sql = new SQL().SELECT("*").FROM(tableName)
                .WHERE("DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'");

        //获取全部属性
        List<Property> props = entity.loadProperties();

        for(Property prop : props) {
            if(!entity.isValueNull(prop.getField())) {
                sql.WHERE(prop.getColumn() + " = #{entity." + prop.getField() + "}");
            }
        }

        //企业过滤
        if(tenantId.length != 0 && StringUtils.isNotBlank(tenantId[0])) {
            sql.WHERE("TENANT_ID = '" + tenantId[0] + "'");
        }

        return sql.toString();
    }

    /**
     * 查询全部脚本
     * @param clazz 数据实体类型
     * @param tenantId 租户ID
     */
    public <E extends BaseEntity> String searchAll(final Class<E> clazz, final String... tenantId) {
        //拼接SQL
        SQL sql = new SQL().SELECT("*").FROM(readTableName(clazz))
                .WHERE("DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'");

        //企业过滤
        if(tenantId.length != 0 && StringUtils.isNotBlank(tenantId[0])) {
            sql.WHERE("TENANT_ID = '" + tenantId[0] + "'");
        }

        return sql.toString();
    }

    /**
     * 排序查询脚本
     * @param clazz 数据实体类型
     * @param tenantId 租户ID
     */
    public <E extends BaseEntity> String searchAllSortable(final Class<E> clazz, final String orderColumn, final String order, final String... tenantId) {
        //拼接SQL
        SQL sql = new SQL().SELECT("*").FROM(readTableName(clazz))
                .WHERE("DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'");

        //企业过滤
        if(tenantId.length != 0 && StringUtils.isNotBlank(tenantId[0])) {
            sql.WHERE("TENANT_ID = '" + tenantId[0] + "'");
        }

        //排序
        sql.ORDER_BY(orderColumn + " " + order);

        return sql.toString();
    }

    /**
     * mybatis SQL查询脚本
     * @param sql 查询对象
     */
    public String search(final SQL sql) {
        return sql.toString();
    }

    /**
     * mybatis SQL分页查询脚本
     * @param sql 查询对象
     * @param offset 起始数据位置
     * @param limit	数据个数
     */
    public String pageSearch(SQL sql, final Integer offset, final Integer limit) {
        //mysql分页
        return "SELECT _DATA.* FROM (" + sql.toString() + ") _DATA LIMIT " + offset + ", " + limit;
    }

    /**
     * 根据SQL查询数据个数脚本
     * @param sql 查询sql
     */
    public String countSearch(SQL sql) {
        String sqlResult = "SELECT COUNT(1) FROM (";
        return sqlResult + sql.toString() + ") _DATA";
    }

    /**
     * 原生sql查询
     * @param sql 原生sql
     */
    public String nativeSqlSearch(final String sql) {
        return sql;
    }

    /**
     * QBC查询脚本
     * @param criteria 查询对象
     */
    public String qbcSearch(Criteria criteria) {
        Class<BaseEntity> clazz = criteria.getEntityClass();

        //拼装sql
        SQL sql = new SQL().SELECT("*").FROM(readTableName(clazz));

        sql.WHERE(criteria.whereString());

        //排序
        if(StringUtils.isNotBlank(criteria.getOrderCol()) &&
                criteria.getAsc() != null) {
            sql.ORDER_BY(criteria.getOrderCol() + (criteria.getAsc() ? " ASC" : " DESC"));
        }

        return sql.toString();

    }

    /**
     * QBC分页查询脚本
     * @param criteria 查询对象
     */
    public String qbcPageSearch(Criteria criteria) {
        Class<BaseEntity> clazz = criteria.getEntityClass();

        //拼装sql
        SQL sql = new SQL().SELECT("*").FROM(readTableName(clazz));

        sql.WHERE(criteria.whereString());

        //排序
        sql.ORDER_BY(criteria.getOrderCol() + (criteria.getAsc() ? " ASC" : " DESC"));

        //mysql分页
        return "SELECT _DATA.* FROM (" + sql.toString() + ") _DATA LIMIT " + criteria.getFirstResult() + ", " + criteria.getMaxResults();

    }

    /**
     * 查询数据总数
     * @param criteria 查询对象
     */
    public String qbcCountSearch(Criteria criteria) {

        Class<BaseEntity> clazz = criteria.getEntityClass();

        String sqlResult = "SELECT COUNT(1) FROM (";

        SQL sql = new SQL().SELECT("COUNT(1)").FROM(readTableName(clazz));

        sql.WHERE(criteria.whereString());

        return sqlResult + sql.toString() + ") _DATA";
    }

    /**
     * 新增脚本
     * <p>1、如果参数PO无ID，则随机生成UUID
     * <p>2、赋值DELETED为false
     * <p>3、自动写入最后编辑时间
     * @param entity 新增数据PO
     */
    public <E extends BaseEntity> String insert(E entity) {

        //获取全部关联数据库的属性
        List<Property> props = entity.loadProperties();

        SQL sql = new SQL().INSERT_INTO(entity.getTableName());

        //生成UUID主键
        if(StringUtils.isBlank(entity.getId())) {
            sql.VALUES("ID", "'" + UUID.randomUUID().toString().toUpperCase() + "'");
        } else {
            sql.VALUES("ID", "#{id}");
        }

        /*
         * 写入字段值，排除掉DELETED和LAST_MODIFY_TIME
         */
        for(Property prop : props) {
            if(!prop.getColumn().equalsIgnoreCase("LAST_MODIFY_TIME")
                    && !prop.getColumn().equalsIgnoreCase("DELETED")) {
                sql.VALUES(prop.getColumn(), "#{" + prop.getField() + prop.getTypeHandler() + "}");
            }
        }
        sql.VALUES("DELETED", "'" + BooleanTypeHandler.BOOL_FALSE + "'");  //设置DELETED为false
        sql.VALUES("LAST_MODIFY_TIME", CURRENT_TIME);  //写入最后编辑时间

        return sql.toString();
    }

    /**
     * 更新脚本
     * <p>1、只更新属性不为null的内容
     * <p>2、赋值DELETED为false
     * <p>3、自动写入最后编辑时间
     * @param entity 更新数据PO
     */
    public <E extends BaseEntity> String update(E entity) {

        //确认主键非空
        Object id = Reflections.invokeGetter(entity, "id");
        if(id == null) {
            throw new NullPointerException("对象：" + entity + "的主键值为空！");
        }

        //获取全部关联数据库的属性
        List<Property> props = entity.loadProperties();

        //拼接SQL
        SQL sql = new SQL().UPDATE(entity.getTableName());

        for(Property prop : props) {
            //不为null的属性进行更新，将LAST_MODIFY_TIME字段排除
            if(Reflections.invokeGetter(entity, prop.getField()) != null
                    && !prop.getColumn().equalsIgnoreCase("LAST_MODIFY_TIME")
                    && !prop.getColumn().equalsIgnoreCase("DELETED")) {
                sql.SET(prop.getColumn() + " = #{" + prop.getField() + prop.getTypeHandler() + "}");
            }
        }
        sql.SET("DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'");  //设置DELETED为false
        sql.SET("LAST_MODIFY_TIME = " + CURRENT_TIME);  //更新最后编辑时间

        sql.WHERE("ID = '" + entity.getId() + "'");

        return sql.toString();
    }

    /**
     * 逻辑删除脚本
     * <p>1、赋值DELETED为true
     * <p>2、自动写入最后编辑时间
     * <p>3、accountId不为空则更新最后编辑人
     * @param clazz 操作实体类型
     * @param id 要删除的主键
     * @param accountId 操作人账户ID
     */
    public <E extends BaseEntity> String delete(final Class<E> clazz, final String id, final String accountId) {
        //组装SQL
        SQL sql = new SQL().UPDATE(readTableName(clazz))
                .SET("DELETED = '" + BooleanTypeHandler.BOOL_TRUE + "'")
                .SET("LAST_MODIFY_TIME = " + CURRENT_TIME);

        if(StringUtils.isNotBlank(accountId)) {
            sql.SET("LAST_MODIFY_ACCOUNT_ID = '" + accountId + "'");
        }

        sql.WHERE("ID = '" + id + "'");

        return sql.toString();
    }

    /**
     * 批量逻辑删除脚本
     * <p>1、赋值DELETED为true
     * <p>2、自动写入最后编辑时间
     * <p>3、accountId不为空则更新最后编辑人
     * @param clazz 操作实体类型
     * @param ids 要删除的主键集合
     * @param accountId 操作人账户ID
     */
    public <E extends BaseEntity> String deleteAll(final Class<E> clazz, final List<String> ids, final String accountId) {
        //组装SQL
        SQL sql = new SQL().UPDATE(readTableName(clazz))
                .SET("DELETED = '" + BooleanTypeHandler.BOOL_TRUE + "'")
                .SET("LAST_MODIFY_TIME = " + CURRENT_TIME);

        if(StringUtils.isNotBlank(accountId)) {
            sql.SET("LAST_MODIFY_ACCOUNT_ID = '" + accountId + "'");
        }

        StringBuilder stringBuilder = new StringBuilder();

        sql.WHERE("ID IN (" + EntityUtils.inSql(ids) + ")");

        return sql.toString();
    }

    /**
     * 物理删除脚本，执行DELETE sql脚本
     * @param clazz 操作实体类型
     * @param id 要删除的主键
     */
    public <E extends BaseEntity> String destroy(final Class<E> clazz, final String id) {
        //组装SQL
        SQL sql = new SQL().DELETE_FROM(readTableName(clazz));
        sql.WHERE("ID = '" + id + "'");

        return sql.toString();
    }

    /**
     * 批量物理删除脚本，执行DELETE sql脚本
     * @param clazz 操作实体类型
     * @param idArray 要删除的主键集合
     */
    public <E extends BaseEntity> String destroyAll(final Class<E> clazz, final String[] idArray) {
        //组装SQL
        SQL sql = new SQL().DELETE_FROM(readTableName(clazz));
        StringBuilder stringBuilder = new StringBuilder();

        // 拼接‘IN’中的内容
        for(String id : idArray) {
            stringBuilder.append("'").append(id).append("'").append(",");
        }
        String idStr = StringUtils.removeEnd(stringBuilder.toString(), ",");
        sql.WHERE("ID IN (" + idStr + ")");

        return sql.toString();
    }

    /**
     * 获取实体类表名
     * @param clazz 实体类
     * @return 实体类表名
     */
    private static <E extends BaseEntity>  String readTableName(final Class<E> clazz) {
        //获取表名
        Object tableNameObj = Reflections.readClassAnnotationProp(clazz, Table.class, "name");
        if(tableNameObj == null) {
            throw new BusinessException("Could not find annotation [Table] on class [" + clazz.getName() + "]");
        }
        return tableNameObj.toString();
    }
}
