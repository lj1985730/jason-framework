/**
 *  读取对象属性，支持多级对象嵌套属性
 * @param obj   对象
 * @param property  属性名
 * @return 对象属性值
 */
$.fn.readProperty = function(obj, property) {
    if(!obj || !property) {
        return null;
    }
    var value;
    if(property.indexOf('.') < 0) { //无对象嵌套
        value = obj[property];
    } else {    //关联对象属性特殊处理
        var properties = property.split('.');
        value = obj[properties[0]];
        for(var i = 1; i < properties.length; i ++) {    //递归到最子对象属性
            if(value instanceof Object) {
                value = value[properties[i]];
            } else {
                break;
            }
        }
    }
    return value;
};

/**
 * 表单初始化
 * @param method	初始化方法 load/clear
 * @param data		数据对象
 */
$.fn.form = function(method, data) {
    try {
        //加载表单值
        if('load' === method) {
            // 所有文本控件
            var elements = $(this).find(":text, textarea, :hidden, [type='number'], [type='email'], select, :checked, :checkbox, :radio");
            $.each(elements, function() {    //读取全部组件的name属性值，将data对应的值赋给组件
                var property = this.name;
                if(property) {
                    var value = $.fn.readProperty(data, property);
                    if(typeof (value) !== 'undefined' && value !== null) {
                        if(this.type === 'radio') {
                            if (this.value === value.toString()) {
                                $(this).attr('checked', true);
                                $(this).parent().addClass('checked');
                            } else {
                                $(this).attr('checked', false);
                                $(this).parent().removeClass('checked');
                            }
                        } else if(this.type === 'checkbox') {
                            if($(this).hasClass('make-switch')) {
                                $(this).bootstrapSwitch('state', value);
                            } else {
                                $(this).prop('checked', value);
                            }
                        } else if(this.type.indexOf('select') !== -1) {
                            $(this).val(value).trigger('change');    //select2 initial data
                        } else if($(this).hasClass('tree-select')) {   //自定义树组件tree-select
                            var textId = $(this).attr('id');
                            var treeId = textId.replace('-hidden', '');
                            var $tree = $('#' + treeId);
                            var node = $tree.treeview('findNodes', ['id', value]);
                            $tree.treeview('selectNode', [node, {silent: true}]);
                            $('#' + treeId + '-text').val(node.text);
                            this.value = value;

                            if($(this).parent().hasClass('form-md-floating-label')) {  //md风格form需要触发一下点击
                                $(this).trigger('keydown');
                            }
                        } else {
                            this.value = value;
                            if($(this).parent().hasClass('form-md-floating-label')) {  //md风格form需要触发一下点击
                                $(this).trigger('keydown');
                            }
                        }
                    }
                }
            });

            var static_elements = $(this).find('p');    //静态p标签赋值
            $.each(static_elements, function() {
                var property = $(this).data('property');
                if(property) {
                    var value = $.fn.readProperty(data, property);
                    if(typeof (value) !== 'undefined' && value !== null) {
                        if(typeof (value) === 'boolean') {
                            $(this).text(value ? '是' : '否');
                        } else {
                            $(this).text(value);
                        }
                    }
                }
            });
            //清空表单值
        } else if('clear' === method) {
            $('.tree-select', this).each(function() {
                var textId = $(this).attr('id');
                var treeId = textId.replace('-hidden', '');
                var $tree = $('#' + treeId);
                if($tree.treeview('getSelected').length > 0) {
                    $tree.treeview('unselectNode', $tree.treeview('getSelected'));
                }
            });
            $(':input', this).not(':button, :submit, :reset, :radio, :checkbox, select').val('');
            $(':input:checkbox', this).prop('checked', false);	//处理checkbox
            // $('select', this).find('option:first').prop('selected', true).trigger('change');	//处理select,选中第一项
            //select判断：单选默认第一项，多选清空默认值
            $('select',this).each(function () {
                if ($(this).attr('multiple') === 'multiple'){
                    $(this).val(null).trigger("change");
                }else {
                    $(this).find('option:first').prop('selected', true).trigger("change");
                }
            });

            $(':input', this).not(':button, :submit, :reset, :radio, :checkbox').each(function() {
                if($(this).parent().hasClass('form-md-floating-label')) {  //md风格form需要触发一下点击
                    $(this).trigger('blur');
                }
            });
            if($(this).data('validator') && $(this).data('validator').errorList.length > 0) {
                $(this).data('validator').resetForm();
                $(this).find('.form-group').each(function() {
                    $(this).removeClass('has-error');
                    $(this).find('.help-info').show();
                });
            }
            $('p', this).each(function() {   //清除静态元素内容
                if($(this).data('property')) {
                    $(this).empty();
                }
            });
        }
    } catch(ex) {
        SysMessage.alertError(ex.message);
    }
};

/**
 * 是否为空
 * @param value 值
 */
$.fn.isNull = function(value) {
    return value === null || value === '' || typeof(value) === "undefined";
};

/**
 * 表单值转json数组
 */
