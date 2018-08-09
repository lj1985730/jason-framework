package com.yoogun.workflow.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yoogun.workflow.infrastructure.ProcessState;
import com.yoogun.workflow.infrastructure.TaskState;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.TaskInfo;
import org.flowable.task.api.history.HistoricTaskInstance;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 工作流模型
 * @author Liu Jun
 * @version 2017-09-28 16:25:44
 */
public class WorkflowModel {

    private ProcessDefinition processDefinition; 	// 流程定义对象
    private String processDefinitionId; 			// 流程定义ID
    private String processDefinitionKey; 			// 流程定义Key（流程定义标识）
    private String processDefinitionName; 			// 流程定义名称

    private ProcessInstance processInstance;		// 流程实例
    private HistoricProcessInstance historicProcessInstance;	//流程实例历史记录
    private String processInstanceId; 				// 流程实例ID
    private String processInstanceName; 			// 流程实例名称
    private Map<String, Object> processVariables;	// 流程变量
    private ProcessState processState;              //流程状态

    private HistoricActivityInstance historicActivityInstance;		//历史活动记录

    private TaskInfo task; 									// 任务
    private String taskDefinitionKey; 						// 任务定义Key（任务环节标识）
    private String taskId; 									// 任务ID
    private String taskName; 								// 任务名称
    private Map<String, Object> taskVariables; 				// 任务变量

    private String businessId;								// 业务绑定ID
    private String businessFormKey; 						// 业务表单URL

    private String title; 		    // 任务标题
    private TaskState state; 		// 任务状态

    private String applicantId; 	//流程申请人ID
    private String applicantName;	//流程申请人姓名

    private String assigneeId; 		// 任务执行人ID
    private String assigneeName; 	// 任务执行人名称

    private String comment; 		// 任务意见
    private String flag; 			// 意见状态

    private Date beginDate;			// 开始查询日期
    private Date endDate;			// 结束查询日期

    private List<WorkflowModel> subModels; 		// 子对象

    /**
     *  流程变量Key
     */
    public static final String VAR_PROCESS_DEFINITION_NAME = "processDefinitionName";   //流程名称
    public static final String VAR_STATE = "state"; //流程状态
    public static final String VAR_TITLE = "title"; //任务主题
    public static final String VAR_APPLICANT_ID = "applicantId"; //发起人Id
    public static final String VAR_APPLICANT_NAME = "applicantName"; //发起人姓名

    /**
     * 流程输出变量Key，全部带有判断逻辑的流程网关，需要指定此key的判断表达式
     */
    public static final String VAR_OUTGOING = "outgoing";



    /**
     * 获取流程定义(JsonIgnore)
     * @return 流程定义
     */
    @JsonIgnore
    public ProcessDefinition getProcessDefinition() {
        return processDefinition;
    }

    /**
     * 设置流程定义
     * @param processDefinition 流程定义
     */
    public WorkflowModel processDefinition(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
        return this;
    }

    /**
     * 获取流程定义ID，如果属性processDefinitionId为blank，尝试从其他属性中查询
     * <p>查询优先级：
     * <p>1. processDefinitionId
     * <p>2. processDefinition.id
     * <p>3. processInstance.processDefinitionId
     * <p>4. historicProcessInstance.processDefinitionId
     * <p>5. task.processDefinitionId
     * <p>6. historicActivityInstance.processDefinitionId
     * @return 流程定义ID
     */
    public String getProcessDefinitionId() {
        if(StringUtils.isNotBlank(this.processDefinitionId)) {
            return this.processDefinitionId;
        } else if (this.getProcessDefinition() != null) {			//流程定义
            return this.getProcessDefinition().getId();
        } else if(this.getProcessInstance() != null) {				//流程实例
            return this.getProcessInstance().getProcessDefinitionId();
        } else if(this.getHistoricProcessInstance() != null) {		//流程实例历史记录
            return this.getHistoricProcessInstance().getProcessDefinitionId();
        } else if(this.getTask() != null) {							//任务
            return this.getTask().getProcessDefinitionId();
        } else if(this.getHistoricActivityInstance() != null) {		//历史活动记录
            return this.getHistoricActivityInstance().getProcessDefinitionId();
        }
        return null;
    }

