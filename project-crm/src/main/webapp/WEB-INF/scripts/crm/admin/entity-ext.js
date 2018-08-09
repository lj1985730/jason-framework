/**
 * 附加信息插件
 * @author Wang Chong at 2018-04-26 09:18:49
 * @version 1.0
 */
(function($){

	'use strict';   //开启严格模式
	 
	/**
	 * 调用入口:$('#XXX').entityExt({    --XXX 为div元素
	 *  url1 : String   --获取附加信息路径（默认：appPath + '/crm/ext'）
	 *  url2 : String   --获取对象-附加信息路径（必须,可以通过setEntityId设置）
	 *  url3 : String   --保存对象-附加信息路径（必须）
	 *  readonly : Boolean  --表单元素是否只读（默认false）
	 *  entityId : String   --实体类的id(必须)
	 *  entityTable : String  --实体类对应的数据库的表名（必须）
	 * });
     * $('#XXX').entityExt('setEntityId',entityId);设置实体类id
     */
	$.fn.entityExt = function(option) {
		var value = null;
		var args = Array.prototype.slice.call(arguments, 1);
		this.each(function () {
			var $this = $(this);
			var data = $this.data('entity.ext');
			var options = $.extend({}, $.fn.entityExt.defaults, $this.data(), typeof option === 'object' && option);

			if (typeof option === 'string') {
				if (!data) {
                	return;
				}
				value = $.fn.entityExt.methods[option](this, data.options, args);

				if (option === 'destroy') {
                	$this.removeData('entity.ext');
				}
			}
			if (!data) {
				$this.data('entity.ext', (new EntityExt($this, options)));
			}
        });

		return typeof value === 'undefined' || value === null ? this : value;
	};

	/**
	 * 定义默认配置信息
	 */
	$.fn.entityExt.defaults = {
        url1 : appPath + '/crm/ext/searchByTable',  	//获取附加信息路径默认值
        url2 : '',                                      //获取对象-附加信息路径（必须）
        url3 : '',                                      //保存对象-附加信息路径（必须）
        entityId : '',                                  //实体类的id(必须)
        entityTable : '',                               //实体类对应的数据库的表名（必须）
        readonly : false                                //表单元素是否只读（默认false）
	};

	/**
	 * 控件方法属性
	 */
	$.fn.entityExt.methods = {
        /**
         * 新增实体类初始化附件信息
         */
        initOfCreate : function(target) {
            var $this = $(target).data('entity.ext');
            $this.sOrU = 0;
            //初始化容器
            $this.buildExtCon($(target));
            $this.getExtByTable();
        },
        /**
         * 更新实体类初始化附件信息
         */
        initOfUpdate : function(target) {
            var $this = $(target).data('entity.ext');
            $this.sOrU = 1;
            //初始化容器
            $this.buildExtCon($this);
            $this.getCusExtByCusId($this.options.entityId);
        },
        /**
         * 查看实体初始化附件信息
         */
        initOfView : function(target) {
            var $this = $(target).data('entity.ext');
            //初始化容器
            $this.buildExtCon($this);
            $this.getCusExtByCusId($this.options.entityId);
        },
        /**
         * 设置entityId
         */
        setEntityId : function (target) {
            var $this = $(target).data('entity.ext');
            $this.options.entityId = $(target).data('entityId');
        },
        /**
         * 表单提交
         */
        submitForm : function (target) {
            var $this = $(target).data('entity.ext');
            //保存表单
            $this.submitForm();
        }
	};

    /**
     * 组件对象
     * @param el 组件所挂接的html元素
     * @param options   设置项集合
     * @constructor
     */
	var EntityExt = function (el, options) {
        this.options = options;	    //配置项
        this.$el = el;	                //组件载体
        this.id = el.attr('id');    //ID
    };

    /**
     * 初始化容器方法
     */
    EntityExt.prototype.buildExtCon = function() {
        var $this = this;
        //清空容器
        $this.$el.empty();
        var $portlet = $('<div class="portlet light"></div>');
        var $portletTitle = $('<div class="portlet-title"></div>');
        var $portletBody = $('<div class="portlet-body form"></div>');
        var $caption = $('<div class="caption"><i class="fa fa-gift"></i><apan class="caption-subject">附加信息</apan></div>');
        var $options = $('<div class="actions"></div>');
        // var $submitBut = $('<button type="button" class="btn green" id="extSubmit" style="margin-right: 15px;"><i class="fa fa-check"></i>&nbsp;保存</button>');
        var $tools = $('<div class="tools"></div>');
        var $collapse = $('<a class="collapse"></a>');
        var $fullscreen = $('<a class="fullscreen"></a>');
        var $form = $('<form class="form-horizontal" id="cusExtForm"></form>');
        var $formBody = $('<div class="form-body"></div>');

        //组装
        $tools.append($collapse);
        $tools.append($fullscreen);
        // $options.append($submitBut);
        $portletTitle.append($caption).append($tools).append($options);
        $form.append($formBody);
        $portletBody.append($form);
        $portlet.append($portletTitle).append($portletBody);
        $this.$el.append($portlet);
        //附加信息表单
        $this.cusExtForm = $form;
        $this.cusExtFormBody = $formBody;
        // $a.click();
        // $submitBut.on('click', function () {
        //     if(!$('#editCustomerForm').valid()) {    //表单验证
        //         SysMessage.alertWarning('请先填写客户信息');
        //         return false;
        //     }
        //     CustomerExt.submitForm();
        // });
    };

    /**
     * 根据附加信息前台展现方式构建dom元素
     * @param showType 附加信息前台展现方式：radio,text,date,email
     * @param label 附加信息名字
     * @param value 附加信息值
     * @param extKey 附加信息key(下拉框存的是字典表中CATEGORY_KEY)
     */
    EntityExt.prototype.buildDom = function(showType, label, value, extKey) {
        var $this = this;
        //判断展现类型，构建表单元素
        if (!showType){
            return false;
        }
        switch (showType){
            case 'radio' :
                $this.cusExtFormBody.append($this.radio(label,value));
                break;
            case 'text' :
                $this.cusExtFormBody.append($this.defaultInput(label,value));
                break;
            case 'date' :
                $this.cusExtFormBody.append($this.date(label,value));
                break;
            case 'email' :
                $this.cusExtFormBody.append($this.emailAddress(label,value));
                break;
            case 'select' :
                $this.cusExtFormBody.append($this.select(label,value,extKey));
                break;
            default :
                $this.cusExtFormBody.append($this.defaultInput(label,value));
                break;
        }
    };

    /**
     * 根据表名动态获取附加信息项:$.fn.entityExt.defaults.entityTable
     */
    EntityExt.prototype.getExtByTable = function () {
        var $this = this;
        $.fn.http(
            {
                method : 'get',
                url : $this.options.url1,
                data : {tableName : $this.options.entityTable},
                success : function (result) {
                    if (result && result.length > 0){
                        $this.extIds = [];
                        for (var i = 0; i < result.length; i++){
                            $this.buildDom(result[i].extShowType,result[i].extName,'',result[i].extKey);
                            //储存附加信息id，用于保存时用
                            $this.extIds.push(result[i].id);
                        }
                    }
                }
            }
        )
    };

    /**
     * 根据客户id动态获取客户附件信息
     * @param customerId 客户id
     */
    EntityExt.prototype.getCusExtByCusId = function (customerId) {
        var $this = this;
        $.fn.http(
            {
                method : 'get',
                url : $this.options.url2,
                data : {entityId : customerId},
                success : function (result) {
                    if (result && result.length > 0){
                        $this.extIds = [];
                        $this.customerExtIds = [];
                        for (var i = 0; i < result.length; i++){
                            $this.buildDom(result[i].ext.extShowType,result[i].ext.extName,result[i].extValue,result[i].ext.extKey);
                            //储存附加信息id，用于保存时用
                            $this.extIds.push(result[i].ext.id);
                            //存储客户附加信息主键，用于更新
                            $this.customerExtIds.push(result[i].id);
                        }

                    } else {
                        //清除ids
                        $this.customerExtIds = null;
                        //如果ids,为空，则证明以前没有附加信息，状态改为新增状态
                        $this.sOrU = 0;
                        $this.getExtByTable();
                    }
                }

            }
        );
    };

    /**
     * 表单提交
     */
    EntityExt.prototype.submitForm = function () {
        var $this = this;
        var method = ($this.sOrU === 0 ? 'POST' : 'PUT');
        $.ajax({
            method : method,
            url : $this.options.url3,
            dataType: "json",
            contentType: "application/json",
            data : $this.buildData()
        });
    };

    /**
     * 将表单的值组合成数组
     */
    EntityExt.prototype.buildData = function () {
        var $this = this;
        var customerId = $this.options.entityId;
        var formData = $this.cusExtForm.serializeJson();
        // var formDataVal = Object.values(formData);
        var formDataVal = [];
        if (formDataVal){
            $.each(formData,function (key,val) {
                formDataVal.push(val);
            });
        }
        var requestData = [];
        if (!$this.extIds){
           return ;
        }
        if ($this.sOrU === 0){
            for (var i = 0; i < formDataVal.length; i++){
                requestData.push({entityId:customerId, extId:$this.extIds[i], extValue:formDataVal[i]});
            }
        } else {
            for (var i = 0; i < formDataVal.length; i++){
                requestData.push({id : $this.customerExtIds[i],entityId:customerId, extId:$this.extIds[i], extValue:formDataVal[i]});
            }
        }
        return JSON.stringify(requestData);
    };

    /**
     * 默认的input text
     */
    EntityExt.prototype.defaultInput = function (label,value) {
        var $this = this;
        var $defaultInput = $('<div class="form-group"></div>');
        var $label = $('<label class="col-md-2 control-label">' + label + '</label>');
        var $col_9 = $('<div class="col-md-9"></div>');
        var $input = $('<input class="form-control" name="' + Math.uuid() + 'extValDefault" value="' + value + '" placeholder="请输入'+ label +'..."/>');
        var $p = $('<p class="form-control-static">'+ value + '</p>');

        if ($this.options.readonly){
            $col_9.append($p);
        }else{
            $col_9.append($input);
        }
        $defaultInput.append($label).append($col_9);
        return $defaultInput
    };

    /**
     * 邮箱地址 email
     */
    EntityExt.prototype.emailAddress = function (label,value) {
        var $this = this;
        var $emailAddress = $('<div class="form-group"></div>');
        var $label = $('<label class="col-md-2 control-label">' + label + '</label>');
        var $col_9 = $('<div class="col-md-9"></div>');
        var $p = $('<p class="form-control-static">'+ value + '</p>');
        var $email = $(
            '       <div class="input-inline" style="margin-right: 0">' +
            '           <div class="input-group">' +
            '               <span class="input-group-addon">' +
            '                   <i class="fa fa-envelope"></i>' +
            '               </span>' +
            '               <input class="form-control" name="' + Math.uuid() + 'extValEmail" type="email" value="' + value +'" placeholder="请输入'+ label +'...">' +
            '           </div>' +
            '       </div>'
        );
        if ($this.options.readonly){
            $col_9.append($p);
        }else{
            $col_9.append($email);
        }
        $emailAddress.append($label).append($col_9);
        return $emailAddress;
    };

    /**
     * 单选 radio
     */
    EntityExt.prototype.radio = function (label,value) {
        var uuid = Math.uuid();
        var $this = this;
        var $radio = $('<div class="form-group"></div>');
        var $label = $('<label class="col-md-2 control-label">' + label + '</label>');
        var $div_9 = $('<div class="col-md-9"></div>');
        var $div_group = $('<div class="input-group"></div>');
        var $div_icheck = $('<div class="icheck-inline"></div>');
        var $label_0 = $('<label></label>');
        var $label_1 = $('<label></label>');
        var $input_0 = $('<input type="radio" class="icheck" id="extValue0" data-radio="iradio_square-grey" name="' + uuid + 'extValRadio" value="0"/>');
        var $input_1 = $('<input type="radio" class="icheck" id="extValue1" data-radio="iradio_square-grey" name="' + uuid + 'extValRadio" value="1"/>');
        $label_1.append($input_1).append('&nbsp;否');
        $label_0.append($input_0).append('&nbsp;是');
        $div_icheck.append($label_0).append($label_1);
        $div_group.append($div_icheck);
        $div_9.append($div_group);
        $radio.append($label).append($div_9);

        if (value === $input_0.val()){
            $input_0.attr('checked',true);
            $input_1.attr('checked',false);
        }
        if (value === $input_1.val()){
            $input_0.attr('checked',false);
            $input_1.attr('checked',true);
        }

        if ($this.options.readonly){
            $input_0.attr('disabled',true);
            $input_1.attr('disabled',true);
        }

        return $radio;
    };

    /**
     * 日期
     */
    EntityExt.prototype.date = function (label,value) {
        var $this = this;
        var $date = $('<div class="form-group"></div>');
        var $label = $('<label class="col-md-2 control-label">' + label + '</label>');
        var $div_col9 = $('<div class="col-md-9"></div>');
        var $div_input_icon = $('<div class="input-icon date date-picker" id="dateId1"></div>');
        var $i = $('<i class="fa fa-calendar" id="datepickerIcon"></i>');
        var $input = $('<input class="form-control" id="dateId" name="' + Math.uuid() + 'extValDate" value="' + value +'" placeholder="'+ label +'" readonly style="border-top-left-radius: 4px; border-bottom-left-radius: 4px;"/>');
        var $p = $('<p class="form-control-static">'+ value + '</p>');
        $div_input_icon.append($i).append($input);
        if ($this.options.readonly){
            $div_col9.append($p);
        } else {
            $div_col9.append($div_input_icon);
        }
        $date.append($label).append($div_col9);

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
     * 下拉框
     */
    EntityExt.prototype.select = function (label,value,extKey) {
        var $this = this;
        var $select = $('<div class="form-group"></div>');
        var $label = $('<label class="col-md-2 control-label">' + label + '</label>');
        var $div_9 = $('<div class="col-md-9"></div>');
        var $input = $('<select class="form-control" id="selectId" name="' + Math.uuid() + 'extValSelect" style="width: 100%;"></select>');
        $div_9.append($input);
        $select.append($label).append($div_9);
        if (value){
            $input.comboData(extKey, false, parseInt(value), true);
        }else {
            $input.comboData(extKey, false, null, true);
        }

        if ($this.options.readonly){
            $input.attr('disabled',true);
        }

        return $select;
    };
})(jQuery);

