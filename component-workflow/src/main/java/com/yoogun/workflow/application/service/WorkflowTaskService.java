package com.yoogun.workflow.application.service;

import com.yoogun.auth.application.service.AccountService;
import com.yoogun.auth.domain.model.Account;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.core.application.dto.Page;
import com.yoogun.workflow.application.vo.WorkflowVo;
import com.yoogun.workflow.domain.model.WorkflowModel;
import com.yoogun.workflow.infrastructure.ProcessState;
import com.yoogun.workflow.infrastructure.TaskState;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.flowable.engine.*;
import org.flowable.engine.common.impl.identity.Authentication;
import org.flowable.engine.impl.persistence.entity.CommentEntity;
import org.flowable.engine.impl.persistence.entity.CommentEntityImpl;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.task.Comment;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskInfo;
import org.flowable.task.api.TaskInfoQuery;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 工作流-任务-业务层
 * @author Liu Jun at 2018-4-24 14:29:21
 * @since v1.0.0
 */
@Service
public class WorkflowTaskService {

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private AccountService accountService;

    /**
     * 获取人员待办分页列表
     * @param vo 		查询条件
     * @return 待办分页列表
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<WorkflowModel> searchTodoPager(WorkflowVo vo) {

        if(AuthCache.accountId() == null) {
            throw new AuthenticationException("用户未登录！");
        }

        if(vo == null) {
            throw new NullPointerException("查询vo不可为空！");
        }

        List<WorkflowModel> tasks = this.searchTodoTasks(vo, AuthCache.accountId());	//全部数据

        Page<WorkflowModel> result = new Page<>(vo.getOffset(), vo.getLimit());
        result.setRows(tasks);
        result.setTotal(tasks.size());
        return result;
    }

    /**
     * 获取人员已办分页列表
     * @param vo 		查询条件
     * @return 已办分页列表
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<WorkflowModel> searchHistoricTaskPager(WorkflowVo vo) {

        if(AuthCache.accountId() == null) {
            throw new AuthenticationException("用户未登录！");
        }

        if(vo == null) {
            throw new NullPointerException("查询vo不可为空！");
        }

        HistoricTaskInstanceQuery histTaskQuery = historyService.createHistoricTaskInstanceQuery().taskAssignee(AuthCache.accountId())
                .finished()
                .includeProcessVariables()		//流程变量
                .includeTaskLocalVariables()	//任务变量
                .orderByHistoricTaskInstanceEndTime().desc();
        List<WorkflowModel> models = this.searchAndTransformToModel(histTaskQuery, vo, TaskState.FINISH);

        Page<WorkflowModel> result = new Page<>(vo.getOffset(), vo.getLimit());
        result.setRows(models);  // 填充数据
        result.setTotal(models.size()); // 查询总数
        return result;
    }

    /**
     * 获取待办列表
     * @param vo        查询条件值对象
     * @param accountId    人员账户ID
     * @return 待办列表
     */
    private List<WorkflowModel> searchTodoTasks(WorkflowVo vo, String accountId) {

        List<WorkflowModel> assignedTasks = this.searchAssignedTasks(vo, accountId);	//已签收

        List<WorkflowModel> newTasks = this.searchNewTasks(vo, accountId);	//待签收

        List<WorkflowModel> todoList = new ArrayList<>();
        todoList.addAll(assignedTasks);
        todoList.addAll(newTasks);

        return todoList;
    }

    /**
     * 查询已签收的任务
     * @param vo        查询条件值对象
     * @param accountId    人员账户ID
     * @return 已签收的任务
     */
    private List<WorkflowModel> searchAssignedTasks(WorkflowVo vo, String accountId) {
        //搜索任务
        TaskQuery taskQuery = taskService.createTaskQuery()
                .taskAssignee(accountId)	//签收人
                .active()				//有效
                .includeProcessVariables()	//包含流程变量
                .orderByTaskCreateTime().desc();	//排序

        return this.searchAndTransformToModel(taskQuery, vo, TaskState.TODO);
    }

    /**
     * 查询未被签收的任务
     * @param vo    查询条件值对象
     * @param accountId 人员账户ID
     * @return 未被签收的任务
     */
    private List<WorkflowModel> searchNewTasks(WorkflowVo vo, String accountId) {

        TaskQuery taskQuery = taskService.createTaskQuery()
                .taskCandidateUser(accountId)	//候选人
                .active()					//生效
                .includeProcessVariables()	//流程变量
                .orderByTaskCreateTime().desc();	//排序

        return this.searchAndTransformToModel(taskQuery, vo, TaskState.CLAIM);
    }

