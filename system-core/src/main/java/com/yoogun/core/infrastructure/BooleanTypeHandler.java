package com.yoogun.core.infrastructure;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * mybatis boolean类型转换器
 */
@MappedTypes({Boolean.class})
@MappedJdbcTypes({JdbcType.VARCHAR,JdbcType.CHAR})
public class BooleanTypeHandler implements TypeHandler<Boolean> {

    /**
     * 逻辑‘是’数据库值
     */
    public static final String BOOL_TRUE = "T";

    /**
     * 逻辑‘非’数据库值
     */
    public static final String BOOL_FALSE = "F";

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, Boolean aBoolean, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, aBoolean == null ? null : (aBoolean ? BOOL_TRUE : BOOL_FALSE));
    }

    @Override
    public Boolean getResult(ResultSet resultSet, String s) throws SQLException {
        String str = resultSet.getString(s);
        return getResult(str);
    }

    @Override
    public Boolean getResult(ResultSet resultSet, int i) throws SQLException {
        String str = resultSet.getString(i);
        return getResult(str);
    }

    @Override
    public Boolean getResult(CallableStatement callableStatement, int i) throws SQLException {
        return callableStatement.getBoolean(i);
    }

    private Boolean getResult(String str) {
        if(StringUtils.isBlank(str)) {
            return null;
        }
        Boolean result = Boolean.FALSE;
        if (str.equalsIgnoreCase(BOOL_TRUE)) {
            result = Boolean.TRUE;
        }
        return result;
    }
}
