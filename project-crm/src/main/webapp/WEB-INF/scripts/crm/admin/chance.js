/***********************************
 * 本页代码用于处理对应页面的js，默认生成了基本的CRUD操作，需要根据具体的业务进行调整。
 * @author Sheng Baoyu at 2018-03-19 10:18:11
 * @modify Liu Jun at 2018-4-26 13:02:18
 * @since v1.0.0
 * *********************************/
!function ($) {

	'use strict';

	var baseUrl = appPath + '/crm/chance';
    var $table;

	/**
 	 * 初始化方法
 	 */
	$(function() {
		/**
 	 	 * 初始化table
 	 	 */
		$table = $('#chanceTable');
		$table.bootstrapTable(
			$.extend({
					url : baseUrl,
					formatSearch : function () {
						return '搜索商机名称';
					}
				},
				$.extend({}, generalTableOption, { queryParams : chanceParams })
			)
		);
		/**
	 	 * 按钮初始化
	 	 */
		$('#viewChance').on('click', function() { $.fn.showViewChanceWin($table); });
		$('#createChance').on('click', function() { $.fn.showEditChanceWin(0, $table); });
		$('#updateChance').on('click', function() { $.fn.showEditChanceWin(1, $table); });
		$('#deleteChance').on('click', function() { remove(); });
	});

	/**
 	 * 查询条件
 	 * @param params 基本查询条件，包含search、sort、order、limit、offset
 	 */
	function chanceParams(params) {
		var localParams = {};
		return $.extend(localParams, params);
	}

    /**
     * 导出excel
     */
    $.fn.export = function() {
        $.fn.download(baseUrl + '/listExcel');
    };

}(window.jQuery);
