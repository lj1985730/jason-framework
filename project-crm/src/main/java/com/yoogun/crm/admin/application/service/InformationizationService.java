package com.yoogun.crm.admin.application.service;

import com.yoogun.auth.application.service.PermissionService;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthService;
import com.yoogun.base.application.service.SysDictService;
import com.yoogun.base.domain.model.SysDict;
import com.yoogun.core.application.dto.Page;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.core.infrastructure.exception.BusinessException;
import com.yoogun.crm.admin.domain.model.Informationization;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 客户管理-客户信息化明细-业务层
 * @author Liu Jun at 2018-7-20 10:55:05
 * @since v1.0.0
 */
@Service
public class InformationizationService extends BaseAuthService<Informationization> {

    @Resource
    private SysDictService sysDictService;

    /**
     * 根据客户获取信息化详情
     * @param customerId 客户ID
     * @return 信息化详情
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<Informationization> searchByCustomer(String customerId) {

        if(StringUtils.isBlank(customerId)) {
            throw new BusinessException("客户ID不可为空！");
        }

        SQL sql = new SQL().SELECT("INFO.*, " +
                "SUBJECT.NAME AS SUBJECT_TEXT, " +
                "BRAND.NAME AS BRAND_TEXT, " +
                "PRODUCT_LINE.NAME AS PRODUCT_LINE_TEXT, " +
                "SATISFACTION.NAME AS SATISFACTION_TEXT, " +
                "STATE.NAME AS STATE_TEXT")
                .FROM(tableName + " INFO");
        sql.WHERE("INFO." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
        sql.WHERE("INFO.TENANT_ID = '" + AuthCache.tenantId() + "'");

        sql.WHERE("INFO.CUSTOMER_ID = '" + customerId + "'");    //过滤客户

        //系统科目
        sql.LEFT_OUTER_JOIN("SYS_DICT SUBJECT ON INFO.SUBJECT = SUBJECT.CODE " +
                "AND SUBJECT.CATEGORY_KEY = 'CRM_CSR_INFO_SUBJECT' " +
                "AND SUBJECT." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "' " +
                "AND SUBJECT.TENANT_ID IN ('" + AuthCache.tenantId() + "', '" + PermissionService.superTenantId + "')");

        //软件品牌
        sql.LEFT_OUTER_JOIN("SYS_DICT BRAND ON INFO.BRAND = BRAND.CODE " +
                "AND BRAND.CATEGORY_KEY = 'CRM_SOFTWARE_BRAND' " +
                "AND BRAND." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "' " +
                "AND BRAND.TENANT_ID IN ('" + AuthCache.tenantId() + "', '" + PermissionService.superTenantId + "')");

        //产品线
        sql.LEFT_OUTER_JOIN("SYS_DICT PRODUCT_LINE ON INFO.PRODUCT_LINE = PRODUCT_LINE.CODE " +
                "AND PRODUCT_LINE.CATEGORY_KEY = 'CRM_PROD_LINE' " +
                "AND PRODUCT_LINE." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "' " +
                "AND PRODUCT_LINE.TENANT_ID IN ('" + AuthCache.tenantId() + "', '" + PermissionService.superTenantId + "')");

        //满意度
        sql.LEFT_OUTER_JOIN("SYS_DICT SATISFACTION ON INFO.SATISFACTION = SATISFACTION.CODE " +
                "AND SATISFACTION.CATEGORY_KEY = 'CRM_CSR_INFO_SATISFACTION' " +
                "AND SATISFACTION." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "' " +
                "AND SATISFACTION.TENANT_ID IN ('" + AuthCache.tenantId() + "', '" + PermissionService.superTenantId + "')");

        //运行状况
        sql.LEFT_OUTER_JOIN("SYS_DICT STATE ON INFO.STATE = STATE.CODE " +
                "AND STATE.CATEGORY_KEY = 'CRM_CSR_INFO_STATE' " +
                "AND STATE." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "' " +
                "AND STATE.TENANT_ID IN ('" + AuthCache.tenantId() + "', '" + PermissionService.superTenantId + "')");

        sql.ORDER_BY("INFO.SUBJECT ASC");    //默认排序

        return super.pageSearch(sql, 0, 100);
    }

    /**
     *  创建新企业的整套信息化数据
     * @param customerId 企业ID
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void prepareInfos(String customerId) {
        if(StringUtils.isBlank(customerId)) {
            return;
        }
            List<SysDict> infoSubjects = sysDictService.searchAll("CRM_CSR_INFO_SUBJECT");
        if(infoSubjects == null || infoSubjects.isEmpty()) {
            return;
        }

        infoSubjects.forEach(dict -> {
            //判断没有该科目才新增，当科目字典新增时，更新客户即可新增信息化科目数据
            if(!subjectInfoExist(customerId, dict.getCode())) {
                Informationization info = new Informationization();
                info.setCustomerId(customerId);
                info.setSubject(dict.getCode());
                this.create(info);
            }
        });
    }

    /**
     *  判断客户对应系统科目的信息化信息是否存在
     * @param customerId    客户ID
     * @param subject   系统科目
     * @return true 存在；false 不存在
     */
    private Boolean subjectInfoExist(String customerId, Integer subject) {
        if(StringUtils.isBlank(customerId) || subject == null) {    //参数为空则按已存在处理
            return true;
        }
        String sql = "SELECT ID FROM CRM_INFORMATIONIZATION " +
                "WHERE CUSTOMER_ID = '" + customerId + "' " +
                "AND SUBJECT = " + subject + " " +
                "AND " + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "' " +
                "AND TENANT_ID IN ('" + AuthCache.tenantId() + "', '" + PermissionService.superTenantId + "')";
        List<?> result = dao.sqlSearch(sql);

        return result != null && !result.isEmpty();
    }

}
