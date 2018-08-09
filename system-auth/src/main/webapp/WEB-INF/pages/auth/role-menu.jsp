<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
	<div class="row">
		<div class="col-md-12">
			<div class="portlet light" style="margin-bottom: 0; box-shadow: 0 0 0 0 rgba(0, 0, 0, 0);">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-check-square-o"></i>选择菜单权限（可多选）
					</div>
					<div class="actions">
						<yonyou:save id="bindMenu" permission="auth:role:bindMenu" />
					</div>
				</div>
				<div class="portlet-body" style="padding: 0;">
					<div id="treePanel"></div>
				</div>
			</div>
		</div>
	</div>
	<script src="scripts/auth/role-menu.js"></script>
</html>