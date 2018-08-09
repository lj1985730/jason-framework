package com.yoogun.auth.application.service;

import com.yoogun.auth.application.vo.PersonVo;
import com.yoogun.auth.domain.model.Person;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthService;
import com.yoogun.core.application.dto.Page;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.core.infrastructure.EntityUtils;
import com.yoogun.core.infrastructure.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * 权限-人员-业务层
 * @author Liu Jun at 2016-8-12 00:35:32
 * @since v1.0.0
 */
@Service
public class PersonService extends BaseAuthService<Person> {

	@Resource
	private DepartmentService departmentService;

	@Resource
	private DeptPersonService deptPersonService;

	/**
	 * Table分页查询
	 * @param vo 查询参数VO
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<Person> pageSearch(PersonVo vo) {
		SQL sql;
		if(StringUtils.isNotBlank(vo.getOrgId())) {
			sql = new SQL().SELECT("PERSON.*, " +
					"GENDER.NAME AS GENDER_NAME, " +
					"STATE.NAME AS STATE_NAME, " +
					"NATURE.NAME AS NATURE_NAME, " +
					"NATIONALITY.NAME AS NATIONALITY_NAME, " +
					"MAIN_DEPT.NAME AS DEPARTMENT_NAME");
		} else {
			sql = new SQL().SELECT("PERSON.*, " +
					"GENDER.NAME AS GENDER_NAME, " +
					"STATE.NAME AS STATE_NAME, " +
					"NATURE.NAME AS NATURE_NAME, " +
					"NATIONALITY.NAME AS NATIONALITY_NAME, " +
					"MAIN_DEPT.NAME AS DEPARTMENT_NAME");
		}
		sql.FROM(tableName + " PERSON");
		sql.WHERE("PERSON." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
		sql.WHERE("PERSON.TENANT_ID = '" + AuthCache.tenantId() + "'");
		//性别
		sql.LEFT_OUTER_JOIN("SYS_DICT GENDER ON PERSON.GENDER = GENDER.CODE " +
				"AND GENDER.CATEGORY_KEY = 'COM_PSN_GENDER' " +
				"AND GENDER." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "' " +
				"AND GENDER.TENANT_ID IN ('" + AuthCache.tenantId() + "', '" + PermissionService.superTenantId + "')");
		//民族
		sql.LEFT_OUTER_JOIN("SYS_DICT NATIONALITY ON PERSON.NATIONALITY = NATIONALITY.CODE " +
				"AND NATIONALITY.CATEGORY_KEY = 'COM_PSN_NATION' " +
				"AND NATIONALITY." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "' " +
				"AND NATIONALITY.TENANT_ID IN ('" + AuthCache.tenantId() + "', '" + PermissionService.superTenantId + "')");
		//状态
		sql.LEFT_OUTER_JOIN("SYS_DICT STATE ON PERSON.STATE = STATE.CODE " +
				"AND STATE.CATEGORY_KEY = 'PERSON_STATE' " +
				"AND STATE." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "' " +
				"AND STATE.TENANT_ID IN ('" + AuthCache.tenantId() + "', '" + PermissionService.superTenantId + "')");
		//性质
		sql.LEFT_OUTER_JOIN("SYS_DICT NATURE ON PERSON.NATURE = NATURE.CODE " +
				"AND NATURE.CATEGORY_KEY = 'COM_PSN_NATURE' " +
				"AND NATURE." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "' " +
				"AND NATURE.TENANT_ID IN ('" + AuthCache.tenantId() + "', '" + PermissionService.superTenantId + "')");
		//人员-兼职部门
		sql.LEFT_OUTER_JOIN("AUTH_DEPARTMENT_PERSON DEPARTMENT_PERSON ON PERSON.ID = DEPARTMENT_PERSON.PERSON_ID " +
				"AND DEPARTMENT_PERSON." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "' " +
				"AND DEPARTMENT_PERSON.TENANT_ID = '" + AuthCache.tenantId() + "'");
		// 兼职部门
		sql.LEFT_OUTER_JOIN("AUTH_DEPARTMENT PART_DEPT ON DEPARTMENT_PERSON.DEPARTMENT_ID = PART_DEPT.ID " +
				"AND PART_DEPT." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "' " +
				"AND PART_DEPT.TENANT_ID = '" + AuthCache.tenantId() + "'");
		// 主职部门
		sql.LEFT_OUTER_JOIN("AUTH_DEPARTMENT MAIN_DEPT ON PERSON.DEPARTMENT_ID = MAIN_DEPT.ID " +
				"AND MAIN_DEPT." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "' " +
				"AND MAIN_DEPT.TENANT_ID = '" + AuthCache.tenantId() + "'");

		// 按照部门过滤
		if(StringUtils.isNotBlank(vo.getOrgId())) {    //存在按照机构过滤条件
			//先查询出选择部门的全部下级部门ID
			String[] allRelationDepartmentIds = departmentService.getAllSubNodeId(vo.getOrgId());
			String departmentIdInSql = EntityUtils.inSql(allRelationDepartmentIds);
			sql.AND();
			sql.WHERE("MAIN_DEPT.ID IN (" + departmentIdInSql + ") OR PART_DEPT.ID IN (" + departmentIdInSql + ")");
		}

		//过滤条件--人员状态
		if(StringUtils.isNotBlank(vo.getState())) {
			sql.WHERE("PERSON.STATE= '" + vo.getState() + "'");
		}
		//过滤条件--人员分类
		if(StringUtils.isNotBlank(vo.getCategory())) {
			sql.WHERE("PERSON.CATEGORY= '" + vo.getCategory() + "'");
		}
		//过滤条件--人员性质
		if(StringUtils.isNotBlank(vo.getNature())) {
			sql.WHERE("PERSON.NATURE= '" + vo.getNature() + "'");
		}

		//模糊查询,姓名、英文名、部门、岗位
		if(StringUtils.isNotBlank(vo.getSearch())) {
			sql.AND();
			sql.WHERE("PERSON.NAME like '%" + vo.getSearch() + "%' " +
					"OR PERSON.EN_NAME like '%" + vo.getSearch() + "%' " +
					"OR DEPARTMENT.NAME like '%" + vo.getSearch() + "%' ");
		}

		//排序
		if(StringUtils.isNotBlank(vo.getSort()) && StringUtils.isNotBlank(vo.getOrder())) {
			try {
				sql.ORDER_BY(this.entityClass.newInstance().getColName(vo.getSort()) + " " + vo.getOrder());
			} catch (Exception e) {
				throw new BusinessException("获取'" + this.entityClass + "'的实例出错！", e);
			}
		} else {	//默认排序
			sql.ORDER_BY("PERSON.NAME ASC");
		}

		return this.pageSearch(sql, vo.getOffset(), vo.getLimit());
	}

	/**
	 * 判断人员是否存在
	 * @return true 存在；false 不存在
	 */
	private Boolean exist(Person person) {

		SQL sql = new SQL();
		sql.SELECT("1")
				.FROM("AUTH_PERSON A")
				.WHERE("A.DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("A.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("A.ID_NUMBER = '" + person.getIdNumber() + "'")
				.OR()
				.WHERE("(A.NAME = '" + person.getName() + "'")
				.AND()
				.WHERE("A.BIRTH_DATE = '" + person.getBirthDate() + "')");
		if(StringUtils.isBlank(person.getId())) {
			sql.AND().WHERE("A.ID <> '" + person.getId() + "'");
		}
		return dao.countSearch(sql) > 0;
	}

	@Override
	protected void beforeCreate(Person entity) {
		super.beforeCreate(entity);
		String uuid = UUID.randomUUID().toString().toUpperCase();
		entity.setId(uuid);

		//暂时不控制人员重复，人员重复判断逻辑待商议 by Liu Jun at 2018-5-8 14:04:30
//		if(exist(entity)) {
//			throw new BusinessException("该人员已存在！");
//		}

		//部门-人员表插入信息
		if(StringUtils.isBlank(entity.getDepartmentId())){
			throw new BusinessException("请选择部门！");
		}
		String[] deptIds = entity.getPartTimeDepartments().split(",");
		deptPersonService.setPersonDept(entity.getId(), deptIds);
//		for(String deptId : deptIds){
//			DeptPerson deptPerson = new DeptPerson();
//			deptPerson.setPersonId(entity.getId());
//			deptPerson.setDepartmentId(deptId);
//			deptPersonService.create(deptPerson);
//		}
	}

	@Override
	protected void beforeModify(Person entity){
		super.beforeModify(entity);
		if (StringUtils.isBlank(entity.getDepartmentId())){
			throw new BusinessException("请选择部门！");
		}
		String[] deptIds = entity.getPartTimeDepartments().split(",");
		deptPersonService.setPersonDept(entity.getId(),deptIds);
	}

	@Override
	protected void beforeRemove(String id){
		super.beforeRemove(id);
		//删除部门人员信息
		deptPersonService.removeAllDept(id);
	}
}