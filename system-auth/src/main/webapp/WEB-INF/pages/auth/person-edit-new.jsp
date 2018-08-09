<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
	<yonyou:modal id="editWin" modalClass="modal-lg modal-xl modal-xxl modal-xxxl modal-xxxxl" title="编辑人员信息" editable="true">
		<form id="personEditForm" class="form-horizontal form-row-stripped" data-toggle="validator">
			<input id="id" name="id" type="hidden" />
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4" for="name">姓名</label>
						<div class="col-md-8">
							<input class="form-control" id="name" name="name" placeholder="员工姓名..." required maxlength="100" />
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4" for="enName">英文名称</label>
						<div class="col-md-8">
							<input class="form-control" id="enName" name="enName" placeholder="英文名称..."  maxlength="100" />
						</div>
					</div>
				</div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label class="control-label col-md-4" for="gender">性别</label>
                        <div class="col-md-8">
                            <select class="form-control" id="gender" name="gender" style="width: 100%;" required></select>
                        </div>
                    </div>
                </div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4">主职部门</label>
						<div class="col-md-8">
							<select class="form-control" id="departmentId" name="departmentId" style="width: 100%;"  title="主职部门"></select>
						</div>
					</div>
				</div>
				<div class="col-md-8">
					<div class="form-group">
						<label class="control-label col-md-2">兼职部门</label>
						<div class="col-md-10">
							<select class="form-control" id="partTimeDepartments" name="partTimeDepartments" style="width: 100%;"  title="兼职部门" multiple></select>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4" for="idNumber">身份证号</label>
						<div class="col-md-8">
							<input class="form-control" id="idNumber" name="idNumber" placeholder="身份证号..." maxlength="18" />
						</div>
					</div>
				</div>
				<div class="col-md-8">
					<div class="form-group">
						<label class="control-label col-md-2" for="address">家庭住址</label>
						<div class="col-md-10">
							<input class="form-control" id="address" name="address" placeholder="家庭住址..." maxlength="100" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4" for="phone">手机号码</label>
						<div class="col-md-8">
							<input class="form-control" type="tel" id="phone" name="phone" placeholder="手机号码..."/>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4" for="officeNumber">办公号码</label>
						<div class="col-md-8">
							<input class="form-control" id="officeNumber" name="officeNumber" placeholder="办公号码..." />
						</div>
					</div>
				</div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label class="control-label col-md-4" for="email">邮箱</label>
                        <div class="col-md-8">
                            <input class="form-control" type="email" id="email" name="email" placeholder="邮箱..." maxlength="100" />
                        </div>
                    </div>
                </div>
			</div>
			<div class="row">
                <div class="col-md-4">
                    <div class="form-group">
                        <label class="control-label col-md-4" for="country">国籍</label>
                        <div class="col-md-8">
                            <input class="form-control" id="country" name="country" placeholder="所属国籍..." maxlength="100" />
                        </div>
                    </div>
                </div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4" for="nationality">民族</label>
						<div class="col-md-8">
							<select class="form-control" id="nationality" name="nationality" style="width: 100%;"></select>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4" for="birthPlace">出生地</label>
						<div class="col-md-8">
							<input class="form-control" id="birthPlace" name="birthPlace" placeholder="出生地..." maxlength="100" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4" for="nature">人员性质</label>
						<div class="col-md-8">
							<select class="form-control" id="nature" name="nature" style="width: 100%;" required></select>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4" for="state">人员状态</label>
						<div class="col-md-8">
							<select class="form-control" id="state" name="state" style="width: 100%;" required></select>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4">出生日期</label>
						<div class="col-md-8">
							<yonyou:date id="birthDate" name="birthDate" placeholder="出生日期..." end="current" orientation="top" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-2" for="remark">备注</label>
						<div class="col-md-10">
							<textarea class="form-control" id="remark" name="remark" placeholder="备注..." rows="3" maxlength="1000"></textarea>
						</div>
					</div>
				</div>
			</div>
		</form>
	</yonyou:modal>
</html>