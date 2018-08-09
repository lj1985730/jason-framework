package com.yoogun.workflow.application.service;

import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.core.application.dto.Page;
import com.yoogun.core.infrastructure.exception.BusinessException;
import com.yoogun.workflow.application.vo.WorkflowVo;
import com.yoogun.workflow.domain.model.WorkflowModel;
import com.yoogun.workflow.infrastructure.ProcessState;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工作流-流程-业务层
 * @author  Liu Jun at 2018-4-13 16:14:45
 * @since v1.0.0
 */
@Service
public class WorkflowProcessService {

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private HistoryService historyService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private IdentityService identityService;

    @Resource
    private TaskService taskService;

    /**
     * 查询流程定义主键
     * @param processInstanceId 流程实例ID
     * @return 流程定义主键
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String searchProcessDefinitionId(String processInstanceId) {
        if(StringUtils.isBlank(processInstanceId)) {
            throw new NullPointerException("流程实例ID不可为空！");
        }
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()   //流程实例
                .processInstanceId(processInstanceId).singleResult();

        String processDefinitionId;
        if(processInstance != null) {   //如果流程正在进行
            processDefinitionId = processInstance.getProcessDefinitionId(); //获取流程定义
        } else {    //如果流程已完成，到历史记录中检索流程
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(processInstanceId).singleResult();
            processDefinitionId = historicProcessInstance.getProcessDefinitionId();
        }

        return processDefinitionId;
    }

    /**
     * 获取流程实例对象
     * @param processInstanceId 流程实例ID
     * @return 流程实例对象
     */
    ProcessInstance searchProcessInstance(String processInstanceId) {
        if(StringUtils.isBlank(processInstanceId)) {
            throw new NullPointerException("流程实例ID不可为空！");
        }
        return runtimeService.createProcessInstanceQuery()
                .processInstanceTenantId(AuthCache.tenantId())  //租户
                .processInstanceId(processInstanceId)
                .includeProcessVariables()
                .singleResult();
    }

    /**
     *  根据业务查询流程实例
     * @param processDefinitionKey 流程定义KEY
     * @param businessId    业务ID
     * @return 流程实例
     */
    private ProcessInstance searchProcessInstance(String processDefinitionKey, String businessId) {
        if(StringUtils.isBlank(processDefinitionKey)) {
            throw new NullPointerException("流程定义不可为空！");
        }
        if(StringUtils.isBlank(businessId)) {
            throw new NullPointerException("业务主键不可为空！");
        }

        //查询流程实例
        return runtimeService.createProcessInstanceQuery()
                .processInstanceTenantId(AuthCache.tenantId())  //租户
                .processInstanceBusinessKey(businessId, processDefinitionKey)   //流程定义以及业务主键
                .includeProcessVariables()
                .singleResult();
    }

    /**
     * 获取流程实例历史对象
     * @param processInstanceId 流程实例ID
     * @return 流程实例历史对象
     */
    HistoricProcessInstance searchHistoricProcessInstance(String processInstanceId) {
        if(StringUtils.isBlank(processInstanceId)) {
            throw new NullPointerException("流程实例ID不可为空！");
        }
        return historyService.createHistoricProcessInstanceQuery()
                .processInstanceTenantId(AuthCache.tenantId())  //租户
                .processInstanceId(processInstanceId)
                .includeProcessVariables()
                .singleResult();
    }

//    /**
//     * 获取流程实例历史对象
//     * @param processDefinitionKey 流程定义KEY
//     * @param businessId    业务ID
//     * @return 流程实例历史对象
//     */
//    private HistoricProcessInstance searchHistoricProcessInstance(String processDefinitionKey, String businessId) {
//        if(StringUtils.isBlank(processDefinitionKey)) {
//            throw new NullPointerException("流程定义不可为空！");
//        }
//        if(StringUtils.isBlank(businessId)) {
//            throw new NullPointerException("业务主键不可为空！");
//        }
//        return historyService.createHistoricProcessInstanceQuery()
//                .processInstanceTenantId(AuthCache.tenantId())  //租户
//                .processDefinitionKey(processDefinitionKey)
//                .processInstanceBusinessKey(businessId)
//                .includeProcessVariables()
//                .singleResult();
//    }

