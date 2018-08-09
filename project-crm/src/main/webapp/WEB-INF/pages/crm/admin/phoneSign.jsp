<%-- 作 者：Wang chong --%>
<%-- 本页说明：--%>
<%-- 本页地址:crm/admin/SignController/phoneHomeView --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>

<html>
	<head>
		<link type="text/css" rel="stylesheet" href="plugins/fullcalendar-3.9.0/fullcalendar.css" />
		<link type="text/css" rel="stylesheet" href="plugins/fullcalendar-3.9.0/fullcalendar.print.css" media="print"/>
		<style>
			.signList-item {
				background: #f6fbfc;
				padding: 10px;
				margin-bottom: 10px;
				margin-bottom: 15px;
				overflow: hidden;
			}
			.signList-item-title {
				font-size: 15px;
				color: #2b4a5c;
				font-weight: 600;
				padding-top: 3px;
				padding-bottom: 13px;
			}
			.signList-controls {
				margin-top: 5px;
			}
			#sign-panel {
				-webkit-transition: left 0.5s ease;
				-moz-transition: left 0.5s ease;
				-o-transition: left 0.5s ease;
				transition: left 0.5s ease;
			}
		</style>
	</head>
	<yonyou:nav father="CRM" model="签到管理" />
	<div class="row">
		<div class="col-md-9">
			<div class="portlet box blue-dark">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-list">

						</i>
						日历页面
					</div>
				</div>
				<div class="portlet-body">
					<div id="calendar"></div>
				</div>
			</div>
		</div>
		<div class="col-md-3" id="sign-panel">
			<div class="portlet box blue-dark">
				<div class="portlet-title">
					<div class="caption" id="signTitle">
						<i class="fa fa-list"></i>签到页面
					</div>
				</div>
				<div class="portlet-body">
					<div class="signList-item">
						<div class="signList-item-title">
							<button type="button" class="btn grey-cascade btn-xs" id="signInBut">签到</button>
						</div>
						<div class="signList-item-text">
							<i class="fa fa-map-marker"></i>&nbsp;&nbsp;&nbsp;<span id="signInAdd">--- </span>
						</div>
						<div class="signList-controls">
							<i class="fa fa-calendar"></i>&nbsp;&nbsp;<span id="signInTime">--- </span>
						</div>
					</div>
					<div class="signList-item">
						<div class="signList-item-title">
							<button type="button" class="btn grey-cascade btn-xs" id="signOutBut">签退</button>
						</div>
						<div class="signList-item-text">
							<i class="fa fa-map-marker"></i>&nbsp;&nbsp;&nbsp;<span id="signOutAdd">--- </span>
						</div>
						<div class="signList-controls">
							<i class="fa fa-calendar"></i>&nbsp;&nbsp;<span id="signOutTime">--- </span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="sign-edit.jsp"></jsp:include>
	<script type="text/javascript" src="plugins/fullcalendar-3.9.0/fullcalendar.js"></script>
	<script type="text/javascript" src="plugins/fullcalendar-3.9.0/locale/zh-cn.js"></script>
	<script type="text/javascript" src="scripts/crm/admin/phoneSign.js"></script>
</html>