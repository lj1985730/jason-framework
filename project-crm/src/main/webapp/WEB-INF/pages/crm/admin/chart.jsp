<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
	<%-- chart依赖 --%>
	<%@ include file="/WEB-INF/includes/common-chart.inc" %>
	<yonyou:nav father="CRM" model="统计图表" />
	<div class="row">
		<div class="col-md-6">
			<div class="portlet light">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-chart"></i>销售商机对比
					</div>
					<div class="tools">
						<a class="collapse"></a>
					</div>
				</div>
				<div class="portlet-body">
					<div>
						<yonyou:dateRange id="personChanceDate" startName="startDate" endName="endDate" />
					</div>
					<div id="person-chance-chart-container" style="width: 100%; height: 500px;">
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-6">
			<div class="portlet light">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-chart"></i>人员日程对比
					</div>
					<div class="tools">
						<a class="collapse"></a>
					</div>
				</div>
				<div class="portlet-body">
					<div>
						<yonyou:dateRange id="personScheduleDate" startName="startDate" endName="endDate" />
					</div>
					<div id="person-schedule-chart-container" style="width: 100%; height: 500px;">
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="scripts/crm/admin/chart.js"></script>
</html>