    /**
     * 查询任务并转换为模型
     * @param taskQuery 查询对象
     * @param vo 条件值对象
     * @param state 任务状态
     * @return 模型集合
     */
    @SuppressWarnings({ "unchecked" })
    private List<WorkflowModel> searchAndTransformToModel(TaskInfoQuery taskQuery, WorkflowVo vo, TaskState state) {

        this.appendSearchConditions(taskQuery, vo);        // 设置查询条件

        List<TaskInfo> taskList = taskQuery.listPage(vo.getOffset(), vo.getLimit());	// 执行查询

        List<WorkflowModel> result = new ArrayList<>();	//结果集

        //task转模型
        ProcessDefinition processDefinition;
        for (TaskInfo task : taskList) {
            processDefinition = repositoryService.getProcessDefinition(task.getProcessDefinitionId());
            result.add(new WorkflowModel().processDefinition(processDefinition).task(task).state(state));
        }

        return result;
    }

    /**
     * 对任务查询增加条件
     * @param taskQuery 查询对象
     * @param vo    条件值对象
     */
    private void appendSearchConditions(TaskInfoQuery taskQuery, WorkflowVo vo) {
        // 设置查询条件
        if (StringUtils.isNotBlank(vo.getProcessDefinitionKey())) {	//流程定义
            taskQuery.processDefinitionKey(vo.getProcessDefinitionKey());
        }
        if (StringUtils.isNotBlank(vo.getProcessInstanceId())) {	    //流程实例
            taskQuery.processInstanceId(vo.getProcessInstanceId());
        }
        if (StringUtils.isNotBlank(vo.getBeginDateStr())) {         //开始时间
            taskQuery.taskCreatedAfter(vo.getBeginDate());
        }
        if (StringUtils.isNotBlank(vo.getEndDateStr())) {           //截至时间
            taskQuery.taskCreatedBefore(vo.getEndDate());
        }
    }

