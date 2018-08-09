<!-- 自定义切换组件，封装bootstrap-switch-->
<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="name" required="true" rtexprvalue="true" type="java.lang.String" description="表单name" %>
<%@ attribute name="checked" required="false" rtexprvalue="true" type="java.lang.Boolean" description="是否选中" %>
<%@ attribute name="ontext" required="false" rtexprvalue="true" type="java.lang.String" description="显示值-开" %>
<%@ attribute name="offtext" required="false" rtexprvalue="true" type="java.lang.String" description="显示值-关" %>

<input type="checkbox" class="make-switch" name="${name}" data-handle-width="100"
       data-on-text="${empty ontext ? '是' : ontext}" data-off-text="${empty offtext ? '否' : offtext}"
       data-on-color="success" data-off-color="default" title="单击切换" />