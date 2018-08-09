/*
 * ScheduleService.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.application.service;

import com.yoogun.auth.application.service.PermissionService;
import com.yoogun.auth.domain.model.Account;
import com.yoogun.auth.domain.model.Department;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthService;
import com.yoogun.core.application.dto.Page;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.core.infrastructure.EntityUtils;
import com.yoogun.core.infrastructure.exception.BusinessException;
import com.yoogun.crm.admin.application.vo.ScheduleVo;
import com.yoogun.crm.admin.domain.model.Schedule;
import com.yoogun.utils.infrastructure.ExportCacheUtils;
import com.yoogun.utils.infrastructure.excel.ExcelExporter;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * 日程管理-应用业务<br/>
 * 主要业务逻辑代码
 * @author Sheng Baoyu at 2018-03-20 14:59:38
 * @since v1.0.0
 */
@Service
public class ScheduleService extends BaseAuthService<Schedule> {

	@Resource
	private PermissionService permissionService;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<Schedule> pageSearch(ScheduleVo vo) {
		SQL sql = new SQL().SELECT("SCHEDULE.*," +
				"DEPT.NAME AS DEPT_NAME," +
				"VISIT_TYPE.NAME AS SCHEDULE_VISIT_STR," +
				"CUSTOMER.CUSTOMER_NAME AS CUSTOMER_NAME," +
				"CHANCE.NAME AS CHANCE_NAME," +
				"SALES_STAGE.NAME AS CURRENT_STAGE," +
				"CHANCE.PRE_SELLERS AS PRE_SELLERS")
				.FROM(tableName +" SCHEDULE");
		sql.WHERE("SCHEDULE." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
		sql.WHERE("SCHEDULE.TENANT_ID = '" + AuthCache.tenantId() + "'");

		sql.LEFT_OUTER_JOIN("AUTH_DEPARTMENT DEPT ON SCHEDULE.COMPANY_DEPT_ID = DEPT.ID");	//所属部门
		sql.LEFT_OUTER_JOIN("SYS_DICT VISIT_TYPE ON SCHEDULE.SCHEDULE_VISIT = VISIT_TYPE.CODE AND VISIT_TYPE.CATEGORY_KEY = 'CRM_VISIT_TYPE'");	//拜访类型
		sql.LEFT_OUTER_JOIN("CU_CUSTOMER CUSTOMER ON SCHEDULE.CUSTOMER_ID = CUSTOMER.ID");	//客户
		sql.LEFT_OUTER_JOIN("CU_CHANCE CHANCE ON SCHEDULE.CHANCE_ID = CHANCE.ID");	//商机
		sql.LEFT_OUTER_JOIN("SYS_DICT SALES_STAGE ON CHANCE.CHANCE_STEP = SALES_STAGE.CODE AND SALES_STAGE.CATEGORY_KEY = 'CRM_SALES_STAGE'");	//销售阶段
		sql.LEFT_OUTER_JOIN("AUTH_PERSON CREATOR ON CREATOR.ID = SCHEDULE.SCHEDULE_CREATE_ID");	//创建人
		sql.LEFT_OUTER_JOIN("ACT_ID_USER CREATOR_ACCOUNT ON CREATOR.ID = CREATOR_ACCOUNT.PERSON_ID");	//创建账户

		//过滤条件
		if(StringUtils.isNotBlank(vo.getCustomerId())) {	//客户过滤
			sql.WHERE("CUSTOMER.ID = '" + vo.getCustomerId() + "'");
		}
		if(StringUtils.isNotBlank(vo.getChanceId())) {	//商机过滤
			sql.WHERE("CHANCE.ID = '" + vo.getChanceId() + "'");
		}
		
		//模糊查询,增加了AND，放在条件最后。否则后续条件还需要先加AND
		if(StringUtils.isNotBlank(vo.getSearch())) {
			sql.AND();
			sql.WHERE("SCHEDULE.SCHEDULE_TITLE like '%" + vo.getSearch() + "%' ");
		}

		//权限过滤
		if (!AuthCache.isAdmin()) {
			List<Account> accounts = permissionService.searchDataPermission(AuthCache.accountId());
			if(accounts != null && !accounts.isEmpty()) {
				List<String> accountIds = EntityUtils.getAllId(accounts);
				String accountId = EntityUtils.inSql(accountIds);
				sql.WHERE("(SCHEDULE.SCHEDULE_CREATE_ID = '" + AuthCache.personId() + "' OR SCHEDULE.SCHEDULE_PARTIN LIKE '%" + AuthCache.personId() + "%' OR CREATOR_ACCOUNT.ID in ("+ accountId + "))" );
			} else {
				sql.WHERE("(SCHEDULE.SCHEDULE_CREATE_ID = '" + AuthCache.personId() + "' OR SCHEDULE.SCHEDULE_PARTIN LIKE '%" + AuthCache.personId() + "%')");
			}
		}
		
		//排序
		if(StringUtils.isNotBlank(vo.getSort()) && StringUtils.isNotBlank(vo.getOrder())) {
			try {
				sql.ORDER_BY(this.entityClass.newInstance().getColName(vo.getSort()) + " " + vo.getOrder());
			} catch (Exception e) {
				throw new BusinessException("获取'" + this.entityClass + "'的实例出错！", e);
			}
		} else {
			//默认排序
			sql.ORDER_BY("SCHEDULE.LAST_MODIFY_TIME DESC");
		}

		ExportCacheUtils.put("schedule-" + AuthCache.accountId(), sql.toString());	//缓存查询脚本
		
		return this.pageSearch(sql, vo.getOffset(), vo.getLimit());
	}

	@Override
	protected void beforeCreate(Schedule entity) {
		super.beforeCreate(entity);
		entity.setScheduleCreateId(AuthCache.personId());
		Department department = AuthCache.department();
		if(department != null) {
			entity.setCompanyDeptId(department.getId());
		}
	}

	/**
	 *  导出excel
	 */
	public byte[] export() {

		Object cachedSql = ExportCacheUtils.get("schedule-" + AuthCache.accountId());
		if(cachedSql == null) {
			throw new NullPointerException("未查询到导出数据！");
		}

		//初始化导出器，并写入数据
		ExcelExporter exporter =
				new ExcelExporter(Schedule.class, "日程信息", true)
						.tenant(AuthCache.tenantId())
						.put(this.sqlSearch(cachedSql.toString()));
		byte[] fileContent = exporter.write();
		exporter.dispose();	//关闭excel处理器
		return fileContent;
	}
}