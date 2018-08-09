package com.yoogun.base.application.service;

import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthService;
import com.yoogun.base.application.vo.SysFileVo;
import com.yoogun.base.domain.model.SysFolderFile;
import com.yoogun.core.application.dto.Page;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.core.infrastructure.EntityUtils;
import com.yoogun.core.infrastructure.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 系统-文件夹与文件关系-应用业务
 * @author Liu Jun at 2017-12-19 11:04:29
 * @since V1.0.0
 */
@Service
public class SysFolderFileService extends BaseAuthService<SysFolderFile> {

    /**
     * Table分页查询
     * @param vo 查询参数
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<SysFolderFile> pageSearch(SysFileVo vo) {

        SQL sql = new SQL().SELECT("A.*, B.SIZE, B.EXTENSION").FROM(tableName + " A, SYS_FILE B");
        sql.WHERE("A.FILE_ID = B.ID");
        sql.WHERE("A." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
        sql.WHERE("A.TENANT_ID = '" + AuthCache.tenantId() + "'");

        if(StringUtils.isNotBlank(vo.getSearch())) {
            sql.WHERE("A.FILE_NAME like '%" + vo.getSearch() + "%'");
        }

        if(StringUtils.isNotBlank(vo.getFolderId())) {
            sql.WHERE("A.FOLDER_ID = '" + vo.getFolderId() + "'");
        }

        if(StringUtils.isNotBlank(vo.getBusinessDataId())) {
            sql.WHERE("A.BUSINESS_DATA_ID = '" + vo.getBusinessDataId() + "'");
        }

        if(StringUtils.isNotBlank(vo.getBusinessKey())) {
            sql.WHERE("A.BUSINESS_KEY = '" + vo.getBusinessKey() + "'");
        }

        //排序
        if(StringUtils.isNotBlank(vo.getSort()) && StringUtils.isNotBlank(vo.getOrder())) {
            try {
                sql.ORDER_BY(this.entityClass.newInstance().getColName(vo.getSort()) + " " + vo.getOrder());
            } catch (Exception e) {
                throw new BusinessException("获取'" + this.entityClass + "'的实例出错！", e);
            }
        } else {	//默认排序
            sql.ORDER_BY("A.FILE_NAME ASC");
        }

        return this.pageSearch(sql, vo.getOffset(), vo.getLimit());
    }

    /**
     * 根据文件夹获取全部文件
     * @param folderId 文件夹ID
     * @return 文件集合
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<SysFolderFile> searchFilesByFolder(String folderId) {

        if(StringUtils.isBlank(folderId)) {
            return null;
        }

        SQL sql = new SQL().SELECT("A.*, B.SIZE, B.EXTENSION").FROM(tableName + " A, SYS_FILE B");
        sql.WHERE("A.FILE_ID = B.ID");
        sql.WHERE("A." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
        sql.WHERE("A.TENANT_ID = '" + AuthCache.tenantId() + "'");
        sql.WHERE("A.FOLDER_ID = '" + folderId + "'");
        sql.ORDER_BY("A.FILE_NAME ASC");
        return this.search(sql);
    }

    /**
     * 根据业务主键查询逻辑文件主键（folderFileId）
     * @param businessId 业务主键
     * @param businessKey 业务类型（非必填）
     * @return 逻辑文件主键（folderFileId）
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<String> searchFileIdsByBusiness(String businessId, String... businessKey) {
        if(StringUtils.isBlank(businessId)) {
            return null;
        }
        SQL sql = new SQL().SELECT("ID").FROM(tableName);
        sql.WHERE(BaseEntity.DELETE_PARAM+ " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
        sql.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'");
        sql.WHERE("BUSINESS_DATA_ID = '" + businessId + "'");
        if(businessKey != null && businessKey.length > 0) { //如果传递参数，则增加业务类型条件
            sql.WHERE("BUSINESS_KEY IN (" + EntityUtils.inSql(Arrays.asList(businessKey)) + ")");
        }
        sql.ORDER_BY("LAST_MODIFY_TIME DESC");
        List<SysFolderFile> files = this.search(sql);   //查询逻辑文件PO

        return EntityUtils.getAllId(files); //解析出逻辑文件ID
    }
}
