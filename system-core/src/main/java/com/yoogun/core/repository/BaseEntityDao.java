/*
 * BaseDao.java Copyright 2015 LandSea, Inc. All rights reserved.
 */
package com.yoogun.core.repository;

import com.yoogun.core.domain.model.BaseEntity;
import org.springframework.stereotype.Repository;

/**
 *  核心-基础-基本 DAO层
 * @author liujun
 */
@Repository
public interface BaseEntityDao extends BaseDao<BaseEntity> {
}