    /**
     * 获取流程下历史任务列表
     * @param processInstanceId 流程实例ID
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<HistoricTaskInstance> searchHistoicTask(String processInstanceId) {

        if(StringUtils.isBlank(processInstanceId)) {
            throw new NullPointerException("流程实例ID不可为空！");
        }

        List<HistoricTaskInstance> taskInstanceList = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricTaskInstanceEndTime().asc()
                .list();

        if(taskInstanceList == null || taskInstanceList.isEmpty()) {
            return null;
        }

        return taskInstanceList;
    }

    /**
     * 获取流程任务批注
     * @param taskId 流程任务ID
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<CommentEntity> searchTaskComments(String taskId) {

        if(StringUtils.isBlank(taskId)) {
            throw new NullPointerException("任务ID不可为空！");
        }

        List<Comment> comments = taskService.getTaskComments(taskId);   //查询批注

        if(comments == null || comments.isEmpty()) {    //无数据直接返回
            return Collections.emptyList();
        }

        List<CommentEntity> resList = new ArrayList<>();

        CommentEntity commentEntity;
        for(Comment comment : comments) {   //转换批注接口为实体对象，主要为了处理人名

            commentEntity = new CommentEntityImpl();

            try {
                BeanUtils.copyProperties(commentEntity, comment);   //属性拷贝
            } catch (Exception e) {
                throw new RuntimeException("数据转换出错！", e);
            }

            Account account = accountService.searchById(comment.getUserId());  //批注人处理
            commentEntity.setMessage(account.getName());
            resList.add(commentEntity);
        }

        return resList;
    }

    /**
     * 签收任务
     * @param taskId 任务ID
     * @param accountId 签收账户ID（用户登录名）
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void claim(String taskId, String accountId) {
        if(StringUtils.isBlank(taskId)) {
            throw new NullPointerException("任务ID不可为空！");
        }
        if(StringUtils.isBlank(accountId)) {
            throw new NullPointerException("签收用户ID不可为空！");
        }
        taskService.claim(taskId, accountId);
    }

    /**
     * 提交任务, 并保存意见
     * @param taskId 任务ID
     * @param processInstanceId 流程实例ID
     * @param accountId 操作人账户ID
     * @param comment 任务提交意见的内容
     * @param variables 任务变量
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void complete(String taskId, String processInstanceId, String accountId, String comment, String outgoing, ProcessState state, Map<String, Object> variables) {
        if(StringUtils.isBlank(taskId)) {
            throw new NullPointerException("任务ID不可为空！");
        }
        if(StringUtils.isBlank(processInstanceId)) {
            throw new NullPointerException("流程实例ID不可为空！");
        }
        if(StringUtils.isBlank(accountId)) {
            throw new NullPointerException("操作人账户ID不可为空！");
        }

        // 添加意见
        if (StringUtils.isNotBlank(comment)) {
            this.addTaskComment(taskId, processInstanceId, comment, accountId); // 批注人的名称,一定要写，不然查看的时候不知道人物信息
        }

        // 准备流程变量
        if (variables == null) {
            variables = new HashMap<>();
        }

        if(StringUtils.isNotBlank(outgoing)) {
            variables.put(WorkflowModel.VAR_OUTGOING, outgoing);  //下节点流向
        }

        variables.put(WorkflowModel.VAR_STATE, state == null ? ProcessState.UNKNOWN : state);  //流程状态

        taskService.complete(taskId, variables);     // 提交任务
    }

    /**
     * <p>通过任务，普通单线任务，无多实例
     * <p>用于最简单的单线流程，流程状态逻辑单一
     * <p>复杂流程（并联线路、子流程等）请使用complete
     * @param taskId 任务ID
     * @param processInstanceId 流程实例ID
     * @param comment 任务意见内容
     * @param accountId 操作人账户ID
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void passTask(String taskId, String processInstanceId, String accountId, String comment) {
        this.complete(taskId, processInstanceId, accountId, comment, "pass", ProcessState.PENDING, null);   // 提交任务
    }

    /**
     * <p>驳回任务，普通单线任务，无多实例
     * <p>用于最简单的单线流程，流程状态逻辑单一
     * <p>复杂流程（并联线路、子流程等）请使用complete
     * @param taskId 任务ID
     * @param processInstanceId 流程实例ID
     * @param accountId 操作人账户ID
     * @param comment 任务意见内容
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void rejectTask(String taskId, String processInstanceId, String accountId, String comment) {
        this.complete(taskId, processInstanceId, accountId, comment, "reject", ProcessState.REJECTED, null);        // 提交任务
    }

    /**
     *  取消任务（流程回退）
     * @param currentActivityId 当前流程任务所在节点ID
     * @param destActivityId 回退到目标流程节点ID
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void cancelTask(String processInstanceId, String currentActivityId, String destActivityId) {
        List<String> originalActivityIds = new ArrayList<>();
        originalActivityIds.add(currentActivityId);
        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId(processInstanceId)
                .moveActivityIdsToSingleActivityId(originalActivityIds, destActivityId)
                .changeState();
    }

    /**
     * 添加任务批注
     * @param taskId 任务ID
     * @param processInstanceId 流程实例ID
     * @param comment 批注
     * @param accountId 操作人账户ID
     */
    private void addTaskComment(String taskId, String processInstanceId, String comment, String accountId) {
        if(StringUtils.isBlank(taskId)) {
            throw new NullPointerException("任务ID不可为空！");
        }
        if(StringUtils.isBlank(processInstanceId)) {
            throw new NullPointerException("流程实例ID不可为空！");
        }
        if(StringUtils.isBlank(comment)) {
            throw new NullPointerException("批注不可为空！");
        }
        if(StringUtils.isNotBlank(accountId)) {
            Authentication.setAuthenticatedUserId(accountId);  // 批注人的名称
        }
        taskService.addComment(taskId, processInstanceId, comment);
    }

     /**
     * 委派任务
     * @param taskId 任务ID
     * @param accountId 被委派人账户ID
     */
     @Transactional(propagation = Propagation.REQUIRED)
     public void delegateTask(String taskId, String accountId){
        taskService.delegateTask(taskId, accountId);
     }

     /**
     * 被委派人完成任务
     * @param taskId 任务ID
     */
     @Transactional(propagation = Propagation.REQUIRED)
     public void resolveTask(String taskId) {
        taskService.resolveTask(taskId);
     }

    /**
     * 删除任务
     * @param taskId 任务ID
     * @param deleteReason 删除原因
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteTask(String taskId, String deleteReason) {
        taskService.deleteTask(taskId, deleteReason);
    }

    /**
     * 设置任务的办理人
     * @param task  任务
     * @param userId  办理人ID
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void setTaskAssignee(String userId, Task task) {
        task.setAssignee(userId);
        taskService.saveTask(task);
    }

    /**
     * 添加任务候选人
     * @param taskId    任务
     * @param accountId    候选人账户ID
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addCandidateUser(String taskId, String accountId) {
        taskService.addCandidateUser(taskId, accountId);
    }
}
