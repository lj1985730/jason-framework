package com.yoogun.initialize.infrastructure;

/**
 * 脚本占位符
 * @author Liu Jun
 * @since v2.1.1
 */
public enum SqlPlaceholder {

    /**
     * 占位符-UUID
     */
    ID("${id}"),

    /**
     * 占位符-租户ID
     */
    TENANT_ID("${tenantId}"),

    /**
     * 占位符-超管ID
     */
    SUPER_ADMIN_ID("${superAdminId}"),

    /**
     * 占位符-用友企业Id
     */
    YONYOU_ID("${yonyouId}");

    private String placeholder;

    SqlPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    @Override
    public String toString() {
        return placeholder;
    }
}
