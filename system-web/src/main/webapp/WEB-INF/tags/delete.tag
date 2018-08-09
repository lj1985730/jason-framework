<!-- 自定义删除按钮-->
<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>

<!-- 参数 -->
<%@ attribute name="id" required="true" rtexprvalue="true" type="java.lang.String" description="绑定id属性" %>
<%@ attribute name="label" required="false" rtexprvalue="true" type="java.lang.String" description="按钮文本" %>
<%@ attribute name="permission" required="false" rtexprvalue="true" type="java.lang.String" description="权限" %>

<yonyou:btn-default id="${id}" label="${empty label ? '删除' : label}" icon="fa fa-trash-o" permission="${permission}" />