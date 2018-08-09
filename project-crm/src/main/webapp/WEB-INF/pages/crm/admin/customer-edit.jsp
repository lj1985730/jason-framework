<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>

<html>
	<yonyou:modal id="editCustomerWin" modalClass="modal-lg modal-xl modal-xxl modal-xxxl modal-xxxxl" title="编辑客户信息" editable="true">
		<form id="editCustomerForm" class="form-horizontal form-bordered form-row-stripped" data-toggle="validator">
			<input type="hidden" id="id" name="id" />
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
										<label class="control-label col-md-1" style="width: 11.1%" for="edit_name">名称</label>
										<div class="col-md-11" style="width: 88.9%">
											<input class="form-control" id="edit_name" name="name" placeholder="名称" required maxlength="30" />
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_shortName">简称</label>
										<div class="col-md-8">
											<input class="form-control" id="edit_shortName" name="shortName" placeholder="简称" maxlength="30" />
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_uscc">统一信用代码</label>
										<div class="col-md-8">
											<input class="form-control" id="edit_uscc" name="uscc" placeholder="统一信用代码" maxlength="18" />
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_origin">数据来源</label>
										<div class="col-md-8">
											<select class="form-control" id="edit_origin" name="origin" style="width: 100%;"></select>
										</div>
									</div>
								</div>
								<div class="col-md-12">
									<div class="pull-right">
										<button type="button" class="btn btn-sm green" id="createNewCustomerBtn"><i class="fa fa-save"></i>&nbsp;保存</button>
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
										<label class="control-label col-md-4" for="edit_legalRepresentative">法定代表人</label>
										<div class="col-md-8">
											<input class="form-control" id="edit_legalRepresentative" name="legalRepresentative" placeholder="法定代表人" maxlength="30" />
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_lrNumber">法人电话</label>
										<div class="col-md-8">
											<input class="form-control" id="edit_lrNumber" name="lrNumber" placeholder="法人电话" maxlength="20" />
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_establishmentDate">成立时间</label>
										<div class="col-md-8">
											<yonyou:date id="edit_establishmentDate" name="establishmentDate" />
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_registeredCapital">注册资本(万元)</label>
										<div class="col-md-8">
											<input class="form-control" id="edit_registeredCapital" name="registeredCapital" placeholder="注册资本" maxlength="10" />
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_nature">企业性质</label>
										<div class="col-md-8">
											<select class="form-control" id="edit_nature" name="nature" style="width: 100%;"></select>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_industry">所属行业</label>
										<div class="col-md-8">
											<select class="form-control" id="edit_industry" name="industry" style="width: 100%;"></select>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">集团企业</label>
										<div class="col-md-8">
											<yonyou:radio name="isGroup" labels="是,否" values="true,false" />
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_listingStatus">上市情况</label>
										<div class="col-md-8">
											<select class="form-control" id="edit_listingStatus" name="listingStatus" style="width: 100%;"></select>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_superior">上级单位</label>
										<div class="col-md-8">
											<input class="form-control" id="edit_superior" name="superior" placeholder="上级单位" maxlength="30" />
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
										<label class="control-label col-md-1" style="width: 11.1%" for="edit_address">公司地址</label>
										<div class="col-md-11" style="width: 88.9%">
											<textarea class="form-control" rows="2" id="edit_address" name="address" placeholder="公司地址" maxlength="150"></textarea>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_tel">公司电话</label>
										<div class="col-md-8">
											<input class="form-control" id="edit_tel" name="tel" placeholder="公司电话" maxlength="20" />
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_fax">公司传真</label>
										<div class="col-md-8">
											<input class="form-control" id="edit_fax" name="fax" placeholder="公司传真" maxlength="20" />
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_website">公司网站</label>
										<div class="col-md-8">
											<input class="form-control" id="edit_website" name="website" placeholder="公司网站" maxlength="100" />
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_area">所属区域</label>
										<div class="col-md-8">
											<select class="form-control" id="edit_area" name="area" style="width: 100%;"></select>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_province">所在省</label>
										<div class="col-md-8">
											<select class="form-control" id="edit_province" name="province" style="width: 100%;"></select>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_city">所在市</label>
										<div class="col-md-8">
											<select class="form-control" id="edit_city" name="city" style="width: 100%;"></select>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_district">所在区</label>
										<div class="col-md-8">
											<select class="form-control" id="edit_district" name="district" style="width: 100%;"></select>
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
										<label class="control-label col-md-4" for="edit_size">公司规模</label>
										<div class="col-md-8">
											<select class="form-control" id="edit_size" name="size" style="width: 100%;"></select>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_totalAssets">总资产(万元)</label>
										<div class="col-md-8">
											<input type="number" class="form-control" id="edit_totalAssets" name="totalAssets" placeholder="总资产(万元)" maxlength="20" max="999999999" />
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_annualOutputValue">年产值(万元)</label>
										<div class="col-md-8">
											<input type="number" class="form-control" id="edit_annualOutputValue" name="annualOutputValue" placeholder="年产值(万元)" maxlength="20" max="99999999" />
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_annualOutputScale">年产值规模</label>
										<div class="col-md-8">
											<select class="form-control" id="edit_annualOutputScale" name="annualOutputScale" style="width: 100%;"></select>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_employeeSize">用工规模</label>
										<div class="col-md-8">
											<select class="form-control" id="edit_employeeSize" name="employeeSize" style="width: 100%;"></select>
										</div>
									</div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-1" style="width: 11.1%" for="edit_mainBusiness">主营业务</label>
										<div class="col-md-11" style="width: 88.9%">
											<textarea class="form-control" rows="2" id="edit_mainBusiness" name="mainBusiness" placeholder="主营业务" maxlength="200"></textarea>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_operateStatus">经营状况</label>
										<div class="col-md-8">
											<select class="form-control" id="edit_operateStatus" name="operateStatus" style="width: 100%;"></select>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_marketPosition">市场地位</label>
										<div class="col-md-8">
											<select class="form-control" id="edit_marketPosition" name="marketPosition" style="width: 100%;"></select>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_potential">发展潜力</label>
										<div class="col-md-8">
											<select class="form-control" id="edit_potential" name="potential" style="width: 100%;"></select>
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
											<yonyou:radio name="hasItDepartment" labels="是,否" values="true,false" />
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_itManager">信息部主管</label>
										<div class="col-md-8">
											<input class="form-control" id="edit_itManager" name="itManager" placeholder="信息部主管" maxlength="20" />
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_itManagerNumber">主管电话</label>
										<div class="col-md-8">
											<input type="number" class="form-control" id="edit_itManagerNumber" name="itManagerNumber" placeholder="信息部主管电话" maxlength="20" />
										</div>
									</div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-2" style="width: 11.1%" for="edit_itSituation">信息化状况</label>
										<div class="col-md-10" style="width: 88.9%">
											<textarea class="form-control" rows="2" id="edit_itSituation" name="itSituation" placeholder="信息化状况" maxlength="200"></textarea>
										</div>
									</div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-1" style="width: 11.1%" for="edit_mainSoftwareBrand">主要软件品牌</label>
										<div class="col-md-10" style="width: 88.9%">
											<select class="form-control" id="edit_mainSoftwareBrand" name="mainSoftwareBrand" multiple style="width: 100%;"></select>
										</div>
									</div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-1" style="width: 11.1%" for="edit_mainSoftwareLine">主要产品线</label>
										<div class="col-md-10" style="width: 88.9%">
											<select class="form-control" id="edit_mainSoftwareLine" name="mainSoftwareLine" multiple style="width: 100%;"></select>
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
							<button type="button" class="btn green" id="viewContact"><i class="fa fa-search"></i>&nbsp;查看</button>
							<button type="button" class="btn green" id="createContact"><i class="fa fa-plus"></i>&nbsp;增加</button>
							<button type="button" class="btn green" id="modifyContact"><i class="fa fa-edit"></i>&nbsp;修改</button>
							<button type="button" class="btn red" id="deleteContact"><i class="fa fa-trash-o"></i>&nbsp;删除</button>
						</div>
					</div>
					<div class="portlet-body">
						<div class="table-container">
							<table id="contactTable">
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
						<div class="actions">
							<button type="button" class="btn green" id="modifyInfoBtn"><i class="fa fa-edit"></i>&nbsp;修改</button>
						</div>
					</div>
					<div class="portlet-body">
						<div class="table-container">
							<table id="informationizationTable">
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
		<yonyou:file-table id="customerDoc" businessKey="CUSTOMER" readonly="false" />
	</yonyou:modal>
	<%--<jsp:include page="/WEB-INF/pages/auth/person-select-modal-new.jsp" />--%>
	<script type="text/javascript" src="scripts/crm/admin/customer-edit.js"></script>
	<jsp:include page="/WEB-INF/pages/crm/admin/contact-edit.jsp" />
	<jsp:include page="/WEB-INF/pages/crm/admin/informationization-edit.jsp" />
</html>