<%--
  User: Sheng Baoyu
  Date: 2017-12-25
  Time: 16:23
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
	<yonyou:modal id="editWin" modalClass="modal-lg modal-xl modal-xxl modal-xxxl modal-xxxxl" title="编辑会议" editable="true">
		<form id="editForm" class="form-horizontal form-bordered form-row-stripped" data-toggle="validator">
			<input class="switch" id="id" name="id" type="hidden" />
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4" for="name">会议名称</label>
						<div class="col-md-8">
							<input class="form-control" id="name" name="name" placeholder="会议名称..." required maxlength="128" />
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4" for="location">会议地点</label>
						<div class="col-md-8">
							<input class="form-control" id="location" name="location" placeholder="会议地点..." required maxlength="128" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4">开会时间</label>
						<div class="col-md-8">
							<input class="form-control col-md-5 " id="startTime" name="startTime"  value="${startTime}" style="min-width: 192px;" readonly />
							<span class="input-group-addon col-md-1" style="line-height:20px;">至</span>
							<input class="form-control col-md-5 " id="endTime" name="endTime"  value="${endTime}" style="min-width: 192px;" readonly />
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4" for="isArchived">是否归档</label>
						<div class="col-md-8">
							<yonyou:radio name="isArchived" labels="是,否" values="true,false" required="required" />
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-2" for="belongsProject">所属项目</label>
						<div class="col-md-10">
							<input class="form-control" id="belongsProject" name="belongsProject" placeholder="所属项目..." maxlength="128" />
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-2" for="joinPerson">参会人员</label>
						<div class="col-md-10">
							<textarea class="form-control" id="joinPerson" name="joinPerson" placeholder="参会人员..." rows="3" maxlength="1000"></textarea>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-2" for="content">会议内容</label>
						<div class="col-md-10">
							<textarea class="form-control" id="content" name="content" placeholder="会议内容..." rows="3" maxlength="1000"></textarea>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-2" for="minutes">会议纪要</label>
						<div class="col-md-10">
							<textarea class="form-control" id="minutes" name="minutes" placeholder="会议纪要..." rows="3" maxlength="2000"></textarea>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-2" for="remark">会议备注</label>
						<div class="col-md-10">
							<textarea class="form-control" id="remark" name="remark" placeholder="会议备注..." rows="3" maxlength="1000"></textarea>
						</div>
					</div>
				</div>
			</div>
		</form>
	</yonyou:modal>
</html>