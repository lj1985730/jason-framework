/*
 * BaseDao.java Copyright 2018 yonyou, Inc. All rights reserved.
 */
package com.yoogun.auth.repository;

import com.yoogun.auth.domain.model.Role;
import com.yoogun.core.repository.BaseDao;
import com.yoogun.core.repository.sqlBuilder.BaseSqlBuilder;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 权限-角色-Mybatis DAO层
 * @author Liu Jun at 2017-12-7 09:56:54
 * @since v1.0.0
 */
@Repository
public interface RoleDao extends BaseDao<Role> {

    /**
     * 封装根据Mybatis SQL查询（分页）
     * @param sql	Mybatis SQL对象
     * @param offset 起始数据位置
     * @param limit	数据个数
     * @return 分页查询对象集合
     */
    @Override
    @Results({
            @Result(property = "roleRlats", column = "{superiorId = ID, tenantId = TENANT_ID}",
                    many = @Many(select = "com.yoogun.auth.repository.RoleRlatDao.searchRoleRlatBySuper"))
    })
    @SelectProvider(type = BaseSqlBuilder.class, method = "pageSearch")
    List<Role> pageSearch(SQL sql, final Integer offset, final Integer limit);
}