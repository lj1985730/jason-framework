package com.yoogun.meeting.admin.application.service;

import com.yoogun.auth.domain.model.Account;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthService;
import com.yoogun.core.application.dto.Page;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.core.infrastructure.exception.BusinessException;
import com.yoogun.meeting.admin.application.vo.MeetingVo;
import com.yoogun.meeting.admin.domain.model.Meeting;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * 会议-会议安排-业务层
 * @author Sheng Baoyu at 2017-12-25 15:34:00
 * @since v1.0.0
 */
@Service
public class MeetingService extends BaseAuthService<Meeting> {

    /**
     * Table分页查询
     * @param vo 查询参数VO
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<Meeting> pageSearch(MeetingVo vo) {
        SQL sql = new SQL().SELECT("*").FROM(tableName);
        sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
        sql.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'");

        //模糊查询
        if(StringUtils.isNotBlank(vo.getSearch())){
            sql.AND();
            sql.WHERE("NAME like '%" + vo.getSearch() + "%'");
        }

        //开始时间
        if(StringUtils.isNotBlank(vo.getStartDate())) {
            sql.WHERE("START_TIME >= '" + vo.getStartDate() + "'");
        }

        //排序
        if(StringUtils.isNotBlank(vo.getSort()) && StringUtils.isNotBlank(vo.getOrder())) {
            try {
                sql.ORDER_BY(this.entityClass.newInstance().getColName(vo.getSort()) + " " + vo.getOrder());
            } catch (Exception e) {
                throw new BusinessException("获取'" + this.entityClass + "'的实例出错！", e);
            }
        } else {	//默认排序
            sql.ORDER_BY("START_TIME DESC");
        }

        return this.pageSearch(sql, vo.getOffset(), vo.getLimit());
    }

    @Override
    protected void beforeCreate(Meeting entity) {
        Account account = AuthCache.account();
        if(account == null){
            throw new AuthenticationException("用户未登陆");
        }
        entity.setLastModifyAccountId(account.getId());

        String uuid = UUID.randomUUID().toString().toUpperCase();
        entity.setId(uuid);
        entity.setTenantId(account.getTenantId());
    }

    @Override
    protected void beforeModify(Meeting entity) {
        Account account = AuthCache.account();
        if(account == null) {
            throw new AuthenticationException("用户未登录");
        }
        entity.setLastModifyAccountId(account.getId());
    }

    @Override
    protected void beforeRemove(Meeting entity) {
        Account account = AuthCache.account();
        if(account == null) {
            throw new AuthenticationException("用户未登录");
        }
        entity.setLastModifyAccountId(account.getId());
    }
}
