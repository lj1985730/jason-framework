<!-- 自定义模态弹窗-->
<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 参数 -->
<%@ attribute name="id" required="true" rtexprvalue="true" type="java.lang.String" description="唯一标识" %>
<%@ attribute name="title" required="true" rtexprvalue="false" type="java.lang.String" description="窗口标题" %>
<%@ attribute name="editable" required="false" rtexprvalue="false" type="java.lang.Boolean" description="是否可保存" %>
<%@ attribute name="modalClass" required="false" rtexprvalue="false" type="java.lang.String" description="模态窗大小" %>
<%@ attribute name="confirmText" required="false" rtexprvalue="false" type="java.lang.String" description="确定按钮文本" %>
<!-- modal-lg(大) -->
<div id="${id}" class="modal fade" role="dialog" data-backdrop="static">
	<div class="${modalClass} modal-dialog">
		<div class="modal-content">
			<div class="modal-header" style="background-color: dimgray; border-top-left-radius: 5px; border-top-right-radius: 5px;">
				<h4 class="modal-title" style="color: white;">
					<i class="icon-pencil"></i>
					<span id="${id}Title" style="font-weight: bold;">${title}</span>
					<i class="fa fa-remove" style="float: right; padding-top: 4px; padding-right: 4px; color: #FFFFFF; transform: scale(1.5);"
					   onmouseover="this.style.cssText='float: right; padding-top: 3px; padding-right: 3px; color: #FFFFFF; transform: scale(2);'"
					   onmouseout="this.style.cssText='float: right; padding-top: 4px; padding-right: 4px; color: color: #FFFFFF; transform: scale(1.5);'" data-dismiss="modal" data-target="#${id}"></i>
				</h4>
			</div>
			<div class="modal-body">
				<!-- 标签体 -->
				<jsp:doBody />
			</div>

			<div class="modal-footer">
				<c:if test="${not empty editable && editable}">
					<c:if test="${not empty confirmText}">
						<yonyou:save id="${id}SubmitBtn" text="${confirmText}" />
					</c:if>
					<c:if test="${empty confirmText}">
						<yonyou:save id="${id}SubmitBtn" />
					</c:if>
				</c:if>
				<button type="button" class="btn default" id="${id}CancelBtn" data-dismiss="modal" data-target="#${id}"><i class="fa fa-remove"></i>&nbsp;关闭</button>
			</div>
        </div>
    </div>
</div>