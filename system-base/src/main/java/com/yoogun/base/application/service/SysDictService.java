package com.yoogun.base.application.service;

import com.yoogun.auth.application.service.PermissionService;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthService;
import com.yoogun.base.domain.model.SysDict;
import com.yoogun.core.application.dto.Page;
import com.yoogun.core.application.vo.TableParam;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.core.infrastructure.exception.BusinessException;
import com.yoogun.utils.infrastructure.DictUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 系统-字典-应用业务
 * @author Liu Jun at 2017-11-8 15:54:02
 * @since V1.0.0
 */
@Service
@CacheConfig(cacheNames = { DictUtils.DICT_CACHE_NAME })
public class SysDictService extends BaseAuthService<SysDict> {

    /**
     * Table分页查询
     * @param vo 查询参数
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<SysDict> pageSearch(TableParam vo) {

        SQL sql = new SQL().SELECT("*").FROM(tableName);
        sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
        sql.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'");

        if(StringUtils.isNotBlank(vo.getSearch())) {
            sql.WHERE("CATEGORY_KEY LIKE '%" + vo.getSearch() + "%' OR NAME LIKE '%" + vo.getSearch() + "%'");
        }

        //排序
        if(StringUtils.isNotBlank(vo.getSort()) && StringUtils.isNotBlank(vo.getOrder())) {
            try {
                sql.ORDER_BY(this.entityClass.newInstance().getColName(vo.getSort()) + " " + vo.getOrder());
            } catch (Exception e) {
                throw new BusinessException("获取'" + this.entityClass + "'的实例出错！", e);
            }
        } else {	//默认排序
            sql.ORDER_BY("CATEGORY_KEY ASC, SORT_NUMBER ASC");
        }

        return this.pageSearch(sql, vo.getOffset(), vo.getLimit());
    }

    /**
     * 根据字典分类、父字典编码获取全部子字典
     * @param categoryKey  字典分类KEY
     * @param parentCode  父字典编码
     * @return 全部子字典
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<SysDict> getByParent(String categoryKey, Integer parentCode) {
        if(StringUtils.isBlank(categoryKey)) {
            return null;
        }
        SQL sql = new SQL().SELECT("*").FROM(tableName);
        sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
        sql.WHERE("TENANT_ID IN ('" + AuthCache.tenantId() + "', '" + PermissionService.superTenantId + "')");
        sql.WHERE("CATEGORY_KEY = '" + categoryKey + "'");
        if(parentCode != null) {
            sql.WHERE("PARENT_CODE = " + parentCode);
        }
        sql.ORDER_BY("SORT_NUMBER ASC");
        return dao.search(sql);
    }

    /**
     *  根据地区获取省份
     * @param areaCode 地区字典编码
     * @return 省份字典数据
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<SysDict> getProvinceByArea(Integer areaCode) {
        return this.getByParent("COM_GEO_PROVINCE", areaCode);
    }

    /**
     *  根据省份获取城市
     * @param provinceCode 省份字典编码
     * @return 城市字典数据
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<SysDict> getCityByProvince(Integer provinceCode) {
        return this.getByParent("COM_GEO_CITY", provinceCode);
    }

    /**
     *  根据城市获取区县
     * @param cityCode 城市字典编码
     * @return 区县字典数据
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<SysDict> getDistrictByCity(Integer cityCode) {
        return this.getByParent("COM_GEO_DISTRICT", cityCode);
    }

    /**
     * 根据类型获取字典
     * @param category 字典类型
     * @return 字典
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<SysDict> searchAll(String category) {
        return searchAll(category, AuthCache.tenantId());
    }

    /**
     * 根据类型获取字典，缓存结果集
     * @param category 字典类型
     * @param tenantId 租户ID
     * @return 字典
     */
    @Cacheable(key = "#category + '$' + #tenantId")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<SysDict> searchAll(String category, String tenantId) {

        if(StringUtils.isBlank(category)) {
            return null;
        }

        SQL sql = new SQL().SELECT("*").FROM(tableName);
        sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
        sql.WHERE("TENANT_ID IN ('" + tenantId + "', '" + SysConfigService.superTenantId + "')");
        sql.WHERE("CATEGORY_KEY = '" + category + "'");
        sql.ORDER_BY("SORT_NUMBER ASC");
        return this.search(sql);
    }

    /**
     * <p>新增单个实体对象
     * <p>DELETE和LAST_MODIFY_TIME自动写入
     * @param entity	实体对象
     */
    @Override
    @CacheEvict(allEntries=true)
    @Transactional(propagation = Propagation.REQUIRED)
    public void create(SysDict entity) {
        super.create(entity);
    }

    /**
     * <p>更新单个实体对象
     * <p>DELETE和LAST_MODIFY_TIME自动写入
     * @param entity 待更新实体对象
     */
    @Override
    @CacheEvict(allEntries=true)
    @Transactional(propagation = Propagation.REQUIRED)
    public void modify(SysDict entity) {
        super.modify(entity);
    }

    /**
     * <p>删除单个实体对象（逻辑删除）
     * <p>DELETE和LAST_MODIFY_TIME自动写入
     * <p>带有beforeRemove(id)切入点
     * @param id 待删除实体ID
     * @param accountId 账户ID
     */
    @Override
    @CacheEvict(allEntries=true)
    @Transactional(propagation = Propagation.REQUIRED)
    public void remove(String id, String accountId) {
        super.remove(id, accountId);
    }

    /**
     * <p>删除单个实体对象（逻辑删除）
     * <p>DELETE和LAST_MODIFY_TIME自动写入
     * <p>带有beforeRemove(entity)切入点
     * @param entity 待删除实体
     */
    @Override
    @CacheEvict(allEntries=true)
    @Transactional(propagation = Propagation.REQUIRED)
    public void remove(SysDict entity) {
        super.remove(entity);
    }


    /**
     * <p>批量删除实体对象（逻辑删除）
     * <p>DELETE和LAST_MODIFY_TIME自动写入
     * @param ids 待删除实体id集合
     * @param accountId 操作账户ID
     */
    @Override
    @CacheEvict(allEntries=true)
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeAll(List<String> ids, String accountId) {
        super.removeAll(ids, accountId);
    }

    /**
     * <p>删除单个实体对象（物理删除）
     * @param id 主键
     */
    @Override
    @CacheEvict(allEntries=true)
    @Transactional(propagation = Propagation.REQUIRED)
    public void destroy(Serializable id) {
        super.destroy(id);
    }

    /**
     * <p>批量删除实体对象（物理删除）<br>
     * @param idArray 待删除实体
     */
    @Override
    @CacheEvict(allEntries=true)
    @Transactional(propagation = Propagation.REQUIRED)
    public void destroyAll(String[] idArray) {
        super.destroyAll(idArray);
    }

}
