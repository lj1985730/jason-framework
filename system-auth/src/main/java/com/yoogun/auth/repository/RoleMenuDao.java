package com.yoogun.auth.repository;

import com.yoogun.auth.domain.model.RoleMenu;
import com.yoogun.core.repository.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * 权限-角色菜单-Mybatis DAO层
 * @author Wang Chong at 2018-01-09 22:01
 * @since V1.0.0
 */
@Repository
public interface RoleMenuDao extends BaseDao<RoleMenu> {
}
