<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
	<div class="row">
		<div class="col-md-12">
			<div class="portlet light" style="margin-bottom: 0; box-shadow: 0 0 0 0 rgba(0, 0, 0, 0);">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-check-square-o"></i>选择管辖角色（可多选）
					</div>
					<div class="actions">
						<yonyou:save id="bindRoleRlat" permission="auth:role:bindRoleRlat" />
					</div>
				</div>
				<div class="portlet-body" style="padding: 0;">
					<table id="roleRlatTable" data-show-toggle="false" data-show-columns="false"
						   data-save="$.fn.bindRoleRlat();">
						<thead>
							<tr role="row" class="heading">
								<th data-field="checkbox" data-checkbox="true"></th>
								<th data-field="name" data-sortable="true">角色名称</th>
								<th data-field="dataPermission" data-sortable="true" data-formatter="$.fn.isDataPermission">数据权限</th>
								<th data-field="auditPermission" data-sortable="true" data-formatter="$.fn.isAuditPermission">审核权限</th>
								<th data-field="enabled" data-formatter="booleanFormatter">启用</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
	<script src="scripts/auth/role-rlat.js"></script>
</html>