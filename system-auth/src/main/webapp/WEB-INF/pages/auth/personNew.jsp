<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
	<div class="row">
		<div class="col-md-12">
			<div class="portlet light" style="margin-bottom:0;">
				<div class="portlet-title">
					<div class="actions">
						<yonyou:create id="createPerson" permission="auth:person:create" />
						<yonyou:update id="updatePerson" permission="auth:person:update" />
						<yonyou:delete id="deletePerson" permission="auth:person:delete" />
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
	</div>

	<jsp:include page="person-edit-new.jsp" />
	<script src="scripts/auth/personNew.js"></script>
</html>