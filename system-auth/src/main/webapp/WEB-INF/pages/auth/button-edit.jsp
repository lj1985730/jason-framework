<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
	<yonyou:modal id="editWin" modalClass="modal-lg modal-xl modal-xxl modal-xxxl modal-xxxxl" title="编辑按钮" editable="true">
		<form id="editForm" data-toggle="validator">
			<input class="switch" id="id" name="id" type="hidden" />
			<input class="switch" id="menuId" name="menuId" type="hidden" />
			<div class="form-body">
				<div class="form-group form-md-line-input form-md-floating-label">
					<input class="form-control" id="name" name="name" required maxlength="20" />
					<label for="name">按钮名称</label>
					<div class="form-control-focus"></div>
					<span class="help-block help-info"><b>必填</b>，请输入最多20个字符</span>
				</div>
				<div class="form-group form-md-line-input form-md-floating-label">
					<input class="form-control" id="elementId" name="elementId" required maxlength="30" />
					<label for="elementId">HTML元素ID</label>
					<span class="help-block help-info"><b>必填</b>，请输入最多30个字符</span>
				</div>
				<div class="form-group form-md-radios">
					<label>是否启用</label>
					<div class="md-radio-inline">
						<div class="md-radio">
							<input type="radio" id="enabled_true" name="enabled" value="true" class="md-radiobtn" checked>
							<label for="enabled_true">
								<span></span>
								<span class="check"></span>
								<span class="box"></span>
								是 </label>
						</div>
						<div class="md-radio">
							<input type="radio" id="enabled_false" name="enabled" value="false" class="md-radiobtn">
							<label for="enabled_false">
								<span></span>
								<span class="check"></span>
								<span class="box"></span>
								否 </label>
						</div>
					</div>
				</div>
				<div class="form-group form-md-line-input form-md-floating-label">
					<input class="form-control" id="permission" name="permission" required maxlength="100" />
					<label for="permission">权限字符串</label>
					<span class="help-block help-info"><b>必填</b>，请输入最多100个字符</span>
				</div>
				<div class="form-group form-md-line-input form-md-floating-label">
					<textarea class="form-control" id="remark" name="remark" rows="3" maxlength="300"></textarea>
					<label for="remark">备注</label>
					<span class="help-block help-info">请输入最多300个字符</span>
				</div>
			</div>
		</form>
	</yonyou:modal>
</html>