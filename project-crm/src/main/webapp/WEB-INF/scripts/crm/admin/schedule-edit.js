/***********************************
 * CRM-编辑日程-脚本
 * @author Liu Jun at 2018-4-26 14:02:26
 * @since v1.0.0
 * *********************************/
!function ($) {

    'use strict';

    var baseUrl = appPath + '/crm/schedule';
    var $table, $win, $form;
    var $startTime = $("#edit_scheduleStart");
    var $endTime = $("#edit_scheduleEnd");
    var $customerSelectorWin, $customerSelectorTable;
    var $chanceSelectorWin, $chanceSelectorTable;

    /**
     * 初始化方法
     */
    $(function() {
        /**
         * 初始化table
         */
        $table = $('#scheduleTable');

        /**
         * 按钮初始化
         */
        $('#scheduleEditWinSubmitBtn').on('click', function() { submit() });

        /**
         * 编辑页初始化
         */
        $win = $('#scheduleEditWin');
        $form = $('#scheduleEditWinForm');

        $('#scheduleVisit').comboData('CRM_VISIT_TYPE', false, null, true);
        $('#edit_schedulePartin').comboData('PERSON', false, null, false);  //参与人(多选)

        /* 客户选择 */
        $customerSelectorWin = $("#customerSelectWin");
        $customerSelectorTable = $("#customerTable-select");
        // 点击弹出客户选择窗体
        $("#scheduleCustomerSelector").off('click').on("click", function () {
            $customerSelectorWin.modal('show');
            $customerSelectorTable.bootstrapTable('uncheckAll');
        });
        // 选中客户
        $("#customerSelectWinSubmitBtn").off('click').on("click",function () { selectCustomer(); });

        /* 商机选择 */
        $chanceSelectorWin = $("#chanceSelectWin");
        $chanceSelectorTable = $("#chanceTable");
        // 点击弹出商机选择窗体
        $("#chanceSelector").off('click').on("click",function () {
            $chanceSelectorWin.modal('show');
            $chanceSelectorTable.bootstrapTable('uncheckAll');
        });
        // 选中商机
        $("#chanceSelectWinSubmitBtn").off('click').on("click",function () { selectChance(); });

        //开始时间控件
        $startTime.datetimepicker({
            autoclose:true,
            startView : 1,
            maxView : 1,
            language:"zh-CN",
            format:'yyyy-mm-dd hh:ii'
        }).on('changeDate', function() {	//设置时间约束-结束时间为最大时间
            $startTime.datetimepicker('setDate', $startTime.datetimepicker('getDate'));
            $endTime.datetimepicker('setStartDate', $startTime.datetimepicker('getDate'));
        }).on('clearDate', function() {	//清除时间时取消对结束时间的约束
            $endTime.datetimepicker('setStartDate', null);
        });
        //结束时间控件
        $endTime.datetimepicker({
            autoclose:true,
            startView : 1,
            maxView : 1,
            language:"zh-CN",
            format:'yyyy-mm-dd hh:ii'
        }).on('changeDate', function() {	//设置时间约束-结束时间为最大时间
            $endTime.datetimepicker('setDate', $endTime.datetimepicker('getDate'));
            $startTime.datetimepicker('setEndDate', $endTime.datetimepicker('getDate'));
        }).on('clearDate', function() {	//清除时间时取消对结束时间的约束
            $startTime.datetimepicker('setEndDate', null);
        });

        $form.validation(); //初始化表单验证，需要在各种三方组件之后初始化，以保证监听正确的元素
    });

    /**
     * 显示编辑窗口
     * @param saveOrUpdate	0 新增；1 修改
     */
    var sOrU;
    $.fn.showEditScheduleWin = function(saveOrUpdate) {
        sOrU = saveOrUpdate;
        var $fileTable = $('#cuScheduleDoc');
        try {
            $startTime.datetimepicker('setEndDate', null);
            $endTime.datetimepicker('setStartDate', null);
            $form.form('clear');
            if(sOrU === 1) {
                var row = $table.bootstrapTable('getSelections')[0];
                if (!row) {
                    SysMessage.alertNoSelection();
                    return false;
                }

                //参与人将json字符串转成id数组
                var schedulePartin = row.schedulePartin;
                if(row.schedulePartin !== null && row.schedulePartin !== ""){
                    var partins = JSON.parse(row.schedulePartin);
                    var partPerson = [];
                    for(var i = 0;i < partins.length; i++){
                        partPerson[i] = partins[i].id;
                    }
                    row.schedulePartin = partPerson;
                }
                $("#edit_chanceName").val(row.chanceName).trigger("change");

                filterChance(row.customerId);   //过滤客户商机

                $form.form('load', row);
                row.schedulePartin = schedulePartin;
                $fileTable.fileTable('load', row.id);//加载商机主键



            }else {
                var $id = $('#id');
                $id.val(Math.uuid());  //生成新主键
                $fileTable.fileTable('load', $id.val());    //附件组件绑定新ID
            }
            $win.modal('show');
        } catch(e) {
            SysMessage.alertError(e.message);
        }
    };

    /**
     * 提交表单
     */
    function submit() {

        if(!$form.valid()) {    //表单验证
            return false;
        }

        var editFormData= $form.serializeJson();

        var partins = $("#edit_schedulePartin").select2("data");
        if(partins !== null) {
            var result = [];
            for(var i = 0; i < partins.length; i++){
                var obj = partins[i];
                result.push({ id : obj.id, text : obj.text });
            }
            editFormData.schedulePartin = JSON.stringify(result);
        }

        var method = (sOrU === 0 ? 'POST' : 'PUT');
        $.fn.http({
            method : method,
            url : baseUrl,
            data : JSON.stringify(editFormData),
            success : function() {
                $win.modal('hide');
                $table.bootstrapTable('refresh');
            }
        });
    }

    /**
     * 选择客户
     */
    var selectCustomer = function () {

        //先清除已存在的商机
        $('#chanceId').val('');
        $('#edit_chanceName').val('');

        var row = $customerSelectorTable.bootstrapTable('getSelections')[0];
        if (!row) {
            $("#scheduleCustomerId").val('');
            $("#scheduleCustomerName").val('');
            filterChance(null);
        } else {
            $("#scheduleCustomerId").val(row.id);
            $("#scheduleCustomerName").val(row.customerName);
            filterChance(row.id);
        }

        $customerSelectorWin.modal('hide');


    };

    /**
     * 过滤客户商机
     */
    var filterChance = function(customerId) {
        $chanceSelectorTable.bootstrapTable('removeAll');
        if(customerId) {
            $chanceSelectorTable.bootstrapTable('refresh', { url : appPath + '/crm/customer/' + customerId + '/chances-page' });   // 加载客户商机
        } else {
            $chanceSelectorTable.bootstrapTable('refresh', { url : appPath + '/crm/chance' });   // 加载全部商机
        }
    };

    /**
     * 选择关联机会
     */
    var selectChance = function () {
        var row = $chanceSelectorTable.bootstrapTable('getSelections')[0];
        if (!row) {
            $("#chanceId").val('');
            $("#edit_chanceName").val('');
            $("#edit_preSellerName").text('');
            $("#edit_currentStage").text('');
        } else {
            $("#chanceId").val(row.id);
            $("#edit_chanceName").val(row.name);
            $("#edit_preSellerName").text(row.preSellerName ? row.preSellerName : '');
            $("#edit_currentStage").text(row.chanceStepName ? row.chanceStepName : '');
        }
        $chanceSelectorWin.modal('hide');
    };


}(window.jQuery);
