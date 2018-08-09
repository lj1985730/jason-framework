<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
	<div class="portlet box blue-dark">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-info-circle"></i>
				<span class="caption-subject bold">菜单明细</span>
			</div>
			<div class="tools">
				<a class="collapse"></a>
				<a class="fullscreen"></a>
			</div>
			<div class="actions">
				<button type="button" class="btn btn-default" id="formSubmitBtn"><i class="fa fa-check"></i>&nbsp;保存</button>
			</div>
		</div>
		<div class="portlet-body form">
			<form id="editForm" data-toggle="validator">
				<input type="hidden" id="id" name="id" />
				<input type="hidden" id="parentId" name="parentId" />
				<input type="hidden" id="level" name="level" />
				<div class="form-body">
					<div class="form-group form-md-line-input form-md-floating-label">
						<input class="form-control" id="name" name="name" required maxlength="40" />
						<label for="name">菜单名称</label>
						<span class="help-block">请输入最多40个字符</span>
					</div>
					<div class="form-group form-md-line-input form-md-floating-label">
						<input class="form-control" type="number" id="sortNumber" name="sortNumber" max="99" step="1" />
						<label for="sortNumber">排序号</label>
						<span class="help-block">请输入最大99的数字</span>
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
						<input class="form-control" id="uri" name="uri" maxlength="160" />
						<label for="uri">资源标识符</label>
						<span class="help-block">请输入最多160个字符</span>
					</div>
					<div class="form-group form-md-line-input form-md-floating-label">
						<input class="form-control" id="icon" name="icon" maxlength="30" />
						<label for="icon">标准图标</label>
						<span class="help-block">请输入最多30个字符</span>
					</div>
					<div class="form-group form-md-line-input form-md-floating-label">
						<input class="form-control" id="bigIcon" name="bigIcon" maxlength="30" />
						<label for="bigIcon">大图标</label>
						<span class="help-block">请输入最多30个字符</span>
					</div>
					<div class="form-group form-md-line-input form-md-floating-label">
						<textarea class="form-control" id="description" name="description" rows="3" maxlength="300"></textarea>
						<label for="description">描述</label>
						<span class="help-block">请输入最多300个字符</span>
					</div>
					<div class="form-group form-md-line-input form-md-floating-label">
						<input class="form-control" id="shortcut" name="shortcut" maxlength="30" />
						<label for="shortcut">快捷键</label>
						<span class="help-block">请输入最多30个字符</span>
					</div>
					<div class="form-group form-md-radios">
						<label>菜单性质</label>
						<div class="md-radio-inline">
							<div class="md-radio">
								<input type="radio" id="isPublic_0" name="isPublic" value="0" class="md-radiobtn">
								<label for="isPublic_0">
									<span></span><span class="check"></span><span class="box"></span> 全部
								</label>
							</div>
							<div class="md-radio">
								<input type="radio" id="isPublic_1" name="isPublic" value="1" class="md-radiobtn" checked>
								<label for="isPublic_1">
									<span></span><span class="check"></span><span class="box"></span> 前台
								</label>
							</div>
							<div class="md-radio">
								<input type="radio" id="isPublic_2" name="isPublic" value="2" class="md-radiobtn">
								<label for="isPublic_2">
									<span></span><span class="check"></span><span class="box"></span> 后台
								</label>
							</div>
						</div>
					</div>
					<div class="form-group form-md-radios">
						<label>可分配</label>
						<div class="md-radio-inline">
							<div class="md-radio">
								<input type="radio" id="assignable_true" name="assignable" value="true" class="md-radiobtn" checked>
								<label for="assignable_true">
									<span></span><span class="check"></span><span class="box"></span> 是
								</label>
							</div>
							<div class="md-radio">
								<input type="radio" id="assignable_false" name="assignable" value="false" class="md-radiobtn">
								<label for="assignable_false">
									<span></span><span class="check"></span><span class="box"></span> 否
								</label>
							</div>
						</div>
					</div>
					<div class="form-group form-md-line-input form-md-floating-label">
						<input class="form-control" id="permission" name="permission" maxlength="100" />
						<label for="permission">权限字符串</label>
						<span class="help-block">请输入最多100个字符</span>
					</div>
					<div class="form-group form-md-line-input form-md-floating-label">
						<textarea class="form-control" id="remark" name="remark" rows="3" maxlength="300"></textarea>
						<label for="remark">备注</label>
						<span class="help-block">请输入最多300个字符</span>
					</div>
				</div>
			</form>
		</div>
	</div>
</html>