    /**
     * 设置流程定义ID
     * @param processDefinitionId 流程定义ID
     */
    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    /**
     * 获取流程定义KEY，如果属性processDefinitionKey为blank，尝试从其他属性中查询
     * <p>查询优先级：
     * <p>1. processDefinitionKey
     * <p>2. processDefinition.key
     * <p>3. substringBefore(processDefinitionId, ":")
     * <p>4. processInstance.processDefinitionKey
     * <p>5. historicProcessInstance.processDefinitionKey
     * @return 流程定义KEY
     */
    public String getProcessDefinitionKey() {
        if(StringUtils.isNotBlank(this.processDefinitionKey)) {
            return this.processDefinitionKey;
        } else if(this.getProcessDefinition() != null) {					//流程定义
            return this.getProcessDefinition().getKey();
        } else if(StringUtils.isNotBlank(this.getProcessDefinitionId())) {	//尝试从流程定义ID中获取KEY
            return StringUtils.substringBefore(this.getProcessDefinitionId(), ":");
        } else if(this.getProcessInstance() != null) {						//流程实例
            return getProcessInstance().getProcessDefinitionKey();
        } else if(this.getHistoricProcessInstance() != null) {				//流程实例历史记录
            return this.getHistoricProcessInstance().getProcessDefinitionKey();
        }
        return null;
    }

    /**
     * 设置流程定义KEY
     * @param processDefinitionKey 流程定义KEY
     */
    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    /**
     * 获取流程定义名称，如果属性processDefinitionName为blank，尝试从其他属性中查询
     * <p>查询优先级：
     * <p>1. processDefinitionName
     * <p>2. processDefinition.name
     * <p>4. processInstance.processDefinitionName
     * <p>5. historicProcessInstance.processDefinitionName
     * @return 流程定义名称
     */
    public String getProcessDefinitionName() {
        if(StringUtils.isNotBlank(this.processDefinitionName)) {
            return this.processDefinitionName;
        } else if(this.getProcessDefinition() != null) {					//流程定义
            return this.getProcessDefinition().getName();
        } else if(this.getProcessInstance() != null) {						//流程实例
            return getProcessInstance().getProcessDefinitionName();
        } else if(this.getHistoricProcessInstance() != null) {				//流程实例历史记录
            return this.getHistoricProcessInstance().getProcessDefinitionName();
        } else if(this.getProcessVariables() != null && this.getProcessVariables().containsKey(VAR_PROCESS_DEFINITION_NAME)) {
            return this.getProcessVariables().get(VAR_PROCESS_DEFINITION_NAME).toString();
        }
        return null;
    }

    /**
     * 设置流程定义名称
     * @param processDefinitionName 流程定义名称
     */
    public void setProcessDefinitionName(String processDefinitionName) {
        this.processDefinitionName = processDefinitionName;
    }

    /**
     * 获取流程实例(JsonIgnore)
     * @return 流程实例
     */
    @JsonIgnore
    public ProcessInstance getProcessInstance() {
        return processInstance;
    }

    /**
     * 设置流程实例
     * @param processInstance 流程实例
     */
    public WorkflowModel processInstance(ProcessInstance processInstance) {
        this.processInstance = processInstance;
        return this;
    }

    /**
     * 获取历史流程实例记录(JsonIgnore)
     * @return 历史流程实例记录
     */
    @JsonIgnore
    public HistoricProcessInstance getHistoricProcessInstance() {
        return historicProcessInstance;
    }

    /**
     * 设置历史流程实例记录
     * @param historicProcessInstance 历史流程实例记录
     */
    public WorkflowModel historicProcessInstance(HistoricProcessInstance historicProcessInstance) {
        this.historicProcessInstance = historicProcessInstance;
        return this;
    }

    /**
     * 获取流程实例ID，如果属性processInstanceId为blank，尝试从其他属性中查询
     * <p>查询优先级：
     * <p>1. processInstanceId
     * <p>2. processInstance.id
     * <p>3. historicProcessInstance.id
     * <p>4. task.processInstanceId
     * <p>5. historicActivityInstance.processInstanceId
     * @return 流程实例ID
     */
    public String getProcessInstanceId() {
        if(StringUtils.isNotBlank(this.processInstanceId)) {
            return this.processInstanceId;
        } else if(this.getProcessInstance() != null) {					//流程实例
            return this.getProcessInstance().getId();
        } else if(this.getHistoricProcessInstance() != null) {			//流程实例历史记录
            return this.getHistoricProcessInstance().getId();
        } else if(this.getTask() != null) {								//任务
            return this.getTask().getProcessInstanceId();
        } else if(this.getHistoricActivityInstance() != null) {			//历史活动记录
            return this.getHistoricActivityInstance().getProcessInstanceId();
        }
        return null;
    }

