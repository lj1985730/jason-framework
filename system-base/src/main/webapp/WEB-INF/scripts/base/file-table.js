/**
 * 文件上传插件
 * @author Liu Jun
 * @version 1.0
 * @date 2017-12-21 10:06:32
 */
(function($){

	'use strict';//开启严格模式
	 
	/**
	 * 调用入口:$('#XXX').fileTable({});
     * 赋值文件夹ID：$('#XXX').fileTable('setFolderId', folderId);
     * 赋值实例ID：$('#XXX').fileTable('setBusinessData', businessKey, businessDataId);
	 */
	$.fn.fileTable = function(option, args) {
		var value = null;
		this.each(function () {
			var $this = $(this);
			var data = $this.data('file.table');
			var options = $.extend({}, $.fn.fileTable.defaults, $this.data(), typeof option === 'object' && option);

			if (typeof option === 'string') {
				if (!data) {
                	return;
				}
				value = $.fn.fileTable.methods[option](this, data.options, args);
				if (option === 'destroy') {
                	$this.removeData('file.table');
				}
			}
			if (!data) {
				$this.data('file.table', (new FileTable($this, options)));
			}
        });
		 
		return typeof value === 'undefined' || value === null ? this : value;
	};
	 
	/**
	 * 定义默认配置信息
	 */
	$.fn.fileTable.defaults = {
        url : appPath + '/base/files',  	//数据搜索Url
        label : '附件列表',		//标题
        prefix : '_file_table_',	    //组件id前缀
        businessKey : '',		    //业务类型Key
        businessDataId : '',		//业务数据ID
        folderId : '',		            //文件夹Id
		readonly : false,			//只读
		preview : false,			//预览
		cls : '',					//自定义样式,多个样式用逗号隔开 class1,class2
		onBeforeLoad : function(param) {},	//param 请求参数
		onLoadSuccess : function(data) {},	//data加载成功后返回的数据
		onLoadError : function() {}
	};
	
	/**
	 * 控件方法属性
	 */
	$.fn.fileTable.methods = {
        /**
         * 设置业务键
         */
        setBusinessKey : function(target, options, businessKey) {
            if(typeof(businessKey) === 'undefined' || !businessKey) {
                options.businessKey = '';
            } else {
                options.businessKey = businessKey;
            }
            this.updateFormData(target, options);   //更新上传表单的业务数据
        },
        /**
         * 设置业务数据ID
         */
        setBusinessDataId : function(target, options, businessDataId) {
            if(typeof(businessDataId) === 'undefined' || !businessDataId) {
                options.businessDataId = '';
            } else {
                options.businessDataId = businessDataId;
            }
            this.updateFormData(target, options);   //更新上传表单的业务数据
        },
        /**
         * 加载业务数据
         */
        load : function(target, options, businessDataId) {
            this.setBusinessDataId(target, options, businessDataId);
            var $this = $(target).data('file.table');
            $this.load();
        },
		/**
		 * 设置业务数据
		 */
		setBusinessData : function(target, options, businessKey, businessDataId) {
		    this.setBusinessKey(target, options, businessKey);
		    this.setBusinessDataId(target, options, businessDataId);
		},
        /**
         * 设置文件夹ID
         */
        setFolderId : function(target, options, folderId) {
            if(typeof(folderId) === 'undefined' || !folderId) {
                options.folderId = '';
            } else {
                options.folderId = folderId;
            }
            this.updateFormData(target, options);   //更新上传表单的业务数据
        },
        /**
         * 更新上传表单的业务数据
         */
        updateFormData : function(target, options) {
            var $this = $(target).data('file.table');
            $this.options.folderId = options.folderId;
            $this.options.businessKey = options.businessKey;
            $this.options.businessDataId = options.businessDataId;
        },
        /**
         *  是否只读
         */
		readonly : function(target, options, readonly) {
            var $this = $(target).data('file.table');
            $this.options.readonly = readonly;
            $this.$fileInput.attr("readonly", readonly);
            $this.$uploadBtn.attr("readonly", readonly);
            if(readonly) {
                $this.$toolbar.hide();
                $this.$btnGroup.hide();
            } else {
                $this.$toolbar.show();
                $this.$btnGroup.show();
            }
        },
        /**
         *  清空数据
         */
        clear : function(target) {
            var $this = $(target).data('file.table');
            $this.$table.bootstrapTable('removeAll');
        }
	};

    /**
     * 组件对象
     * @param el 组件所挂接的html元素
     * @param options   设置项集合
     * @constructor
     */
	var FileTable = function (el, options) {
        this.options = options;	    //配置项
        this.$el = el;	                //组件载体
        this.id = el.attr('id');    //ID
        this.contentId = null;  //组件ID
        this.$toolbar = null;			//操作面板
        this.$btnGroup = null;			//按钮组
        this.$table = null;			//数据网格
        this.$fileInput = null;	    //上传文件
        this.$fileViewer = null;	    //上传文件
        this.$uploadBtn = null;		//上传按钮
        this.$downloadBtn = null;		//下载按钮
        this.$previewBtn = null;		//预览按钮
        this.$removeBtn = null;		//删除按钮
        this.init();
    };
    
    //初始化控件
    FileTable.prototype.init = function() {
        var $this = this;
        // var index = $('div[id^="' + this.options.prefix + '"]').length + 1;
        // $this.contentId = $this.options.prefix + index;
        // var contentHtml =
        //     '<div class="portlet box blue-dark">' +
        //     '	<div class="portlet-title">' +
        //     '		<div class="caption">' +
        //     '        	<i class="fa fa-file-o"></i>' + this.options.label +
        //     '		</div>' +
        //     '       <div class="actions" id="' + $this.id + '_btn_group" >' +
        //     '           <button type="button" class="btn btn-warning" id="' + $this.id + '_preview_btn"><i class="fa fa-eye"></i>&nbsp;预览</button>' +
        //     '           <button type="button" class="btn btn-success" id="' + $this.id + '_download_btn"><i class="fa fa-download"></i>&nbsp;下载</button>' +
        //     '           <button type="button" class="btn btn-danger" id="' + $this.id + '_remove_btn"><i class="fa fa-trash-o"></i>&nbsp;删除</button>' +
        //     '	    </div>' +
        //     '	</div>' +
        //     '   <div class="portlet-body">' +
        //     '       <div id="' + $this.id + '_table_toolbar">' +
        //     '           <div class="row">' +
        //     '               <div class="col-xl-4 col-lg-5 col-md-6 col-sm-9 col-xs-9">' +
        //     '                   <input type="file" id="' + $this.id + '_file_input" name="file" class="btn btn-default" />' +
        //     '               </div>' +
        //     '               <div class="col-xl-8 col-lg-7 col-md-6 col-sm-3 col-xs-3">' +
        //     '                   <a type="button" id="' + $this.id + '_upload_btn" class="btn btn-success">' +
        //     '                       <i class="glyphicon glyphicon-cloud-upload"></i>&nbsp;上传' +
        //     '                   </a>' +
        //     '               </div>' +
        //     '           </div>' +
        //     '       </div>' +
        //     '       <div class="table-container">' +
        //     '           <table id="' + $this.id + '_table" data-show-refresh="false"' +
        //     '               			data-show-toggle="false" data-show-columns="false"' +
        //     '                       data-toolbar="#' + $this.id + '_table_toolbar">' +
        //     '               <thead>' +
        //     '               <tr role="row" class="heading">' +
        //     '                   <th data-field="checkbox" data-checkbox="true" data-align="center" data-edit="false"></th>' +
        //     '                   <th data-field="fileId" data-align="center" data-edit="false" data-formatter="indexFormatter"></th>' +
        //     '                   <th data-field="fileName" data-sortable="true" data-align="center" data-edit="false">文件名称</th>' +
        //     '                   <th data-field="size" data-sortable="true" data-align="center" data-edit="false">文件大小</th>' +
        //     '                   <th data-field="uploadTime" data-sortable="true" data-align="center" data-edit="false">上传时间</th>' +
        //     '               </tr>' +
        //     '               </thead>' +
        //     '           </table>' +
        //     '		</div>' +
        //     '	</div>' +
        //     '</div>';
        // $(contentHtml).appendTo($this.$el);
        $this.$btnGroup = $('#' + $this.id + '_btn_group');
        $this.$toolbar = $('#' + $this.id + '_table_toolbar');

        $this.$table = $('#' + $this.id + '_table');
        $this.$fileInput = $('#' + $this.id + '_file_input');
        $this.$fileViewer = $('#' + $this.id + '_file_viewer');
        $this.$uploadBtn = $('#' + $this.id + '_upload_btn');
        $this.$uploadBtn.off('click').on('click', $.proxy(this.upload, this));
        $this.$downloadBtn = $('#' + $this.id + '_download_btn');
        $this.$downloadBtn.off('click').on('click', $.proxy(this.download, this));
        $this.$previewBtn = $('#' + $this.id + '_preview_btn');
        $this.$previewBtn.off('click').on('click', $.proxy(this.preview, this));
        $this.$removeBtn = $('#' + $this.id + '_remove_btn');
        $this.$removeBtn.off('click').on('click', $.proxy(this.remove, this));

        if($this.options.readonly) {
            $this.$toolbar.hide();
            // $this.$btnGroup.hide();
            $this.$removeBtn.hide();
        } else {
            $this.$toolbar.show();
            // $this.$btnGroup.show();
            $this.$removeBtn.show();
        }

        $this.initTable();
        $this.initFileupload();
    };

    /**
     * 初始化表格
     */
    FileTable.prototype.initTable = function() {
        var $this = this;
        $this.$table.bootstrapTable($.extend(
            { 
                url : $this.options.url, 
                formatSearch : function () { return '文件名称'; },
                onCheck : function (row) {
                    if (row){
                       var ext = row.extension;
                       if (!(ext === 'jpeg' || ext === 'jpg' || ext === 'pdf')){
                           $this.$previewBtn.css('display','none');
                       } else {
                           $this.$previewBtn.css('display','inline-block');
                       }
                    }
                }
            },
            $.extend({}, generalTableOption, { queryParams : $.proxy($this.tableSearchParams, $this) })
        ));
    };

    /**
     * 初始化表格
     */
    FileTable.prototype.initFileupload = function() {
        var $this = this;
        $this.$fileInput.fileupload({
            dataType : 'json',
            url : appPath + '/base/file/',
            formData : {
                folderId : $this.options.folderId,
                businessKey : $this.options.businessKey,
                businessDataId : $this.options.businessDataId
            },
            add : function(e, data) {
                $this.$fileViewer.val(data.files[0].name);
                $this.$uploadBtn.off('click').on('click', function () {
                    data.submit();
                });
            },
            done : function () {
                SysMessage.alertSuccess('上传成功！');
                $this.$table.bootstrapTable('refresh');
                $this.$fileInput.after($this.$fileInput.clone().val(""));
                $this.$fileInput.remove();
                $this.$fileViewer.val("");
            },
            fail : function () {	//服务器响应失败处理函数
                SysMessage.alertError('上传失败！');
                $this.$table.bootstrapTable('refresh');
                $this.$fileInput.after($this.$fileInput.clone().val(""));
                $this.$fileInput.remove();
                $this.$fileViewer.val("");
            }
        });

        //文件上传前触发事件
        $this.$fileInput.bind('fileuploadsubmit', function (e, data) {
            data.formData = {
                folderId : $this.options.folderId,
                businessKey : $this.options.businessKey,
                businessDataId : $this.options.businessDataId
            };
        });
    };

    /**
     * 查询条件
     */
    FileTable.prototype.tableSearchParams = function(params) {
        var $this = this;
        if(!$this.options.folderId) {
            if(!$this.options.businessKey || !$this.options.businessDataId) {
                return false;
            }
        }
        var localParams = {
            folderId : $this.options.folderId,
            businessKey : $this.options.businessKey,
            businessDataId : $this.options.businessDataId
        };
        return $.extend(localParams, params);
    };

    /**
     * 向服务器请求网格数据
     */
    FileTable.prototype.load = function() {
        var $this = this;
        if(!$this.options.folderId) {
            if(!$this.options.businessKey || !$this.options.businessDataId) {
                SysMessage.alertWarning('未找到文件挂接的业务信息！');
                return false;
            }
        }
        $this.$table.bootstrapTable('refresh', { silent : true });
    };

    /**
     * 下载文件
     */
    FileTable.prototype.download = function() {
        var row = this.$table.bootstrapTable('getSelections')[0];
        if(!row) {
            SysMessage.alertNoSelection();
            return false;
        }
        window.location.href = appPath + '/base/file/' + row.id + '/attachment';
    };

    /**
     * 删除文件
     */
    FileTable.prototype.remove = function () {
        var $this = this;
        var row = $this.$table.bootstrapTable('getSelections')[0];
        if(!row) {
            SysMessage.alertNoSelection();
            return false;
        }
        $.ajax({
            method : 'DELETE',
            url : appPath + '/base/file/' + row.id,
            dataType: "json",
            contentType: "application/json",
            success : function (result) {
                SysMessage.alertSuccess(result.message);
                $this.$table.bootstrapTable('refresh', { silent : true });
            }
        });
    };

    /**
     * 文件预览
     */
    FileTable.prototype.preview = function () {
        var $this = this;
        var row = $this.$table.bootstrapTable('getSelections')[0];
        if(!row) {
            SysMessage.alertNoSelection();
            return false;
        }
        var url = appPath + '/base/file/' + row.id + '/inline';
        window.open(url);
    };

    /**
     * 格式化时间
     */
    $.fn.fileTable.fmtTime = function(value) {
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

