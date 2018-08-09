<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
	<yonyou:nav father="系统管理" model="企业管理" />

	<div class="row">
		<div class="col-md-12">
			<div class="portlet box blue-dark">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-globe"></i>企业列表
					</div>
					<div class="actions">
						<yonyou:btn-default id="enableTenant" label="启用/禁用" icon="fa fa-retweet" />
						<yonyou:create id="createTenant" />
						<yonyou:update id="updateTenant" />
						<yonyou:delete id="deleteTenant" />
					</div>
				</div>
				<div class="portlet-body">
					<div class="table-container">
						<table id="tenantTable">
							<thead>
							<tr role="row" class="heading">
								<th data-field="checkbox" data-checkbox="true"></th>
								<th data-field="id" data-formatter="indexFormatter">序号</th>
								<th data-field="name" data-sortable="true">名称</th>
								<th data-field="uscc" data-sortable="true">统一社会信用代码</th>
								<th data-field="category">类型</th>
								<th data-field="legalRepresentative">法人代表</th>
								<th data-field="registeredCapital">注册资本</th>
								<th data-field="establishmentDate">成立日期</th>
								<th data-field="enabled" data-formatter="booleanFormatter">是否启用</th>
							</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="tenant-edit.jsp" />

	<script src="scripts/auth/tenant.js"></script>
</html>