    /**
     * 设置流程实例ID
     * @param processInstanceId 流程实例ID
     */
    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    /**
     * 获取流程实例名称，如果属性processInstanceName为blank，尝试从其他属性中查询
     * <p>查询优先级：
     * <p>1. processInstanceName
     * <p>2. processInstance.name
     * <p>3. historicProcessInstance.name
     * @return 流程实例名称
     */
    public String getProcessInstanceName() {
        if(StringUtils.isNotBlank(this.processInstanceName)) {
            return this.processInstanceName;
        } else if(this.getProcessInstance() != null) {					//流程实例
            return this.getProcessInstance().getName();
        } else if(this.getHistoricProcessInstance() != null) {			//流程实例历史记录
            return this.getHistoricProcessInstance().getName();
        }
        return null;
    }

    /**
     * 设置流程实例名称
     * @param processInstanceName 流程实例名称
     */
    public void setProcessInstanceName(String processInstanceName) {
        this.processInstanceName = processInstanceName;
    }

    /**
     * 获取流程变量，如果属性processVariable为null，尝试从其他属性中查询
     * <p>查询优先级：
     * <p>1. processVariables
     * <p>2. processInstance.processVariables
     * <p>3. historicProcessInstance.processVariables
     * <p>4. task.processVariables
     * @return 流程变量Map
     */
    public Map<String, Object> getProcessVariables() {
        if(this.processVariables != null) {
            return this.processVariables;
        } else if(this.getProcessInstance() != null) {						//流程定义
            return this.getProcessInstance().getProcessVariables();
        } else if(this.getHistoricProcessInstance() != null) {				//历史流程实例记录
            return this.getHistoricProcessInstance().getProcessVariables();
        } else if(this.getTask() != null) {                    				//任务
            return this.getTask().getProcessVariables();
        }
        return null;
    }

    /**
     * 设置流程变量
     * @param processVariables 流程变量
     */
    public void setProcessVariables(Map<String, Object> processVariables) {
        this.processVariables = processVariables;
    }

    /**
     * 获取流程状态，如果属性processState为null，尝试从其他属性中查询
     * <p>查询优先级：
     * <p>1. 根据historicProcessInstance的endTime和deleteReason判断是删除还是完成
     * <p>2. processVariables
     * <p>3. task.processVariables
     * @return 流程状态
     */
    public ProcessState getProcessState() {
        if(this.getHistoricProcessInstance() != null) {
            if (this.getHistoricProcessInstance().getEndTime() != null) {
                this.processState = ProcessState.FINISHED;
                if (StringUtils.isNotBlank(this.getHistoricProcessInstance().getDeleteReason())) {
                    this.processState = ProcessState.DELETED;
                }
                return this.processState;
            }
        }
        if(this.getProcessVariables() != null && this.getProcessVariables().containsKey(VAR_STATE)) {   //流程参数
            return (ProcessState)this.getProcessVariables().get(VAR_STATE);
        } else if(this.getTask() != null && this.getTask().getProcessVariables().containsKey(VAR_STATE)) { //任务
            return (ProcessState)this.getTask().getProcessVariables().get(VAR_STATE);
        }
        return this.processState;
    }

    /**
     * 设置流程状态
     * @param processState 流程状态
     */
    public void setProcessState(ProcessState processState) {
        this.processState = processState;
    }

    /**
     * 获取任务（JsonIgnore）
     * @return 任务
     */
    @JsonIgnore
    public TaskInfo getTask() {
        return task;
    }

    /**
     * 设置任务
     * @param task 任务
     */
    public WorkflowModel task(TaskInfo task) {
        this.task = task;
        return this;
    }

    /**
     * 获取任务定义KEY，如果属性taskDefinitionKey为blank，尝试从其他属性中查询
     * <p>查询优先级：
     * <p>1. taskDefinitionKey
     * <p>2. task.taskDefinitionKey
     * @return 任务定义KEY
     */
    public String getTaskDefinitionKey() {
        if(this.taskDefinitionKey != null) {
            return this.taskDefinitionKey;
        } else if(this.getTask() != null) {						//任务
            return this.getTask().getTaskDefinitionKey();
        }
        return null;
    }

    /**
     * 设置任务定义KEY
     * @param taskDefinitionKey 任务定义KEY
     */
    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    /**
     * 获取任务ID，如果属性taskId为blank，尝试从其他属性中查询
     * <p>查询优先级：
     * <p>1. taskId
     * <p>2. task.id
     * <p>3 historicActivityInstance.taskId
     * @return 任务ID
     */
    public String getTaskId() {
        if(this.taskId != null) {
            return this.taskId;
        } else if(this.getTask() != null) {						//任务
            return this.getTask().getId();
        } else if(this.getHistoricActivityInstance() != null) {	//历史活动记录
            return this.getHistoricActivityInstance().getTaskId();
        }
        return null;
    }

