/***********************************
 * 本页代码用于处理对应页面的js，默认生成了基本的CRUD操作，需要根据具体的业务进行调整。
 * @author Wang chong at 2018-03-06 15:39:52
 * @since v1.0.0
 * *********************************/

/**
 * 格式化-位置标识
 * @param value 显示值
 */
function addMapMarker (value) {
    if(value) {
        return '<i class="fa fa-map-marker"/>&nbsp;&nbsp;' + value;
    } else {
        return value;
    }
}
/**
 * 格式化-日期标识
 * @param value 显示值
 */
function addDateMarker (value) {
    if(value) {
        return '<i class="fa fa-calendar"/>&nbsp;&nbsp;' + value;
    } else {
        return value;
    }
}

!function ($) {

	'use strict';

	var baseUrl="appPath + /crm/sign";
    var $table, $editWin, $editForm;

	/**
 	 * 初始化方法
 	 */
	$(function() {
		/**
 	 	 * 初始化table
 	 	 */
		var curYear = new Date().getFullYear();
		var curMonth = new Date().getMonth() + 1;
		if (curMonth <= 9){
            $('#signDate').val(curYear + '-0' + curMonth);
		}else {
            $('#signDate').val(curYear + '-' + curMonth);
		}

		$table = $('#signTable');
		$table.bootstrapTable(
			$.extend(
				{
					url : baseUrl,
					formatSearch : function () {
						return '搜索XXX';
					},
                    onLoadSuccess:function (data) {
						//设置已出勤天数
                        $('#completeSign').animateNumber({ number: data.total });
						//设置本月应出勤天数
						var currentDay = new Date($('#signDate').val());
                        var currentMonth = currentDay.getMonth() + 1;
                        var currentYear = currentDay.getFullYear();
                        var beginDay = new Date(currentYear + '-' + currentMonth + '-' + '01');
                        var endDay = new Date(currentYear + '-' + currentMonth + '-' + new Date(currentYear,currentMonth,0).getDate());
                        $('#mustSign').animateNumber({ number: getWorkDayCount('cn',beginDay,endDay)});
						//设置现应出勤天数
						var endDay1;
						if (currentMonth === curMonth && currentYear === curYear){
                            endDay1 = new Date(currentYear + '-' + currentMonth + '-' + new Date().getDate());
						}else {
                            endDay1 = endDay;
						}
                        $('#nowSign').animateNumber(
                        	{
							number: getWorkDayCount('cn',beginDay,endDay1),
							},
							function () {
                                //设置缺勤天数
                                var missSign = eval($('#nowSign').html() - $('#completeSign').html());
                                if (missSign){
                                    if (missSign <= 0){
                                        $('#missSign').html('0');
                                    }else {
                                        $('#missSign').animateNumber({ number: missSign });
                                    }
                                }else {
                                    $('#missSign').html('');
                                }
                            }
						);
                    }
				},
				$.extend({}, generalTableOption, { queryParams : signParams })
			)
		);

		/**
	 	 * 按钮初始化
	 	 */
		$('#createSign').on('click', function() { showEditWin(0); });
		$('#updateSign').on('click', function() { showEditWin(1); });
		$('#deleteSign').on('click', function() { remove(); });
		$('#signEditWinSubmitBtn').on('click', function() { submit() });

        /**
		 * 初始化按日期过滤签到列表
         */
        $('#signDate').on('change',function () {
            $table.bootstrapTable('refresh');
        });

		/**
	 	 * 编辑页初始化
	 	 */
	 	$editWin = $('#signEditWin');
	 	$editForm = $('#signEditWinForm');

		$editForm.validation(); //初始化表单验证，需要在各种三方组件之后初始化，以保证监听正确的元素
	});
	/**
 	 * 查询条件
 	 * @param params 基本查询条件，包含search、sort、order、limit、offset
 	 */
	function signParams(params) {
		var localParams = {
            signDate : $('#signDate').val()// 查询条件
		};
		return $.extend(localParams, params);
	}

	/**
 	 * 显示编辑窗口
 	 * @param saveOrUpdate	0 新增；1 修改
 	 */
	var sOrU;
	function showEditWin(saveOrUpdate) {
		sOrU = saveOrUpdate;
		try {
			$editForm.form('clear');
			if(sOrU === 1) {
				var row = $table.bootstrapTable('getSelections')[0];
				if (!row) {
					SysMessage.alertNoSelection();
					return false;
				}
				$editForm.form('load', row);
			}
			$editWin.modal('show');
		} catch(e) {
			SysMessage.alertError(e.message);
		}
	}

	/**
 	 * 提交表单
	 */
	function submit() {

		if(!$editForm.valid()) {    //表单验证
			return false;
		}

		var method = (sOrU === 0 ? 'POST' : 'PUT');
		$.fn.http({
			method : method,
			url : baseUrl,
			data : JSON.stringify($editForm.serializeJson()),
			success : function() {
				$editWin.modal('hide');
				$table.bootstrapTable('refresh');
			}
		});
	}

	/**
	 * 执行删除动作的操作
	 */
	var remove = function() {
		var row = $table.bootstrapTable('getSelections')[0];
		if (!row) {
			SysMessage.alertNoSelection();
			return false;
		}
		bootbox.confirm('确定要删除吗？', function (callback) {
			if(callback) {
				$.fn.http({
					method : 'DELETE',
					url : baseUrl + '/' + row.id,
					success : function() {
						$table.bootstrapTable('refresh');
					}
				});
			}
		});
	};


}(window.jQuery);
