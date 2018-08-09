<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
	<div class="row">
		<div class="col-md-12">
			<div id="todoToolbar">
				<div class="col-md-12" style="padding-right:0;">
					<div class="row">
						<div class="col-md-6 pull-right" style="width: 200px;">
							<button type="button" class="btn green" id="viewTaskBtn" style="padding-top: 6px; padding-bottom: 6px;">
								<i class="fa fa-search"></i>&nbsp;流程查看
							</button>
						</div>
					</div>
				</div>
			</div>
			<table id="todoTaskTable" data-toolbar="#todoToolbar">
				<thead>
				<tr role="row" class="heading">
					<th data-field="checkbox" data-checkbox="true"></th>
					<th data-field="id" data-formatter="indexFormatter">序号</th>
					<th data-field="processDefinitionName" data-sortable="false">流程名称</th>
					<th data-field="processInstanceId" data-sortable="true">流程编号</th>
					<th data-field="taskName" data-sortable="false">当前任务</th>
					<th data-field="processState" data-sortable="false" >流程状态</th>
					<th data-field="applicantName" data-sortable="false">申请人</th>
					<th data-field="taskCreateDate" data-sortable="false">任务开始时间</th>
				</tr>
				</thead>
			</table>
		</div>
	</div>
	<script src="scripts/workflow/task-todo.js"></script>
	<script src="scripts/workflow-historic-modal.js"></script>
</html>