    /**
     * 获取本人发起的流程分页数据
     * @param vo 		查询条件
     * @return 本人发起的流程分页数据
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<WorkflowModel> searchAppliedPager(WorkflowVo vo) {

        if(AuthCache.accountId() == null) {
            throw new AuthenticationException("用户未登录！");
        }

        if(vo == null) {
            throw new NullPointerException("查询vo不可为空！");
        }

        List<WorkflowModel> modelList = this.searchAppliedProcess(vo, AuthCache.accountId());	//全部数据

        Page<WorkflowModel> result = new Page<>(vo.getOffset(), vo.getLimit());
        result.setRows(modelList);
        result.setTotal(modelList.size());
        return result;
    }

    /**
     *  获取人员发起的流程数据
     * @param vo 		查询条件
     * @param accountId 		查询账户ID
     * @return 人员发起的流程数据
     */
    private List<WorkflowModel> searchAppliedProcess(WorkflowVo vo, String accountId) {

        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery();

        this.checkFinished(query, vo.finished());

        //分页查询
        List<HistoricProcessInstance> processInstances = query
                .startedBy(accountId)
                .includeProcessVariables()
                .orderByProcessInstanceStartTime().desc()
                .listPage(vo.getOffset(), vo.getLimit());

        return this.transformToModel(processInstances);
    }

    /**
     * 获取当前登录人参与的流程分页数据
     * @param vo 		查询条件
     * @return 本人参与的流程分页数据
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<WorkflowModel> searchInvolvedPager(WorkflowVo vo) {

        if(AuthCache.accountId() == null) {
            throw new AuthenticationException("用户未登录！");
        }

        if(vo == null) {
            throw new NullPointerException("查询vo不可为空！");
        }

        List<WorkflowModel> modelList = this.searchInvolvedProcess(vo, AuthCache.accountId());	//全部数据

        Page<WorkflowModel> result = new Page<>(vo.getOffset(), vo.getLimit());
        result.setRows(modelList);
        result.setTotal(modelList.size());
        return result;
    }

    /**
     *  获取当前登录人参与的流程数据
     * @param vo 		查询条件
     * @param accountId 		查询账户ID
     * @return 人员参与的流程数据
     */
    private List<WorkflowModel> searchInvolvedProcess(WorkflowVo vo, String accountId) {

        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery();

        this.checkFinished(query, vo.finished());

        //分页查询
        List<HistoricProcessInstance> processInstances = query
                .involvedUser(accountId)    //与账户相关的
                .includeProcessVariables()
                .orderByProcessInstanceStartTime().desc()             //开始时间倒序
                .listPage(vo.getOffset(), vo.getLimit());   //分页

        return this.transformToModel(processInstances);
    }

    /**
     *  将流程实例数据转换为模型
     * @param processInstances 流程实例
     * @return 模型
     */
    private List<WorkflowModel> transformToModel(List<HistoricProcessInstance> processInstances) {

        List<WorkflowModel> processes = new ArrayList<>();

        if(processInstances != null && !processInstances.isEmpty()) {
            //将流程实例转换为模型
            WorkflowModel model;
            for(HistoricProcessInstance processInstance : processInstances) {
                model = new WorkflowModel().historicProcessInstance(processInstance);

                List<Task> tasks = taskService.createTaskQuery()    //查询当前任务
                        .processInstanceId(processInstance.getId())
                        .active()
                        .includeProcessVariables()
                        .orderByTaskCreateTime().asc().list();

                if(tasks != null && !tasks.isEmpty()) {
                    model.task(tasks.get(0));   //只取最早生成的任务
                }

                processes.add(model);
            }
        }

        return processes;
    }

    /**
     * 判断是否完成查询条件
     * @param query 查询对象
     * @param finished 是否完成
     */
    private void checkFinished(HistoricProcessInstanceQuery query, Boolean finished) {
        if(finished != null) {
            if(finished) {   //历史数据
                query.finished();
            } else {   //运行中数据
                query.unfinished();
            }
        }
    }

    /**
     * 查询流程列表分页
     * @param vo 流程实例ID
     * @return 流程实例历史对象
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<Object[]> searchProcess(WorkflowVo vo, String category) {
        /*
		 * 查询两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
		 */
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().latestVersion()
                .active().orderByProcessDefinitionKey().asc();

        if (StringUtils.isNotEmpty(category)) {
            processDefinitionQuery.processDefinitionCategory(category);
        }

        Page<Object[]> result = new Page<>(vo.getOffset(), vo.getLimit());

        result.setTotal(Long.valueOf(processDefinitionQuery.count()).intValue());   //总数

        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(vo.getOffset(), vo.getLimit());

        List<Object[]> list = new ArrayList<>();

        for (ProcessDefinition processDefinition : processDefinitionList) {
            String deploymentId = processDefinition.getDeploymentId();
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
            list.add(new Object[] { processDefinition, deployment });
        }

