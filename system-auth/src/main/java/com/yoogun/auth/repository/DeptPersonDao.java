/*
 * AuthdeptpersonDao.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.auth.repository;

import com.yoogun.auth.domain.model.DeptPerson;
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
 * 部门-人员-Mybatis DAO<br/>
 * @author Wang Chong at 2018-04-19 11:29:59
 * @since v1.0.0
 */
@Repository
public interface DeptPersonDao extends BaseDao<DeptPerson> {

    /**
     * 封装根据Mybatis SQL查询（分页）
     * @param sql	Mybatis SQL对象
     * @param offset 起始数据位置
     * @param limit	数据个数
     * @return 分页查询对象集合
     */
    @Override
    @Results({
            @Result(property = "department", column = "{departmentId = DEPARTMENT_ID, tenantId = TENANT_ID}",
                    one = @One(select = "com.yoogun.auth.repository.DepartmentDao.searchDepartmentById")),
            @Result(property = "person", column = "{id = PERSON_ID, tenantId = TENANT_ID}",
                    one = @One(select = "com.yoogun.auth.repository.PersonDao.searchPersonById")),
    })
    @SelectProvider(type = BaseSqlBuilder.class, method = "pageSearch")
    List<DeptPerson> pageSearch(SQL sql, final Integer offset, final Integer limit);

}