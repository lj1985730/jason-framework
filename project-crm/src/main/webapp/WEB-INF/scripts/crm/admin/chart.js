/***********************************
 * 本页代码用于处理对应页面的js，默认生成了基本的CRUD操作，需要根据具体的业务进行调整。
 * @author Sheng Baoyu at 2018-03-19 10:18:11
 * @modify Liu Jun at 2018-4-26 13:02:18
 * @since v1.0.0
 * *********************************/
!function ($) {

	'use strict';

	var baseUrl = appPath + '/crm/chart';

	var $personChanceBar, $personScheduleBar;

	/**
 	 * 初始化方法
 	 */
	$(function() {

        $personChanceBar = echarts.init(document.getElementById('person-chance-chart-container'), null, { renderer: 'canvas' });
        loadPersonChance();

        $personScheduleBar = echarts.init(document.getElementById('person-schedule-chart-container'), null, { renderer: 'canvas' });
        loadPersonSchedule();

        //浏览器大小改变时重置大小
        window.onresize = function () {
            $personChanceBar.resize();
            $personScheduleBar.resize();
        };

		/**
	 	 * 按钮初始化
	 	 */
		// $('#viewChance').on('click', function() { $.fn.showViewChanceWin($table); });
	});

    /**
	 * 加载人员商机对比Bar
     */
	var loadPersonChance = function() {
        $.fn.http({
            method : 'GET',
            url : baseUrl + '/personChance',
            success : function(data) {
            	var chartData = $.extend(true, {}, generalChartOption, data);
                $personChanceBar.setOption(chartData);
            }
        });
	};

    /**
     * 加载人员日程对比Bar
     */
    var loadPersonSchedule = function() {
        $.fn.http({
            method : 'GET',
            url : baseUrl + '/personSchedule',
            success : function(data) {
                var chartData = $.extend(true, {}, generalChartOption, data);
                $personScheduleBar.setOption(chartData);
            }
        });
    };

}(window.jQuery);
