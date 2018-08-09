package com.yoogun.workflow.application.service;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * 工作流-绘制-业务层
 * @author Liu Jun at 2018-4-24 14:29:56
 * @since v1.0.0
 */
@Service
public class WorkflowDrawService {

    @Resource
    private WorkflowProcessService workflowProcessService;

    @Resource
    private ProcessEngine processEngine;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private HistoryService historyService;

    /**
     * 读取带跟踪的图片
     * @param processInstanceId 流程实例ID
     * @return 封装了各种节点信息
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public InputStream searchTraceImg(String processInstanceId) {

        String processDefinitionId = workflowProcessService.searchProcessDefinitionId(processInstanceId);

        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);

        List<HistoricActivityInstance> highLightedActivities = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId).list();
        //高亮环节id集合
        List<String> highLightedActivityIds = new ArrayList<>();
        for(HistoricActivityInstance activityInstance : highLightedActivities) {
            highLightedActivityIds.add(activityInstance.getActivityId());
        }

        //高亮线路id集合
        List<String> highLightedFlowIds = this.getHighLightedFlows(bpmnModel, highLightedActivityIds);

        return processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator()
                .generateDiagram(bpmnModel, "png", highLightedActivityIds, highLightedFlowIds, "宋体", "宋体", "宋体", null, 1.0);
    }

    /**
     * 获取需要高亮的线
     * @param bpmnModel 流程模型
     * @param highLightedActivityIds 高亮activity Id集合
     * @return 需要高亮的flow Id集合
     */
    private List<String> getHighLightedFlows(BpmnModel bpmnModel, List<String> highLightedActivityIds) {
        List<String> highFlows = new ArrayList<>(); // 用以保存高亮的线flowId

        Process process = bpmnModel.getProcesses().get(0);
        Collection<FlowElement> flowElements = process.getFlowElements();
        SequenceFlow flow;
        for(FlowElement flowElement : flowElements) {
            if(flowElement instanceof SequenceFlow) {
                flow = (SequenceFlow) flowElement;
                if(highLightedActivityIds.contains(flow.getSourceRef()) && highLightedActivityIds.contains(flow.getTargetRef())) {
                    highFlows.add(flow.getId());
                }
            }
        }
        return highFlows;
    }

//    /**
//     * 流程跟踪图信息
//     * @param processInstanceId 流程实例ID
//     * @return 封装了各种节点信息
//     */
//    public List<Map<String, Object>> searchTraceInfos(String processInstanceId) {
//        Execution execution = runtimeService.createExecutionQuery().executionId(processInstanceId).singleResult();// 流程执行
//
//        String activityId = execution.getActivityId() == null ? "" : execution.getActivityId();
//
//        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
//                .processInstanceId(processInstanceId).singleResult();
//
//        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
//                .getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
//
//        List<ActivityImpl> activitiList = processDefinition.getActivities();    // 获得当前任务定义的所有节点
//
//        List<Map<String, Object>> activityInfos = new ArrayList<>();
//        for (ActivityImpl activity : activitiList) {
//
//            String id = activity.getId();
//            boolean currentActiviti = false;
//
//            // 当前节点
//            if (id.equals(activityId)) {
//                currentActiviti = true;
//            }
//
//            Map<String, Object> activityImageInfo = this.packageSingleActivitiInfo(activity, processInstance, currentActiviti);
//
//            activityInfos.add(activityImageInfo);
//        }
//
//        return activityInfos;
//    }

//    /**
//     * 封装输出信息，包括：当前节点的X、Y坐标、变量信息、任务类型、任务描述
//     * @param activity	流程节点
//     * @param processInstance	流程实例
//     * @param currentActiviti	是否当前节点
//     * @return 输出信息
//     */
//    private Map<String, Object> packageSingleActivitiInfo(ActivityImpl activity, ProcessInstance processInstance,
//                                                          boolean currentActiviti) {
//        Map<String, Object> activityInfo = new HashMap<>();
//        activityInfo.put("currentActiviti", currentActiviti);
//        this.setPosition(activity, activityInfo);
//        this.setWidthAndHeight(activity, activityInfo);
//
//        Map<String, Object> vars = new HashMap<>();
//        Map<String, Object> properties = activity.getProperties();
//        vars.put("节点名称", properties.searchById("name"));
//        vars.put("任务类型", ActUtils.parseToZhType(properties.searchById("type").toString()));
//        vars.put("节点说明", properties.searchById("documentation"));
//        vars.put("描述", activity.getProcessDefinition().getDescription());
//
//        ActivityBehavior activityBehavior = activity.getActivityBehavior(); //判断节点类型
//        if (activityBehavior instanceof UserTaskActivityBehavior) { //用户节点
//
//            Task currentTask = null;
//
//            // 当前节点的task
//            if (currentActiviti) {
//                currentTask = workflowTaskService.getCurrentTaskInfo(processInstance);
//            }
//
//            // 当前任务的分配角色
//            UserTaskActivityBehavior userTaskActivityBehavior = (UserTaskActivityBehavior) activityBehavior;
//            TaskDefinition taskDefinition = userTaskActivityBehavior.getTaskDefinition();
//            Set<Expression> candidateGroupIdExpressions = taskDefinition.getCandidateGroupIdExpressions();
//            if (!candidateGroupIdExpressions.isEmpty()) {
//
//                setTaskGroup(vars, candidateGroupIdExpressions);    // 任务的处理角色
//
//                // 当前处理人
//                if (currentTask != null) {
//                    this.setCurrentTaskAssignee(vars, currentTask);
//                }
//            }
//        }
//
//        activityInfo.put("vars", vars);
//        return activityInfo;
//    }

//    /**
//     * 获取流程组件
//     * @param processDefinitionId   流程定义ID
//     * @param activityId            组件ID
//     * @return 流程组件
//     */
//    public ActivityImpl getActivity(String processDefinitionId, String activityId) {
//        if(StringUtils.isBlank(processDefinitionId)) {
//            throw new NullArgumentException("流程定义ID不可为空！");
//        }
//        if(StringUtils.isBlank(activityId)) {
//            throw new NullArgumentException("组件ID不可为空！");
//        }
//        return this.searchProcessDefinition(processDefinitionId).findActivity(activityId);
//    }

//    /**
//     * 获取流程定义实体
//     * @param processDefinitionId 流程定义ID
//     * @return 流程定义实体
//     */
//    private ProcessDefinitionEntity searchProcessDefinition(String processDefinitionId) {
//        return (ProcessDefinitionEntity) ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(processDefinitionId);
//    }

//    /**
//     * 设置宽度、高度属性
//     * @param activity  节点
//     * @param activityInfo 节点信息集合
//     */
//    private void setWidthAndHeight(ActivityImpl activity, Map<String, Object> activityInfo) {
//        activityInfo.put("width", activity.getWidth());
//        activityInfo.put("height", activity.getHeight());
//    }

//    /**
//     * 设置坐标位置
//     * @param activity  节点
//     * @param activityInfo  节点信息集合
//     */
//    private void setPosition(ActivityImpl activity, Map<String, Object> activityInfo) {
//        activityInfo.put("x", activity.getX());
//        activityInfo.put("y", activity.getY());
//    }
}
