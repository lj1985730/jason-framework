package com.yoogun.core.infrastructure;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 核心-实体类-帮助
 * Created by Liu on 2016/8/9.
 */
public class EntityUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * 获得所有entity的Id
     * @param entityList entity集合
     * @return Id集合
     */
    public static <E extends BaseEntity> List<String> getAllId(List<E> entityList) {
        if(entityList == null || entityList.isEmpty()) {
			return null;
		}
		List<String> entityIds = new ArrayList<>();
        entityList.forEach(entity -> entityIds.add(entity.getId()));
		return entityIds;
    }

    /**
     * List转换为IN/NOT IN sql字符串
     * @param datas 数据list 支持数值和字符串以及Boolean类型
     * @return sql字符串
     */
    public static String inSql(List<?> datas) {
        StringBuilder inSql = new StringBuilder();
        for(Object data : datas) {
            if(data instanceof Long || data instanceof Integer || data instanceof Float || data instanceof Double) {
                inSql.append(data).append(", ");
            } else if(data instanceof String) {
                inSql.append("'").append(data).append("', ");
            } else if(data instanceof Boolean) {
                inSql.append("'").append(((Boolean)data ? BooleanTypeHandler.BOOL_TRUE : BooleanTypeHandler.BOOL_FALSE)).append("', ");
            }
        }
        return StringUtils.removeEnd(inSql.toString().trim(), ",");
    }

    /**
     * List转换为IN/NOT IN sql字符串
     * @param datas 数据list 支持数值和字符串以及Boolean类型
     * @return sql字符串
     */
    public static String inSql(Object[] datas) {
        return inSql(Arrays.asList(datas));
    }

    /**
     * 获取select后的column声明sql段
     * @param clazz 实体类型
     * @return select后的column声明sql段
     */
    public static <T extends BaseEntity> String selectSql(Class<T> clazz) {
        List<Property> properties = BaseEntity.loadProperties(clazz);
        StringBuilder sql = new StringBuilder("ID AS id");
        for (Property property : properties) {
            sql.append(",").append(property.getColumn()).append(" AS ").append(property.getField());
        }
        return sql.toString();
    }

    /**
     *  加载json中的属性，拼接成字符串
     * @param json json字符串
     * @param property 需要加载的属性名
     * @return 属性拼接成字符串
     */
    public static String loadJsonProperties(String json, String property) {
        if(StringUtils.isBlank(json)) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        try {
            List<Map<String, String>> list = mapper.readValue(json, new TypeReference<List<Map<String, String>>>() {});
            for(Map<String, String> map : list) {
                builder.append(map.get(property)).append(",");
            }
        } catch (IOException e) {
            throw new BusinessException("加载json中的属性出错，json:" + json + ", property:" + property, e);
        }
        return StringUtils.removeEnd(builder.toString(), ",");
    }
}
