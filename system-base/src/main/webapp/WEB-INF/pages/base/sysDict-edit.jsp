<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
	<yonyou:modal id="editDictWin" modalClass="modal-lg modal-xl modal-xxl modal-xxxl modal-xxxxl" title="编辑字典" editable="true">
		<form id="editDictForm" class="form-horizontal form-bordered form-row-stripped" data-toggle="validator">
			<input class="switch" type="hidden" id="id" name="id" />
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-2" for="categoryKey">字典分类</label>
						<div class="col-md-10">
							<input class="form-control" id="categoryKey" name="categoryKey" placeholder="字典分类..." required maxlength="200" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-2" for="name">字典名称</label>
						<div class="col-md-10">
							<input class="form-control" id="name" name="name" placeholder="字典名称..." required maxlength="20" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-2" for="code">字典编码</label>
						<div class="col-md-10">
							<input type="number" class="form-control" id="code" name="code" placeholder="字典编码..." required min="1" max="999999999" maxlength="9" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-2" for="parentCode">父级编码</label>
						<div class="col-md-10">
							<input type="number" class="form-control" id="parentCode" name="parentCode" placeholder="父级编码..." min="1" max="999999999" maxlength="9" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-2" for="sortNumber">排序号</label>
						<div class="col-md-10">
							<input type="number" class="form-control" id="sortNumber" name="sortNumber" placeholder="父级编码..." min="1" max="999999999" maxlength="9" />
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