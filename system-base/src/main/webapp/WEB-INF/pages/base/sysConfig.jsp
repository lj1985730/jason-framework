<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
	<yonyou:nav father="系统管理" model="系统参数" />

	<div class="row">
		<div class="col-md-12">
			<div class="portlet light">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-globe"></i>参数列表
					</div>
					<div class="actions">
						<yonyou:create id="createSysConfig" />
						<yonyou:update id="updateSysConfig" />
						<yonyou:delete id="deleteSysConfig" />
					</div>
				</div>
				<div class="portlet-body">
					<table id="configTable">
						<thead>
						<tr role="row" class="heading">
							<th data-field="checkbox" data-checkbox="true"></th>
							<th data-field="id" data-formatter="indexFormatter">序号</th>
							<th data-field="cfgKey" data-sortable="true">配置键</th>
							<th data-field="cfgValue" data-sortable="true">配置值</th>
							<th data-field="enabled" data-formatter="booleanFormatter">是否启用</th>
							<th data-field="editable" data-formatter="booleanFormatter">是否可修改</th>
							<th data-field="remark">备注</th>
						</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="sysConfig-edit.jsp" />

	<script src="scripts/base/sysConfig.js"></script>
</html>