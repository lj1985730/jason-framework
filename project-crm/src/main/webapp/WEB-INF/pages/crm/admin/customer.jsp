<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<link type="text/css" rel="stylesheet" href="plugins/bootstrap-fileinput/bootstrap-fileinput.css" />
<html>
	<yonyou:nav father="统一营销数据库" model="统一营销管理" />
	<div class="row">
		<div class="col-md-12">
			<div class="portlet box blue-dark">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-list"></i>客户列表
					</div>
					<div class="tools">
						<a class="collapse"></a>
						<a class="fullscreen"></a>
					</div>
					<div class="actions">
						<yonyou:btn-default id="applyCustomer" label="提交审核" icon="fa fa-mail-forward" permission="crm:customer:apply"  />
						<yonyou:btn-view id="viewCustomer" />
						<yonyou:create id="createCustomer" permission="crm:customer:create" />
						<yonyou:update id="updateCustomer" permission="crm:customer:update" />
						<yonyou:delete id="deleteCustomer" permission="crm:customer:delete" />
					</div>
				</div>
				<div class="portlet-body">
					<div class="table-container">
						<table id="customerTable" data-export="$.fn.export();">
							<thead>
								<tr role="row" class="heading">
									<th data-field="checkbox" data-checkbox="true"></th>
									<th data-field="id" data-formatter="indexFormatter">序号</th>
									<th data-field="name">客户名称</th>
									<th data-field="uscc">统一信用代码</th>
									<th data-field="legalRepresentative">法定代表人</th>
									<th data-field="establishmentDate">成立时间</th>
									<th data-field="industryText">行业</th>
									<th data-field="natureText">企业性质</th>
									<th data-field="sizeText">公司规模</th>
									<th data-field="originText">线索来源</th>
									<th data-field="originTracker">线索跟踪人</th>
									<th data-field="salesman">销售对应人</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/pages/crm/admin/customer-edit.jsp" />
	<jsp:include page="/WEB-INF/pages/crm/admin/customer-view.jsp" />
	<jsp:include page="/WEB-INF/pages/crm/admin/contact-view.jsp" />
	<script type="text/javascript" src="scripts/crm/admin/customer.js"></script>
</html>