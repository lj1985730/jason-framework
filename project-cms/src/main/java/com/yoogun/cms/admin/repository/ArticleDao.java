/*
 * ArticleDao.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.cms.admin.repository;

import com.yoogun.cms.admin.domain.model.Article;
import com.yoogun.core.repository.BaseDao;
import org.springframework.stereotype.Repository;


/**
 * CMS-文章-DAO<br/>
 * @author  Liu Jun at 2018-6-5 14:34:13
 * @since v1.0.0
 */
@Repository
public interface ArticleDao extends BaseDao<Article> {

}