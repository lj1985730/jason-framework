/**
 * 工作流程记录查看弹窗插件
 * @author Liu Jun
 * @version 1.0
 * @date 2017-09-20 15:18:49
 */
(function($){

	'use strict';//开启严格模式
	 
	/**
	 * 调用入口:$('#btn_id').workflowHistoricView({});
     * 赋值实例ID：$('#btn_id').workflowHistoricView('setProcessInstanceId', XXX);
	 */
	$.fn.workflowHistoricView = function(option, agrs) {
		var value = null;
		this.each(function () {
			var $this = $(this);
			var data = $this.data('workflow.historic.view');
			var options = $.extend({}, $.fn.workflowHistoricView.defaults, $this.data(), typeof option === 'object' && option);

			if (typeof option === 'string') {
				if (!data) {
                	return;
				}
				value = $.fn.workflowHistoricView.methods[option](this, data.options, agrs);
				if (option === 'destroy') {
                	$this.removeData('workflow.historic.view');
				}
			}
			if (!data) {
				//为元素添加data属性
				//判断标签类型,如果是select标签则转换成input标签
				if($this[0].tagName !== 'BUTTON') {
					var buttonEl = $('<button type="button" class="btn btn-md btn-default table_btn">' +
										'<span class="glyphicon glyphicon-zoom-in"></span>&nbsp;' + options.btnText +
									'</button>');
					buttonEl.insertAfter($this);
					var newEl = $this.next();
					newEl.attr('id', $this.attr('id'));
					newEl.attr('style', $this.attr('style'));
					$this.remove();
					$this = $(newEl);
				}
				$this.data('workflow.historic.view', (new WorkflowHistoricView($this, options)));
			}
        });
		 
		return typeof value === 'undefined' || value === null ? this : value;
	};
	 
	/**
	 * 定义默认配置信息
	 */
	$.fn.workflowHistoricView.defaults = {
        baseUrl : basePath + 'workflow/',	//业务跟路径
        btnText : '流程查看',		//按钮文本
        modalIdPrefix : '_workflow_historic_modal_',	//弹窗id前缀
        processInstanceId : '',		//流程实例主键
		disabled : false,			//禁用
		cls : '',					//自定义样式,多个样式用逗号隔开 class1,class2
		onBeforeLoad : function(param) {},	//param 请求参数
		onLoadSuccess : function(data) {},	//data加载成功后返回的数据
		onLoadError : function() {}
	};
	
	/**
	 * 控件方法属性
	 */
	$.fn.workflowHistoricView.methods = {
		/**
		 * 设置流程实例ID
		 */
		setProcessInstanceId : function(target, options, processInstanceId) {
		    var $this = $(target).data('workflow.historic.view');
		    if(typeof(processInstanceId) === 'undefined' || !processInstanceId) {
                $this.options.processInstanceId = '';
            } else {
                $this.options.processInstanceId = processInstanceId;
            }
		},
		/**
		 * 获取所有审批历史数据集
		 */
		getData : function(target, options) {
			return options.data;
		},
		enable : function(target, flag) {
            $(target).disable(!flag);
        }
	};
	
	var WorkflowHistoricView = function (el, options) {
        this.options = options;	    //配置项
        this.$el = el;	            //文本框
        this.id = el.attr('id');    //ID
        this.$contentModal = null;	//弹窗
        this.$img = null;			//图片
		this.$grid = null;			//数据网格
        this.contentModalId = null;		//弹窗id
        this.data = [];					//历史数据集合
        this.init();
    };
    
    //初始化控件
    WorkflowHistoricView.prototype.init = function() {
    	this.initModal();	//初始化窗体
    };

    /**
     * 创建下拉列表
     */
    WorkflowHistoricView.prototype.initModal = function() {
        var $this = this;
        var index = $('div[id^="' + this.options.modalIdPrefix + '"]').length + 1;
        $this.contentModalId = $this.options.modalIdPrefix + index;
        var modalHtml =
            '<div id="' + $this.contentModalId + '" class="modal fade" tabindex="-1" role="dialog" data-backdrop="static">' +
            '	<div class="modal-lg modal-xl modal-xxl modal-xxxl modal-dialog">' +
            '		<div class="modal-content">' +
            '        	<div class="modal-header" style="background-color: dimgray; border-top-left-radius: 5px; border-top-right-radius: 5px;">' +
            '           	<h4 class="modal-title" style="color: white;">' +
            '               	<i class="glyphicon glyphicon-zoom-in"></i>' +
            '                	<span style="font-weight:bold;">流程查看</span>' +
            '                   <li class="glyphicon glyphicon-remove pull-right" style="transform: scale(1); top:4px;"' +
            '                       onmouseover="this.style.cssText=\'transform: scale(1.5); top:4px;\'"' +
            '                       onmouseout="this.style.cssText=\'transform: scale(1); top:4px;\'"' +
            '                       data-dismiss="modal" data-target="#' + $this.contentModalId + '"></li>' +
            '                </h4>' +
            '			</div>' +
            '			<div class="modal-body" style="height: 472px; overflow: auto;">' +
            '				<div class="sm-col-12" style="height: 220px; overflow: auto; text-align: center;">' +
            '                   <img id="diagramHolder_' + $this.id + '"  style="height: 100%;"/>' +
            '				</div>' +
            '				<div class="sm-col-12">' +
            '					<div class="table-container" style="padding-top: 15px; height: 220px; overflow-y: auto;">' +
            '        				<table id="' + $this.contentModalId + '_table"' +
            '               			data-search="false" data-show-refresh="false"' +
            '               			data-show-toggle="false" data-show-columns="false">' +
            '            				<thead>' +
            '                				<tr role="row" class="heading">' +
            '                    				<th data-field="checkbox" data-checkbox="true" data-align="center" data-edit="false"></th>' +
            '                    				<th data-field="persistentState.name" data-sortable="false" data-align="center" data-edit="false">执行环节</th>' +
            '                    				<th data-field="taskDefinitionKey" data-sortable="false" data-align="center" data-edit="false" data-visible="false">任务名称</th>' +
            '                    				<th data-field="name" data-sortable="false" data-align="center" data-edit="false">执行人</th>' +
            '                    				<th data-field="startTime" data-sortable="false" data-align="center" data-edit="false" data-formatter="$.fn.workflowHistoricView.fmtTime">开始时间</th>' +
            '                    				<th data-field="claimTime" data-sortable="false" data-align="center" data-edit="false" data-formatter="$.fn.workflowHistoricView.fmtTime">签收时间</th>' +
            '                    				<th data-field="endTime" data-sortable="false" data-align="center" data-edit="false" data-formatter="$.fn.workflowHistoricView.fmtTime">完成时间</th>' +
            '                				</tr>' +
            '            				</thead>' +
            '        				</table>' +
            '    				</div>' +
            '				</div>' +
            '			</div>' +
            '           <div class="modal-footer">' +
            '               <button type="button" class="btn default" id="${id}_closeBtn" data-dismiss="modal" data-target="#${id}"><i class="fa fa-remove"></i>&nbsp;关闭</button>' +
            '           </div>' +
            '		</div>' +
            '	</div>' +
            '</div>';
        $(modalHtml).appendTo($(document.body));
        $this.$contentModal = $('#' + $this.contentModalId);
        $this.$img = $('#' + $this.contentModalId + '_img');
        $this.$table = $('#' + $this.contentModalId + '_table');
        $('#' + $this.contentModalId + '_closeBtn').on('click', function() {
            $this.hideModal();
        });
        $this.$el.on('click', function() {
            $this.showModal();
        });
        $this.initTable();
    };

    /**
     * 初始化表格
     */
    WorkflowHistoricView.prototype.initTable = function() {
        var $this = this;
        $this.$table.bootstrapTable({
            method : 'GET',
            url : '',
            pagination : false,				    // 是否分页
            cache : false,
            uniqueId : 'id'
        });
    };

    /**
     * 显示弹窗
     */
    WorkflowHistoricView.prototype.showModal = function() {

        if(!this.options.processInstanceId) {
            toastr.error('未找到流程数据！');
            return false;
        }

        this.loadDiagram();
        this.loadTable();

        this.$contentModal.modal('show');
    };

    /**
     * 加载制流程图
     */
    WorkflowHistoricView.prototype.loadDiagram = function() {
        var $this = this;
        $('#diagramHolder_' + $this.id).attr('src',
            $this.options.baseUrl + 'processInstances/' + $this.options.processInstanceId + '/traceImg');
    };

    /**
     * 向服务器请求网格数据
     */
    WorkflowHistoricView.prototype.loadTable = function() {
        var $this = this;
        $this.$table.bootstrapTable('refresh', {
            url : $this.options.baseUrl + 'processInstances/' + $this.options.processInstanceId + '/historicTasks',
            silent : true
        });
    };

    /**
     * 隐藏弹窗
     */
    WorkflowHistoricView.prototype.hideModal = function() {
		if(this.$contentModal.css('display') !== 'none') {
            this.$contentModal.modal('hide');
		}
	};

    /**
     * 格式化时间
     */
    $.fn.workflowHistoricView.fmtTime = function(value) {
        if(!value) {
            return "";
        }
        var date = new Date(value);
        return date.getFullYear() +
            '-' + fillZero(date.getMonth() + 1) +
            '-' + fillZero(date.getDay()) +
            ' ' + fillZero(date.getHours()) +
            ':' + fillZero(date.getMinutes()) +
            ':' + fillZero(date.getSeconds());
    };

    /**
     * 格式化时间填充0
     */
    var fillZero = function(value) {
        if(!value && value !== 0 && value !== '0') {
            return '';
        }
        if(Number(value) < 10) {
            return '0' + value;
        }
        return value;
    };

})(jQuery);

