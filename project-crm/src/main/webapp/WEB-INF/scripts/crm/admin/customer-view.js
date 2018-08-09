/***********************************
 * CRM-客户管理-编辑页-脚本
 * @author Liu Jun at 2018-7-12 09:07:10
 * @since v1.0.0
 * *********************************/
!function ($) {

    'use strict';

    var baseUrl = appPath + '/crm/customer';
    var $table, $win, $form;
    var $viewContactBtn, $contactTable, $infoTable;

    var $id;    //主键标签，用于判断主体数据是否存在，在新增联系人和附件前，需要保存主体数据

    var $fileTable; //附件表格组件

    /**
     * 初始化方法
     */
    $(function() {

        /**
         * 编辑页组件初始化
         */
        $win = $('#viewCustomerWin');
        $win.on('hide.bs.modal', function() {
            $table.bootstrapTable('refresh');
        });
        $form = $('#viewCustomerForm');
        $id = $('#view_id');

        initContactPart();  //初始化联系人

        initInformationizationPart();   //初始化信息化详情

        //下拉框初始化
        $('#view_mainSoftwareBrand').comboData('CRM_SOFTWARE_BRAND', false, null, true);  //主要软件品牌
        $('#view_mainSoftwareLine').comboData('CRM_PROD_LINE', false, null, true);  //主要软件产品线

        $fileTable = $('#viewCustomerDoc'); //初始化附件部分

    });

    /**
     * 初始化联系人部分
     */
    var initContactPart = function() {

        $viewContactBtn = $('#viewViewContact');

        $viewContactBtn.off('click').on('click', function() {
            $.fn.showViewContactWin($contactTable);
        });

        // 初始化联系人table
        $contactTable = $('#viewContactTable');
        $contactTable.bootstrapTable(
            $.extend( { url : '' }, $.extend({}, generalTableOption, { queryParams: commonParams }))
        );
    };

    /**
     * 初始化信息化详情部分
     */
    var initInformationizationPart = function() {

        // 初始化联系人table
        $infoTable = $('#viewInformationizationTable');
        $infoTable.bootstrapTable(
            $.extend( { url : '' }, $.extend({}, generalTableOption, { queryParams: commonParams }))
        );
    };

    /**
     * 显示编辑窗口
     * @param $outTable	数据源table
     */
    $.fn.showViewCustomerWin = function($outTable) {
        $table = $outTable;
        var row = $outTable.bootstrapTable('getSelections')[0];
        if (!row) {
            SysMessage.alertNoSelection();
            return false;
        }
        try {
            clearForm();    //清空表单
            //字符串转数组
            if (typeof row.mainSoftwareBrand === 'string') {
                row.mainSoftwareBrand = row.mainSoftwareBrand.split(',');
            }
            if (typeof row.mainSoftwareLine === 'string') {
                row.mainSoftwareLine = row.mainSoftwareLine.split(',');
            }
            fillForm(row);
            $win.modal('show');
        } catch(e) {
            SysMessage.alertError(e.message);
        }
    };

    /**
     *  清空全部表单
     */
    var clearForm = function() {
        $form.form('clear');    //清除客户信息
        $contactTable.bootstrapTable('removeAll');    //清除联系人信息
        $contactTable.bootstrapTable('refreshOptions', { url : '' });   // 清空url
        $infoTable.bootstrapTable('removeAll');    //清除信息化详情
        $infoTable.bootstrapTable('refreshOptions', { url : '' });   // 清空url
        $fileTable.fileTable('clear');    //清除附件
    };

    /**
     *  填充表单
     *  @param data 数据
     */
    var fillForm = function(data) {
        $form.form('load', data);    //加载客户信息
        $contactTable.bootstrapTable('refresh', { url : baseUrl + '/' + data.id + '/contacts' });   // 加载联系人
        $infoTable.bootstrapTable('refresh', { url : baseUrl + '/' + data.id + '/infos' });   // 加载信息化详情
    };

}(window.jQuery);
