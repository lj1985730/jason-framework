package com.yoogun.base.application.service;

import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthTreeService;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.domain.model.TreeEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.core.infrastructure.EntityUtils;
import com.yoogun.core.infrastructure.Reflections;
import com.yoogun.core.infrastructure.exception.BusinessException;
import com.yoogun.utils.application.dto.CommonTreeview;
import com.yoogun.utils.infrastructure.BsTreeviewUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 业务层-通用树形实体<br>
 * 使用TreeEntity充当泛型<br>
 * 充当通用树状数据Service层
 * @author Liu Jun
 * @version 2018-1-16 11:39:07
 */
@Service
public class CommonTreeService extends BaseAuthTreeService<TreeEntity> {

    /**
     * 查询树形DTO数据
     * @param entityName 实体名
     * @param rootId    根节点Id
     * @return 树形数据
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<CommonTreeview> searchTreeview(String entityName, String rootId) {

        if(StringUtils.isBlank(entityName)) {
            throw new NullPointerException("实体类型不可为空！");
        }

        List<TreeEntity> treeEntities = this.searchTree(entityName, rootId);    //查询树形实体集合

        return BsTreeviewUtils.load(CommonTreeview.class, treeEntities);

    }

    /**
     * 查询树形实体数据
     * @param entityName 实体名
     * @param rootId    根节点Id
     * @return 树形数据
     */
    private List<TreeEntity> searchTree(String entityName, String rootId) {

        if(StringUtils.isBlank(entityName)) {
            throw new NullPointerException("实体类型不可为空！");
        }
        String tableName;
        try {
            //读取表名
            Class clazz = Class.forName(entityName);
            Object tableNameObj = Reflections.readClassAnnotationProp(clazz, Table.class, "name");
            tableName = tableNameObj == null ? "" : tableNameObj.toString();
        } catch (ClassNotFoundException e) {
            throw new BusinessException("未找到实体类型：" + entityName);
        }

        if(StringUtils.isBlank(rootId)) {   //如果没有根节点，则搜索全部数据
            return this.searchAll(tableName);
        } else {    //否则以根节点向下搜索
            return this.getAllChildren(tableName, rootId);
        }
    }

    /**
     * 获取全部树形数据，只过滤租户和删除标识
     * @param tableName 表名
     * @return 全部树形数据
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<TreeEntity> searchAll(String tableName) {
        if(StringUtils.isBlank(tableName)) {
            return null;
        }
        SQL sql = new SQL().SELECT(EntityUtils.selectSql(TreeEntity.class)).FROM(tableName);
        sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
        sql.WHERE( "TENANT_ID = '" + AuthCache.tenantId()+ "'");
        sql.ORDER_BY("SORT_NUMBER ASC");
        List<TreeEntity> entities = dao.search(sql);
        return (entities == null || entities.isEmpty()) ? null : entities;
    }

    /**
     * 查询单个节点
     * @param tableName 表名
     * @param id 节点主键
     * @return 单个节点对象
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public TreeEntity searchById(String id, String tableName) {
        if(StringUtils.isBlank(id) || StringUtils.isBlank(tableName)) {
            return null;
        }
        SQL sql = new SQL().SELECT(EntityUtils.selectSql(TreeEntity.class)).FROM(tableName);
        sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
        sql.WHERE( "TENANT_ID = '" + AuthCache.tenantId()+ "'");
        sql.WHERE( "ID = '" + id + "'");
        List<TreeEntity> result = dao.search(sql);
        return (result == null || result.isEmpty()) ? null : result.get(0);
    }

    /**
     * 取对象所有子节点
     * @param id 目标对象
     * @return 自身以及子节点list
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<TreeEntity> getAllChildren(String tableName, String id) {
        if(StringUtils.isBlank(id) || StringUtils.isBlank(tableName)) {
            return null;
        }
        TreeEntity rootNode = this.searchById(id);
        if(rootNode == null) {
            return null;
        }

        List<TreeEntity> result = new ArrayList<>();
        result.add(rootNode);
        return this.getAllChildren(tableName, result, new String[] {id});
    }

    /**
     * 递归获取全部子节点
     * @param tableName 表名
     * @param result 结果集
     * @param parentIds 父Id数组
     * @return 全部子节点
     */
    private List<TreeEntity> getAllChildren(String tableName, List<TreeEntity> result, String[] parentIds) {
        List<TreeEntity> children = this.getChildren(tableName, parentIds);
        if(children == null) {
            return result;
        } else {
            String[] childrenIds = new String[children.size()];
            for (int i = 0; i < children.size(); i++) {
                childrenIds[i] = children.get(i).getId();
            }
            result.addAll(children);
            return this.getAllChildren(tableName, result, childrenIds);	//递归调用
        }
    }

    /**
     * 获取所有子节点
     * @param tableName	 表名
     * @param parentIds	 父Id数组
     * @return 子节点集合
     */
    private List<TreeEntity> getChildren(String tableName, String[] parentIds) {
        if(parentIds == null || ArrayUtils.isEmpty(parentIds)) {
            return null;
        }

        SQL sql = new SQL().SELECT(EntityUtils.selectSql(TreeEntity.class)).FROM(tableName);
        sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
        sql.WHERE( "TENANT_ID = '" + AuthCache.tenantId()+ "'");
        sql.WHERE( "PARENT_ID IN (" + EntityUtils.inSql(Arrays.asList(parentIds)) + ")");
        sql.ORDER_BY("SORT_NUMBER ASC");
        List<TreeEntity> subList = dao.search(sql);
        return (subList == null || subList.isEmpty()) ? null : subList;
    }
}