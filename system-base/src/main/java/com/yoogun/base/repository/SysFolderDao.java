/*
 * SysDictDao.java Copyright 2018 yonyou, Inc. All rights reserved.
 */
package com.yoogun.base.repository;

import com.yoogun.base.domain.model.SysFolder;
import com.yoogun.core.repository.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * 系统-文件夹-Mybatis DAO层
 * @author Liu Jun at 2017-12-19 10:27:15
 * @since v1.0.0
 */
@Repository
public interface SysFolderDao extends BaseDao<SysFolder> {

}