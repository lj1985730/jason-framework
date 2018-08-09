<%--
  Created by IntelliJ IDEA.
  User: LiuJun
  Date: 2017/12/11
  Time: 18:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
    <%--<div class="page-bar" style="margin-bottom: 5px;">
        <div class="page-toolbar">
            <div class="chat-form" style="margin-top: 0; background-color: #FFFFFF;">
                <div class="input-cont" style="margin-right: 43px;">
                    <div class="input-group date-picker input-daterange" style="width: 100%">
                        <input class="form-control col-md-4" id="startMonth" style="min-width: 110px;" readonly />
                        <span class="input-group-addon">至</span>
                        <input class="form-control col-md-4" id="endMonth" style="min-width: 110px;" readonly />
                    </div>
                </div>
                <div class="btn-cont">
                    <span class="arrow"></span>
                    <a href="javascript:void(0);" class="btn blue icn-only" onclick="doSearch();">
                        <i class="fa fa-search icon-white"></i>
                    </a>
                </div>
            </div>
        </div>
    </div>--%>

    <div class="row">
        <div class="col-md-4">
            <jsp:include page="grossProfitRatio.jsp" />
        </div>
        <div class="col-md-4">
            <jsp:include page="netProfitRatio.jsp" />
        </div>
        <div class="col-md-4">
            <jsp:include page="optProfitRatio.jsp" />
        </div>
    </div>
    <div class="row" style="margin-bottom: 80px;">
        <div class="col-md-4">
            <jsp:include page="costProfitRatio.jsp" />
        </div>
        <div class="col-md-4">
            <jsp:include page="netAssetYield.jsp" />
        </div>
        <div class="col-md-4">
            <jsp:include page="totalAssetRewardRatio.jsp" />
        </div>
    </div>

    <script type="text/javascript">
//        $('#startMonth').datepicker({
//            rtl: Metronic.isRTL(),
//            language : 'zh-CN',
//            format : 'yyyy-mm',
//            startView : 1,
//            minViewMode : 1,
//            autoclose : true,
//            todayHighlight: true,
//            clearBtn : true
//        }).on('changeDate', function(selected) {	//设置时间约束-结束时间为最大时间
//            $('#endMonth').datepicker('setStartDate', new Date(selected.date.valueOf()));
//        }).on('clearDate', function() {	//清除时间时取消对结束时间的约束
//            $('#endMonth').datepicker('setStartDate', null);
//        });
//
//        $('#endMonth').datepicker({
//            rtl: Metronic.isRTL(),
//            language : 'zh-CN',
//            format : 'yyyy-mm',
//            startView : 1,
//            minViewMode : 1,
//            autoclose : true,
//            todayHighlight: true,
//            clearBtn : true
//        }).on('changeDate', function() {	//设置时间约束-起始时间为最小时间
//            $('#startMonth').datepicker('setEndDate', this.value);
//        }).on('clearDate', function () {	//清除时间时取消对开始时间的约束
//            $('#startMonth').datepicker('setEndDate', null);
//        });

//        var doSearch = function() {
//
//        }
    </script>
</html>
