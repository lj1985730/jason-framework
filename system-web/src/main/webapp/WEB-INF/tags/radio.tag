<!-- 自定义全屏按钮-->
<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ attribute name="name" required="true" rtexprvalue="true" type="java.lang.String" description="表单name" %>
<%@ attribute name="labels" required="false" rtexprvalue="true" type="java.lang.String" description="显示值" %>
<%@ attribute name="values" required="false" rtexprvalue="true" type="java.lang.Object" description="实际值" %>
<%@ attribute name="required" required="false" rtexprvalue="true" type="java.lang.Boolean" description="是否必须" %>

<div class="input-group">
    <div class="icheck-inline" data-error-container="#${name}_error">
        <c:if test="${empty values}">
            <label>
                <input type="radio" class="icheck" data-radio="iradio_square-grey" name="${name}" value="true" <c:if test="${not empty required}">required</c:if> /> 是
            </label>
            <label>
                <input type="radio" class="icheck" data-radio="iradio_square-grey" name="${name}" value="false" <c:if test="${not empty required}">required</c:if> /> 否
            </label>
        </c:if>
        <c:if test="${not empty values}">
            <c:forEach var="item" items="${values}" varStatus="status">
                <label>
                    <input type="radio" class="icheck" data-radio="iradio_square-grey" name="${name}" value="${item}" <c:if test="${not empty required}">required</c:if> />&nbsp;${fn:split(labels, ',')[status.index]}
                </label>
            </c:forEach>
        </c:if>
    </div>
</div>
<div id="${name}_error"></div>
