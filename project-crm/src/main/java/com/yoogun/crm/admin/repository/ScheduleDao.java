/*
 * ScheduleDao.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.repository;

import com.yoogun.core.repository.BaseDao;
import com.yoogun.crm.admin.domain.model.Schedule;
import org.springframework.stereotype.Repository;


/**
 * Schedule-Mybatis DAO<br/>
 * @author Sheng Baoyu at 2018-03-20 14:59:38
 * @since v1.0.0
 */
@Repository
public interface ScheduleDao extends BaseDao<Schedule> {
}