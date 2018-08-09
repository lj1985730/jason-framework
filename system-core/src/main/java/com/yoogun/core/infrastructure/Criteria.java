package com.yoogun.core.infrastructure;

import com.yoogun.core.application.vo.TableParam;
import com.yoogun.core.domain.model.BaseEntity;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 核心-查询对象
 * @author Liu Jun at 2017-11-11 17:07:47
 * @since v1.0.0
 */
public class Criteria {

    private final Class<BaseEntity> entityClass;

    /**
     * 首个记录位置
     */
    private Integer firstResult;

    /**
     * 记录个数
     */
    private Integer maxResults;

    /**
     * 排序字段
     */
    private String orderProp;

    /**
     * 排序规则
     */
    private Boolean asc;

    /**
     * 查询条件
     */
    private List<Restrictions> restrictionsList = new ArrayList<>();

    public Criteria(Class<BaseEntity> entityClass) {
        this.entityClass = entityClass;
    }

    public static Criteria forClass(Class entityClass) {
        return new Criteria(entityClass);
    }

    /**
     * 加载固定表格查询参数
     * @param tableParam 表格查询对象
     */
    public Criteria loadCommonParams(TableParam tableParam) {
        this.setFirstResult(tableParam.getOffset());
        this.setMaxResults(tableParam.getLimit());
        //排序
        if(StringUtils.isNotBlank(tableParam.getOrder()) && StringUtils.isNotBlank(tableParam.getSort())) {
            if(tableParam.getSort().equalsIgnoreCase("ASC")) {
                this.asc(tableParam.getOrder());
            } else if(tableParam.getSort().equalsIgnoreCase("DESC")) {
                this.desc(tableParam.getOrder());
            }
        }
        return this;
    }

    public Class<BaseEntity> getEntityClass() {
        return this.entityClass;
    }

    public Criteria add(Restrictions restrictions) {
        this.restrictionsList.add(restrictions);
        return this;
    }

    public Integer getFirstResult() {
        return firstResult;
    }

    public Criteria setFirstResult(Integer firstResult) {
        this.firstResult = firstResult;
        return this;
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    public Criteria setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
        return this;
    }

    public Criteria asc(String propName) {
        this.orderProp = propName;
        this.asc = true;
        return this;
    }

    public Criteria desc(String propName) {
        this.orderProp = propName;
        this.asc = false;
        return this;
    }

    public String getOrderCol() {
        return orderProp;
    }

    public Boolean getAsc() {
        return asc;
    }


    public String whereString() {
        StringBuilder whereSql = new StringBuilder();
        for(Restrictions restrictions : restrictionsList) {
            String op = restrictions.getOp();
            String colName = getColName(restrictions.getPropertyName());
            switch (op) {
                case "IS NULL" :
                case "IS NOT NULL" :
                    whereSql.append(colName).append(" ").append(op).append(" AND ");
                    break;
                case "=" :
                case ">" :
                case ">=" :
                case "<" :
                case "<=" :
                case "<>" :
                    Object value = restrictions.getValue();
                    if(value instanceof String) {
                        whereSql.append(colName)
                                .append(" ").append(op).append(" ")
                                .append("'").append(value).append("'")
                                .append(" AND ");
                    } else if(value instanceof Integer || value instanceof Long
                            || value instanceof Float || value instanceof Double) {
                        whereSql.append(colName)
                                .append(" ").append(op).append(" ")
                                .append(value)
                                .append(" AND ");
                    } else if(value instanceof Boolean) {
                        whereSql.append(colName)
                                .append(" ").append(op).append(" ")
                                .append("'")
                                .append(((Boolean)value ? BooleanTypeHandler.BOOL_TRUE : BooleanTypeHandler.BOOL_FALSE))
                                .append("'")
                                .append(" AND ");
                    } else if(value instanceof LocalDate) {
                        LocalDate val = (LocalDate)value;
                        whereSql.append(colName)
                                .append(" ").append(op).append(" ")
                                .append("'")
                                .append(val.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                                .append("'")
                                .append(" AND ");
                    } else if(value instanceof LocalDateTime) {
                        LocalDateTime val = (LocalDateTime)value;
                        whereSql.append(colName)
                                .append(" ").append(op).append(" ")
                                .append("'")
                                .append(val.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")))
                                .append("'")
                                .append(" AND ");
                    }
                    break;
                case "IN" :
                case "NOT IN" :
                    StringBuilder inSql = new StringBuilder();
                    inSql.append(restrictions.getPropertyName())
                            .append(" ").append(op).append(" (");
                    Object[] values = (Object[])restrictions.getValue();
                    for(Object obj : values) {
                        if(obj instanceof String) {
                            inSql.append("'").append(obj).append("', ");
                        } else if(obj instanceof Integer || obj instanceof Long
                                || obj instanceof Float || obj instanceof Double) {
                            inSql.append(obj).append(", ");
                        } else if(obj instanceof Boolean) {
                            inSql.append("'").append(((Boolean)obj ? BooleanTypeHandler.BOOL_TRUE : BooleanTypeHandler.BOOL_FALSE)).append("', ");
                        }
                    }
                    whereSql.append(StringUtils.removeEnd(inSql.toString(), ",")).append(")");
                    break;

            }
        }
        return StringUtils.removeEnd(whereSql.toString(), " AND ");
    }


    /**
     * 加载列名
     * @param fieldName 属性名
     * @return 表名
     */
    private String getColName(String fieldName) {
        return Reflections.readFieldAnnotationProp(
                entityClass, fieldName, Column.class, "name").toString();
    }

}
