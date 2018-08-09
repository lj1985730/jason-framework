<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
	<div class="row">
		<div class="col-md-12">
			<div class="portlet light" style="margin-bottom: 0; box-shadow: 0 0 0 0 rgba(0, 0, 0, 0);">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-check-square-o"></i>选择管辖岗位
					</div>
					<div class="actions">
						<yonyou:save id="bindSubordinates" permission="auth:post:bindSubordinates" />
					</div>
				</div>
				<div class="portlet-body" style="padding: 0;">
					<div id="subordinatePostTree"></div>
				</div>
			</div>
		</div>
	</div>
</html>