/***********************************
 * CRM-客户管理-主页-脚本
 * @modify by Liu Jun at 2018年7月12日
 * @since v1.0.0
 * *********************************/
!function ($) {

    'use strict';

    var baseUrl = appPath + '/crm/customer';
    var $table;
    var timeoutId;  //延迟计时器

    /**
     * 初始化方法
     */
    $(function() {

        /* 初始化主table */
        $table = $('#customerTable');
        $table.bootstrapTable(
            $.extend({ url : baseUrl }, $.extend({}, generalTableOption, { queryParams : customerParams }))
        );

        /**
         * 按钮初始化
         */
        $('#viewCustomer').off('click').on('click', function() { $.fn.showViewCustomerWin($table); });

        $('#queryToggleBtn').off('click').on('click', function() {   //展开、折叠高级搜索
            if($('#queryPanel').is(':hidden')) {
                $('#queryToggleIcon').removeClass('fa-toggle-down').addClass('fa-toggle-up');
            } else {
                $('#queryToggleIcon').removeClass('fa-toggle-up').addClass('fa-toggle-down');
            }
        });

        $('#queryBtn').off('click').on('click', function() { doQuery(); });
        $('#query_name').on('input', function() {
            clearTimeout(timeoutId);
            timeoutId = setTimeout(function () { doQuery(); }, 500);
        });

        /**
         * 下拉框初始化
         */
        $('#query_origin').comboData('CRM_CSR_DATA_ORIGIN', true, null, true);  //数据来源
        $('#query_nature').comboData('COM_ENT_NATURE', true, null, true);  //企业性质
        $('#query_industry').comboData('COM_ENT_INDUSTRY', true, null, true);  //所属行业
        $('#query_listingStatus').comboData('COM_ENT_LISTING_STATUS', true, null, true);  //上市情况
        $('#query_area').comboData('COM_GEO_AREA', true, null, true);  //所属区域
        $('#query_province').comboData('COM_GEO_PROVINCE', true, null, true);  //所属省
        $('#query_city').comboData('COM_GEO_CITY', true, null, true);  //所属市
        $('#query_district').comboData('COM_GEO_DISTRICT', true, null, true);  //所属区县
        $('#query_size').comboData('COM_ENT_SIZE', true, null, true);  //公司规模
        $('#query_annualOutputScale').comboData('COM_ENT_ANNUAL_OUTPUT_SCALE', true, null, true);  //年产值规模
        $('#query_employeeSize').comboData('COM_ENT_EMPLOYEE_SIZE', true, null, true);  //用工规模
        $('#query_operateStatus').comboData('COM_ENT_OPERATE_STATUS', true, null, true);  //经营状况
        $('#query_marketPosition').comboData('COM_ENT_MARKET_POSITION', true, null, true);  //市场地位
        $('#query_potential').comboData('COM_ENT_POTENTIAL', true, null, true);  //发展潜力

        for(var y = 1945; y <= new Date().getFullYear(); y ++) {
            $('#query_establishmentYear').append(
                '<option>' + y + '</option>'
            );
        }
    });

    /**
     * 通用查询条件，用于多数无条件的bootstrap-table引用
     * @param params 基本查询条件，包含search、sort、order、limit、offset
     */
    var customerParams = function(params) {
        return $.extend($('#queryCustomerForm').serializeJson(), params);
    };

    /**
     * 执行搜索
     */
    var doQuery = function() {
        $table.bootstrapTable('refresh');
    };

}(window.jQuery);
