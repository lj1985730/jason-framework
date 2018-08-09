/*
 * TenantDao.java Copyright 2018 yonyou, Inc. All rights reserved.
 */
package com.yoogun.auth.repository;

import com.yoogun.auth.domain.model.Tenant;
import com.yoogun.core.repository.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * 权限-租户-Mybatis DAO层
 * @author Liu Jun at 2017-11-13 10:05:33
 * @since v1.0.0
 */
@Repository
public interface TenantDao extends BaseDao<Tenant> {
}