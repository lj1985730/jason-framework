/*
 * ExtDao.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.repository;

import com.yoogun.core.repository.BaseDao;
import com.yoogun.crm.admin.domain.model.Ext;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


/**
 * Ext-Mybatis DAO<br/>
 * @author Wang Chong at 2018-03-22 10:11:47
 * @since v1.0.0
 */
@Repository
public interface ExtDao extends BaseDao<Ext> {

    @Select("SELECT * FROM CU_EXT WHERE ID = #{id}")
    Ext selectById (String id);

}