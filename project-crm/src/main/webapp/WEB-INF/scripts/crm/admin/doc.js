/***********************************
 * 本页代码用于处理对应页面的js，默认生成了基本的CRUD操作，需要根据具体的业务进行调整。
 * @author Wang chong at 2018-03-08 13:50:37
 * @since v1.0.0
 * *********************************/
!function ($) {

	'use strict';

	var baseUrl="appPath + /crm/doc";
    var $table, $editWin, $editForm;

	/**
 	 * 初始化方法
 	 */
	$(function() {
		/**
 	 	 * 初始化table
 	 	 */
		$table = $('#docTable');
		$table.bootstrapTable(
			$.extend(
				{
					url : baseUrl,
					formatSearch : function () {
						return '搜索文档名称或扩展名...';
					}
				},
				$.extend({}, generalTableOption, { queryParams : docParams })
			)
		);
		/**
	 	 * 按钮初始化
	 	 */
		$('#createDoc').on('click', function() { showEditWin(0); });
		$('#updateDoc').on('click', function() { showEditWin(1); });
		$('#deleteDoc').on('click', function() { remove(); });
		$('#docEditWinSubmitBtn').on('click', function() { submit() });

		/**
	 	 * 编辑页初始化
	 	 */
	 	$editWin = $('#docEditWin');
	 	$editForm = $('#docEditWinForm');

		$editForm.validation(); //初始化表单验证，需要在各种三方组件之后初始化，以保证监听正确的元素
	});
	/**
 	 * 查询条件
 	 * @param params 基本查询条件，包含search、sort、order、limit、offset
 	 */
	function docParams(params) {
		var localParams = {
			// 查询条件
		}
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
	}

}(window.jQuery);
