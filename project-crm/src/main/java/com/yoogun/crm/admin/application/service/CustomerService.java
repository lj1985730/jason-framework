/*
 * CustomerService.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.application.service;

import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthService;
import com.yoogun.core.application.dto.Page;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.core.infrastructure.exception.BusinessException;
import com.yoogun.crm.admin.application.vo.CustomerVo;
import com.yoogun.crm.admin.domain.model.Contact;
import com.yoogun.crm.admin.domain.model.Customer;
import com.yoogun.utils.infrastructure.EhCacheUtils;
import com.yoogun.utils.infrastructure.ExportCacheUtils;
import com.yoogun.utils.infrastructure.excel.ExcelExporter;
import com.yoogun.workflow.application.service.WorkflowProcessService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


/**
 * 客户管理-应用业务<br/>
 * 主要业务逻辑代码
 * @author Liu Jun at 2018-7-20 10:54:17
 * @since v1.0.0
 */
@Service
public class CustomerService extends BaseAuthService<Customer> {

	/**
	 * 工作流定义-客户准入
	 */
	private static final String WORKFLOW_DEF_CUSTOMER_ADMITTANCE = "customerAdmittance";

	@Resource
	private ContactService contactService;

	@Resource
	private InformationizationService informationizationService;

	@Resource
	private WorkflowProcessService workflowProcessService;

