/*
 * DocDao.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.repository;

import com.yoogun.core.repository.BaseDao;
import com.yoogun.crm.admin.domain.model.Doc;
import org.springframework.stereotype.Repository;


/**
 * Doc-Mybatis DAO<br/>
 * @author Wang chong at 2018-03-08 13:50:37
 * @since v1.0.0
 */
@Repository
public interface DocDao extends BaseDao<Doc> {
}