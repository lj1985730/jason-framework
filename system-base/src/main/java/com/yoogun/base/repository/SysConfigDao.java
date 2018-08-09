/*
 * BaseDao.java Copyright 2018 yonyou, Inc. All rights reserved.
 */
package com.yoogun.base.repository;

import com.yoogun.base.domain.model.SysConfig;
import com.yoogun.core.repository.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * 系统-配置-Mybatis DAO层
 * @author Liu Jun at 2017-11-13 10:12:26
 * @since v1.0.0
 */
@Repository
public interface SysConfigDao extends BaseDao<SysConfig> {

}