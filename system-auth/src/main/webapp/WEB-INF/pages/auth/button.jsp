<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>

	<yonyou:nav father="系统管理" model="按钮管理" />

	<div class="row">
		<div class="col-md-3">
			<div class="portlet light" style="margin-bottom: 0;">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-list font-green-sharp"></i>
						<span class="caption-subject font-green-sharp bold">菜单列表</span>
					</div>
				</div>
				<div class="portlet-body" style="padding: 0;">
					<div id="treePanel"></div>
				</div>
			</div>
		</div>

		<div class="col-md-9">
			<div class="portlet box blue-dark">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-list"></i>
						<span class="caption-subject bold">按钮列表</span>
					</div>
					<div class="actions">
						<yonyou:btn-default id="enableButton" label="启用/禁用" icon="fa fa-retweet" />
						<yonyou:create id="createButton" />
						<yonyou:update id="updateButton" />
						<yonyou:delete id="deleteButton" />
					</div>
				</div>
				<div class="portlet-body">
					<div class="table-container">
						<table id="buttonTable">
							<thead>
							<tr role="row" class="heading">
								<th data-field="checkbox" data-checkbox="true"></th>
								<th data-field="id" data-formatter="indexFormatter">序号</th>
								<th data-field="name" data-sortable="true">名称</th>
								<th data-field="elementId" data-sortable="true">HTML元素ID</th>
								<th data-field="enabled" data-formatter="booleanFormatter">是否启用</th>
								<th data-field="permission" data-sortable="true">权限字符串</th>
								<th data-field="remark">备注</th>
							</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="button-edit.jsp" />

	<script type="text/javascript" src="scripts/base/tree-select.js"></script>
	<script src="scripts/auth/button.js"></script>
</html>