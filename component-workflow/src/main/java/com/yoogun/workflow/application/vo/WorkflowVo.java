package com.yoogun.workflow.application.vo;

import com.yoogun.core.application.vo.TableParam;
import com.yoogun.workflow.infrastructure.TaskState;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 工作流-查询VO
 * @author LiuJun
 * @since v1.0.0
 */
public class WorkflowVo extends TableParam implements Serializable {

    private String processDefinitionId; 			// 流程定义ID
    private String processDefinitionKey; 			// 流程定义Key（流程定义标识）

    private String processInstanceId; 				// 流程实例ID
    private String processInstanceName; 			// 流程实例名称

    private String taskDefinitionKey; 				// 任务定义Key（任务环节标识）
    private String taskId; 							// 任务ID
    private String taskName; 						// 任务名称

    private String businessId;						// 业务绑定ID

    private String title; 		                    // 任务标题
    private TaskState state; 		                // 任务状态

    private String applicantId; 	                //流程申请人ID
    private String applicantName;	                //流程申请人姓名

    private String assigneeId; 		                // 任务执行人ID
    private String assigneeName; 	                // 任务执行人名称

    private String comment; 		                // 任务意见
    private String flag; 			                // 意见状态

    private String beginDateStr;			        // 开始查询日期字符串
    private String endDateStr;			            // 结束查询日期字符串

    private Date beginDate;			                // 开始查询日期
    private Date endDate;			                // 结束查询日期

    private String reason;                      // 操作原因
    private Boolean finished;                    //是否已完成

    /**
     * 构造器，根据request获取参数
     *
     * @param request 请求体
     */
    public WorkflowVo(HttpServletRequest request) {
        super(request);
        this.processDefinitionId = request.getParameter("processDefinitionId");
        this.processDefinitionKey = request.getParameter("processDefinitionKey");
        this.processInstanceId = request.getParameter("processInstanceId");
        this.processInstanceName = request.getParameter("processInstanceName");
        this.taskDefinitionKey = request.getParameter("taskDefinitionKey");
        this.taskId = request.getParameter("taskId");
        this.businessId = request.getParameter("businessId");
        this.title = request.getParameter("title");
        this.taskName = request.getParameter("taskName");
        this.applicantId = request.getParameter("applicantId");
        this.applicantName = request.getParameter("applicantName");
        this.beginDateStr = request.getParameter("beginDateStr");
        this.endDateStr = request.getParameter("endDateStr");
        this.reason = request.getParameter("reason");
        this.finished = StringUtils.isBlank(request.getParameter("finished")) ? null : Boolean.valueOf(request.getParameter("finished"));
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public String getProcessInstanceName() {
        return processInstanceName;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getBusinessId() {
        return businessId;
    }

    public String getTitle() {
        return title;
    }

    public TaskState getState() {
        return state;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public String getComment() {
        return comment;
    }

    public String getFlag() {
        return flag;
    }

    public Date getBeginDate() {
        if(this.beginDate != null) {
            return this.beginDate;
        } else if(StringUtils.isNotBlank(this.beginDateStr)) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.beginDateStr);
            } catch (ParseException e) {
                throw new RuntimeException("日期转化出错！");
            }
        }
        return null;
    }

    public Date getEndDate() {
        if(this.endDate != null) {
            return this.endDate;
        } else if(StringUtils.isNotBlank(this.endDateStr)) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd 23:59:59").parse(this.endDateStr);
            } catch (ParseException e) {
                throw new RuntimeException("日期转化出错！");
            }
        }
        return null;
    }

    public String getBeginDateStr() {
        if(StringUtils.isNotBlank(this.beginDateStr)) {
            return this.beginDateStr;
        } else if(this.beginDate != null) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.beginDate);
        }
        return null;
    }

    public String getEndDateStr() {
        if(StringUtils.isNotBlank(this.endDateStr)) {
            return this.endDateStr;
        } else if(this.endDate != null) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.endDate);
        }
        return null;
    }

    public String getReason() {
        return reason;
    }

    public Boolean finished() {
        return finished;
    }
}
