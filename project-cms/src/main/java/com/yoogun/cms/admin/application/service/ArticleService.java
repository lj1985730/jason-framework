/*
 * ArticleService.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.cms.admin.application.service;

import com.yoogun.auth.infrastructure.BaseAuthService;
import com.yoogun.auth.infrastructure.BaseAuthTreeService;
import com.yoogun.cms.admin.domain.model.Article;
import com.yoogun.cms.admin.domain.model.Subject;
import org.springframework.stereotype.Service;


/**
 * CMS-文章-应用业务<br/>
 * 主要业务逻辑代码
 * @author Liu Jun at 2018-6-5 14:12:01
 * @since v1.0.0
 */
@Service
public class ArticleService extends BaseAuthService<Article> {

}