	@Cacheable(value = EhCacheUtils.SYSTEM_CACHE)
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<Customer> pageSearch(CustomerVo vo) {

		SQL sql = new SQL().SELECT("CUSTOMER.*")
				.FROM(tableName + " CUSTOMER");
		sql.WHERE("CUSTOMER." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
		sql.WHERE("CUSTOMER.TENANT_ID = '" + AuthCache.tenantId() + "'");

		//过滤条件
		if(StringUtils.isNotBlank(vo.getOrigin())) {
			sql.WHERE("CUSTOMER.ORIGIN = " + vo.getOrigin());
		}
		if(StringUtils.isNotBlank(vo.getArea())) {
			sql.WHERE("CUSTOMER.AREA = " + vo.getArea());
		}
		if(StringUtils.isNotBlank(vo.getProvince())) {
			sql.WHERE("CUSTOMER.PROVINCE = " + vo.getProvince());
		}
		if(StringUtils.isNotBlank(vo.getCity())) {
			sql.WHERE("CUSTOMER.CITY = " + vo.getCity());
		}
		if(StringUtils.isNotBlank(vo.getDistrict())) {
			sql.WHERE("CUSTOMER.DISTRICT = " + vo.getDistrict());
		}
		if(StringUtils.isNotBlank(vo.getAddress())) {
			sql.WHERE("CUSTOMER.ADDRESS LIKE '%" + vo.getAddress() + "%'");
		}
		if(StringUtils.isNotBlank(vo.getLegalRepresentative())) {
			sql.WHERE("CUSTOMER.LEGAL_REPRESENTATIVE LIKE '%" + vo.getLegalRepresentative() + "%'");
		}
		if(StringUtils.isNotBlank(vo.getEstablishmentDate())) {
			sql.WHERE("DATE_FORMAT(CUSTOMER.ESTABLISHMENT_DATE, '%Y') = '" + vo.getEstablishmentDate() + "'");
		}
		if(StringUtils.isNotBlank(vo.getMinRegisteredCapital())) {
			sql.WHERE("CUSTOMER.REGISTERED_CAPITAL >= " + vo.getMinRegisteredCapital());
		}
		if(StringUtils.isNotBlank(vo.getMaxRegisteredCapital())) {
			sql.WHERE("CUSTOMER.REGISTERED_CAPITAL <= " + vo.getMaxRegisteredCapital());
		}
		if(StringUtils.isNotBlank(vo.getNature())) {
			sql.WHERE("CUSTOMER.NATURE = " + vo.getNature());
		}
		if(StringUtils.isNotBlank(vo.getIndustry())) {
			sql.WHERE("CUSTOMER.INDUSTRY = " + vo.getIndustry());
		}
		if(StringUtils.isNotBlank(vo.getListingStatus())) {
			sql.WHERE("CUSTOMER.LISTING_STATUS = " + vo.getListingStatus());
		}
		if(StringUtils.isNotBlank(vo.getIsGroup())) {
			sql.WHERE("CUSTOMER.IS_GROUP = '" + vo.getIsGroup() + "'");
		}
		if(StringUtils.isNotBlank(vo.getSize())) {
			sql.WHERE("CUSTOMER.SIZE = " + vo.getSize());
		}
		if(StringUtils.isNotBlank(vo.getMinTotalAssets())) {
			sql.WHERE("CUSTOMER.TOTAL_ASSETS >= " + vo.getMinTotalAssets());
		}
		if(StringUtils.isNotBlank(vo.getMaxTotalAssets())) {
			sql.WHERE("CUSTOMER.TOTAL_ASSETS <= " + vo.getMaxTotalAssets());
		}
		if(StringUtils.isNotBlank(vo.getEmployeeSize())) {
			sql.WHERE("CUSTOMER.EMPLOYEE_SIZE = " + vo.getEmployeeSize());
		}
		if(StringUtils.isNotBlank(vo.getAnnualOutputScale())) {
			sql.WHERE("CUSTOMER.ANNUAL_OUTPUT_SCALE = " + vo.getAnnualOutputScale());
		}
		if(StringUtils.isNotBlank(vo.getMinAnnualOutputValue())) {
			sql.WHERE("CUSTOMER.ANNUAL_OUTPUT_VALUE >= " + vo.getMinAnnualOutputValue());
		}
		if(StringUtils.isNotBlank(vo.getMaxAnnualOutputValue())) {
			sql.WHERE("CUSTOMER.ANNUAL_OUTPUT_VALUE <= " + vo.getMaxAnnualOutputValue());
		}
		if(StringUtils.isNotBlank(vo.getOperateStatus())) {
			sql.WHERE("CUSTOMER.OPERATE_STATUS = " + vo.getOperateStatus());
		}
		if(StringUtils.isNotBlank(vo.getMarketPosition())) {
			sql.WHERE("CUSTOMER.MARKET_POSITION = " + vo.getMarketPosition());
		}
		if(StringUtils.isNotBlank(vo.getPotential())) {
			sql.WHERE("CUSTOMER.POTENTIAL = " + vo.getPotential());
		}

        if(StringUtils.isNotBlank(vo.getName())) {
            sql.AND();
            sql.WHERE("CUSTOMER.NAME LIKE '%" + vo.getName() + "%'" +
					" OR CUSTOMER.SHORT_NAME LIKE '%" + vo.getName() + "%'" +
					" OR CUSTOMER.USCC LIKE '%" + vo.getName() + "%'");
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
			sql.ORDER_BY("CUSTOMER.LAST_MODIFY_TIME DESC");
		}

		ExportCacheUtils.put("customer-" + AuthCache.accountId(), sql.toString());	//缓存查询脚本

		return super.pageSearch(sql, vo.getOffset(), vo.getLimit());
	}

	/**
	 *  导出excel
	 */
	public byte[] export() {

		Object cachedSql = ExportCacheUtils.get("customer-" + AuthCache.accountId());
		if(cachedSql == null) {
			throw new NullPointerException("未查询到导出数据！");
		}

		List<Customer> mainDatas = this.sqlSearch(cachedSql.toString());	//主表数据

		//初始化导出器，并写入数据
		ExcelExporter exporter =
				new ExcelExporter(Customer.class, "客户信息", false)
						.tenant(AuthCache.tenantId())
						.put(mainDatas);

		List<Contact> contacts;
		for(Customer mainData : mainDatas) {	//遍历客户
			contacts = contactService.pageSearch(mainData.getId());	//查询联系人
			exporter.append(Contact.class, mainData.getName() + "的联系人", contacts);	//联系人写入excel
		}

		byte[] fileContent = exporter.write();
		exporter.dispose();	//关闭excel处理器
		return fileContent;
	}

	/**
	 * 开启客户准入工作流
	 * @param id 客户ID
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void startCustomerAdmittanceWorkflow(String id) {
		workflowProcessService.startProcess(
				WORKFLOW_DEF_CUSTOMER_ADMITTANCE, id, AuthCache.accountId(), AuthCache.personName(), "客户准入申请", null);
	}

	/**
	 * 撤销客户准入工作流
	 * @param id 客户ID
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void cancelCustomerAdmittanceWorkflow(String id) {
		workflowProcessService.cancelProcess(
				WORKFLOW_DEF_CUSTOMER_ADMITTANCE, id, "撤销客户准入申请");
	}

	/**
	 * 保存客户信息，并且返回新增的主键
	 * @param entity 客户信息
	 * @return 新增主键
	 */
	public String createAndReturnId(Customer entity) {
		beforeCreate(entity);	//前置处理
		dao.insert(entity);
		informationizationService.prepareInfos(entity.getId());	//生成一套信息化数据
		return entity.getId();
	}

	@Override
	protected void beforeCreate(Customer entity) {
		super.beforeCreate(entity);
		entity.setId(UUID.randomUUID().toString().toUpperCase());	//手动生成ID，为了表单分步保存
		if(usccExist(entity)) {
			throw new BusinessException("客户统一社会信用代码已存在！");
		}
		entity.setCreateTime(LocalDateTime.now());
	}

	@Override
	protected void beforeModify(Customer entity) {
		super.beforeModify(entity);
		if(usccExist(entity)) {
			throw new BusinessException("客户统一社会信用代码已存在！");
		}
		informationizationService.prepareInfos(entity.getId());	//更新一套信息化数据
	}

	@Override
	protected void beforeRemove(String id) {
		super.beforeRemove(id);
		contactService.removeByCustomer(id, AuthCache.accountId());	//删联系人表数据
	}

	/**
	 * 判断uscc唯一性
	 */
	private Boolean usccExist(Customer entity) {
		if(StringUtils.isBlank(entity.getUscc())){
			throw new BusinessException("USCC为空！");
		}
		SQL sql = new SQL().SELECT("ID").FROM("CU_CUSTOMER");
		sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
		sql.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'");
		sql.WHERE("USCC = '" + entity.getUscc() + "'");
		sql.WHERE("ID != '" + entity.getId() + "'");
		return dao.countSearch(sql) > 0;
	}

}