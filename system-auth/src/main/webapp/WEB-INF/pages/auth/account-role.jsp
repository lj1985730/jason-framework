<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
	<div class="row">
		<div class="col-md-12">
			<table id="roleSelectorTable"
				   data-show-toggle="false" data-show-columns="false"
				   data-single-select="false" data-save="$.fn.bindRoleToAccount();">
				<thead>
				<tr role="row" class="heading">
					<th data-field="checkbox" data-checkbox="true"></th>
					<th data-field="name" data-sortable="true">角色名称</th>
					<th data-field="code" data-sortable="true">角色编码</th>
					<th data-field="isSystem" data-formatter="booleanFormatter">系统角色</th>
				</tr>
				</thead>
			</table>
		</div>
	</div>
	<script src="scripts/auth/account-role.js"></script>
</html>