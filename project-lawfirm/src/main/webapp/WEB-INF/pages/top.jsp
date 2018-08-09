<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
	<head>
		<script type="text/javascript" src="scripts/top.js"></script>
	</head>
	<div id="pageHeader" class="page-header navbar navbar-fixed-top" >
		<div class="page-logo">
			<a href="javascript:">
				<img src="imgs/logo-light.png" alt="logo" class="logo-default" />
			</a>
			<div class="menu-toggler sidebar-toggler">
				<!-- DOC: Remove the above "hide" to enable the sidebar toggler button on header -->
			</div>
		</div>
		<a href="javascript:" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse"></a>
		<div class="page-top">
			<div class="top-menu">
				<ul class="nav navbar-nav pull-right">
					<li class="dropdown dropdown-user">
						<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
							<img alt="" id="profileInfo_photo_id" class="img-circle" src=""  style="display: none;" />
							<span id="topPersonName" class="username username-hide-on-mobile">
								你好：<shiro:principal />
								<shiro:guest>访客</shiro:guest>
							</span>
							<i class="fa fa-angle-down"></i>
						</a>
						<ul class="dropdown-menu dropdown-menu-default">
							<li>
								<a href="auth/person/index">
								<i class="icon-user"></i> 我的信息 </a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="javascript:changePassword();">
								<i class="icon-key"></i> 修改密码 </a>
							</li>
							<li>
								<a href="javascript:logout();">
								<i class="icon-power"></i> 退出系统 </a>
							</li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</div>

	<!--------------------------修改密码的弹出层---------------------------->
	<yonyou:modal id="changePassWin" modalClass="modal-lg modal-xl modal-xxl modal-xxxl modal-xxxxl" title="修改密码" editable="true">
		<form id="changePassForm" class="form-horizontal form-bordered form-row-stripped" data-toggle="validator">
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label col-md-2" for="oldPassword">原密码</label>
							<div class="col-md-10">
								<input id="oldPassword" type="text" class="form-control" placeholder="原密码..." />
							</div>
						</div>
					</div>
					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label col-md-2" for="newPassword">新密码</label>
							<div class="col-md-10">
								<input id="newPassword" type="text" class="form-control" placeholder="新密码..." />
							</div>
						</div>
					</div>
					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label col-md-2">确认密码</label>
							<div class="col-md-10">
								<input id="againPassword" type="text" class="form-control" placeholder="确认新密码..." />
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
	</yonyou:modal>
</html>

