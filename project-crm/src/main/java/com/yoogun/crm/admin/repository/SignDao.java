/*
 * SignDao.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.repository;

import com.yoogun.core.repository.BaseDao;
import com.yoogun.crm.admin.domain.model.Sign;
import org.springframework.stereotype.Repository;


/**
 * Sign-Mybatis DAO<br/>
 * @author Wang chong at 2018-03-06 15:39:52
 * @since v1.0.0
 */
@Repository
public interface SignDao extends BaseDao<Sign> {
}