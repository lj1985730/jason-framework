package com.yoogun.auth.infrastructure;

import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.core.infrastructure.exception.BusinessException;
import org.apache.ibatis.jdbc.SQL;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 *  查询切面，在分页查询前增加企业过滤
 *  @author Liu Jun at 2017-12-8 15:21:33
 *  @since v1.0.0
 */
@Aspect
@Component
public class SearchAspect {

    /**
     * 切入点：BaseDao.search
     * 定义Pointcut，Pointcut的名称为aspectjMethod()，此方法没有返回值和参数
     * 该方法就是一个标识，不进行调用
     */
    @Pointcut("execution(* (com.yoogun.core.repository.BaseDao+ " +
            "&& !com.yoogun.auth.repository.TenantDao).search(..))")
    private void addTenantConditionWhenSearch() {}

    /**
     * 切入点：BaseDao.countSearch
     * 定义Pointcut，Pointcut的名称为aspectjMethod()，此方法没有返回值和参数
     * 该方法就是一个标识，不进行调用
     */
    @Pointcut("execution(* (com.yoogun.core.repository.BaseDao+ " +
            "&& !com.yoogun.auth.repository.TenantDao).countSearch(..))")
    private void addTenantConditionWhenCountSearch() {}

    /**
     * 切入点：BaseDao.pageSearch
     * 定义Pointcut，Pointcut的名称为aspectjMethod()，此方法没有返回值和参数
     * 该方法就是一个标识，不进行调用
     */
    @Pointcut("execution(* (com.yoogun.core.repository.BaseDao+ " +
            "&& !com.yoogun.auth.repository.TenantDao).pageSearch(..))")
    private void addTenantConditionWhenPageSearch() {}

    /**
     * 查询数量前置方法
     * @param joinPoint 切入点对象
     */
    @Before("addTenantConditionWhenCountSearch()")
    public void beforeCountSearch(JoinPoint joinPoint) {

        Object[] args = joinPoint.getArgs();

        if(args == null || args.length != 1 || !(args[0] instanceof SQL)) {
            throw new BusinessException("参数错误");
        }

        SQL sql = (SQL)args[0];

        this.addDeletedCondition(sql);
        this.addTenantIdCondition(sql);
    }

    /**
     * 查询数量前置方法
     * @param joinPoint 切入点对象
     */
    @Before("addTenantConditionWhenPageSearch()")
    public void beforePageSearch(JoinPoint joinPoint) {

        Object[] args = joinPoint.getArgs();

        if(args == null || args.length != 3 || !(args[0] instanceof SQL)) {
            throw new BusinessException("参数错误");
        }

        SQL sql = (SQL)args[0];

        this.addDeletedCondition(sql);
        this.addTenantIdCondition(sql);

    }

    /**
     * 增加逻辑删除条件
     * @param sql 查询sql对象
     */
    private void addDeletedCondition(SQL sql) {
        //删除标识
        sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
    }

    /**
     * 增加租户ID条件
     * @param sql 查询sql对象
     */
    private void addTenantIdCondition(SQL sql) {
        //企业过滤
        if(!Objects.equals(AuthCache.tenantId(), PropertiesLoader.properties.getProperty("yonyouId"))) {
            sql.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'");
        }
    }

    private void getAlias(SQL sql) {
        String sqlStr = sql.toString();

    }
}
