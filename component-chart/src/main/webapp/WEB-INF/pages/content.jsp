<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%--<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>--%>
<!DOCTYPE html>
<html>
	<head>
		<title>统计分析系统</title>
		<%@ include file="/WEB-INF/includes/common-chart.inc" %>
		<%--<link type="text/css" rel="stylesheet" href="css/themes/darkblue.css" id="style_color" />--%>
		<!-- 装饰头文件 -->
		<sitemesh:write property="head" />
	</head>
	<body class="page-header-fixed page-footer-fixed page-sidebar-closed-hide-logo">
		<!-- 页头 -->
		<%--<jsp:include page="top.jsp" />--%>
		<div class="clearfix"></div>
		<div class="page-container">
			<!-- 左侧菜单 -->
    		<%--<jsp:include page="left.jsp" />--%>
    		<!-- 主显示内容 -->
			<div class="page-content-wrapper">
				<div class="page-content">
					<sitemesh:write property="body" />
				</div>
			</div>
		</div>
		<!-- 页脚 -->
		<%--<jsp:include page="bottom.jsp" />--%>
	</body>
</html>