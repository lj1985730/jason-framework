<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
	<script type="text/javascript" src="scripts/base/file-table.js"></script>
	<yonyou:modal id="editConfigWin" modalClass="modal-lg modal-xl modal-xxl modal-xxxl modal-xxxxl" title="编辑企业" editable="true">
		<form id="editConfigForm" class="form-horizontal form-bordered form-row-stripped" data-toggle="validator">
			<input class="switch" type="hidden" id="id" name="id" />
			<input class="switch" type="hidden" id="editable" name="editable" />
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-2" for="cfgKey">配置键</label>
						<div class="col-md-10">
							<input class="form-control" id="cfgKey" name="cfgKey" placeholder="配置键..." required maxlength="200" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-2" for="cfgValue">配置值</label>
						<div class="col-md-10">
							<input class="form-control" id="cfgValue" name="cfgValue" placeholder="配置值..." required maxlength="200" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-2">是否生效</label>
						<div class="col-md-10">
							<yonyou:switch name="enabled" />
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
		<yonyou:file-table id="attachment" businessKey="SYS_CONFIG" readonly="false" />
	</yonyou:modal>
</html>