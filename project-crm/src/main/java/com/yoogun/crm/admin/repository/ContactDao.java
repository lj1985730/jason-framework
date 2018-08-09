/*
 * ContactDao.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.repository;

import com.yoogun.core.repository.BaseDao;
import com.yoogun.crm.admin.domain.model.Contact;
import org.springframework.stereotype.Repository;


/**
 * Contact-Mybatis DAO<br/>
 * @author Sheng Baoyu at 2018-02-25 14:44:35
 * @since v1.0.0
 */
@Repository
public interface ContactDao extends BaseDao<Contact> {
}