<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>

<html>
	<yonyou:modal id="editContactWin" modalClass="modal-lg modal-xl modal-xxl modal-xxxl modal-xxxxl" title="编辑联系人信息" editable="true">
		<form id="editContactForm" class="form-horizontal form-bordered form-row-stripped" data-toggle="validator">
			<input type="hidden" id="customerId" name="customerId" />
			<input type="hidden" id="contactId" name="id" />
			<div class="row">
				<div class="col-md-12">
					<div class="portlet light">
						<div class="portlet-body">
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_contact_name">姓名</label>
										<div class="col-md-8">
											<input class="form-control" id="edit_contact_name" name="name" placeholder="名称" required maxlength="30" />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_contact_gender">性别</label>
										<div class="col-md-8">
											<select class="form-control" id="edit_contact_gender" name="gender" style="width: 100%;"></select>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_contact_birthdate">生日</label>
										<div class="col-md-8">
											<yonyou:date id="edit_contact_birthdate" name="birthdate" format="mm-dd" />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_contact_department">部门</label>
										<div class="col-md-8">
											<select class="form-control" id="edit_contact_department" name="department" style="width: 100%;"></select>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_contact_post">职位</label>
										<div class="col-md-8">
											<select class="form-control" id="edit_contact_post" name="post" style="width: 100%;"></select>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_contact_officeNumber">办公电话</label>
										<div class="col-md-8">
											<input class="form-control" id="edit_contact_officeNumber" name="officeNumber" placeholder="办公电话" maxlength="20" />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_contact_mobileNumber">手机号</label>
										<div class="col-md-8">
											<input class="form-control" id="edit_contact_mobileNumber" name="mobileNumber" placeholder="手机号" maxlength="20" />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_contact_weixin">微信</label>
										<div class="col-md-8">
											<input class="form-control" id="edit_contact_weixin" name="weixin" placeholder="微信" maxlength="20" />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_contact_email">EMAIL</label>
										<div class="col-md-8">
											<input class="form-control" id="edit_contact_email" name="email" placeholder="EMAIL" maxlength="100" />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_contact_qq">QQ</label>
										<div class="col-md-8">
											<input class="form-control" id="edit_contact_qq" name="qq" placeholder="QQ" maxlength="20" />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_contact_characterTrait">性格特点</label>
										<div class="col-md-8">
											<textarea class="form-control" id="edit_contact_characterTrait" name="characterTrait" rows="2" placeholder="性格特点" maxlength="200"></textarea>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_contact_family">家庭情况</label>
										<div class="col-md-8">
											<textarea class="form-control" id="edit_contact_family" name="family" rows="2" placeholder="家庭情况" maxlength="200"></textarea>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_contact_profession">所学专业</label>
										<div class="col-md-8">
											<textarea class="form-control" id="edit_contact_profession" name="profession" rows="2" placeholder="所学专业" maxlength="200"></textarea>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_contact_background">背景</label>
										<div class="col-md-8">
											<textarea class="form-control" id="edit_contact_background" name="background" rows="2" placeholder="背景" maxlength="200"></textarea>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_contact_factions">公司内派系</label>
										<div class="col-md-8">
											<textarea class="form-control" id="edit_contact_factions" name="factions" rows="2" placeholder="公司内派系" maxlength="200"></textarea>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_contact_type">联系人分类</label>
										<div class="col-md-8">
											<select class="form-control" id="edit_contact_type" name="type" style="width: 100%;">
												<option selected>第一联系人</option>
												<option>第二联系人</option>
												<option>第三联系人</option>
											</select>
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
	<script type="text/javascript" src="scripts/crm/admin/contact-edit.js"></script>
</html>