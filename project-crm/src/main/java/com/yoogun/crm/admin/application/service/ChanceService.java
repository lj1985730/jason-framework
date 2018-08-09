/*
 * ChanceService.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.application.service;

import com.yoogun.auth.application.service.PermissionService;
import com.yoogun.auth.domain.model.Account;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthService;
import com.yoogun.chart.application.dto.EchartDto;
import com.yoogun.chart.infrastructure.echarts.AxisType;
import com.yoogun.core.application.dto.Page;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.core.infrastructure.EntityUtils;
import com.yoogun.core.infrastructure.exception.BusinessException;
import com.yoogun.crm.admin.application.vo.ChanceVo;
import com.yoogun.crm.admin.domain.model.Chance;
import com.yoogun.utils.infrastructure.ExportCacheUtils;
import com.yoogun.utils.infrastructure.excel.ExcelExporter;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * 商机管理-应用业务<br/>
 * 主要业务逻辑代码
 * @author Sheng Baoyu at 2018-03-19 10:18:11
 * @since v1.0.0
 */
@Service
public class ChanceService extends BaseAuthService<Chance> {

	@Resource
	private PermissionService permissionService;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<Chance> pageSearch(ChanceVo vo) {
		SQL sql = new SQL()
				.SELECT("A.*, B.NAME AS CHANCE_STEP_NAME, C.CUSTOMER_NAME, D.NAME AS COMPANY_DEPT_NAME, E.NAME AS SALESMAN_NAME")
				.FROM(tableName + " A");
		sql.WHERE("A." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
		sql.WHERE("A.TENANT_ID = '" + AuthCache.tenantId() + "'");

		sql.LEFT_OUTER_JOIN("SYS_DICT B ON A.CHANCE_STEP = B.CODE AND B.CATEGORY_KEY = 'CRM_SALES_STAGE'");//销售阶段
		sql.LEFT_OUTER_JOIN("CU_CUSTOMER C ON A.CUSTOMER_ID = C.ID");	//客户表
		sql.LEFT_OUTER_JOIN("AUTH_DEPARTMENT D ON A.COMPANY_DEPT_ID = D.ID");	//部门表
		sql.LEFT_OUTER_JOIN("AUTH_PERSON E ON A.SALESMAN_ID = E.ID");	//人员表
		sql.LEFT_OUTER_JOIN("ACT_ID_USER F ON A.SALESMAN_ID = F.PERSON_ID");	//用户表

		if(StringUtils.isNotBlank(vo.getCustomerId())) {
			sql.WHERE("A.CUSTOMER_ID = '" + vo.getCustomerId() + "'");
		}

		//权限过滤
		if (!AuthCache.isAdmin()) {//判断是否是管理员
			List<Account> accounts = permissionService.searchDataPermission(AuthCache.accountId());//获取当前用户管辖权限
			if(accounts != null && !accounts.isEmpty()) {
				String accountIds = EntityUtils.inSql(EntityUtils.getAllId(accounts));
				//销售人员是自己的商机和参与人是自己的商机和自己管辖的下属商机
				sql.WHERE("(A.SALESMAN_ID = '" + AuthCache.personId() + "' OR A.CHANCE_PARTIN LIKE '%" + AuthCache.personId() + "%' OR F.ID IN ("+ accountIds + "))" );
			} else {
				//销售人员是自己的商机和参与人是自己的商机
				sql.WHERE("(A.SALESMAN_ID = '" + AuthCache.personId() + "' OR A.CHANCE_PARTIN LIKE '%" + AuthCache.personId() + "%')");
			}
		}

		//模糊查询,增加了AND，放在条件最后。否则后续条件还需要先加AND
		if(StringUtils.isNotBlank(vo.getSearch())) {
			sql.AND();
			sql.WHERE("A.NAME LIKE '%" + vo.getSearch() + "%' OR C.CUSTOMER_NAME LIKE '%" + vo.getSearch() + "%' ");
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
			sql.ORDER_BY("CHANCE_RESULT_DATE DESC");
		}

		ExportCacheUtils.put("chance-" + AuthCache.accountId(), sql.toString());	//缓存查询脚本

		return this.pageSearch(sql, vo.getOffset(), vo.getLimit());
	}

	/**
	 *  导出excel
	 */
	public byte[] export() {

		Object cachedSql = ExportCacheUtils.get("chance-" + AuthCache.accountId());
		if(cachedSql == null) {
			throw new NullPointerException("未查询到导出数据！");
		}

		//初始化导出器，并写入数据
		ExcelExporter exporter =
				new ExcelExporter(Chance.class, "商机信息", true)
						.tenant(AuthCache.tenantId())
						.put(this.sqlSearch(cachedSql.toString()));
		byte[] fileContent = exporter.write();
		exporter.dispose();	//关闭excel处理器
		return fileContent;
	}

	/**
	 * 人员商机柱状对比表
	 * @param vo 查询条件
	 * @return 图表数据集
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public EchartDto searchPersonChanceBar(ChanceVo vo) {
		SQL sql = new SQL()
				.SELECT("B.NAME AS NAME, COUNT(1) AS COUNT")
				.FROM(tableName + " A")
				.LEFT_OUTER_JOIN("AUTH_PERSON B ON A.SALESMAN_ID = B.ID" +
						" AND B." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'" +
						" AND B.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("A." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("A.TENANT_ID = '" + AuthCache.tenantId() + "'");

		if(StringUtils.isNotBlank(vo.getBeginDate())) {
			sql.WHERE("A.CREATE_DATE >= '" + vo.getBeginDate() + "'");
		}

		if(StringUtils.isNotBlank(vo.getEndDate())) {
			sql.WHERE("A.CREATE_DATE <= '" + vo.getEndDate() + "'");
		}

		sql.GROUP_BY("A.SALESMAN_ID");

		List<Map<String, Object>> data = dao.sqlSearchNoMapped(sql.toString());

		// 初始化chart数据集
		EchartDto dto = new EchartDto("姓名", AxisType.CATEGORY, "数量", AxisType.VALUE);

		//组装数据
		List<Object[]> chartData = new LinkedList<>();
		for(Map<String, Object> map : data) {
			chartData.add(new Object[] { map.get("NAME"), map.get("COUNT") });
		}

		return dto.appendBarData(chartData);
	}

	/**
	 * 人员日程柱状对比表
	 * @param vo 查询条件
	 * @return 图表数据集
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public EchartDto searchPersonScheduleBar(ChanceVo vo) {
		SQL sql = new SQL()
				.SELECT("A. NAME AS NAME, COUNT(1) AS COUNT")
				.FROM("AUTH_PERSON A")
				.LEFT_OUTER_JOIN("CU_SCHEDULE B ON B.SCHEDULE_PARTIN LIKE CONCAT('%', A.ID, '%')" +
						" AND B." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'" +
						" AND B.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("A." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("A.TENANT_ID = '" + AuthCache.tenantId() + "'");

		if(StringUtils.isNotBlank(vo.getBeginDate())) {
			sql.WHERE("B.SCHEDULE_END >= '" + vo.getBeginDate() + " 00:00" + "'");
		}

		if(StringUtils.isNotBlank(vo.getEndDate())) {
			sql.WHERE("B.SCHEDULE_START <= '" + vo.getEndDate() + " 59:59" + "'");
		}

		sql.GROUP_BY("A.ID, A.NAME");

		List<Map<String, Object>> data = dao.sqlSearchNoMapped(sql.toString());

		// 初始化chart数据集
		EchartDto dto = new EchartDto("姓名", AxisType.CATEGORY, "次数", AxisType.VALUE);

		//组装数据
		List<Object[]> chartData = new LinkedList<>();
		for(Map<String, Object> map : data) {
			chartData.add(new Object[] { map.get("NAME"), map.get("COUNT") });
		}

		return dto.appendBarData(chartData);
	}
}