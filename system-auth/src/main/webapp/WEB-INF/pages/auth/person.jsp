<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
	<yonyou:nav father="系统管理" model="人员管理" />
	<div class="row">
		<div class="col-md-4">
			<div class="portlet light">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-list font-green-sharp"></i>
						<span class="caption-subject font-green-sharp bold">人员列表</span>
					</div>
					<div class="actions">
						<div class="btn-group">
							<button type="button" class="btn green dropdown-toggle" data-toggle="dropdown">
								<i class="fa fa-gear"></i>&nbsp;操作
								<i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right" role="menu">
								<shiro:hasPermission name="auth:person:create">
									<li id="createPerson"><a href="javascript:"><i class="fa fa-plus"></i>&nbsp;新增</a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="auth:person:update">
									<li id="updatePerson"><a href="javascript:"><i class="fa fa-edit"></i>&nbsp;修改</a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="auth:person:delete">
									<li id="deletePerson"><a href="javascript:"><i class="fa fa-trash-o"></i>&nbsp;删除</a></li>
								</shiro:hasPermission>
							</ul>
						</div>
					</div>
				</div>
				<div class="portlet-body" style="padding: 0;">
					<table id="personTable" data-show-toggle="false" data-show-columns="false">
						<thead>
						<tr role="row" class="heading">
							<th data-field="checkbox" data-checkbox="true"></th>
							<th data-field="name" data-sortable="true">姓名</th>
							<th data-field="genderName">性别</th>
							<th data-field="stateName">状态</th>
							<th data-field="natureName">性质</th>
						</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
		<div class="col-md-8">
			<div class="portlet box blue-dark tabbable">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-info-circle"></i>
						<span class="caption-subject">人员信息</span>
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
							<a href="#postTab" data-toggle="tab">所属岗位</a>
						</li>
					</ul>
				</div>
				<div class="portlet-body">
					<div class="tab-content">
						<div class="tab-pane active" id="infoTab">
							<jsp:include page="person-info.jsp" />
						</div>
						<div class="tab-pane" id="postTab">
							<jsp:include page="person-post.jsp" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="person-edit.jsp" />
	<script src="scripts/auth/person.js"></script>
</html>