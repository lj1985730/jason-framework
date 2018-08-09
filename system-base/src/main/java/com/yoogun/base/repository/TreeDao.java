/*
 * TreeDao.java Copyright 2018 yonyou, Inc. All rights reserved.
 */
package com.yoogun.base.repository;

import com.yoogun.core.domain.model.TreeEntity;
import com.yoogun.core.repository.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * 系统-树形数据-Mybatis DAO层
 * @author Liu Jun at 2018-1-16 13:58:38
 * @since v1.0.0
 */
@Repository
public interface TreeDao extends BaseDao<TreeEntity> {

}