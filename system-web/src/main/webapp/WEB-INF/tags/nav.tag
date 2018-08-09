<!-- 自定义页头导航条 -->
<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 参数 -->
<%@ attribute name="father" required="false" rtexprvalue="true" type="java.lang.String" description="父级菜单" %>
<%@ attribute name="model" required="true" rtexprvalue="true" type="java.lang.String" description="模块名称" %>

<%--<div class="page-head">
	<div class="page-title">
		<h1>${model} <small>${model}</small></h1>
	</div>
</div>--%>

<%-- 面包屑 --%>
<ul class="page-breadcrumb breadcrumb">
	<li>
		<a href="javascript:openMenuUrl('/home');" style="color: #5b9bd1; font-weight: bold;">首页</a>
	</li>

	<c:if test="${not empty father}">
	<li>${father}</li>
	</c:if>

	<li>${model}</li>
</ul>