/*
 * SysDictDao.java Copyright 2018 yonyou, Inc. All rights reserved.
 */
package com.yoogun.base.repository;

import com.yoogun.base.domain.model.SysDict;
import com.yoogun.core.repository.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * 系统-字典-Mybatis DAO层
 * @author Liu Jun at 2017-11-15 22:17:08
 * @since v1.0.0
 */
@Repository
public interface SysDictDao extends BaseDao<SysDict> {

}