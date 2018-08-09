/*
 * AuthrolerlatDao.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.auth.repository;

import com.yoogun.auth.domain.model.RoleRlat;
import com.yoogun.core.repository.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 角色关系-Mybatis DAO<br/>
 * @author Wang Chong at 2018-05-01 13:27:08
 * @since v1.0.0
 */
@Repository
public interface RoleRlatDao extends BaseDao<RoleRlat> {

    /**
     * 根据上级角色id,查询下级角色
     */
    @Select("SELECT * FROM AUTH_ROLE_RLAT WHERE SUPERIOR_ID = #{superiorId} AND TENANT_ID = #{tenantId} AND DELETED = 'F'")
    List<RoleRlat> searchRoleRlatBySuper (@Param("superiorId") final String superiorId, @Param("tenantId") final String... tenantId);
}