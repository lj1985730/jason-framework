<!-- 封装通用按钮，可增加权限控制 -->
<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 参数 -->
<%@ attribute name="id" required="true" rtexprvalue="true" type="java.lang.String" description="绑定id属性" %>
<%@ attribute name="label" required="false" rtexprvalue="true" type="java.lang.String" description="按钮文本" %>
<%@ attribute name="permission" required="false" rtexprvalue="true" type="java.lang.String" description="权限" %>
<%@ attribute name="icon" required="false" rtexprvalue="true" type="java.lang.String" description="按钮图标" %>

<c:if test="${not empty permission}">
<shiro:hasPermission name="${permission}">
	<button type="button" class="btn btn-default" id="${id}">
		<i class="${icon}"></i>&nbsp;${empty label ? '' : label}
	</button>
</shiro:hasPermission>
</c:if>

<c:if test="${empty permission}">
	<button type="button" class="btn btn-default" id="${id}">
		<i class="${icon}"></i>&nbsp;${empty label ? '' : label}
	</button>
</c:if>