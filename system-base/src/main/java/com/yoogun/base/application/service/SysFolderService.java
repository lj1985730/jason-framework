package com.yoogun.base.application.service;

import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthTreeService;
import com.yoogun.base.application.vo.SysFolderVo;
import com.yoogun.base.domain.model.SysFolder;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统-文件夹-应用业务
 * @author Liu Jun at 2017-11-8 15:54:02
 * @since V1.0.0
 */
@Service
public class SysFolderService extends BaseAuthTreeService<SysFolder> {

    /**
     * 根据条件获取文件夹
     * @param vo 查询条件
     * @return 文件夹集合
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<SysFolder> searchAll(SysFolderVo vo) {

        SQL sql = new SQL().SELECT("A.*").FROM(tableName + " A, SYS_FOLDER_FILE B");
        sql.WHERE("A.ID = B.FOLDER_ID");
        sql.WHERE("A." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
        sql.WHERE("A.TENANT_ID = '" + AuthCache.tenantId() + "'");
        sql.WHERE("B." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
        sql.WHERE("B.TENANT_ID = '" + AuthCache.tenantId() + "'");

        if(StringUtils.isNotBlank(vo.getBusinessId())) {
            sql.WHERE("B.BUSINESS_ID = '" + vo.getBusinessId() + "'");
        }

        if(StringUtils.isNotBlank(vo.getParentId())) {
            sql.WHERE("A.PARENT_ID = '" + vo.getParentId() + "'");
        }

        if(StringUtils.isNotBlank(vo.getName())) {
            sql.WHERE("A.NAME LIKE '%" + vo.getName() + "%'");
        }

        if(StringUtils.isNotBlank(vo.getCode())) {
            sql.WHERE("A.CODE LIKE '%" + vo.getCode() + "%'");
        }

        sql.ORDER_BY("A.SORT_NUMBER ASC");
        return this.search(sql);
    }
}
