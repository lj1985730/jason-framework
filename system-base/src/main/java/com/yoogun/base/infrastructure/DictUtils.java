package com.yoogun.base.infrastructure;

import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.base.application.service.SysDictService;
import com.yoogun.base.domain.model.SysDict;
import com.yoogun.core.infrastructure.SpringContextUtils;
import com.yoogun.core.infrastructure.mapper.JsonMapper;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统-字典-工具类
 * @author Liu Jun at 2017-12-19 10:36:09
 * @version v1.0.0
 */
public class DictUtils {

	public static final String DICT_CACHE_NAME = com.yoogun.utils.infrastructure.DictUtils.DICT_CACHE_NAME;
	
	private static SysDictService dictService = SpringContextUtils.getBean(SysDictService.class);

	/**
	 * 根据分类获取字典内容
	 * @param category 分类
	 * @param tenantId 租户Id
	 * @return 字典内容
	 */
	public static List<SysDict> getDictList(String category, String tenantId) {
		return dictService.searchAll(category, tenantId);
	}

	/**
	 * 获取字典编号
	 * @param name 字典名称
	 * @param category 分类
	 * @param defaultCode 为null时返回默认值
	 * @return 字典编号
	 */
	public static String getDictCode(String name, String category, String defaultCode) {
		if (StringUtils.isNotBlank(category) && StringUtils.isNotBlank(name)) {
			for (SysDict dict : getDictList(category, AuthCache.tenantId())){
				if (name.equals(dict.getName())) {
					return dict.getCode().toString();
				}
			}
		}
		return defaultCode;
	}

	/**
	 *获取字典名称
	 * @param code 字典编号
	 * @param category 分类
	 * @param defaultValue 默认值
	 * @return 字典名称
	 */
	public static String getDictName(Integer code, String category, String defaultValue){
		if (StringUtils.isNotBlank(category) && code != null) {
			return getDictName(code.toString(), category, defaultValue);
		}
		return defaultValue;
	}

	/**
	 *获取字典名称
	 * @param code 字典编号
	 * @param category 分类
	 * @param defaultValue 默认值
	 * @return 字典名称
	 */
	public static String getDictName(String code, String category, String defaultValue){
		if (StringUtils.isNotBlank(category) && StringUtils.isNotBlank(code)) {
			for (SysDict dict : getDictList(category, AuthCache.tenantId())) {
				if (code.equals(dict.getCode().toString())) {
					return dict.getName();
				}
			}
		}
		return defaultValue;
	}

	/**
	 * 获取字典名称组
	 * @param codes 字典编号组，','分割
	 * @param category 字典分类
	 * @param defaultNames 为null时返回默认值
	 * @return 字典名称组
	 */
	public static String getDictNames(String codes, String category, String defaultNames) {
		if (StringUtils.isNotBlank(category) && StringUtils.isNotBlank(codes)) {
			List<String> valueList = new ArrayList<>();
			for (String value : StringUtils.split(codes, ",")) {
				valueList.add(getDictName(value, category, defaultNames));
			}
			return StringUtils.join(valueList, ",");
		}
		return defaultNames;
	}
	
	/**
	 * 返回字典列表（JSON）
	 * @param category 字典分类
	 * @return 典列表（JSON）
	 */
	public static String getDictListJson(String category) {
		return JsonMapper.toJsonString(getDictList(category, AuthCache.tenantId()));
	}
	
}
