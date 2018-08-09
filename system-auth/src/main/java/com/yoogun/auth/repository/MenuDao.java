/*
 * MenuDao.java Copyright 2018 yonyou, Inc. All rights reserved.
 */
package com.yoogun.auth.repository;

import com.yoogun.auth.domain.model.Menu;
import com.yoogun.core.repository.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * 权限-菜单-Mybatis DAO层
 * @author Liu Jun at 2017-11-13 10:19:04
 * @since v1.0.0
 */
@Repository
public interface MenuDao extends BaseDao<Menu> {
}