package com.yoogun.workflow.application.service;

import com.yoogun.workflow.domain.model.WorkflowModel;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.FormService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 工作流-流程表单-业务层
 * @author  Liu Jun at 2018-4-13 16:14:45
 * @since v1.0.0
 */
@Service
public class WorkflowFormService {

    @Resource
    private WorkflowProcessService workflowProcessService;

    @Resource
    private FormService formService;

    /**
     * 获取流程表单可编辑URL
     * @param processInstanceId 	流程实例ID
     * @param taskDefinitionKey	    流程任务定义KEY
     * @return 流程表单只读URL
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String getEditableFormUrl(String processInstanceId, String taskDefinitionKey) {
        if(StringUtils.isBlank(processInstanceId)) {
            throw new NullPointerException("流程实例ID不可为空！");
        }
        if(StringUtils.isBlank(taskDefinitionKey)) {
            throw new NullPointerException("流程任务定义KEY不可为空！");
        }
        return this.getFormUrl(processInstanceId, taskDefinitionKey, true);
    }

    /**
     * 获取流程表单只读URL
     * @param processInstanceId 	流程实例ID
     * @param taskDefinitionKey	    流程任务定义KEY
     * @return 流程表单只读URL
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String getReadonlyFormUrl(String processInstanceId, String taskDefinitionKey) {
        if(StringUtils.isBlank(processInstanceId)) {
            throw new NullPointerException("流程实例ID不可为空！");
        }
        if(StringUtils.isBlank(taskDefinitionKey)) {
            throw new NullPointerException("流程任务定义KEY不可为空！");
        }
        return this.getFormUrl(processInstanceId, taskDefinitionKey, false);
    }

    /**
     * 获取流程表单URL
     * @param processInstanceId 	流程实例ID
     * @param taskDefinitionKey	    流程任务定义KEY
     * @param editable 	            表单是否可编辑
     * @return 流程表单URL
     */
    private String getFormUrl(String processInstanceId, String taskDefinitionKey, Boolean editable) {

        WorkflowModel model = new WorkflowModel();
        ProcessInstance processInstance = workflowProcessService.searchProcessInstance(processInstanceId);
        if(processInstance != null) {
            model.processInstance(processInstance);
        } else {
            HistoricProcessInstance historicProcessInstance = workflowProcessService.searchHistoricProcessInstance(processInstanceId);
            model.historicProcessInstance(historicProcessInstance);
        }
        // 获取流程定义上的表单KEY
        String formKey = this.getFormKey(model.getProcessDefinitionId(), taskDefinitionKey);

        StringBuilder formUrl = new StringBuilder(formKey);

        if(editable != null) {
            if(StringUtils.containsIgnoreCase(formUrl, "?")) {  //如果是带参url，则拼参数
                formUrl.append("&editable=").append(editable);
            } else {
                formUrl.append("?editable=").append(editable);
            }
        }
        return formUrl.toString();
    }

    /**
     * 获取流程表单（首先获取任务节点表单KEY，如果没有则取流程开始节点表单KEY）
     * @param processDefinitionId   流程定义ID
     * @param taskDefinitionKey     任务定义KEY
     * @return 流程表单
     */
    private String getFormKey(String processDefinitionId, String taskDefinitionKey) {
        if (StringUtils.isBlank(processDefinitionId)) {
            throw new NullPointerException("流程定义ID不可为空！");
        }

        String formKey = "";
        if (StringUtils.isNotBlank(taskDefinitionKey)) {
            try {
                formKey = formService.getTaskFormKey(processDefinitionId, taskDefinitionKey);
            } catch (Exception e) {
                formKey = "";
            }
        }
        if (StringUtils.isBlank(formKey)) {
            formKey = formService.getStartFormKey(processDefinitionId);
        }
        return formKey;
    }
}