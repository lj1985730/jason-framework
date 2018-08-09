<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
	<style>
		.fixed-table-toolbar .bars {
			width: 50%;
		}
	</style>
	<div class="row">
		<div class="col-md-12">
			<div id="involvedToolbar" class="pull-left">
				<div class="col-md-12" style="padding-right:0;">
					<div class="row">
						<div class="col-md-6 pull-right" style="width: 200px;">
							<div class="form-group">
								<yonyou:radio name="involvedFinished" labels="未完成,已完成" values="false,true" />
							</div>
						</div>
					</div>
				</div>
			</div>
			<table id="involvedTable" data-toolbar="#involvedToolbar">
				<thead>
				<tr role="row" class="heading">
					<th data-field="checkbox" data-checkbox="true"></th>
					<th data-field="id" data-formatter="indexFormatter">序号</th>
					<th data-field="processDefinitionName" data-sortable="false">流程名称</th>
					<th data-field="processInstanceId" data-sortable="true">流程编号</th>
					<th data-field="assigneeName" data-sortable="false">当前处理人</th>
					<th data-field="processState" data-sortable="false" >流程状态</th>
				</tr>
				</thead>
			</table>
		</div>
	</div>
	<script src="scripts/workflow/task-involved.js"></script>
</html>