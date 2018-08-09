<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
	<yonyou:nav father="系统管理" model="我的企业" />

	<div class="row">
		<div class="col-md-12">
			<div class="portlet box blue-dark">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-info-circle"></i>企业信息
					</div>
					<div class="tools">
						<a class="collapse"></a>
						<a class="fullscreen"></a>
					</div>
					<div class="actions">
						<yonyou:update id="updateSelfTenant" permission="auth:tenant:update" />
					</div>
				</div>
				<div class="portlet-body">
					<form id="readonlyForm" class="form-horizontal form-bordered form-row-stripped" data-toggle="validator">
						<input class="switch" id="id" name="id" type="hidden" />

						<h3 class="form-section">基本信息</h3>

						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">名称：</label>
									<div class="col-md-8">
										<p class="form-control-static" data-property="name">${tenant.name}</p>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">简称：</label>
									<div class="col-md-8">
										<p class="form-control-static" data-property="shortName">${tenant.shortName}</p>
									</div>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">英文名称：</label>
									<div class="col-md-8">
										<p class="form-control-static" data-property="enName">${tenant.enName}</p>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">企业类型：</label>
									<div class="col-md-8">
										<p class="form-control-static" id="readonly-category" data-property="category">${tenant.categoryText}</p>
									</div>
								</div>
							</div>
						</div>

						<h3 class="form-section">联系方式</h3>

						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label class="control-label col-md-2">公司地址：</label>
									<div class="col-md-10">
										<p class="form-control-static" data-property="location">${tenant.location}</p>
									</div>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">联系人：</label>
									<div class="col-md-8">
										<p class="form-control-static" data-property="linkman">${tenant.linkman}</p>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">联系电话：</label>
									<div class="col-md-8">
										<p class="form-control-static" data-property="phone">${tenant.phone}</p>
									</div>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">传真：</label>
									<div class="col-md-8">
										<p class="form-control-static" data-property="fax">${tenant.fax}</p>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">邮箱：</label>
									<div class="col-md-8">
										<p class="form-control-static" data-property="email">${tenant.email}</p>
									</div>
								</div>
							</div>
						</div>

						<h3 class="form-section">注册信息</h3>

						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">统一社会信用码：</label>
									<div class="col-md-8">
										<p class="form-control-static" data-property="uscc">${tenant.uscc}</p>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">法定代表人：</label>
									<div class="col-md-8">
										<p class="form-control-static" data-property="legalRepresentative">${tenant.legalRepresentative}</p>
									</div>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">成立日期：</label>
									<div class="col-md-8">
										<p class="form-control-static" data-property="establishmentDate">${tenant.establishmentDate}</p>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">营业期限：</label>
									<div class="col-md-8">
										<p class="form-control-static" id="readonly-businessTerm">
											${tenant.businessTermStart} ~ ${tenant.businessTermEnd}
										</p>
									</div>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">注册资本（元）：</label>
									<div class="col-md-8">
										<p class="form-control-static" data-property="registeredCapital">${tenant.registeredCapital}</p>
									</div>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label class="control-label col-md-2">经营范围：</label>
									<div class="col-md-10">
										<p class="form-control-static" data-property="businessScope">${tenant.businessScope}</p>
									</div>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label class="control-label col-md-2">备注：</label>
									<div class="col-md-10">
										<p class="form-control-static" data-property="remark">${tenant.remark}</p>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="tenant-edit.jsp" />

	<script src="scripts/auth/tenant-self.js"></script>
</html>