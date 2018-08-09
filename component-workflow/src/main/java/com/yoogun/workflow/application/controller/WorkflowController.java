package com.yoogun.workflow.application.controller;

import com.yoogun.auth.domain.model.Account;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.core.application.controller.BaseController;
import com.yoogun.core.application.dto.JsonResult;
import com.yoogun.core.application.dto.Page;
import com.yoogun.workflow.application.service.WorkflowDrawService;
import com.yoogun.workflow.application.service.WorkflowProcessService;
import com.yoogun.workflow.application.service.WorkflowTaskService;
import com.yoogun.workflow.application.vo.WorkflowVo;
import com.yoogun.workflow.domain.model.WorkflowModel;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.flowable.engine.impl.persistence.entity.CommentEntity;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 工作流通用Controller
 * @author Liu Jun
 * @version 2017-09-21 13:04:32
 */
@Controller
@RequestMapping(value = "**/workflow")
public class WorkflowController extends BaseController {

	@Resource
	private WorkflowProcessService workflowProcessService;

	@Resource
	private WorkflowTaskService workflowTaskService;

	@Resource
	private WorkflowDrawService workflowDrawService;

	/**
	 * 获取当前登陆人的待办任务列表
	 * @return 待办任务列表
	 */
	@RequestMapping(value = { "/todoTasks" }, method = RequestMethod.GET)
	@ResponseBody
	public Page<WorkflowModel> searchTodoTasks(HttpServletRequest request) {
		WorkflowVo vo = new WorkflowVo(request);
		return workflowTaskService.searchTodoPager(vo);
	}

	/**
	 * 获取当前登陆人发起的任务
	 * @return 发起的任务列表
	 */
	@RequestMapping(value = { "/appliedProcesses" }, method = RequestMethod.GET)
	@ResponseBody
	public Page<WorkflowModel> searchAppliedProcesses(HttpServletRequest request) {
		WorkflowVo vo = new WorkflowVo(request);
		return workflowProcessService.searchAppliedPager(vo);
	}

	/**
	 * 获取当前登陆人参与的任务
	 * @return 当前登陆人参与的任务
	 */
	@RequestMapping(value = { "/involvedProcesses" }, method = RequestMethod.GET)
	@ResponseBody
	public Page<WorkflowModel> searchInvolvedProcesses(HttpServletRequest request) {
		WorkflowVo vo = new WorkflowVo(request);
		return workflowProcessService.searchInvolvedPager(vo);
	}

	/**
	 * 获取流程实例下的历史任务列表
	 * @param processInstanceId 流程实例ID
	 * @return 历史任务列表
	 */
	@RequestMapping(value = {"/processInstances/{processInstanceId}/historicTasks"})
	@ResponseBody
	public List<HistoricTaskInstance> searchHistoicInstanceTasks(@PathVariable String processInstanceId) {
		if(StringUtils.isBlank(processInstanceId)) {
			throw new NullPointerException("流程实例ID");
		}
		return workflowTaskService.searchHistoicTask(processInstanceId);
	}

	/**
	 * 获取任务标注
	 * @param taskId 任务ID
	 * @return 任务标注
	 */
	@RequestMapping(value = {"/tasks/{taskId}/comments"})
	@ResponseBody
	public List<CommentEntity> searchTaskComments(@PathVariable String taskId) {
		if(StringUtils.isBlank(taskId)) {
			throw new NullPointerException("流程实例ID");
		}
		return workflowTaskService.searchTaskComments(taskId);
	}

	/**
	 * 查询流程定义主键
	 * @param processInstanceId 流程实例ID
	 * @return 流程定义主键
	 */
	@RequestMapping(value = "/processInstances/{processInstanceId}/processDefinitionId")
	@ResponseBody
	public String searchProcessDefinitionId(@PathVariable("processInstanceId") String processInstanceId) {
		if(StringUtils.isBlank(processInstanceId)) {
			throw new NullPointerException("流程实例ID");
		}
		return workflowProcessService.searchProcessDefinitionId(processInstanceId);
	}

