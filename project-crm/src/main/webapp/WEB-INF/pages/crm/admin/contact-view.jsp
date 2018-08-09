<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>

<html>
	<yonyou:modal id="viewContactWin" modalClass="modal-lg modal-xl modal-xxl modal-xxxl modal-xxxxl" title="查看联系人信息">
		<form id="viewContactForm" class="form-horizontal form-bordered form-row-stripped">
			<div class="row">
				<div class="col-md-12">
					<div class="portlet light">
						<div class="portlet-body">
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">姓名</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="name"></p>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">性别</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="genderText"></p>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">生日</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="birthdate"></p>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">部门</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="departmentText"></p>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">职位</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="postText"></p>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">办公电话</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="officeNumber"></p>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">手机号</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="mobileNumber"></p>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">微信</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="weixin"></p>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">EMAIL</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="email"></p>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">QQ</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="qq"></p>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">性格特点</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="characterTrait"></p>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">家庭情况</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="family"></p>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">所学专业</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="profession"></p>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">背景</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="background"></p>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">公司内派系</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="factions"></p>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">联系人分类</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="type"></p>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
	</yonyou:modal>
	<script type="text/javascript" src="scripts/crm/admin/contact-view.js"></script>
</html>