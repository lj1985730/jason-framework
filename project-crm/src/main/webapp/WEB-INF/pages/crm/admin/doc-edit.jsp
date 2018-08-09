<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>

<html>
	<yonyou:modal id="docEditWin" modalClass="modal-lg modal-xl modal-xxl modal-xxxl modal-xxxxl" title="编辑数据" editable="true">
		<form id="docEditWinForm" class="form-horizontal form-bordered form-row-stripped" data-toggle="validator">
			<input type="hidden" id="id" name="id" />
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-4">文档名称</label>
						<div class="col-md-8">
							<input class="form-control" id="edit_docName" name="docName" placeholder="文档名称" required maxlength="64" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-4">文件编号</label>
						<div class="col-md-8">
							<input class="form-control" id="edit_fileId" name="fileId" placeholder="文件编号" required maxlength="32" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-4">所属公司</label>
						<div class="col-md-8">
							<input class="form-control" id="edit_docCorp" name="docCorp" placeholder="所属公司" required maxlength="32" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-4">所属部门</label>
						<div class="col-md-8">
							<input class="form-control" id="edit_docDept" name="docDept" placeholder="所属部门" required maxlength="32" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-4">所属客户</label>
						<div class="col-md-8">
							<input class="form-control" id="edit_docCustomer" name="docCustomer" placeholder="所属客户" required maxlength="32" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-4">个人文档</label>
						<div class="col-md-8">
							<input class="form-control" id="edit_docPerson" name="docPerson" placeholder="个人文档" required maxlength="32" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-4">使用频率</label>
						<div class="col-md-8">
							<input class="form-control" id="edit_docFrequency" name="docFrequency" placeholder="使用频率" required maxlength="5" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-4">属性名称</label>
						<div class="col-md-8">
							<input class="form-control" id="edit_docCreateDate" name="docCreateDate" placeholder="属性名称" required maxlength="32" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-4">属性模块</label>
						<div class="col-md-8">
							<input class="form-control" id="edit_docUpdateDate" name="docUpdateDate" placeholder="属性模块" required maxlength="16" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-4">租户ID</label>
						<div class="col-md-8">
							<input class="form-control" id="edit_tenantId" name="tenantId" placeholder="租户ID" required maxlength="18" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-4">是否删除</label>
						<div class="col-md-8">
							<input class="form-control" id="edit_deleted" name="deleted" placeholder="是否删除" required maxlength="1" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-4">操作人</label>
						<div class="col-md-8">
							<input class="form-control" id="edit_lastModifyAccountId" name="lastModifyAccountId" placeholder="操作人" required maxlength="18" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-4">操作时间</label>
						<div class="col-md-8">
							<input class="form-control" id="edit_lastModifyTime" name="lastModifyTime" placeholder="操作时间" required maxlength="10" />
						</div>
					</div>
				</div>
			</div>
		</form>
	</yonyou:modal>
</html>