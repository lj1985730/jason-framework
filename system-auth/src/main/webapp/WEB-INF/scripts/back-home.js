!function ($) {

    'use strict';

    var $table;

	$(function() {

        /**
         * 表格初始化
         */
        $table = $('#tenantTable');
        $table.bootstrapTable(
			$.extend(
			    {
                    url : appPath + '/auth/tenant',
                    formatSearch : function () {
                        return '搜索名称';
                    },
                    onCheck : function(row) {	//选中规划表某一行时触发指标表刷新事件
                    },
                    onExpandRow: function (index, row, $detail) {
                    },
                    onUncheck : function() {
                    }
                },
                $.extend({}, generalTableOption, { queryParams : tenantParams })
			)
		);

        $('#selectTenant').off('click').on('click', function() { selectTenant(); });
	});

	/**
	 * 查询条件
	 * @param params 基本查询条件，包含search、sort、order、limit、offset
	 */
	function tenantParams(params) {
		var localParams = {
			category : $('search_category').val()
		};
		return $.extend(localParams, params);
	}

    /**
     * 提交表单
     */
	var selectTenant = function() {

        var row = $table.bootstrapTable('getSelections')[0];
        if (!row) {
            SysMessage.alertNoSelection();
            return false;
        }

	    $.fn.http({
            method : 'PUT',
            url : appPath + '/auth/account/setTenant/' + row.id,
            success : function() {
                window.location.reload();
            }
        });
    };

}(window.jQuery);
