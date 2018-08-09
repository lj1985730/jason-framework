package com.yoogun.utils.application.service;

import com.yoogun.core.application.service.BaseService;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.utils.infrastructure.DictUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 系统-字典-应用业务
 * @author Liu Jun at 2017-11-8 15:54:02
 * @since V1.0.0
 */
@Service
@CacheConfig(cacheNames = { DictUtils.DICT_CACHE_NAME })
public class DictService extends BaseService<BaseEntity> {

    /**
     * 根据类型获取字典
     *  获取的结果将被缓存，缓存的key为category|tenantId
     * @param category 字典类型
     * @return 字典
     */
    @Cacheable(key = "#category + '|' + #tenantId")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Map<String, Object>> searchByCategory(String category, String tenantId) {

        if(StringUtils.isBlank(category)) {
            return null;
        }

        SQL sql = new SQL().SELECT("NAME, CODE").FROM("SYS_DICT");
        sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
        if(StringUtils.isNotBlank(tenantId)) {
            sql.WHERE("TENANT_ID IN ('" + tenantId + "', '" + this.loadSuperTenantId() + "')");
        }

        sql.WHERE("CATEGORY_KEY = '" + category + "'");
        sql.ORDER_BY("SORT_NUMBER ASC");
        return dao.sqlSearchNoMapped(sql.toString());
    }

    /**
     *  加载系统核心配置中的超管企业ID
     * @return 配置值
     */
    @Cacheable(key = "'superTenantId'")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String loadSuperTenantId() {
        SQL sql = new SQL()
                .SELECT("CFG_VALUE")
                .FROM("SYS_CONFIG")
                .WHERE(BaseEntity.DELETE_PARAM  + " = '" + BooleanTypeHandler.BOOL_FALSE + "'")
                .WHERE("CFG_KEY = 'superTenantId'");
        List<Map<String, Object>> sysConfigs = dao.sqlSearchNoMapped(sql.toString());
        if(sysConfigs == null || sysConfigs.isEmpty()) {
            return null;
        }
        Map<String, Object> sysConfig = sysConfigs.get(0);
        if(!sysConfig.containsKey("CFG_VALUE")) {
            return null;
        }
        return sysConfig.get("CFG_VALUE") == null ? null : sysConfig.get("CFG_VALUE").toString();
    }

    @Override
    protected void beforeCreate(BaseEntity entity) {

    }

    @Override
    protected void beforeModify(BaseEntity entity) {

    }

    @Override
    protected void beforeRemove(BaseEntity entity) {

    }
}
