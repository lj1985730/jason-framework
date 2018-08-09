<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
	<style>
		#viewCustomerForm .select2-selection--multiple {
			background-color: #fff;
		}
	</style>
	<yonyou:modal id="viewCustomerWin" modalClass="modal-lg modal-xl modal-xxl modal-xxxl modal-xxxxl" title="查看客户信息">
		<form id="viewCustomerForm" class="form-horizontal form-bordered form-row-stripped">
			<input type="hidden" id="view_id" name="id" />
			<div class="row">
				<div class="col-md-12">
					<div class="portlet light">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-info"></i>
								<span class="caption-subject">基本信息</span>
							</div>
							<div class="tools">
								<a class="collapse"></a>
								<a class="fullscreen"></a>
							</div>
						</div>
						<div class="portlet-body">
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-1">名称</label>
										<div class="col-md-11">
											<p class="form-control-static" data-property="name"></p>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label col-md-4">简称</label>
											<div class="col-md-8">
												<p class="form-control-static" data-property="shortName"></p>
											</div>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label col-md-4">统一信用代码</label>
											<div class="col-md-8">
												<p class="form-control-static" data-property="uscc"></p>
											</div>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label col-md-4">数据来源</label>
											<div class="col-md-8">
												<p class="form-control-static" data-property="originText"></p>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="portlet light">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-info"></i>
								<span class="caption-subject">工商信息</span>
							</div>
							<div class="tools">
								<a class="collapse"></a>
								<a class="fullscreen"></a>
							</div>
						</div>
						<div class="portlet-body">
							<div class="row">
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">法定代表人</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="legalRepresentative"></p>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">法人电话</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="lrNumber"></p>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">成立时间</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="establishmentDate"></p>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">注册资本(万元)</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="registeredCapital"></p>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">企业性质</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="natureText"></p>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">所属行业</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="industryText"></p>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">集团企业</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="isGroup"></p>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">上市情况</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="listingStatusText"></p>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">上级单位</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="superior"></p>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="portlet light">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-link"></i>
								<span class="caption-subject">联系方式</span>
							</div>
							<div class="tools">
								<a class="collapse"></a>
								<a class="fullscreen"></a>
							</div>
						</div>
						<div class="portlet-body">
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-1" style="width: 11.1%">公司地址</label>
										<div class="col-md-11" style="width: 88.9%">
											<p class="form-control-static" data-property="address"></p>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">公司电话</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="tel"></p>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">公司传真</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="fax"></p>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">公司网站</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="website"></p>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">所属区域</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="areaText"></p>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">所在省</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="provinceText"></p>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">所在市</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="cityText"></p>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">所在区</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="districtText"></p>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="portlet light">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-cny"></i>
								<span class="caption-subject">商业特征</span>
							</div>
							<div class="tools">
								<a class="collapse"></a>
								<a class="fullscreen"></a>
							</div>
						</div>
						<div class="portlet-body">
							<div class="row">
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">公司规模</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="sizeText"></p>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">总资产(万元)</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="totalAssets"></p>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">年产值(万元)</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="annualOutputValue"></p>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">年产值规模</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="annualOutputScaleText"></p>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">用工规模</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="employeeSizeText"></p>
										</div>
									</div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-1" style="width: 11.1%">主营业务</label>
										<div class="col-md-11" style="width: 88.9%">
											<p class="form-control-static" data-property="mainBusiness"></p>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">经营状况</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="operateStatusText"></p>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">市场地位</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="marketPositionText"></p>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">发展潜力</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="potentialText"></p>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="portlet light">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-link"></i>
								<span class="caption-subject">信息化概要</span>
							</div>
							<div class="tools">
								<a class="collapse"></a>
								<a class="fullscreen"></a>
							</div>
						</div>
						<div class="portlet-body">
							<div class="row">
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">有信息部门</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="hasItDepartment"></p>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">信息部主管</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="itManager"></p>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">主管电话</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="itManagerNumber"></p>
										</div>
									</div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-2" style="width: 11.1%">信息化状况</label>
										<div class="col-md-10" style="width: 88.9%">
											<p class="form-control-static" data-property="itSituation"></p>
										</div>
									</div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-1" style="width: 11.1%" for="view_mainSoftwareBrand">主要软件品牌</label>
										<div class="col-md-10" style="width: 88.9%">
											<select class="form-control" id="view_mainSoftwareBrand" name="mainSoftwareBrand" multiple style="width: 100%;" disabled></select>
										</div>
									</div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-1" style="width: 11.1%" for="view_mainSoftwareLine">主要产品线</label>
										<div class="col-md-10" style="width: 88.9%">
											<select class="form-control" id="view_mainSoftwareLine" name="mainSoftwareLine" multiple style="width: 100%;" disabled></select>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
		<div class="row">
			<div  class="col-md-12">
				<div class="portlet light">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-users"></i>
							<span class="caption-subject">联系人列表</span>
						</div>
						<div class="tools">
							<a class="collapse"></a>
							<a class="fullscreen"></a>
						</div>
						<div class="actions">
							<button type="button" class="btn green" id="viewViewContact"><i class="fa fa-search"></i>&nbsp;查看</button>
						</div>
					</div>
					<div class="portlet-body">
						<div class="table-container">
							<table id="viewContactTable">
								<thead>
								<tr role="row" class="heading">
									<th data-field="checkbox" data-checkbox="true"></th>
									<th data-field="id" data-formatter="indexFormatter">序号</th>
									<th data-field="name">姓名</th>
									<th data-field="genderText">性别</th>
									<th data-field="birthdate">生日</th>
									<th data-field="departmentText">部门</th>
									<th data-field="postText">职位</th>
									<th data-field="officeNumber">办公电话</th>
									<th data-field="mobileNumber">手机号</th>
									<th data-field="weixin">微信</th>
									<th data-field="email">email</th>
									<th data-field="qq">QQ</th>
									<th data-field="modifierAccount" data-formatter="accountFormatter">最后修改人</th>
									<th data-field="lastModifyTime">最后修改时间</th>
								</tr>
								</thead>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div  class="col-md-12">
				<div class="portlet light">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-list-alt"></i>
							<span class="caption-subject">信息化详情</span>
						</div>
						<div class="tools">
							<a class="collapse"></a>
							<a class="fullscreen"></a>
						</div>
					</div>
					<div class="portlet-body">
						<div class="table-container">
							<table id="viewInformationizationTable">
								<thead>
								<tr role="row" class="heading">
									<th data-field="checkbox" data-checkbox="true"></th>
									<th data-field="id" data-formatter="indexFormatter">序号</th>
									<th data-field="subjectText">系统科目</th>
									<th data-field="purchased" data-formatter="booleanFormatter">是否购买</th>
									<th data-field="brandText">品牌</th>
									<th data-field="productLineText">产品线</th>
									<th data-field="satisfactionText">满意度</th>
									<th data-field="stateText">运行状况</th>
									<th data-field="lastModifyTime">最后修改时间</th>
								</tr>
								</thead>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<yonyou:file-table id="viewCustomerDoc" businessKey="CUSTOMER" readonly="true" />
	</yonyou:modal>
	<script type="text/javascript" src="scripts/crm/admin/customer-view.js"></script>
</html>