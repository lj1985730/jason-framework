<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
	<div class="row">
		<div class="col-md-12">
			<div class="portlet light" style="margin-bottom: 0; box-shadow: 0 0 0 0 rgba(0, 0, 0, 0);">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-check-square-o"></i>选择所属岗位（可多选）
					</div>
					<div class="actions">
						<yonyou:save id="bindPosts" permission="auth:person:bindPosts" />
					</div>
				</div>
				<div class="portlet-body" style="padding: 0;">
					<div id="departmentPostTree"></div>
				</div>
			</div>
		</div>
	</div>
	<script src="scripts/auth/person-post.js"></script>
</html>