<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
	<yonyou:modal id="editWin" modalClass="modal-md modal-lg" title="编辑岗位" editable="true">
		<form id="editForm" class="form-horizontal form-bordered form-row-stripped" data-toggle="validator">
			<input class="switch" id="id" name="id" type="hidden" />
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-3">所属部门</label>
						<div class="col-md-9">
							<p class="form-control-static" id="departmentName" data-property="department.name"></p>
							<input type="hidden" id="departmentId" name="departmentId" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-3" for="name">岗位名称</label>
						<div class="col-md-9">
							<input class="form-control" id="name" name="name" placeholder="岗位名称..." required maxlength="30" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-3" for="name">岗位编码</label>
						<div class="col-md-9">
							<input class="form-control" id="code" name="code" placeholder="岗位编码..." maxlength="20" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-3" for="isLeader">部门长</label>
						<div class="col-md-9">
							<yonyou:radio name="isLeader" labels="是,否" values="true,false" required="required" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-3" for="remark">备注</label>
						<div class="col-md-9">
							<textarea class="form-control" id="remark" name="remark" placeholder="备注..." rows="3" maxlength="200"></textarea>
						</div>
					</div>
				</div>
			</div>
		</form>
	</yonyou:modal>
</html>