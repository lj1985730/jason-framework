<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
<div id="personWin" class="modal fade" role="dialog" data-backdrop="static">
	<div class="modal-lg modal-xl modal-xxl modal-xxxl modal-xxxxl modal-dialog">
		<div class="modal-content">
			<div class="modal-header" style="background-color: dimgray; border-top-left-radius: 5px; border-top-right-radius: 5px;">
				<h4 class="modal-title" style="color: white;">
					<i class="icon-pencil"></i>
					<span id="personWinTitle" style="font-weight: bold;">选择人员</span>
					<i class="fa fa-remove" style="float: right; padding-top: 4px; padding-right: 4px; color: #FFFFFF; transform: scale(1.5);"
					   onmouseover="this.style.cssText='float: right; padding-top: 3px; padding-right: 3px; color: #FFFFFF; transform: scale(2);'"
					   onmouseout="this.style.cssText='float: right; padding-top: 4px; padding-right: 4px; color: color: #FFFFFF; transform: scale(1.5);'" data-dismiss="modal" data-target="#personWin"></i>
				</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-3" style="padding-left: 5px; padding-right: 5px; min-width: 220px;">
						<div class="portlet light">
							<div class="portlet-title">
								<div class="caption">
									<i class="fa fa-cog font-green-sharp"></i>
									<span class="caption-subject font-green-sharp bold">部门列表</span>
								</div>
							</div>
							<div class="portlet-body" style="padding: 0;">
								<div id="departmentPostTree"></div>
							</div>
						</div>
					</div>
					<div class="col-md-9">
						<div class="portlet box blue-dark">
							<div class="portlet-title">
								<div class="caption">
									<i class="fa fa-globe"></i>人员列表
								</div>
								<div class="actions">
									<yonyou:btn-default id="personSelectorCheckBtn" label="选择" icon="fa fa-check" />
								</div>
							</div>
							<div class="portlet-body">
								<table id="personTable" class="table-condensed">
									<thead>
									<tr role="row" class="heading">
										<th data-field="checkbox" data-checkbox="true" style="width: 0;"></th>
										<th data-field="id" data-formatter="indexFormatter">序号</th>
										<th data-field="name" data-sortable="true">姓名</th>
										<th data-field="genderName">性别</th>
										<th data-field="idNumber">身份证号</th>
										<th data-field="phone">手机号码</th>
										<th data-field="stateName">人员状态</th>
										<th data-field="natureName">人员性质</th>
									</tr>
									</thead>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="scripts/base/tree-select.js"></script>
<script src="scripts/auth/person-select-modal-new.js"></script>
</html>