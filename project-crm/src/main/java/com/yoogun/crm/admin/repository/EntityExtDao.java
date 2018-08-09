/*
 * CustomerExtDao.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.repository;

import com.yoogun.core.repository.BaseDao;
import com.yoogun.crm.admin.domain.model.EntityExt;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * EntityExt-Mybatis DAO<br/>
 * @author Wang Chong at 2018-03-22 10:13:42
 * @since v1.0.0
 */
@Repository
public interface EntityExtDao extends BaseDao<EntityExt> {

    /**
     * 根据客户id查询实体附加信息
     * @param entityId 实体id
     */
    @Select("SELECT * FROM CU_ENTITY_EXT WHERE ENTITY_ID = #{entityId}")
    @Results({
            @Result(column = "EXT_ID", property = "ext",
                    one = @One(select = "com.yoogun.crm.admin.repository.ExtDao.selectById"))
    })
    List<EntityExt> searchByEntityId(@Param("entityId") String entityId);

}