    /**
     * 设置任务ID
     * @param taskId 任务ID
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * 获取任务名称，如果属性taskName为blank，尝试从其他属性中查询
     * <p>查询优先级：
     * <p>1. taskName
     * <p>2. task.name
     * @return 任务名称
     */
    public String getTaskName() {
        if(this.taskName != null) {
            return this.taskName;
        } else if(this.getTask() != null) {						//任务
            return this.getTask().getName();
        }
        return null;
    }

    /**
     * 设置任务名称
     * @param taskName 任务名称
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * 获取任务变量，如果属性taskVariables为null，尝试从其他属性中查询
     * <p>查询优先级：
     * <p>1. taskVariables
     * <p>2. task.taskLocalVariables
     * @return 任务变量Map
     */
    public Map<String, Object> getTaskVariables() {
        if(this.taskVariables != null) {
            return this.taskVariables;
        } else if(this.getTask() != null) {						//任务
            return this.getTask().getTaskLocalVariables();
        }
        return null;
    }

    /**
     * 设置任务变量
     * @param taskVariables 任务变量
     */
    public void setTaskVariables(Map<String, Object> taskVariables) {
        this.taskVariables = taskVariables;
    }

    /**
     * 获取历史活动记录(JsonIgnore)
     * @return 历史活动记录
     */
    @JsonIgnore
    public HistoricActivityInstance getHistoricActivityInstance() {
        return historicActivityInstance;
    }

    /**
     * 设置历史活动记录
     * @param historicActivityInstance 历史活动记录
     */
    public void setHistoricActivityInstance(HistoricActivityInstance historicActivityInstance) {
        this.historicActivityInstance = historicActivityInstance;
    }

    /**
     * 设置业务数据ID
     * @param businessId 业务数据ID
     */
    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    /**
     * 获取业务数据ID
     * @return 业务数据ID
     */
    public String getBusinessId() {
        return this.businessId;
    }

    /**
     * 获取业务数据表单URI，如果属性businessFormKey为blank，尝试从其他属性中查询
     * <p>查询优先级：
     * <p>1. businessFormKey
     * <p>2. task.formKey
     * @return 业务数据表单URI
     */
    public String getBusinessFormKey() {
        if(this.businessFormKey != null) {
            return this.businessFormKey;
        } else if(this.getTask() != null) {
            return this.getTask().getFormKey();
        }
        return null;
    }

    /**
     * 设置业务数据表单URI
     * @param businessFormKey 业务数据表单URI
     */
    public void setBusinessFormKey(String businessFormKey) {
        this.businessFormKey = businessFormKey;
    }

    /**
     * 获取任务标题，如果属性title为blank，尝试从其他属性中查询
     * <p>查询优先级：
     * <p>1. title
     * <p>2. processVariables.searchById(VAR_TITLE)
     * @return 任务标题
     */
    public String getTitle() {
        if(this.title != null) {
            return this.title;
        } else if(this.getProcessVariables() != null && this.getProcessVariables().containsKey(VAR_TITLE)) {
            return this.getProcessVariables().get(VAR_TITLE).toString();
        }
        return null;
    }

    /**
     * 设置任务标题
     * @param title 任务标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取任务状态
     * @return 任务状态
     */
    public TaskState getState() {
        return state;
    }

    /**
     * 设置任务状态
     * @param state 任务状态
     */
    public WorkflowModel state(TaskState state) {
        this.state = state;
        return this;
    }

    /**
     * 获取流程申请人ID，如果属性applicantId为blank，尝试从其他属性中查询
     * <p>查询优先级：
     * <p>1. applicantId
     * <p>2. processVariables.searchById(VAR_APPLICANT_ID)
     * @return 流程申请人ID
     */
    public String getApplicantId() {
        if(this.applicantId != null) {
            return this.applicantId;
        } else if(this.getProcessVariables() != null && this.getProcessVariables().containsKey(VAR_APPLICANT_ID)) {
            return this.getProcessVariables().get(VAR_APPLICANT_ID).toString();
        }
        return null;
    }

    /**
     * 设置流程申请人ID
     * @param applicantId 流程申请人ID
     */
    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    /**
     * 获取流程申请人姓名，如果属性applicantName为blank，尝试从其他属性中查询
     * <p>查询优先级：
     * <p>1. applicantName
     * <p>2. processVariables.searchById(VAR_APPLICANT_NAME)
     * @return 流程申请人姓名
     */
    public String getApplicantName() {
        if(this.applicantName != null) {
            return this.applicantName;
        } else if(this.getProcessVariables() != null && this.getProcessVariables().containsKey(VAR_APPLICANT_NAME)) {
            return this.getProcessVariables().get(VAR_APPLICANT_NAME).toString();
        }
        return null;
    }

