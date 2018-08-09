/***********************************
 * CRM-商机管理-编辑页脚本
 * @author Liu Jun at 2018-4-26 13:49:46
 * @since v1.0.0
 * *********************************/
!function ($) {

	'use strict';

	var baseUrl = appPath + '/crm/chance';
    var $table, $win, $form, $fileTable;
    var $customerSelectorWin, $customerSelectorTable;

	/**
 	 * 初始化方法
 	 */
	$(function() {
		/**
 	 	 * 初始化table
 	 	 */
		$table = $('#chanceTable');

		/**
	 	 * 按钮初始化
	 	 */
		$('#chanceEditWinSubmitBtn').on('click', function() { submit() });

		/**
	 	 * 编辑页初始化
	 	 */
	 	$win = $('#chanceEditWin');
        $form = $('#chanceEditWinForm');

        //下拉框初始化
        $('#edit_chanceStep').comboData('CRM_SALES_STAGE', false, null, true);  //所属行业
        $('#edit_chancePartin').comboData('PERSON', false, null, true);  //参与人
        $('#pre_seller').comboData('PERSON', false, null, true);  //售前

        /* 客户选择 */
        $customerSelectorWin = $("#customerSelectWin");
        $customerSelectorTable = $("#customerTable-select");
        // 点击弹出客户选择窗体
        $("#chanceCustomerSelector").off('click').on("click", function () {
            $customerSelectorWin.modal('show');
            $customerSelectorTable.bootstrapTable('uncheckAll');
        });
        // 选中客户
        $("#customerSelectWinSubmitBtn").off('click').on("click",function () { selectCustomer(); });

        $fileTable = $('#cuChanceDoc');	//附件组件

        $form.validation(); //初始化表单验证，需要在各种三方组件之后初始化，以保证监听正确的元素

        //附件信息初始化
        $('#chanceExtCon').entityExt(
            {
                url2 : appPath + '/crm/entityExt/searchByEntityId',
                url3 : appPath + '/crm/entityExt/ext',
                entityTable : 'CU_CHANCE'
            }
        );
	});

	/**
 	 * 显示编辑窗口
 	 * @param saveOrUpdate	0 新增；1 修改
     * @param $outTable	数据源table
 	 */
	var sOrU;
	$.fn.showEditChanceWin = function(saveOrUpdate, $outTable) {
		sOrU = saveOrUpdate;
        $table = $outTable;
		try {
			var deptId = $('#edit_companyDeptId').val();
            $("#edit_chancePartin").attr("val", "");
			$form.form('clear');
			if(sOrU === 1) {
				var row = $table.bootstrapTable('getSelections')[0];
				if (!row) {
					SysMessage.alertNoSelection();
					return false;
				}
				//参与人将json字符串转成id数组
                var chancePartin = row.chancePartin;
				if(chancePartin && !(chancePartin instanceof Array)) {
					//JSON.parse() 方法用于将一个 JSON 字符串转换为对象。
                    var partins = JSON.parse(row.chancePartin);
                    var partPerson = [];
                    for(var i = 0; i < partins.length; i++) {
                        partPerson[i] = partins[i].id;
                    }
                    row.chancePartin = partPerson;
				}
                //售前json字符串转成id数组
                var preSellerStr = row.preSellers;
				if(preSellerStr && !(preSellerStr instanceof Array)){
				    var preSellersArr = JSON.parse(preSellerStr);
				    var preSellerIds = [];
				    for(var i = 0; i < preSellersArr.length; i++){
                        preSellerIds.push(preSellersArr[i].id);
                    }
                    row.preSellers = preSellerIds;
                }
                $form.form('load', row);
                //显示客户附件信息
                $('#chanceExtCon').data('entityId',row.id);
                $('#chanceExtCon').entityExt('setEntityId');
                $('#chanceExtCon').entityExt('initOfUpdate');
                $fileTable.fileTable('load', row.id);	//加载附件
                //商机阶段
                $.fn.ChanceStepObj.getChanceStep(row.chanceStepJson,$('#chance_step'));
			} else {
                var chanceResultDate = currentDate();
                $('#edit_chanceResultDate').val(chanceResultDate);
                $('#edit_companyDeptId').val(deptId);
                var $id = $('#id');
                $id.val(Math.uuid());  //生成新主键
                $fileTable.fileTable('load', $id.val());    //附件组件绑定新ID
                //显示客户附件信息
                $('#chanceExtCon').data('entityId',$id.val());
                $('#chanceExtCon').entityExt('setEntityId');
                $('#chanceExtCon').entityExt('initOfCreate');
                //商机阶段
                $.fn.ChanceStepObj.getChanceStep(null,$('#chance_step'));
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

        var editFormData = $form.serializeJson();

		//获取参与人选中对象
        var partins = $("#edit_chancePartin").select2("data");
        var preSellers = $("#pre_seller").select2("data");
		if(partins !== null) {
            var result = [];	//参与人id和name的数组对象
            for(var i = 0; i < partins.length; i++) {
                var obj = partins[i];
                result.push({ id : obj.id, text : obj.text });
            }
            //JSON.stringify() 方法用于将 JavaScript 值转换为 JSON 字符串
            editFormData.chancePartin = JSON.stringify(result);
		}
		//构建售前对象
		if(preSellers){
		    var preSellerIds = [];
		    for(var i = 0; i < preSellers.length; i++){
                preSellerIds.push({id:preSellers[i].id, text:preSellers[i].text});
            }
            editFormData.preSellers = JSON.stringify(preSellerIds);
        }
		//当前阶段字典表中code值
        editFormData.chanceStep = $.fn.ChanceStepObj.currentChanceStep;
		//更新数据
        $.fn.ChanceStepObj.updateJSON($.fn.ChanceStepObj.$tab_content);
        editFormData.chanceStepJson = JSON.stringify($.fn.ChanceStepObj.chanceStepJSON);

		var method = (sOrU === 0 ? 'POST' : 'PUT');
		$.fn.http({
			method : method,
			url : baseUrl,
			data : JSON.stringify(editFormData),
			success : function() {
				$win.modal('hide');
				$table.bootstrapTable('refresh');
                //附加信息提交
                $('#chanceExtCon').entityExt('submitForm');
			}
		});
	}

    /**
     * 当前日期
     */
    var currentDate = function () {
        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        return year + '-' + month + '-' + strDate;
    };

    /**
     * 选择客户
     */
    var selectCustomer = function () {
        var row = $customerSelectorTable.bootstrapTable('getSelections')[0];
        if (!row) {
            SysMessage.alertNoSelection();
            return false;
        }
        $("#chanceCustomerId").val(row.id);
        $("#chanceCustomerName").val(row.customerName);
        $("#edit_companyDeptId").val(row.department ? row.department.id : '');
        $("#edit_companyDeptName").val(row.department ? row.department.name : '');
        $("#edit_salesmanId").val(row.salesman ? row.salesman.id : '');
        $("#edit_salesmanName").val(row.salesman ? row.salesman.name : '');
        $customerSelectorWin.modal('hide');
    };

}(window.jQuery);
