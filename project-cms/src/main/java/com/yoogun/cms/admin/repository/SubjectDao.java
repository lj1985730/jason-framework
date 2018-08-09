/*
 * SubjectDao.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.cms.admin.repository;

import com.yoogun.core.repository.BaseDao;
import com.yoogun.cms.admin.domain.model.Subject;
import org.springframework.stereotype.Repository;



/**
 * CMS-主题（栏目）-DAO<br/>
 * @author  Liu Jun at 2018-6-5 14:08:34
 * @since v1.0.0
 */
@Repository
public interface SubjectDao extends BaseDao<Subject> {

}