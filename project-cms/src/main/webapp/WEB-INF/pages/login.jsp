<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!--[if lt IE 8]>

<!--<![endif]-->
<!--[if IE 8]> <html lang="zh-CN" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="zh-CN" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="zh-CN">
<!--<![endif]-->
	<head>
		<base href="<%=basePath%>" />
		<title>大连用友CRM</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<%--<meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests">--%>
		<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
		<meta content="yoogun" name="author"/>
		<link rel="shortcut icon" href="imgs/global/favicon.ico" />
		<link type="text/css" rel="stylesheet" href="plugins/font-awesome/css/font-awesome.min.css" />
		<link type="text/css" rel="stylesheet" href="plugins/simple-line-icons/simple-line-icons.min.css" />
		<link type="text/css" rel="stylesheet" href="plugins/bootstrap/css/bootstrap.min.css" />
		<link type="text/css" rel="stylesheet" href="plugins/uniform/css/uniform.default.css" />
		<link type="text/css" rel="stylesheet" href="plugins/icheck/skins/all.css" />
		<link type="text/css" rel="stylesheet" href="css/login/login-soft.css" />
		<link type="text/css" rel="stylesheet" href="css/global/components.css" id="style_components" />
		<link type="text/css" rel="stylesheet" href="css/layout.css" />
	</head>
	<body class="login" onkeydown="enterDown(event)">
		<div class="logo">
			<div class="form-title">
				<span style="color: #FFFFFF; font-size: 70px;">大连用友CRM</span>
			</div>
		</div>
		<div class="menu-toggler sidebar-toggler"></div>
		<!-- BEGIN LOGIN -->
		<div class="content">
		<!-- BEGIN LOGIN FORM -->
			<h3 class="form-title" style="color: #FFFFFF; font-weight: 400;">账户登录</h3>
			<div class="alert alert-danger display-hide">
				<button class="close" data-close="alert"></button>
				<span>
				键入用户名和密码。 </span>
			</div>
			<label id="msg" class="msg"></label>
			<div class="form-group">
				<div class="input-icon">
					<i class="fa fa-user"></i>
					<input type="text" id="name" name="name" value="${user.loginName}" class="form-control placeholder-no-fix" autocomplete="off" placeholder="用户名" /><!-- ${user.loginName} -->
				</div>
			</div>
			<div class="form-group">
				<div class="input-icon">
					<i class="fa fa-lock"></i>
					<input type="password" id="pass" name="pass" value="" class="form-control placeholder-no-fix" autocomplete="off" placeholder="密码" /><!-- ${user.loginPassword} -->
				</div>
			</div>
			<div class="form-actions">
				<label class="checkbox" title="保存时限7天，公共网络慎用" style="color: #FFFFFF;">
					<input type="checkbox" class="icheck" data-checkbox="icheckbox_square-grey" id="rememberMe"  name="rememberMe" /> 记住账号
				</label>
				<button class="btn green pull-right" onclick="doLogin();" style="width: 100px;">
				登录 <i class="m-icon-swapright m-icon-white"></i>
				</button>
			</div>
		</div>
		<!-- BEGIN COPYRIGHT -->
		<div class="copyright" style="color: #FFFFFF; font-size: 15px; font-weight: 400;">
			 2018 &copy; dlyonyou
		</div>
		<script type="text/javascript" src="plugins/jquery.min.js"></script>
		<script type="text/javascript" src="plugins/jquery-migrate.min.js"></script>
		<script type="text/javascript" src="plugins/uniform/jquery.uniform.min.js"></script>
		<script type="text/javascript" src="plugins/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="plugins/icheck/icheck.min.js"></script>
		<script type="text/javascript" src="plugins/jquery.blockui.min.js"></script>
		<script type="text/javascript" src="scripts/login/jquery.cokie.min.js"></script>
		<script type="text/javascript" src="scripts/login/jquery.backstretch.min.js"></script>
		<script type="text/javascript" src="scripts/login/login-soft.js"></script>
		<script type="text/javascript" src="scripts/global/metronic.js"></script>
		<script type="text/javascript" src="scripts/global/sys-message.js"></script>
		<script type="text/javascript">
            var pathArray = top.window.location.href.split('/');
            var protocol = pathArray[0];
            var host = pathArray[2];
            var context = pathArray[3];
            /**
             * 登录
             */
            function doLogin() {
                var msg = $("#msg");
                var name = $("#name").val();
                var pass = $("#pass").val();
                if(typeof(pass) === "undefined" || $.trim(pass) === "") {
                    msg.html('<span style="color: red;">密码不能为空！</span>');
                    return false;
                }
                var rememberMe = document.getElementById("rememberMe").checked;
                msg.html('登录中...');
                $.ajax({
                    type : "POST",
                    timeout : 20000,
                    url : "login",
                    dataType : "json",
                    contentType : "application/json",
                    data : JSON.stringify({
                        name : name,
                        pass : pass,
                        rememberMe : rememberMe
                    }),
                    beforeSend : function() {
                        SysBlock.block(); //loading效果
                    },
                    success : function(data) {
                        if(!data) {
                            msg.html('<span style="color: red;">系统没有响应，请联系管理员。</span>');
                            SysBlock.unblock();
                            return false;
                        }
                        try {
                            if(data.message && data.message.length > 20) {
                                msg.html('<span style="color: red;">' + data.message + '</span>');
                            } else {
                                msg.html('<span style="color: red;">' + data.message + '</span>');
                                msg.fadeIn("normal");
                            }
                            if(data.success) {
                                if(top === window) {
                                    top.location.href = protocol + '//' + host + "/" + context + "/home";
                                }
                            } else {
                                SysBlock.unblock();
							}
                        } catch(e) {
                            SysBlock.unblock();
                        }
                    },
                    error: function(jqXHR, status, errorThrown) {
                        msg.html('<span style="color: red;">出错了！异常：' + status + ' ' + errorThrown + '</span>');
                        SysBlock.unblock();
                    }
                });
            }

            /**
             * 回车登录
             */
            function enterDown(e) {
                var _key;
                if(window.event) { //兼容IE浏览器
                    _key = e.keyCode;
                } else if(e.which) {	//兼容非IE浏览器
                    _key = e.which;
                }
                if (_key === 0xD) {	//按下回车键时触发
                    doLogin();
                }
            }
		</script>
		<script>
			$(function() {
				Metronic.init();
                Metronic.setAssetsPath('');
                Metronic.setGlobalImgPath('imgs/global/');
			});
		</script>
	</body>
</html>