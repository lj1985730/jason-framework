<!-- 自定义时间选择控件-->
<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>

<%@ attribute name="id" required="true" rtexprvalue="false" type="java.lang.String" description="ID" %>
<%@ attribute name="startName" required="true" rtexprvalue="true" type="java.lang.String" description="表单开始name" %>
<%@ attribute name="endName" required="true" rtexprvalue="true" type="java.lang.String" description="表单结束name" %>
<%@ attribute name="placeholder" required="false" rtexprvalue="true" type="java.lang.String" description="占位内容" %>
<%@ attribute name="tagClass" required="false" rtexprvalue="false" type="java.lang.String" description="样式" %>
<%@ attribute name="format" required="false" rtexprvalue="false" type="java.lang.String" description="格式" %>
<%@ attribute name="view" required="false" rtexprvalue="false" type="java.lang.String" description="初始窗口:2day 3month" %>
<%@ attribute name="startValue" required="false" rtexprvalue="true" type="java.lang.String" description="开始值" %>
<%@ attribute name="endValue" required="false" rtexprvalue="true" type="java.lang.String" description="结束值" %>
<%@ attribute name="start" required="false" rtexprvalue="false" type="java.lang.String" description="最小日期" %>
<%@ attribute name="end" required="false" rtexprvalue="false" type="java.lang.String" description="最大日期" %>
<%@ attribute name="onchange" required="false" rtexprvalue="false" type="java.lang.String" description="修改触发" %>

<div class="input-group date-picker input-daterange" style="width: 100%">
	<input class="form-control col-md-4 ${tagClass}" id="datepicker_range_start_${id}" name="${startName}" placeholder="${placeholder}" value="${startValue}" style="min-width: 110px;" readonly />
	<span class="input-group-addon">至</span>
	<input class="form-control col-md-4 ${tagClass}" id="datepicker_range_end_${id}" name="${endName}" placeholder="${placeholder}" value="${endValue}" style="min-width: 110px;" readonly />
</div>

<script type="text/javascript">
	if(!datepicker_option_range) {
		var datepicker_option_range = [];
	}

    datepicker_option_range['${id}'] = {
        rtl: Metronic.isRTL(),
        language : 'zh-CN',
        format : 'yyyy-mm-dd',
        startView : 0,
        minViewMode : 0,
//        orientation: "bottom",
        autoclose : true,
        todayBtn : true,
        todayHighlight: true,
        clearBtn : true
	};
	
	if('${format}') {
		$.extend(datepicker_option_range['${id}'], {format : '${format}'});
	}
	if('${view}') {
		$.extend(datepicker_option_range['${id}'], {startView : '${view}', minViewMode : '${view}'});
	}
	if('${start}') {
		if('${start}' === 'current') {
			$.extend(datepicker_option_range['${id}'], {startDate : new Date()});
		} else {
			$.extend(datepicker_option_range['${id}'], {startDate : '${start}'});
		}
	}
	if('${end}') {
		if('${end}' === 'current') {
			$.extend(datepicker_option_range['${id}'], {endDate : new Date()});
		} else {
			$.extend(datepicker_option_range['${id}'], {endDate : '${end}'});
		}
	}
	
	$('#datepicker_range_start_${id}').datepicker(datepicker_option_range['${id}']
	).on('changeDate', function(selected) {	//设置时间约束-结束时间为最大时间
		$('#datepicker_range_end_${id}').datepicker('setStartDate', new Date(selected.date.valueOf()));
		/**
		 * 增加onchange事件
		 */
		if('${onchange}') {
			eval('${onchange}');
		}
	}).on('clearDate', function() {	//清除时间时取消对结束时间的约束
        $('#datepicker_range_end_${id}').datepicker('setStartDate', null);
	});
	$('#datepicker_range_end_${id}').datepicker(datepicker_option_range['${id}']
    ).on('changeDate', function() {	//设置时间约束-起始时间为最小时间

		$('#datepicker_range_start_${id}').datepicker('setEndDate', this.value);
		/**
		 * 增加onchange事件
		 */
		if('${onchange}') {
			eval('${onchange}');
		}
    }).on('clearDate', function () {	//清除时间时取消对开始时间的约束
        $('#datepicker_range_start_${id}').datepicker('setEndDate', null);
    });
</script>