$.fn.serializeJson = function() {
    var serializeObj = {};
    $(this.serializeArray()).each(function() {
        serializeObj[this.name] = $.trim(this.value);
    });

    return serializeObj;
};

/**
 *  通用table配置
 */
var generalTableOption = {
    pagination : true,
    locale : 'zh_CN',
    pageSize : 10,
    dataType : "json",
    striped : true,
    queryParamsType : "limit",
    sidePagination : "server",	// 设置为服务器端分页
    responseHandler : responseHandler
    // queryParams : queryParams
};

function responseHandler(res) {
    if (res) {
        if(res.success && res.data) {
            return {
                "rows" : res.data.rows,
                "total" : res.data.total
            };
        } else {
            return {
                "rows" : res.rows,
                "total" : res.total
            };
        }
    } else {
        return {
            "rows" : [],
            "total" : 0
        };
    }
}

/**
 * 封装表单验证,表单对象调用
 * @param options 自定义配置参数
 */
$.fn.validation = function(options) {

    var $form = $(this);

    var defaultOptions = {

        errorElement : 'span', //default input error message container
        errorClass : 'help-block help-block-error', // default input error message class
        focusInvalid : false, // do not focus the last invalid input

        errorPlacement : function (error, element) { // render error placement for each input type
            if (element.parent(".input-group").size() > 0) {
                error.insertAfter(element.parent(".input-group"));
            } else if (element.attr("data-error-container")) {
                error.appendTo(element.attr("data-error-container"));
            } else if (element.parents('.radio-list').size() > 0) {
                error.appendTo(element.closest('.radio-list').attr("data-error-container"));
            } else if (element.parents('.radio-inline').size() > 0) {
                error.appendTo(element.closest('.radio-inline').attr("data-error-container"));
            } else if (element.parents('.icheck-inline').size() > 0) {
                error.appendTo(element.closest('.icheck-inline').attr("data-error-container"));
            } else if (element.parents('.checkbox-list').size() > 0) {
                error.appendTo(element.closest('.checkbox-list').attr("data-error-container"));
            } else if (element.parents('.checkbox-inline').size() > 0) {
                error.appendTo(element.closest('.checkbox-inline').attr("data-error-container"));
                //解决select2在元素上面显示错误信息的问题
            } else if (element.siblings('.select2-container').size() > 0) {
                error.insertAfter(element.siblings('.select2-container'));
            } else {
                error.insertAfter(element); // for other inputs, just perform default behavior
            }
        },

        highlight : function (element) { // highlight error inputs
            // set error class to the control group
            $(element).closest('.form-group').addClass('has-error');
        },

        unhighlight : function (element) { // revert the change done by unhighlight
            // set error class to the control group
            $(element).closest('.form-group').removeClass('has-error');
        },

        success : function (label) {
            // set success class to the control group
            label.closest('.form-group').removeClass('has-error');
        },

        //display error alert on form submit
        invalidHandler : function (event, validator) {
            // success3.hide();
            // error3.show();
            // Metronic.scrollTo(error3, -200);
        },

        submitHandler : function (form) {
            // success3.show();
            // error3.hide();
            // form[0].submit(); // submit the form
        }

    };

    if(options) {
        $.extend(defaultOptions, options);
    }

    $form.data('validator', $form.validate(defaultOptions));

    //icheck
    $form.find('.icheck').on("ifToggled", function () {
        $form.validate().element($(this));
    });

    //select2 下拉框
    $('.select2-hidden-accessible').on("select2:select", function () {
        $form.validate().element($(this));
    });

    // 日期框
    $form.find('.date-picker .form-control').on('change', function () {
        //revalidate the chosen dropdown value and show error or success message for the input
        $form.validate().element($(this));
    });

};

var commonComboData = [];
/**
 * 加载下拉框数据
 * @param businessKey 业务KEY
 * @param hasAll 类型 true 首选项为'全部'；false 首选项为'请选择'；
 * @param initValue 初始值
 * @param isCache 是否缓存列表
 * @param params 查询条件参数
 */
