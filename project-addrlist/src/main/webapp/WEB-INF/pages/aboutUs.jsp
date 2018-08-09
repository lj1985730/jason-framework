<%-- 时        间：2016-11-15 11:52:55 --%>
<%-- 作        者：yangyadong --%>
<%-- 本页说明：--%>
<%--通过LsGenerator生成，Designed By sw--%>
<%--本页地址:system/manage/mailConfigController/index--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>

<script type="text/javascript">
	function showAboutUs() {
		$("#rightDiv").height($("#leftDiv").height());
		$("#companyMapIFrame").attr("src", "companyMap?uTime=" + new Date());
		$('#aboutUsWin').modal('show');
	}
	
	function resetDivHeight() {
		$("#rightDiv").height($("#leftDiv").height());
		try{
			//$(window.frames["companyMapIFrame"].document).find("container").css("height", $("#rightDiv").height());
			//$(window.frames["companyMapIFrmae"].document).find("container").height($("#rightDiv").height());
			//$("#companyMapIFrmae")[0].contentWindow.resetContainerDiv($("#rightDiv").width(), $("#rightDiv").height());
		}
		catch(e) { 
			alert(e);
		}
	}
	

</script>

<html>
	<head>
	</head>
	<body>
		<yonyou:modal id="aboutUsWin" title="技术支持" editable="false" modalClass="modal-dialog modal-lg modal-xl modal-xxl modal-xxxl">
			<form id="aboutUsForm" class="form-horizontal form-bordered form-row-stripped" data-toggle="validator">
				<input type="hidden" id="mailConfigId" name="mailConfigId" />
				<div class="row">
					<div class="col-md-12">
						<div class="form-group">
							<img src="images/aboutUs/service24h.jpg" alt="" class="img-responsive">
						</div>
					</div>
					<div class="col-md-6" id="leftDiv">
						<div class="col-md-12">
							<div class="col-md-3">
								<div class="form-group">
									<label class="col-md-12">企&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业：</label>
								</div>
							</div>
							<div class="col-md-9">
								<div class="form-group">
									<label class="col-md-12">大连陆海科技股份有限公司</label>
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="col-md-3">
								<div class="form-group">
									<label class="col-md-12">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：</label>
								</div>
							</div>
							<div class="col-md-9">
								<div class="form-group">
									<label class="col-md-12">大连市高新区敬贤街26号腾讯大厦19楼</label>
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="col-md-3">
								<div class="form-group">
									<label class="col-md-12">总机电话：</label>
								</div>
							</div>
							<div class="col-md-9">
								<div class="form-group">
									<label class="col-md-12">0411-84754811</label>
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="col-md-3">
								<div class="form-group">
									<label class="col-md-12">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱：</label>
								</div>
							</div>
							<div class="col-md-9">
								<div class="form-group">
									<label class="col-md-12">jishuyibu@maritech.cn</label>
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="col-md-3">
								<div class="form-group">
									<label class="col-md-12">产品服务：</label>
								</div>
							</div>
							<div class="col-md-9">
								<div class="form-group">
									<label class="col-md-12">技术一部   王忠辉 （分机号：8827）</label>
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="col-md-3">
								<div class="form-group">
									<label class="col-md-12"></label>
								</div>
							</div>
							<div class="col-md-9">
								<div class="form-group">
									<label class="col-md-12">手机 ：18640896190 </label>
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="col-md-3">
								<div class="form-group">
									<label class="col-md-12">技术服务：</label>
								</div>
							</div>
							<div class="col-md-9">
								<div class="form-group">
									<label class="col-md-12">技术一部   张振东 （分机号：8848）</label>
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="col-md-3">
								<div class="form-group">
									<label class="col-md-12"></label>
								</div>
							</div>
							<div class="col-md-9">
								<div class="form-group">
									<label class="col-md-12">QQ：714228277 </label>
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="col-md-3">
								<div class="form-group">
									<label class="col-md-12">服务微信号：</label>
								</div>
							</div>
							<div class="col-md-9">
								<div class="form-group">
									<label class="col-md-12">此处添加二维码</label>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-6" id="rightDiv">
						<iframe id="companyMapIFrame" onload="resetDivHeight();" src="" style="width:100%; height:100%;"></iframe>
					</div>
				</div>
			</form>
		</yonyou:modal>
	</body>
</html>