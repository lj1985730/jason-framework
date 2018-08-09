package com.yoogun.auth.repository;

import com.yoogun.auth.domain.model.Department;
import com.yoogun.core.repository.BaseDao;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 权限-部门-Mybatis DAO层
 * @author Wang Chong at 2017-12-26 18:22
 * @since V1.0.0
 */
@Repository
public interface DepartmentDao extends BaseDao<Department> {

    /**
     * 按ID查询
     * @param departmentId 部门ID
     * @param tenantId 企业ID
     * @return 查询对象
     */
    @Select("SELECT ID, NAME FROM AUTH_DEPARTMENT WHERE ID = #{departmentId} AND TENANT_ID = #{tenantId}")
    Department searchDepartmentById(final String departmentId, final String... tenantId);
}