$.fn.comboData = function(businessKey, hasAll, initValue, isCache, params) {
    try {
        var $this = $(this);
        if($this.val() !== null) {
            initValue = $this.val();
        }
        $this.select2({
            theme : "bootstrap"
        });
        $this.empty();
        if(this.attr("multiple") !== "multiple") {  //判断多选
            if(hasAll) {
                $this.append('<option value="">全部</option>');
            } else {
                $this.append('<option value="">请选择</option>');
            }
        }
        var comboData = commonComboData[businessKey];
        if(isCache && comboData) {
            $.each(comboData, function(index) {
                if(initValue === comboData[index].id) {
                    $this.append('<option value="' + comboData[index].id + '" selected>' + comboData[index].value + '</option>');
                    $this.val(comboData[index].id).trigger('change');  //select2 use it
                } else {
                    $this.append('<option value="' + comboData[index].id + '">' + comboData[index].value + '</option>');
                }
            });
        } else {
            var args = Array.prototype.slice.call(arguments, 4);    //条件参数

            $.ajax({
                method : "GET",
                url : appPath + '/base/sysComboData',
                dataType : "json",
                contentType : "application/json",
                data : {
                    key : businessKey,
                    params : args
                },
                success : function(result, status) {
                    switch(status) {
                        case "timeout" :
                            SysMessage.alertError('请求超时！请稍后再次尝试。');
                            break;
                        case "error" :
                            SysMessage.alertError('请求错误！请稍后再次尝试。');
                            break;
                        case "success" :
                            var data = result.data;

                            if(!result.success) {
                                SysMessage.alertError(result.message);
                                break;
                            }

                            $.each(data, function(index) {

                                if(data[index].hasOwnProperty('ID')) {
                                    data[index].id = data[index].ID;
                                }

                                if(data[index].hasOwnProperty('VALUE')) {
                                    data[index].value = data[index].VALUE;
                                }

                                if(initValue === data[index].id) {
                                    $this.append('<option value="' + data[index].id + '" selected>' + data[index].value + '</option>');
                                    $this.val(data[index].id).trigger('change');  //select2 use it
                                } else {
                                    $this.append('<option value="' + data[index].id + '">' + data[index].value + '</option>');
                                }
                            });
                            if(isCache) {
                                commonComboData[businessKey] = data;
                            }
                            break;
                        default:
                            SysMessage.alertError('未知错误，请尝试刷新页面或者重新登录！');
                            return;
                    }
                }
            });
        }
    } catch(ex) {
        SysMessage.alertError(ex.message);
    }
};

/**
 * 封装ajax数据交互
 *  2018-3-27 Liu Jun 修改，增加防止快速点击导致重复提交
 * @param request 请求对象
 * request = {
 *  method : 'POST', //请求的方式POST/PUT/GET/DELETE
 *  url : XXXUrl,  //请求地址
 *  data : {},  //数据对象，因为使用restful，GET方式可为null
 *  success : function(data) //成功应答，如不传则默认为提示操作成功
 *  error : function(errorThrown) //失败应答，如不传则默认为提示异常信息
 * }
 * async true异步，false同步
 */
var common_http_timer = {}; //通用http计时器
$.fn.http = function(request, async) {

    clearTimeout(common_http_timer[request.url]);

    if(typeof async === 'undefined') {  //默认是异步操作
        async = true;
    }

    //控制300毫秒内相同url的请求只在最后一次生效
    common_http_timer[request.url] = setTimeout(function() {

        $.ajax({    //查询左侧树选择的数据
            method: request.method,
            url: request.url,
            async: async,
            dataType: "json",
            contentType: "application/json",
            data: request.data,
            beforeSend: function () {
                SysBlock.block(); //loading效果
            },
            complete : function() {
                SysBlock.unblock();
            },
            success: function (data, status) {
                switch (status) {
                    case "timeout" :
                        SysMessage.alertError('请求超时！请稍后再次尝试。');
                        break;
                    case "error" :
                        SysMessage.alertError('请求错误！请稍后再次尝试。');
                        break;
                    case "success" :
                        if (data.success) {
                            if (typeof request.success === 'function') {
                                if (data.data) {
                                    request.success.call(this, data.data);
                                } else {
                                    request.success.call(this);
                                }
                            }
                            if (data.message) {
                                SysMessage.alertSuccess(data.message);
                            }
                        } else {
                            if (data.message) {
                                SysMessage.alertWarning(data.message);
                            }
                        }
                        break;
                    default:
                        SysMessage.alertError('未知错误，请尝试刷新页面或者重新登录！');
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                SysMessage.alertError(errorThrown);
                if (typeof request.error === 'function') {
                    request.error.call(this, errorThrown);
                }
            }
        });

    }, 300);

};

/**
 *  通用下载方法
 *  不建议使用window.location的方式下载文件
 * @param fileUrl 文件url
 */
$.fn.download = function(fileUrl) {
    SysBlock.block(); //loading效果
    var tempObj = $('<a style="display: none;"></a>');   //临时的a标签对象
    tempObj.attr('href', fileUrl);    //赋值下载url
    tempObj.appendTo(document.body);   //临时标签对象写入body
    clearTimeout(common_http_timer[fileUrl]);
    common_http_timer[fileUrl] = setTimeout(function () {    //200ms内防止重复点击
        tempObj[0].click();  //这里需要使用原生的js方法触发点击事件
        tempObj.remove();   //使用完成后删除临时标签对象
        SysBlock.unblock();
    }, 200);
};

/**
 * 字符串占位替换，占位符为%s
 * @param str
 * @returns {*}
 */
var sprintf = function (str) {
    var args = arguments,
        flag = true,
        i = 1;

    str = str.replace(/%s/g, function () {
        var arg = args[i++];

        if (typeof arg === 'undefined') {
            flag = false;
            return '';
        }
        return arg;
    });
    return flag ? str : '';
};

/**
 * 通用查询条件，用于多数无条件的bootstrap-table引用
 * @param params 基本查询条件，包含search、sort、order、limit、offset
 */
var commonParams = function(params) {
    var localParams = {
    };
    return $.extend(localParams, params);
};

