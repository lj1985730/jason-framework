<%-- 作 者：Wang chong --%>
<%-- 本页说明：--%>
<%-- 本页地址:crm/admin/SignController/homeView --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>

<html>
	<style>
		.signTitle {
			font-size:140%;
			color: #37b7fd;
			margin-bottom: 10px;
			border-right: 1px solid #d9cfdf;
		}
		.signDes {
			font-size: 60%;
			color: #999;
		}
	</style>
	<yonyou:nav father="CRM" model="签到管理" />
	<div class="row">
		<div class="col-md-12">
			<div class="portlet box blue-dark">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-list">
							
						</i>
						签到列表
					</div>
					<%--<div class="actions">--%>
						<%--<yonyou:create id="createSign" permission="crm:sign:create" />--%>
						<%--<yonyou:update id="updateSign" permission="crm:sign:update" />--%>
						<%--<yonyou:delete id="deleteSign" permission="crm:sign:delete" />--%>
					<%--</div>--%>
				</div>
				<div class="portlet-body">
					<div class="row" style="margin-bottom: 50px;">
						<div class="col-md-6 col-md-offset-3">
							<yonyou:date id="signDate" name="signDate" placeholder="选择月份..." end="current" orientation="top" format="yyyy-mm"/>
						</div>
					</div>
					<div class="row">
						<div class="col-md-3 text-center lead">
							<p class="signTitle"><span id="mustSign">0</span>天</p>
							<p class="signDes">本月应出勤天数</p>
						</div>
						<div class="col-md-3 text-center lead">
							<p class="signTitle"><span id="nowSign">0</span>天</p>
							<p class="signDes">现应出勤天数</p>
						</div>
						<div class="col-md-3 text-center lead">
							<p class="signTitle"><span id="completeSign">0</span>天</p>
							<p class="signDes">已出勤天数</p>
						</div>
						<div class="col-md-3 text-center lead">
							<p class="signTitle" style="border-right: none;"><span id="missSign">0</span>天</p>
							<p class="signDes">已缺勤天数</p>
						</div>
					</div>
					<div class="table-container">
						<table id="signTable">
							<thead>
								<tr role="row" class="heading">
									<th data-field="checkbox" data-checkbox="true" />
									<th data-field="id" data-formatter="indexFormatter">
										序号
									</th>
									<th data-field="signDate" data-sortable="true" data-formatter="addDateMarker">
										签到日期
									</th>
									<th data-field="person.name" data-sortable="true">
										签到人
									</th>
									<th data-field="tenant.name" data-sortable="true">
										所属公司
									</th>
									<th data-field="department.name" data-sortable="true">
										所属部门
									</th>
									<th data-field="signInTime" data-sortable="true">
										签到时间
									</th>
									<th data-field="signInAddress" data-sortable="true" data-formatter="addMapMarker">
										签到地点
									</th>
									<th data-field="signOutTime" data-sortable="true">
										签退时间
									</th>
									<th data-field="signOutAddress" data-sortable="true" data-formatter="addMapMarker">
										签退地点
									</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="sign-edit.jsp" />

	<script type="text/javascript" src="scripts/crm/admin/sign-work-day.js"></script>
	<script type="text/javascript" src="plugins/jquery.animateNumber.js"></script>
	<script type="text/javascript" src="scripts/crm/admin/sign.js"></script>
</html>