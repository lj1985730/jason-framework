package com.yoogun.base.application.service;

import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthService;
import com.yoogun.base.domain.model.SysComboData;
import com.yoogun.base.domain.model.SysDict;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 系统-下拉框数据-应用业务
 * @author Liu Jun at 2017-11-20 13:37:15
 * @since v1.0.0
 */
@Service
@Transactional
public class SysComboDataService extends BaseAuthService<SysComboData> {

	private static final String TENANT_PLACEHOLDER = "<TENANT_ID>";

	@Resource
	private SysDictService sysDictService;

	/**
	 * 获取下拉框数据
	 * @param key 下拉框业务KEY
	 * @param params 过滤条件值集合
	 * @return 下拉框数据
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Map<String, Object>> loadComboData(String key, String[] params) {
		//优先检索字典
		List<SysDict> dicts = sysDictService.searchAll(key);
		if(dicts != null && !dicts.isEmpty()) {
			return loadComboByDict(dicts);
		}
		//如果字典没有则从SYS_COMBO_DATA表中读取
		String comboSql = this.loadComboSql(key, params);
		if(comboSql == null) {
			return Collections.emptyList();
		}
		return this.dao.sqlSearchNoMapped(comboSql);
	}

	/**
	 * 字典数据转换为下拉框数据
	 * @param dicts 字典数据集合
	 * @return 下拉框数据
	 */
	private List<Map<String, Object>> loadComboByDict(List<SysDict> dicts) {
		List<Map<String, Object>> result = new ArrayList<>();
		Map<String, Object> map;
		for (SysDict dict : dicts) {
			map = new HashMap<>();
			map.put("id", dict.getCode());
			map.put("value", dict.getName());
			result.add(map);
		}
		return result;
	}

	/**
	 * 加载combo查询语句
	 * @param key combo键
	 * @param params 过滤条件值集合
	 * @return 查询sql
	 */
	private String loadComboSql(String key, String[] params) {

		if(StringUtils.isBlank(key)) {
			return null;
		}

		SQL sql = new SQL().SELECT("*").FROM(tableName);

		sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
		sql.WHERE("TENANT_ID IN ('" + AuthCache.tenantId() + "', '" + SysConfigService.superTenantId + "')");
		sql.WHERE("BUSINESS_KEY = '" + key + "'");

		List<SysComboData> list = this.search(sql);

		if(list == null || list.isEmpty()) {
			return null;
		}

		SysComboData comboData = list.get(0);

		String sqlStr = comboData.getContent();	//主查询sql内容

		if(params != null && params.length > 0) {	//如果有过滤参数集合，拼接过滤条件
			sqlStr += " " + this.loadConditions(comboData, params);
		}

		sqlStr += " " + comboData.getOrderBy();	//拼接排序

		//转换占位符
		return StringUtils.replaceAll(sqlStr, TENANT_PLACEHOLDER, AuthCache.tenantId());
	}

	/**
	 * 解析处理条件语句
	 * @param comboData 数据对象
	 * @param params 参数
	 * @return 解析后的条件语句
	 */
	private String loadConditions(SysComboData comboData, String[] params) {
		if(comboData == null) {
			return "";
		}

		String result;
		String content = comboData.getContent();
		//条件语句需要根据主语句中是否含有WHERE关键字来确定拼接时要增加的关键字
		if(StringUtils.containsIgnoreCase(content, "WHERE")) {	//如果主语句中含WHERE关键字
			if(StringUtils.startsWithIgnoreCase(comboData.getConditions().trim(), "AND ")) {
				result = comboData.getConditions();	//如果条件以AND开头直接取条件
			} else {
				result = " AND " + comboData.getConditions();	//如果原始条件不以AND开头，将AND补上
			}
		} else {	//如果主语句不含WHERE关键字
			if(StringUtils.startsWithIgnoreCase(comboData.getConditions().trim(), "WHERE ")) {
				result = comboData.getConditions();	//如果条件以WHERE开头直接取条件
			} else {
				result = " WHERE " + comboData.getConditions();//如果原始条件不以WHERE开头，将WHERE补上
			}
		}

		for(int i = 0; i < params.length; i ++) {	//依次将原始条件语句中的参数占位符替换为真实参数
			StringUtils.replaceIgnoreCase(result, "<PARAM"+ i + ">", params[0]);
		}

		return result;
	}
}