<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
	<style>
		.fixed-table-toolbar  .bars {
			width: 100%;
		}

		#customerQueryToolbar {
			width: 100%;
		}

		#customerQueryToolbar .icheck-inline{
			margin-top: 0;
		}

		#customerQueryToolbar .control-label {
			padding-top: 0 !important;
		}
	</style>
	<yonyou:nav father="统一营销数据库" model="综合查询" />
	<div class="row">
		<div class="col-md-12">
			<div class="portlet box blue-dark">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-list"></i>客户列表
					</div>
					<div class="tools">
						<a class="collapse"></a>
						<a class="fullscreen"></a>
					</div>
					<div class="actions">
						<yonyou:btn-view id="viewCustomer" />
					</div>
				</div>
				<div class="portlet-body">
					<div id="customerQueryToolbar" class="pull-left">
						<form id="queryCustomerForm" class="form-horizontal">
							<div class="row">
								<div class="col-md-8">
									<div class="form-group">
										<label class="control-label col-md-2" for="query_name">模糊搜索</label>
										<div class="col-md-10">
											<input class="form-control" id="query_name" name="name" placeholder="检索名称/简称/统一信用代码" maxlength="30" />
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<button type="button" id="queryToggleBtn" class="btn btn-sm btn-primary" data-toggle="collapse" data-target="#queryPanel">
										<i id="queryToggleIcon" class="fa fa-toggle-down"></i>高级查询
									</button>
								</div>
							</div>
							<div id="queryPanel" class="col-md-12 collapse" style="padding-right:0;">
								<div class="row">
									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label col-md-4" for="query_origin">数据来源</label>
											<div class="col-md-8">
												<select class="form-control" id="query_origin" name="origin" style="width: 100%;"></select>
											</div>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label col-md-4" for="query_area">所属区域</label>
											<div class="col-md-8">
												<select class="form-control" id="query_area" name="area" style="width: 100%;"></select>
											</div>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label col-md-4" for="query_address">公司地址</label>
											<div class="col-md-8">
												<input class="form-control" id="query_address" name="address" placeholder="公司地址" maxlength="30" />
											</div>
										</div>
									</div>
									<div class="clearfix"></div>

									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label col-md-4" for="query_province">所在省</label>
											<div class="col-md-8">
												<select class="form-control" id="query_province" name="province" style="width: 100%;"></select>
											</div>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label col-md-4" for="query_city">所在市</label>
											<div class="col-md-8">
												<select class="form-control" id="query_city" name="city" style="width: 100%;"></select>
											</div>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label col-md-4" for="query_district">所在区</label>
											<div class="col-md-8">
												<select class="form-control" id="query_district" name="district" style="width: 100%;"></select>
											</div>
										</div>
									</div>
									<div class="clearfix"></div>

									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label col-md-4" for="query_tracker">线索跟踪</label>
											<div class="col-md-8">
												<input class="form-control" id="query_tracker" name="tracker" placeholder="线索跟踪人"  maxlength="30"/>
											</div>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label col-md-4" for="query_legalRepresentative">法定代表人</label>
											<div class="col-md-8">
												<input class="form-control" id="query_legalRepresentative" name="legalRepresentative" placeholder="法定代表人" maxlength="30" />
											</div>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label col-md-4" for="query_establishmentYear">成立年度</label>
											<div class="col-md-8">
												<select class="form-control" id="query_establishmentYear" name="establishmentDate" style="width: 100%;">
													<option value="" selected>全部</option>
												</select>
											</div>
										</div>
									</div>
									<div class="clearfix"></div>

									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label col-md-4" for="query_min_registeredCapital">注册资本(万元)</label>
											<div class="col-md-8">
												<input type="number" class="form-control col-md-5" id="query_min_registeredCapital" name="minRegisteredCapital" placeholder="最低资本" maxlength="10" />
												<span class="input-group-addon col-md-2" style="line-height:20px; padding-left: 5px; padding-right: 5px;">至</span>
												<input type="number" class="form-control col-md-5" id="query_max_registeredCapital" name="maxRegisteredCapital" placeholder="最高资本" maxlength="10" />
											</div>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label col-md-4" for="query_nature">企业性质</label>
											<div class="col-md-8">
												<select class="form-control" id="query_nature" name="nature" style="width: 100%;"></select>
											</div>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label col-md-4" for="query_industry">所属行业</label>
											<div class="col-md-8">
												<select class="form-control" id="query_industry" name="industry" style="width: 100%;"></select>
											</div>
										</div>
									</div>
									<div class="clearfix"></div>

									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label col-md-4" for="query_listingStatus">上市情况</label>
											<div class="col-md-8">
												<select class="form-control" id="query_listingStatus" name="listingStatus" style="width: 100%;"></select>
											</div>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label col-md-4">集团企业</label>
											<div class="col-md-8">
												<yonyou:radio name="isGroup" labels="是,否" values="T,F" />
											</div>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label col-md-4" for="query_size">公司规模</label>
											<div class="col-md-8">
												<select class="form-control" id="query_size" name="size" style="width: 100%;"></select>
											</div>
										</div>
									</div>
									<div class="clearfix"></div>

									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label col-md-4" for="query_min_totalAssets">总资产(万元)</label>
											<div class="col-md-8">
												<input type="number" class="form-control col-md-5" id="query_min_totalAssets" name="minTotalAssets" placeholder="最低资产" maxlength="10" />
												<span class="input-group-addon col-md-2" style="line-height:20px; padding-left: 5px; padding-right: 5px;">至</span>
												<input type="number" class="form-control col-md-5" id="query_max_totalAssets" name="maxTotalAssets" placeholder="最高资产" maxlength="10" />
											</div>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label col-md-4" for="query_employeeSize">用工规模</label>
											<div class="col-md-8">
												<select class="form-control" id="query_employeeSize" name="employeeSize" style="width: 100%;"></select>
											</div>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label col-md-4" for="query_annualOutputScale">年产值规模</label>
											<div class="col-md-8">
												<select class="form-control" id="query_annualOutputScale" name="annualOutputScale" style="width: 100%;"></select>
											</div>
										</div>
									</div>
									<div class="clearfix"></div>

									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label col-md-4" for="query_min_annualOutputValue">年产值(万元)</label>
											<div class="col-md-8">
												<input type="number" class="form-control col-md-5" id="query_min_annualOutputValue" name="minAnnualOutputValue" placeholder="最低产值" maxlength="10" />
												<span class="input-group-addon col-md-2" style="line-height:20px; padding-left: 5px; padding-right: 5px;">至</span>
												<input type="number" class="form-control col-md-5" id="query_max_annualOutputValue" name="maxAnnualOutputValue" placeholder="最高产值" maxlength="10" />
											</div>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label col-md-4" for="query_operateStatus">经营状况</label>
											<div class="col-md-8">
												<select class="form-control" id="query_operateStatus" name="operateStatus" style="width: 100%;"></select>
											</div>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label col-md-4" for="query_marketPosition">市场地位</label>
											<div class="col-md-8">
												<select class="form-control" id="query_marketPosition" name="marketPosition" style="width: 100%;"></select>
											</div>
										</div>
									</div>
									<div class="clearfix"></div>

									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label col-md-4" for="query_potential">发展潜力</label>
											<div class="col-md-8">
												<select class="form-control" id="query_potential" name="potential" style="width: 100%;"></select>
											</div>
										</div>
									</div>
									<div class="col-md-4">
										<button type="button" id="queryBtn" class="btn btn-sm btn-warning">
											<i class="fa fa-search"></i>查询
										</button>
									</div>
								</div>
							</div>
						</form>
					</div>
					<div class="table-container">
						<table id="customerTable" data-toolbar="#customerQueryToolbar"
							   data-search="false" data-show-columns="false"
							   data-show-refresh="false"  data-show-toggle="false">
							<thead>
								<tr role="row" class="heading">
									<th data-field="checkbox" data-checkbox="true"></th>
									<th data-field="id" data-formatter="indexFormatter">序号</th>
									<th data-field="name">客户名称</th>
									<th data-field="uscc">统一信用代码</th>
									<th data-field="legalRepresentative">法定代表人</th>
									<th data-field="establishmentDate">成立时间</th>
									<th data-field="industryText">行业</th>
									<th data-field="natureText">企业性质</th>
									<th data-field="sizeText">公司规模</th>
									<th data-field="originText">线索来源</th>
									<th data-field="originTracker">线索跟踪人</th>
									<th data-field="salesman">销售对应人</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/pages/crm/admin/customer-view.jsp" />
	<jsp:include page="/WEB-INF/pages/crm/admin/contact-view.jsp" />
	<script type="text/javascript" src="scripts/crm/admin/customer-query.js"></script>
</html>