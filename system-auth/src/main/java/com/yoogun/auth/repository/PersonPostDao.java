package com.yoogun.auth.repository;

import com.yoogun.auth.domain.model.PersonPost;
import com.yoogun.core.repository.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * 权限-人员岗位关系-Mybatis DAO层
 * @author Liu Jun at 2018-4-2 13:06:12
 * @since V1.0.0
 */
@Repository
public interface PersonPostDao extends BaseDao<PersonPost> {
}
