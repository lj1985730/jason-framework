/*
 * BaseDao.java Copyright 2018 yonyou, Inc. All rights reserved.
 */
package com.yoogun.auth.repository;

import com.yoogun.auth.domain.model.AccountRole;
import com.yoogun.core.repository.BaseDao;
import org.springframework.stereotype.Repository;


/**
 * 权限-账户角色关系-Mybatis DAO层
 * @author Liu Jun at 2017-12-7 10:02:07
 * @since v1.0.0
 */
@Repository
public interface AccountRoleDao extends BaseDao<AccountRole> {
}