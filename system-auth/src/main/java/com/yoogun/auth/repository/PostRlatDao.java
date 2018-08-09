package com.yoogun.auth.repository;

import com.yoogun.auth.domain.model.PostRlat;
import com.yoogun.core.repository.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * 权限-岗位上下级关系-Mybatis DAO层
 * @author Liu Jun at 2018-4-2 10:58:33
 * @since V1.0.0
 */
@Repository
public interface PostRlatDao extends BaseDao<PostRlat> {
}
