/*
 * SysDictDao.java Copyright 2018 yonyou, Inc. All rights reserved.
 */
package com.yoogun.base.repository;

import com.yoogun.base.domain.model.SysFolderFile;
import com.yoogun.core.repository.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * 系统-文件夹与文件关系-Mybatis DAO层
 * @author Liu Jun at 2017-12-19 10:27:15
 * @since v1.0.0
 */
@Repository
public interface SysFolderFileDao extends BaseDao<SysFolderFile> {

}