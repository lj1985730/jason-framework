package com.yoogun.core.infrastructure;

import java.util.Collection;

/**
 * 核心-查询条件
 * @author Liu Jun at 2017-11-11 17:07:47
 * @since v1.0.0
 */
public class Restrictions {

    private final String propertyName;
    private Object value;
    private boolean ignoreCase;
    private final String op;

    public Restrictions(String propertyName, String op) {
        this.propertyName = propertyName;
        this.op = op;
    }

    public Restrictions(String propertyName, Object value, String op) {
        this.propertyName = propertyName;
        this.value = value;
        this.op = op;
    }

    public Restrictions(String propertyName, Object value, String op, boolean ignoreCase) {
        this.propertyName = propertyName;
        this.value = value;
        this.ignoreCase = ignoreCase;
        this.op = op;
    }

    public static Restrictions isNull(String propertyName) {
        return new Restrictions(propertyName, "IS NULL");
    }

    public static Restrictions isNotNull(String propertyName) {
        return new Restrictions(propertyName, "IS NOT NULL");
    }

    public static Restrictions eq(String propertyName, Object value) {
        return new Restrictions(propertyName, value, "=");
    }

    public static Restrictions eqOrIsNull(String propertyName, Object value) {
        return value == null ? isNull(propertyName) : eq(propertyName, value);
    }

    public static Restrictions ne(String propertyName, Object value) {
        return new Restrictions(propertyName, value, "<>");
    }

    public static Restrictions neOrIsNotNull(String propertyName, Object value) {
        return value == null ? isNotNull(propertyName) : ne(propertyName, value);
    }

    public static Restrictions like(String propertyName, Object value) {
        return new Restrictions(propertyName, value, " LIKE ");
    }

    public static Restrictions gt(String propertyName, Object value) {
        return new Restrictions(propertyName, value, ">");
    }

    public static Restrictions lt(String propertyName, Object value) {
        return new Restrictions(propertyName, value, "<");
    }

    public static Restrictions le(String propertyName, Object value) {
        return new Restrictions(propertyName, value, "<=");
    }

    public static Restrictions ge(String propertyName, Object value) {
        return new Restrictions(propertyName, value, ">=");
    }

    public static Restrictions in(String propertyName, Object... values) {
        return new Restrictions(propertyName, values, "IN");
    }

    public static Restrictions in(String propertyName, Collection values) {
        return Restrictions.in(propertyName, values.toArray());
    }

    public static Restrictions notIn(String propertyName, Object... values) {
        return new Restrictions(propertyName, values, "NOT IN");
    }

    public static Restrictions notIn(String propertyName, Collection values) {
        return Restrictions.notIn(propertyName, values.toArray());
    }

    public final String getOp() {
        return this.op;
    }

    public String getPropertyName() {
        return this.propertyName;
    }

    public Object getValue() {
        return value;
    }
}
