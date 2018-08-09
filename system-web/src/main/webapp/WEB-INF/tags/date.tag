<!-- 自定义时间选择控件-->
<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="id" required="true" rtexprvalue="true" type="java.lang.String" description="组件id" %>
<%@ attribute name="name" required="true" rtexprvalue="true" type="java.lang.String" description="表单name" %>
<%@ attribute name="placeholder" required="false" rtexprvalue="true" type="java.lang.String" description="占位内容" %>
<%@ attribute name="tagClass" required="false" rtexprvalue="false" type="java.lang.String" description="样式" %>
<%@ attribute name="format" required="false" rtexprvalue="false" type="java.lang.String" description="格式" %>
<%@ attribute name="view" required="false" rtexprvalue="false" type="java.lang.String" description="初始窗口:2day 3month" %>
<%@ attribute name="value" required="false" rtexprvalue="true" type="java.lang.String" description="值" %>
<%@ attribute name="start" required="false" rtexprvalue="false" type="java.lang.String" description="最小日期" %>
<%@ attribute name="end" required="false" rtexprvalue="false" type="java.lang.String" description="最大日期" %>
<%@ attribute name="orientation" required="false" rtexprvalue="false" type="java.lang.String" description="弹出方向" %>
<%@ attribute name="required" required="false" rtexprvalue="false" type="java.lang.String" description="是否必填" %>

<%--<div class="input-group date date-picker">
	<input class="form-control ${tagClass}" id="${id}" name="${name}" placeholder="${placeholder}" value="${value}" readonly <c:if test="${not empty required}">required</c:if> style="border-top-left-radius: 4px; border-bottom-left-radius: 4px;" />
	<span class="input-group-addon" id="datepicker-icon-${id}">
		<i class="fa fa-calendar"></i>
	</span>
</div>--%>
<div class="input-icon date date-picker">
	<i class="fa fa-calendar" id="datepicker-icon-${id}"></i>
	<input class="form-control ${tagClass}" id="${id}" name="${name}" placeholder="${placeholder}" value="${value}" readonly <c:if test="${not empty required}">required</c:if> style="border-top-left-radius: 4px; border-bottom-left-radius: 4px;" />
</div>

<script type="text/javascript">
	if(!datepicker_option) {
		var datepicker_option = [];
	}

    datepicker_option_${id} = {
        rtl: Metronic.isRTL(),
		language : 'zh-CN',
		format : 'yyyy-mm-dd',
		startView : 0,
        minViewMode : 0,
	    autoclose : true,
        todayBtn : true,
        todayHighlight: true,
        clearBtn : true
	};
	
	if('${format}') {
		$.extend(datepicker_option_${id}, {format : '${format}'});
        if('${format}' === 'yyyy-mm-dd hh:mm') {
            $.extend(datepicker_option_${id}, {startView : 1, minViewMode : 1});
		} else if('${format}' === 'yyyy-mm-dd') {
			$.extend(datepicker_option_${id}, {startView : 2, minViewMode : 2});
		} else if('${format}' === 'yyyy-mm') {
			$.extend(datepicker_option_${id}, {startView : 3, minViewMode : 3});
		} else if('${format}' === 'yyyy') {
			$.extend(datepicker_option_${id}, {startView : 4, minViewMode : 4});
		}
	} else if('${view}') {
		$.extend(datepicker_option_${id}, {startView : '${view}', minViewMode : '${view}'});
	}
	if('${start}') {
		if('${start}' === 'current') {
			$.extend(datepicker_option_${id}, {startDate : new Date()});
		} else {
			$.extend(datepicker_option_${id}, {startDate : '${start}'});
		}
	}
	if('${end}') {
		if('${end}' === 'current') {
			$.extend(datepicker_option_${id}, {endDate : new Date()});
		} else {
			$.extend(datepicker_option_${id}, {endDate : '${end}'});
		}
	}
    if('${orientation}') {
        $.extend(datepicker_option_${id}, {orientation : '${orientation}'});
    }

	$('#${id}').datepicker(datepicker_option_${id});

	$('#datepicker-icon-${id}').on('click', function() {
        $('#${id}').datepicker('show');
	});
</script>