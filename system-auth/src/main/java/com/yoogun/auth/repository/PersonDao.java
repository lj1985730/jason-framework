/*
 * ButtonDao.java Copyright 2018 yonyou, Inc. All rights reserved.
 */
package com.yoogun.auth.repository;

import com.yoogun.auth.domain.model.Person;
import com.yoogun.core.repository.BaseDao;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 权限-人员-Mybatis DAO层
 * @author Liu Jun at 2017-11-13 14:09:57
 * @since v1.0.0
 */
@Repository
public interface PersonDao extends BaseDao<Person> {

    /**
     * 按ID查询 （需要查询人员信息进行修改保存，所以查询人员的所有属性）
     * @param id 部门ID
     * @param tenantId 企业ID
     * @return 查询对象
     */
    @Select("SELECT * FROM AUTH_PERSON WHERE ID = #{id} AND TENANT_ID = #{tenantId} AND DELETED = 'F'")
    Person searchPersonById(final String id, final String... tenantId);
}