    /**
     * 设置流程申请人姓名
     * @param applicantName 流程申请人姓名
     */
    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    /**
     * 获取流程执行人ID，如果属性assigneeId为blank，尝试从其他属性中查询
     * <p>查询优先级：
     * <p>1. assigneeId
     * <p>2. task.assignee
     * <p>3. historicActivityInstance.assignee
     * @return 流程执行人ID
     */
    public String getAssigneeId() {
        if(StringUtils.isNotBlank(this.assigneeId)) {
            return this.assigneeId;
        } else if (this.getTask() != null) {
            return this.getTask().getAssignee();
        } else if (this.getHistoricActivityInstance() != null) {
            return this.getHistoricActivityInstance().getAssignee();
        }
        return assigneeId;
    }

    /**
     * 设置流程执行人ID
     * @param assigneeId 流程执行人ID
     */
    public void setAssigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
    }

    /**
     * 获取流程执行人姓名
     * @return 流程执行人姓名
     */
    public String getAssigneeName() {
        return assigneeName;
    }

    /**
     * 设置流程执行人姓名
     * @param assigneeName 流程执行人姓名
     */
    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    /**
     * 获取审批意见
     * @return 审批意见
     */
    public String getComment() {
        return comment;
    }

    /**
     * 设置审批意见
     * @param comment 审批意见
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * 获取意见状态
     * @return 意见状态
     */
    public String getFlag() {
        return flag;
    }

    /**
     * 设置意见状态
     * @param flag 意见状态
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
     * 获取开始时间
     * @return 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getBeginDate() {
        if(this.getHistoricProcessInstance() != null) {
            return this.getHistoricProcessInstance().getStartTime();
        }
        return beginDate;
    }

    /**
     * 设置开始时间
     * @param beginDate 开始时间
     */
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    /**
     * 获取结束时间
     * @return 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getEndDate() {
        if(this.getHistoricProcessInstance() != null) {
            return this.getHistoricProcessInstance().getEndTime();
        }
        return endDate;
    }

    /**
     * 设置结束时间
     * @param endDate 结束时间
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 获取子对象(JsonIgnore)
     * @return 子对象
     */
    @JsonIgnore
    public List<WorkflowModel> getSubModels() {
        return subModels;
    }

    /**
     * 设置子对象
     * @param subModels 子对象
     */
    public void setSubModels(List<WorkflowModel> subModels) {
        this.subModels = subModels;
    }

    /**
     * 获取任务开始时间task.createTime
     * @return 任务开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getTaskCreateDate() {
        if (this.getTask() != null) {
            return this.getTask().getCreateTime();
        }
        return null;
    }

    /**
     * 获取任务结束时间
     * @return 任务结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getTaskEndDate() {
        if (this.getTask() != null) {
            TaskInfo task = this.getTask();
            if(task instanceof HistoricTaskInstance) {
                return ((HistoricTaskInstance)task).getEndTime();
            }
        }
        return null;
    }

    /**
     * 获取过去的任务历时
     * @return 任务历时
     */
    public String getDurationTime() {
        if(this.getHistoricProcessInstance() == null || this.getHistoricProcessInstance().getDurationInMillis() == null) {
            return null;
        }

        long second, minute, hour, day;

        second = this.getHistoricProcessInstance().getDurationInMillis() / 1000;
        if(second >= 60) {
            minute = second / 60;
            second = second % 60;
            if(minute >= 60) {
                hour = minute / 60;
                minute = minute % 60;
                if(hour >= 24) {
                    day = hour / 24;
                    hour = hour % 24;
                    return day + "天" + hour + "小时" + minute + "分" + second + "秒";
                } else {
                    return hour + "小时" + minute + "分" + second + "秒";
                }
            } else {
                return minute + "分" + second + "秒";
            }
        } else {
            return second + "秒";
        }
    }

    /**
     * 是否是一个待办任务
     * @return true：是；false：否
     */
    public boolean isTodoTask() {
        return this.getState() == TaskState.TODO || this.getState() == TaskState.CLAIM;
    }

    /**
     * 是否是已完成任务
     * @return true：是；false：否
     */
    public boolean isFinishTask() {
        return this.getState() == TaskState.FINISH;
    }

}
