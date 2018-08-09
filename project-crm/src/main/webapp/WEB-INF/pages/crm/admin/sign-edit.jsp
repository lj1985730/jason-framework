<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>

<html>
	<yonyou:modal id="signEditWin" modalClass="modal-md" title="编辑页面" editable="true">
		<form id="signEditWinForm" class="form-horizontal form-bordered form-row-stripped" data-toggle="validator">
			<input type="hidden" id="id" name="id" />
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-4">签到公司</label>
						<div class="col-md-8">
							<input class="form-control" type="hidden" id="edit_signCorp" name="signCorp"/>
							<input class="form-control" readonly id="edit_signCorpName" name="signCorpName" placeholder="签到公司"/>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-4">签到部门</label>
						<div class="col-md-8">
							<input class="form-control" type="hidden" id="edit_signDept" name="signDept"/>
							<input class="form-control" readonly id="edit_signDeptName" name="signDeptName" placeholder="签到部门"/>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-4">签到人</label>
						<div class="col-md-8">
							<input class="form-control" type="hidden" id="edit_signUser" name="signUser" />
							<input class="form-control" readonly id="edit_signUserName" name="signUserName" placeholder="签到人"/>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-4">签到日期</label>
						<div class="col-md-8">
							<input class="form-control" readonly id="edit_signDate" name="signDate" placeholder="签到日期" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-4">签到时间</label>
						<div class="col-md-8" id="signInTimeCon">
							<input class="form-control" readonly id="edit_signInTime" name="signInTime" placeholder="签到时间" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-4">签到地点</label>
						<div class="col-md-8">
							<input class="form-control" id="edit_signInAddress" name="signInAddress" placeholder="签到地点" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-4">签到纬度</label>
						<div class="col-md-8">
							<input class="form-control" id="edit_signInLatitude" name="signInLatitude" placeholder="签到纬度" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-4">签到纬度</label>
						<div class="col-md-8">
							<input class="form-control" id="edit_signInLongitude" name="signInLongitude" placeholder="签到经度" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-4">签退时间</label>
						<div class="col-md-8" id="signOutTimeCon">
							<input class="form-control" readonly id="edit_signOutTime" name="signOutTime" placeholder="签退时间" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-4">签退地点</label>
						<div class="col-md-8">
							<input class="form-control" id="edit_signOutAddress" name="signOutAddress" placeholder="签退地点" required maxlength="128" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-4">签退纬度</label>
						<div class="col-md-8">
							<input class="form-control" id="edit_signOutLatitude" name="signOutLatitude" placeholder="签退纬度" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-4">签退经度</label>
						<div class="col-md-8">
							<input class="form-control" id="edit_signOutLongitude" name="signOutLongitude" placeholder="签退经度" />
						</div>
					</div>
				</div>
			</div>
		</form>
	</yonyou:modal>
</html>