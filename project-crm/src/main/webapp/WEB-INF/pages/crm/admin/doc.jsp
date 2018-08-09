<%-- 作 者：Wang chong --%>
<%-- 本页说明：--%>
<%-- 本页地址:crm/admin/DocController/homeView --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>

<html>
	<yonyou:nav father="模块名" model="功能名" />
	<div class="row">
		<div class="col-md-12">
			<div class="portlet box blue-dark">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-list">
							
						</i>
						XX列表
					</div>
					<div class="actions">
						<yonyou:create id="createDoc" permission="crm:doc:create" />
						<yonyou:update id="updateDoc" permission="crm:doc:update" />
						<yonyou:delete id="deleteDoc" permission="crm:doc:delete" />
					</div>
				</div>
				<div class="portlet-body">
					<div class="table-container">
						<table id="docTable">
							<thead>
								<tr role="row" class="heading">
									<th data-field="checkbox" data-checkbox="true" />
									<th data-field="id" data-formatter="indexFormatter">
										序号
									</th>
									<th data-field="id" data-visible="false">
										文档编号
									</th>
									<th data-field="docName" data-sortable="true">
										文档名称
									</th>
									<th data-field="fileId" data-sortable="true">
										文件编号
									</th>
									<th data-field="docCorp" data-sortable="true">
										所属公司
									</th>
									<th data-field="docDept" data-sortable="true">
										所属部门
									</th>
									<th data-field="docCustomer" data-sortable="true">
										所属客户
									</th>
									<th data-field="docPerson" data-sortable="true">
										个人文档
									</th>
									<th data-field="docFrequency" data-sortable="true">
										使用频率
									</th>
									<th data-field="docCreateDate" data-sortable="true">
										属性名称
									</th>
									<th data-field="docUpdateDate" data-sortable="true">
										属性模块
									</th>
									<th data-field="tenantId" data-sortable="true">
										租户ID
									</th>
									<th data-field="deleted" data-sortable="true">
										是否删除
									</th>
									<th data-field="lastModifyAccountId" data-sortable="true">
										操作人
									</th>
									<th data-field="lastModifyTime" data-sortable="true">
										操作时间
									</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="doc-edit.jsp" />
	<script type="text/javascript" src="scripts/crm/admin/doc.js"></script>
</html>