package com.yoogun.workflow.infrastructure;

/**
 * 工作流业务信息枚举
 * @author Liu Jun
 * @since v1.0.0
 */
public enum WorkflowBusiness {

    //auth
    AUTH_TENANT("auth_tenant", "AUTH_TENANT", "ID"), //审计项目申请流程
    AUDIT_SCHEME_APPLY("audit_scheme_apply", "AUDIT_SCHEME", "PK_SCHEME"),  //审计方案审批流程
    REFORM_NOTICE_AUDIT_APPLY("audit_reformNotice_send", "AUDIT_REFORM_NOTICE", "PK_NOTICE");

    private String processDefinitionKey;    //流程定义KEY
    private String businessTable;           //业务表名
    private String businessPk;          //业务表主键字段

    /**
     * 构造器
     * @param processDefinitionKey 流程定义KEY
     * @param businessTable 业务表名
     * @param businessPk 业务表主键字段
     */
    WorkflowBusiness(String processDefinitionKey, String businessTable, String businessPk) {
        this.processDefinitionKey = processDefinitionKey;
        this.businessTable = businessTable;
        this.businessPk = businessPk;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public String getBusinessTable() {
        return businessTable;
    }

    public String getBusinessPk() {
        return businessPk;
    }
}
