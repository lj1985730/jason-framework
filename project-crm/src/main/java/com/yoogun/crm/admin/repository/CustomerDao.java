/*
 * CustomerDao.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.repository;

import com.yoogun.core.repository.BaseDao;
import com.yoogun.crm.admin.domain.model.Customer;
import org.springframework.stereotype.Repository;


/**
 * Customer-Mybatis DAO<br/>
 * @author Sheng Baoyu at 2018-02-01 16:41:46
 * @since v1.0.0
 */
@Repository
public interface CustomerDao extends BaseDao<Customer> {
}