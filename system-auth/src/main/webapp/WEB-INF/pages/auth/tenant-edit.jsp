<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
	<yonyou:modal id="editWin" modalClass="modal-lg modal-xl modal-xxl modal-xxxl modal-xxxxl" title="编辑企业" editable="true">
		<form id="editForm" class="form-horizontal form-bordered form-row-stripped" data-toggle="validator">
			<input class="switch" id="id" name="id" type="hidden" />
			<input class="switch" id="enabled" name="enabled" type="hidden" />

			<h3 class="form-section">基本信息</h3>

			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4" for="name">名称</label>
						<div class="col-md-8">
							<input class="form-control" id="name" name="name" placeholder="企业名称..." required maxlength="200" />
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4" for="shortName">简称</label>
						<div class="col-md-8">
							<input class="form-control" id="shortName" name="shortName" placeholder="企业简称..." maxlength="100" />
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4" for="enName">英文名称</label>
						<div class="col-md-8">
							<input class="form-control" id="enName" name="enName" placeholder="企业英文名称..." maxlength="100" />
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4" for="category">企业类型</label>
						<div class="col-md-8">
							<select class="form-control" id="category" name="category" style="width: 100%;" required></select>
						</div>
					</div>
				</div>
			</div>

			<h3 class="form-section">联系方式</h3>

			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-2" for="location">公司地址</label>
						<div class="col-md-10">
							<input class="form-control" id="location" name="location" placeholder="企业住所..." maxlength="200" />
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4" for="linkman">联系人</label>
						<div class="col-md-8">
							<input class="form-control" id="linkman" name="linkman" placeholder="企业联系人..." maxlength="100" />
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4" for="phone">联系电话</label>
						<div class="col-md-8">
							<input class="form-control" id="phone" name="phone" placeholder="企业联系电话..." maxlength="100" />
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4" for="fax">传真</label>
						<div class="col-md-8">
							<input class="form-control" id="fax" name="fax" placeholder="企业传真号..." maxlength="100" />
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4" for="email">邮箱</label>
						<div class="col-md-8">
							<input class="form-control" type="email" id="email" name="email" placeholder="企业邮箱..." maxlength="100" />
						</div>
					</div>
				</div>
			</div>

			<h3 class="form-section">注册信息</h3>

			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4" for="uscc">统一社会信用码</label>
						<div class="col-md-8">
							<input class="form-control" id="uscc" name="uscc" placeholder="统一社会信用码..." required minlength="18" maxlength="18" />
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4" for="legalRepresentative">法定代表人</label>
						<div class="col-md-8">
							<input class="form-control" id="legalRepresentative" name="legalRepresentative" placeholder="企业法定代表人..." maxlength="200" />
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4" for="establishmentDate">成立日期</label>
						<div class="col-md-8">
							<yonyou:date id="establishmentDate" name="establishmentDate" placeholder="企业成立日期..." required="required" />
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4">营业期限</label>
						<div class="col-md-8">
							<yonyou:dateRange id="businessTermRange" startName="businessTermStart" endName="businessTermEnd" placeholder="企业营业期限..." />
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4" for="registeredCapital">注册资本（元）</label>
						<div class="col-md-8">
							<input class="form-control" type="number" id="registeredCapital" name="registeredCapital" placeholder="企业注册资本..." maxlength="14" />
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-2" for="businessScope">经营范围</label>
						<div class="col-md-10">
							<textarea class="form-control" id="businessScope" name="businessScope" placeholder="企业经营范围..." rows="3" maxlength="1000"></textarea>
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