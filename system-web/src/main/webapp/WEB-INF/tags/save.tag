<!-- 自定义保存按钮-->
<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="id" required="false" rtexprvalue="true" type="java.lang.String" description="ID" %>
<%@ attribute name="permission" required="false" rtexprvalue="true" type="java.lang.String" description="权限" %>
<%@ attribute name="text" required="false" rtexprvalue="true" type="java.lang.String" description="按钮文本" %>

<c:if test="${not empty permission}">
<shiro:hasPermission name="${permission}">
    <button type="button" class="btn green" id="${id}">
        <i class="fa fa-check"></i>&nbsp;${empty text ? '保存' : text}
    </button>
</shiro:hasPermission>
</c:if>

<c:if test="${empty permission}">
    <button type="button" class="btn green" id="${id}">
        <i class="fa fa-check"></i>&nbsp;${empty text ? '保存' : text}
    </button>
</c:if>