        result.setRows(list);
        return result;
    }

    /**
     * 启动流程
     * @param processDefinitionKey        流程定义
     * @param businessId        业务数据主键
     * @param applicantId       申请人账户ID
     * @param applicantName     申请人姓名
     * @param title             流程标题，(可能支持)显示在待办任务标题
     * @param variables         流程变量
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void startProcess(String processDefinitionKey, String businessId, String applicantId, String applicantName, String title, Map<String, Object> variables) {
        if(StringUtils.isBlank(processDefinitionKey)) {
            throw new NullPointerException("流程定义不可为空！");
        }
        if(StringUtils.isBlank(businessId)) {
            throw new NullPointerException("业务数据主键不可为空！");
        }

        // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
        identityService.setAuthenticatedUserId(applicantId);

        // 设置流程变量
        if (variables == null) {
            variables = new HashMap<>();
        }

        // 设置流程标题
        if (StringUtils.isNotBlank(title)) {
            variables.put(WorkflowModel.VAR_TITLE, title);
        }
        // 设置流程启动人
        if (StringUtils.isNoneBlank(applicantId)) {
            variables.put(WorkflowModel.VAR_APPLICANT_ID, applicantId);
        }
        // 设置流程启动人名称
        if (StringUtils.isNoneBlank(applicantName)) {
            variables.put(WorkflowModel.VAR_APPLICANT_NAME, applicantName);
        }

        variables.put(WorkflowModel.VAR_STATE, ProcessState.START); //保存状态

        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefinitionKey);

        variables.put(WorkflowModel.VAR_PROCESS_DEFINITION_NAME, processDefinition.getName()); //保存流程名称

        // 启动流程
        runtimeService.startProcessInstanceByKey(processDefinitionKey, businessId, variables);
    }

    /**
     * 取消流程
     * @param processInstanceId 流程实例ID
     * @param deleteReason        删除原因
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void cancelProcess(String processInstanceId, String deleteReason) {

        if(StringUtils.isBlank(processInstanceId)) {
            throw new NullPointerException("流程实例ID不可为空！");
        }

        ProcessInstance processInstance = this.searchProcessInstance(processInstanceId);

        this.cancelProcess(processInstance, deleteReason);
    }

    /**
     * 取消流程
     * @param processDefinitionKey        流程定义
     * @param businessId        业务数据主键
     * @param deleteReason        删除原因
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void cancelProcess(String processDefinitionKey, String businessId, String deleteReason) {

        if(StringUtils.isBlank(processDefinitionKey)) {
            throw new NullPointerException("流程定义Key不可为空！");
        }
        if(StringUtils.isBlank(businessId)) {
            throw new NullPointerException("业务主键不可为空！");
        }

        ProcessInstance processInstance = this.searchProcessInstance(processDefinitionKey, businessId);

        this.cancelProcess(processInstance, deleteReason);
    }

    /**
     * 取消流程
     * @param processInstance   流程实例
     * @param deleteReason      删除原因
     */
    private void cancelProcess(ProcessInstance processInstance, String deleteReason) {

        if(processInstance == null) {
            throw new NullPointerException("未找到对应流程！");
        }

        deleteReason = StringUtils.isBlank(deleteReason) ? "删除流程" : deleteReason;

        WorkflowModel model = new WorkflowModel().processInstance(processInstance);   //获取流程当前状态
        ProcessState state = model.getProcessState();
        if(state == null) {
            throw new NullPointerException("未找到流程当前状态！");
        }

        if(StringUtils.isNotBlank(model.getApplicantId()) &&
                !model.getApplicantId().equals(AuthCache.accountId())) { //必须流程发起者本人才可撤销流程
            throw new BusinessException("只有流程发起者本人才可撤销流程！");
        }
        switch (state) {    //根据当前状态判断可否删除流程，审批中的流程不可删除
            case START:
            case SUSPENDED:
            case RETRY:
            case NOT_BOUND:
            case UNKNOWN:   //初始、挂起、重新发起、未绑定、未知状态可以删除
                runtimeService.deleteProcessInstance(processInstance.getId(), deleteReason);
                break;
            case REJECTED:  //在打回状态，如果打回对象是流程发起者，则可以删除
                if(StringUtils.isNotBlank(model.getAssigneeId()) && StringUtils.isNotBlank(model.getApplicantId())) {
                    if(model.getAssigneeId().equals(model.getApplicantId())) {
                        runtimeService.deleteProcessInstance(processInstance.getId(), deleteReason);
                        break;
                    }
                }
            case WAITING:
            case PENDING:
                throw new BusinessException("流程正在审核中，不可删除！");
            case FINISHED:
                throw new BusinessException("流程已完成，不可删除！");
        }
    }
}