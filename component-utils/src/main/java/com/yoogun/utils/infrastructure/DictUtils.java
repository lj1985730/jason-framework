package com.yoogun.utils.infrastructure;

import com.yoogun.utils.application.service.DictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 工具包-字典-工具类
 * @author Liu Jun at 2018-3-22 11:33:58
 * @version v1.0.0
 */
public class DictUtils {

	private static DictService dictService;

	/**
	 *  字典缓存名称，此值与ehcache-utils.xml中的cache名称一致
	 */
	public static final String DICT_CACHE_NAME = "dictCache";

	/**
	 *  注入静态对象
	 * @param dictService 字典服务器
	 */
	@Autowired
	public void setDictService(DictService dictService) {
		DictUtils.dictService = dictService;
	}

	/**
	 *获取字典名称
	 * @param code 字典编号
	 * @param category 分类
	 * @param tenantId 租户ID
	 * @param defaultValue 默认值
	 * @return 字典名称
	 */
	public static String getDictName(String code, String category, String tenantId, String defaultValue) {
		if (StringUtils.isNotBlank(category) && StringUtils.isNotBlank(code)){
			for (Map<String, Object> dict : getDicts(category, tenantId)) {
				if (code.equals(dict.get("CODE").toString())) {
					return dict.get("NAME").toString();
				}
			}
		}
		return defaultValue;
	}

	/**
	 * 获取字典编号
	 * @param name 字典名称
	 * @param category 分类
	 * @param tenantId 租户ID
	 * @param defaultCode 为null时返回默认值
	 * @return 字典编号
	 */
	public static String getDictCode(String name, String category, String tenantId, String defaultCode) {
		if (StringUtils.isNotBlank(category) && StringUtils.isNotBlank(name)) {
			for (Map<String, Object> dict : getDicts(category, tenantId)) {
				if (name.equals(dict.get("NAME").toString())) {
					return dict.get("CODE").toString();
				}
			}
		}
		return defaultCode;
	}

	/**
	 * 根据分类获取字典内容,已经检索过的内容会被缓存
	 * @param category 分类
	 * @param tenantId 租户ID
	 * @return 字典内容
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> getDicts(String category, String tenantId) {

		//查询字典，在查询时会将数据缓存
		List<Map<String, Object>> dicts = dictService.searchByCategory(category, tenantId);

		if (dicts == null) {
			dicts = new ArrayList<>();
		}
		return dicts;
	}

}
