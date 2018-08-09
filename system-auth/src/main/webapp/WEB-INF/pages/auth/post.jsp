<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
	<yonyou:nav father="系统管理" model="岗位管理" />

	<div class="row">
		<div class="col-md-4">
			<div class="portlet light" style="margin-bottom: 0;">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-list font-green-sharp"></i>
						<span class="caption-subject font-green-sharp bold">部门-岗位列表</span>
					</div>
					<div class="actions">
						<div class="btn-group">
							<button id="dropdownBtn" type="button" class="btn green dropdown-toggle" disabled="disabled" data-toggle="dropdown" title="选择一个节点进行操作">
								<i class="fa fa-gear"></i>&nbsp;操作
								<i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right" role="menu">
								<shiro:hasPermission name="auth:post:create">
									<li id="createPost"><a href="javascript:"><i class="fa fa-plus"></i>&nbsp;新增</a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="auth:post:update">
									<li id="updatePost"><a href="javascript:"><i class="fa fa-edit"></i>&nbsp;修改</a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="auth:post:delete">
									<li id="deletePost"><a href="javascript:"><i class="fa fa-trash-o"></i>&nbsp;删除</a></li>
								</shiro:hasPermission>
							</ul>
						</div>
					</div>
				</div>
				<div class="portlet-body" style="padding: 0;">
					<div id="departmentPostTree"></div>
				</div>
			</div>
		</div>
		<div class="col-md-8">
			<div class="portlet box blue-dark tabbable">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-info-circle"></i>
						<span class="caption-subject bold">岗位信息</span>
					</div>
					<div class="tools">
						<a class="collapse"></a>
						<a class="fullscreen"></a>
					</div>
					<ul class="nav nav-tabs">
						<li class="active">
							<a href="#infoTab" data-toggle="tab">基本信息</a>
						</li>
						<li>
							<a href="#postTab" data-toggle="tab">管辖岗位</a>
						</li>
					</ul>
				</div>
				<div class="portlet-body">
					<div class="tab-content">
						<div class="tab-pane active" id="infoTab">
							<jsp:include page="post-info.jsp" />
						</div>
						<div class="tab-pane" id="postTab">
							<jsp:include page="post-rlat.jsp" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="post-edit.jsp" />
	<script type="text/javascript" src="scripts/base/tree-select.js"></script>
	<script src="scripts/auth/post.js"></script>
</html>