/*
 * BaseDao.java Copyright 2018 yonyou, Inc. All rights reserved.
 */
package com.yoogun.auth.repository;

import com.yoogun.auth.domain.model.Account;
import com.yoogun.core.application.vo.TableParam;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.core.repository.BaseDao;
import com.yoogun.core.repository.sqlBuilder.BaseSqlBuilder;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 权限-账户-Mybatis DAO层
 * @author Liu Jun at 2017-11-13 10:06:01
 * @since v1.0.0
 */
@Repository
public interface AccountDao extends BaseDao<Account> {

    @Select("SELECT ID, ID_, FIRST_, PWD_, SALT, PERSON_ID, IS_ADMIN, LEVEL, LAST_LOGIN, " +
            "ENABLED, LOCKED, TENANT_ID, DELETED, LAST_MODIFY_ACCOUNT_ID, " +
            "LAST_MODIFY_TIME FROM ACT_ID_USER WHERE " +
            BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "' " +
            "AND FIRST_ = #{name}")
    @Results({
        @Result(id = true, column = "ID", property = "id"),
        @Result(column = "ID_", property = "id_"),
        @Result(column = "FIRST_", property = "name"),
        @Result(column = "PWD_", property = "password")
    })
    List<Account> searchByName(@Param("name") String name);


    @Results({
        @Result(id = true, column = "ID", property = "id"),
        @Result(column = "ID_", property = "id_"),
        @Result(column = "FIRST_", property = "name"),
        @Result(column = "PWD_", property = "password"),
        @Result(column = "REV_", property = "updateNum")
    })
    @SelectProvider(type = BaseSqlBuilder.class, method = "pageSearch")
    List<Account> pageSearch(SQL sql, final Integer offset, final Integer limit);

}