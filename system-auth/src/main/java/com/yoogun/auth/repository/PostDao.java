/*
 * ButtonDao.java Copyright 2018 yonyou, Inc. All rights reserved.
 */
package com.yoogun.auth.repository;

import com.yoogun.auth.domain.model.Post;
import com.yoogun.core.repository.BaseDao;
import com.yoogun.core.repository.sqlBuilder.BaseSqlBuilder;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限-岗位-Mybatis DAO层
 * @author Liu Jun at 2018-1-11 10:51:24
 * @since v1.0.0
 */
@Repository
public interface PostDao extends BaseDao<Post> {

    /**
     * 封装根据Mybatis SQL查询
     * @param sql	Mybatis SQL对象
     * @return 查询对象集合
     */
    @Override
    @Results({
            @Result(property = "department", column = "{departmentId = DEPARTMENT_ID, tenantId = TENANT_ID}",
                    one = @One(select = "com.yoogun.auth.repository.DepartmentDao.searchDepartmentById"))
    })
    @SelectProvider(type = BaseSqlBuilder.class, method = "search")
    List<Post> search(final SQL sql);
}