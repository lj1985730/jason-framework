<%-- 作 者：Sheng Baoyu --%>
<%-- 本页说明：--%>
<%-- 本页地址:crm/admin/ScheduleController/homeView --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>

<html>
	<yonyou:nav father="CRM" model="日程管理" />
	<div class="row">
		<div class="col-md-12">
			<div class="portlet box blue-dark">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-calendar"></i>日程列表
					</div>
					<div class="tools">
						<a class="collapse"></a>
						<a class="fullscreen"></a>
					</div>
					<div class="actions">
						<yonyou:btn-view id="viewSchedule" />
						<yonyou:create id="createSchedule" permission="crm:schedule:create" />
						<yonyou:update id="updateSchedule" permission="crm:schedule:update" />
						<yonyou:delete id="deleteSchedule" permission="crm:schedule:delete" />
					</div>
				</div>
				<div class="portlet-body">
					<div class="table-container">
						<table id="scheduleTable" data-export="$.fn.export();">
							<thead>
								<tr role="row" class="heading">
									<th data-field="checkbox" data-checkbox="true"></th>
									<th data-field="id" data-formatter="indexFormatter">序号</th>
									<th data-field="customerName">客户</th>
									<th data-field="deptName">所属部门</th>
									<th data-field="chanceName">相关商机</th>
									<th data-field="participant">参与人</th>
									<th data-field="preSellerName">售前</th>
									<th data-field="scheduleVisitStr">拜访类型</th>
									<th data-field="currentStage">当前阶段</th>
									<th data-field="scheduleStart">开始时间</th>
									<th data-field="scheduleEnd">结束时间</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="schedule-edit.jsp" />
	<script type="text/javascript" src="scripts/crm/admin/schedule.js"></script>
</html>