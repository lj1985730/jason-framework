/***********************************
 * CRM-商机管理-商机阶段脚本
 * @author Wang Chong at 2018-05-06 12:49:46
 * @since v1.0.0
 * *********************************/
!function ($) {

	'use strict';

    /**
     * 商机阶段对象
     */
	var ChanceStepObj = {};
	$.fn.ChanceStepObj = ChanceStepObj;

    /**
     * 获取字典表中商机阶段数据
     * ChanceStepObj.chanceStepData
     * ChanceStepObj.isOld 是否是旧数据，0：不是，1：是
     * @param chanceStepJson 商机阶段数据
     * @param $el 商机阶段容器
     */
    ChanceStepObj.getChanceStep = function (chanceStepJson, $el) {
        if(chanceStepJson){
            ChanceStepObj.isOld = 1;
            ChanceStepObj.chanceStepJSON = JSON.parse(chanceStepJson);
            ChanceStepObj.buildStepCon($el, ChanceStepObj.chanceStepJSON);
        }else {
            ChanceStepObj.isOld = 0;
            $.fn.http({
                method : 'GET',
                url : appPath + '/base/sysComboData',
                data : {key : 'CRM_SALES_STAGE'},
                success : function (result) {
                    //商机阶段字典数据
                    ChanceStepObj.chanceStepData = result;
                    ChanceStepObj.buildJSON();
                    ChanceStepObj.buildStepCon($el, ChanceStepObj.chanceStepJSON);
                }
            });
        }
    };

    /**
     * 按商机阶段数据构建json数据结构
     * ChanceStepObj.chanceStepJSON
     */
    ChanceStepObj.buildJSON = function () {
        var chanceStepData = ChanceStepObj.chanceStepData;
        if(!chanceStepData || chanceStepData.length < 1){
            SysMessage.alertInfo('商机阶段数据异常！');
            return false;
        }
        ChanceStepObj.chanceStepJSON = {};
        $.each(chanceStepData,function (index,value) {
            var i = index + 1;
            //state:是否为当前阶段 0：不是，1：是
            ChanceStepObj.chanceStepJSON['step' + i] =
                {id : value.id, value : value.value, startTime : '', remark : '', state : 0};
        });
    };

    /**
     * 根据json数据构建容器
     * @param $el 页面容器
     * @param chanceStepJSON 商机阶段JSON数据
     */
    ChanceStepObj.buildStepCon = function ($el, chanceStepJSON) {
        $el.empty();
        var $portlet = $('<div class="portlet light" id="form_wizard"></div>');
        ChanceStepObj.$form_wizard = $portlet;
        var $portlet_title = $('<div class="portlet-title"></div>');
        var $caption = $('<div class="caption"><i class="fa fa-fire"></i><apan class="caption-subject">商机阶段</apan></div>');
        var $tools = $(
            '<div class="tools">' +
                '<a href="javascript:;" class="collapse"></a>' +
                '<a href="javascript:;" class="fullscreen"></a>' +
            '</div>'
        );
        var $portlet_body = $('<div class="portlet-body form"></div>');
        var $form = $('<form class="form-horizontal" id="step_form"></form>');
        var $form_wizard = $('<div class="form-wizard"></div>');
        var $form_body = $('<div class="form-body"></div>');
        var $form_actions = $('<div class="form-actions"></div>');
        var $row = $('<div class="row"></div>');
        var $offset_3 = $('<div class="col-md-offset-4 col-md-9"></div>');
        // var $button_previous = $('<a href="javascript:;" class="btn blue button-previous"><i class="m-icon-swapleft m-icon-white"></i>回&nbsp;退</a>');
        // var $button_next = $('<a href="javascript:;" class="btn blue button-next">前&nbsp;进<i class="m-icon-swapright m-icon-white"></i></a>');
        var $bar = $('<div id="bar" class="progress progress-striped" role="progressbar"></div>');
        var $progress_bar_success = $('<div class="progress-bar progress-bar-success" style="width:17%;"></div>');
        var $ul = $('<ul class="nav nav-pills nav-justified steps"></ul>');
        var i = 0;//用于记录$a中的数
        var j = 0;//用于记录$tab_pane中的数
        $.each(chanceStepJSON,function (key, value) {
            var count = ++i;
            if(key === 'step0'){
                var $li = $('<li class="active" id="' + key +'"></li>');
                var $a = $('<a href="#tab'+ count + '" data-toggle="tab" class="step"></a>');
                var $number = $('<span class="number">' + count + '</span>');
                var $name = $('<span class="desc"><i class="fa fa-check"></i>' + value.value + '</span>');
                $li.append($a.append($number).append($name));
                $ul.append($li);
            } else {
                var $li1 = $('<li id="' + key + '"></li>');
                var $a1 = $('<a href="#tab'+ count + '" data-toggle="tab" class="step"></a>');
                var $number1 = $('<span class="number">' + count + '</span>');
                var $name1 = $('<span class="desc"><i class="fa fa-check"></i>' + value.value + '</span>');
                $li1.append($a1.append($number1).append($name1));
                $ul.append($li1);
            }
        });

        var $tab_content = $('<div class="tab-content"></div>');
        ChanceStepObj.$tab_content = $tab_content;
        $.each(chanceStepJSON, function (key, value) {
            var count1 = ++j;
            if(key === 'step0'){
                var $tab_pane = $('<div class="tab-pane active" id="tab' + count1 + '"></div>');
                var $date = ChanceStepObj.date(value.startTime);
                var $textarea = ChanceStepObj.textarea(value.remark);
                $tab_pane.append($date).append($textarea);
                $tab_content.append($tab_pane);
            } else {
                var $tab_pane1 = $('<div class="tab-pane" id="tab' + count1 + '"></div>');
                var $date1 = ChanceStepObj.date(value.startTime);
                var $textarea1 = ChanceStepObj.textarea(value.remark);
                $tab_pane1.append($date1).append($textarea1);
                $tab_content.append($tab_pane1);
            }
        });

        //组装
        $form_body.append($ul).append($bar.append($progress_bar_success)).append($tab_content);
        $form_actions.append($row);
        $form_wizard.append($form_body).append($form_actions);
        $form.append($form_wizard);
        $portlet_body.append($form);
        $portlet_title.append($caption).append($tools);
        $portlet.append($portlet_title).append($portlet_body);
        $el.append($portlet);
        $portlet.bootstrapWizard({
            onTabClick: function (tab, navigation, index) {

            },
            onNext: function (tab, navigation, index) {
            },
            onPrevious: function (tab, navigation, index) {
            },
            onTabShow: function (tab, navigation, index) {
                if(ChanceStepObj.isOld === 1 || ChanceStepObj.isOld === 2){
                    ChanceStepObj.isOld++;
                    $.each(ChanceStepObj.chanceStepJSON,function (key, value) {
                        if(value['state']){
                            index = parseInt(key.substr(4,1)) - 1;
                            $ul.find('#' + key).addClass('active');
                            $tab_content.find('.tab-pane').removeClass('active');
                            $tab_content.find('#' + 'tab' + key.substr(4,1)).addClass('active');
                        }
                    });
                }
                ChanceStepObj.handleTitle(navigation, index);
                var total = navigation.find('li').length;
                var current = (index >= 0 ? index : 0) + 1;
                //更新当前商机阶段
                $.each(ChanceStepObj.chanceStepJSON,function (key, value) {
                    value['state'] = 0;
                    if(key === ('step' + current)){
                        value['state'] = 1;
                        //保存商机当前阶段字典code值，存入chance_step，在日常查看用
                        ChanceStepObj.currentChanceStep = current;
                    } else {
                        value['state'] = 0;
                    }
                });
                console.log(current);
                var $percent = (current / total) * 100;
                if(current === 1){
                    ChanceStepObj.$form_wizard.find('.button-previous').css('display','none');
                }
                ChanceStepObj.$form_wizard.find('.button-next').removeClass('disabled');
                ChanceStepObj.$form_wizard.find('.progress-bar').css({
                    width: $percent + '%'
                });
            }
        });
    };

    /**
     * 更新ChanceStepObj.chanceStepJSON数据值,并返回商机阶段JSON数据
     */
    ChanceStepObj.updateJSON = function ($tab_content) {
        var $tabs = $tab_content.find('.tab-pane');
        if($tabs && $tabs.length > 0){
            $.each($tabs, function (index, value) {
                var proName = $(value).attr('id').replace('tab','step');
                ChanceStepObj.chanceStepJSON[proName]['startTime'] = $(value).find('input').val();
                ChanceStepObj.chanceStepJSON[proName]['remark'] = $(value).find('textarea').val();
            });
        }
    };

    /**
     * 已完成的阶段添加样式
     * @param navigation 导航
     * @param index 序号，从0开始
     */
    ChanceStepObj.handleTitle = function (navigation, index) {
        var total = navigation.find('li').length;
        var current = (index >= 0 ? index : 0) + 1;
        var li_list = navigation.find('li');
        for(var i = 0; i < li_list.length; i++){
            $(li_list[i]).removeClass('done');
        }
        for(var i = 0; i < index; i++){
            $(li_list[i]).addClass('done');
        }
        //丢单处理
        if(current === li_list.length){
            $(li_list[index]).addClass('done');
            $(li_list[index - 1]).removeClass('done');
        }
        //成交处理
        if(current === li_list.length - 1){
            $(li_list[current - 1]).addClass('done');
        }
        if (current === 1) {
            ChanceStepObj.$form_wizard.find('.button-previous').hide();
        } else {
            ChanceStepObj.$form_wizard.find('.button-previous').show();
        }

        if (current >= total) {
            ChanceStepObj.$form_wizard.find('.button-next').hide();
        } else {
            ChanceStepObj.$form_wizard.find('.button-next').show();
        }
    };

    /**
     * 日期
     */
    ChanceStepObj.date = function (value) {
        var $date = $('<div class="form-group"></div>');
        var $label = $('<label class="col-md-3 control-label">结束日期</label>');
        var $div_col4 = $('<div class="col-md-4"></div>');
        var $div_input_icon = $('<div class="input-icon date date-picker" id="dateId1"></div>');
        var $i = $('<i class="fa fa-calendar" id="datepickerIcon"></i>');
        var $input = $('<input class="form-control" id="dateId" name="startTime" value="' + value +'" placeholder="结束日期" readonly style="border-top-left-radius: 4px; border-bottom-left-radius: 4px;"/>');
        $div_input_icon.append($i).append($input);
        $div_col4.append($div_input_icon);
        $date.append($label).append($div_col4);

        $input.datepicker({
            rtl: Metronic.isRTL(),
            language : 'zh-CN',
            format : 'yyyy-mm-dd',
            startView : 0,
            minViewMode : 0,
            autoclose : true,
            todayBtn : false,
            todayHighlight: true,
            clearBtn : true
        });
        $div_input_icon.on('click', function() {
            $('#dateId').datepicker('show');
        });
        return $date;
    };

    /**
     * textarea
     */
    ChanceStepObj.textarea = function (value) {
        var $form_group = $('<div class="form-group"></div>');
        var $label = $('<label class="control-label col-md-3">备注</label>');
        var $div_col_4 = $('<div class="col-md-4"></div>');
        var $textarea = $('<textarea class="form-control" name="remark" placeholder="备注">' + value + '</textarea>');
        $form_group.append($label).append($div_col_4.append($textarea));
        return $form_group;
    };

    /**
     * 初始化
     */
    $(function () {
        // ChanceStepObj.getChanceStep();
    });

}(window.jQuery);