	/**
	 * 签收任务
	 * @param taskId 任务ID
	 */
	@RequestMapping(value = "/task/{taskId}/claim", method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult claim(@PathVariable("taskId") String taskId) {
		Account account = AuthCache.account();
		if(account == null) {
			throw new AuthenticationException("当前未登录");
		}
		workflowTaskService.claim(taskId, account.getId());
		return new JsonResult(true, "签收任务成功！");
	}

	/**
	 * 完成任务
	 * @param vo 业务条件
	 */
	@RequestMapping(value = "/task/{taskId}/pass", method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult passTask(@PathVariable("taskId") String taskId, @RequestBody WorkflowVo vo) {
		Account account = AuthCache.account();
		if(account == null) {
			throw new AuthenticationException("当前未登录");
		}
		workflowTaskService.passTask(taskId, vo.getProcessInstanceId(), account.getId(), vo.getComment());
		return new JsonResult(true, "审核任务成功！");
	}

	/**
	 * 驳回任务
	 * @param vo 业务条件
	 */
	@RequestMapping(value = "/task/{taskId}/reject", method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult rejectTask(@PathVariable("taskId") String taskId, @RequestBody WorkflowVo vo) {
		Account account = AuthCache.account();
		if(account == null) {
			throw new AuthenticationException("当前未登录");
		}
		workflowTaskService.rejectTask(taskId, vo.getProcessInstanceId(), account.getId(), vo.getComment());
		return new JsonResult(true, "驳回任务成功");
	}

	/**
	 * 删除流程实例
	 * @param processInstanceId 流程实例ID
	 * @param reason 删除原因
	 */
	@RequestMapping(value = "/processInstance/{processInstanceId}", method = RequestMethod.DELETE)
	@ResponseBody
	public JsonResult cancelProcess(@PathVariable("processInstanceId") String processInstanceId, @RequestParam(required = false) String reason) {
		workflowProcessService.cancelProcess(processInstanceId, reason);
		return new JsonResult(JsonResult.ResultType.REMOVE_SUCCEED);
	}

	/**
	 * 读取流程实例的带跟踪图片
	 * @param processInstanceId 流程实例ID
	 */
	@RequestMapping(value = "/processInstances/{processInstanceId}/traceImg", method = RequestMethod.GET)
	public void searchInstanceTraceImg(@PathVariable String processInstanceId, HttpServletResponse response) {
		if(StringUtils.isBlank(processInstanceId)) {
			throw new NullPointerException("流程实例ID");
		}
		InputStream imageStream = workflowDrawService.searchTraceImg(processInstanceId);
		try {
			IOUtils.copy(imageStream, response.getOutputStream());
		} catch (IOException e) {
			throw new IllegalArgumentException("导出图片出错！", e);
		}
	}

//	/**
//	 * 获取可编辑流程表单
//	 * @param processInstanceId 流程实例ID
//	 * @param taskDefinitionKey 任务定义KEY
//	 */
//	@RequestMapping(value = "/editableFormView")
//	public String editableBusinessFormView(@RequestParam String processInstanceId, @RequestParam String taskDefinitionKey) {
//		return "redirect:" + workflowProcessService.getEditableFormUrl(processInstanceId, taskDefinitionKey);
//	}

//	/**
//	 * 获取只读流程表单
//	 * @param processInstanceId 流程实例ID
//	 * @param taskDefinitionKey 任务定义KEY
//	 */
//    @RequestMapping(value = "/readonlyFormView")
//    public String readonlyBusinessFormView(@RequestParam String processInstanceId, @RequestParam String taskDefinitionKey) {
//        return "redirect:" + workflowProcessService.getReadonlyFormUrl(processInstanceId, taskDefinitionKey);
//    }

//	/**
//	 * 输出流程实例的跟踪流程信息
//	 * @param processInstanceId 流程实例ID
//	 * @return 跟踪流程信息
//	 */
//	@RequestMapping(value = "/processInstances/{processInstanceId}/traceInfo")
//	@ResponseBody
//	public List<Map<String, Object>> searchInstanceTraceInfos(@PathVariable("processInstanceId") String processInstanceId) {
//		if(StringUtils.isBlank(processInstanceId)) {
//			throw new NullPointerException("流程实例ID");
//		}
//		return workflowProcessService.searchTraceInfos(processInstanceId);
//	}

}