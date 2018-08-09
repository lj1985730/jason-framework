<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
	<yonyou:nav father="系统管理" model="系统字典" />

	<div class="row">
		<div class="col-md-12">
			<div class="portlet box blue-dark">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-globe"></i>字典列表
					</div>
					<div class="actions">
						<yonyou:create id="createDict" />
						<yonyou:update id="updateDict" />
						<yonyou:delete id="deleteDict" />
					</div>
				</div>
				<div class="portlet-body">
					<table id="dictTable">
						<thead>
						<tr role="row" class="heading">
							<th data-field="checkbox" data-checkbox="true"></th>
							<th data-field="id" data-formatter="indexFormatter">序号</th>
							<th data-field="categoryKey" data-sortable="true">字典分类</th>
							<th data-field="name" data-sortable="true">字典名称</th>
							<th data-field="code" data-sortable="true">字典编码</th>
							<th data-field="sortNumber" data-sortable="true">排序号</th>
							<th data-field="parentCode" data-sortable="true">父级编码</th>
						</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="sysDict-edit.jsp" />

	<script src="scripts/base/sysDict.js"></script>
</html>