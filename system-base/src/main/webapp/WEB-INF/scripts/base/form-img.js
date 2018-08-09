/**
 * 表单图片插件，可预览待上传图片
 *  本插件不单独保存图片，图片应与表单数据一起提交
 * @author Liu Jun
 * @version 1.0
 * @date 2018-3-16 10:06:42
 */
(function($) {

	'use strict';//开启严格模式
	 
	/**
	 * 调用入口:$('#XXX').formImg({});
     * 赋值文件夹ID：$('#XXX').formImg('setFolderId', folderId);
     * 赋值实例ID：$('#XXX').formImg('setBusinessData', businessKey, businessDataId);
	 */
	$.fn.formImg = function(option, args) {
		var value = null;
		this.each(function () {
			var $this = $(this);
			var object = $this.data('form.img');
			var options = $.extend({}, $.fn.formImg.defaults, $this.data(), typeof option === 'object' && option);

			if (typeof option === 'string') {
				if (!object) {
                	return;
				}
				value = $.fn.formImg.methods[option](this, object.options, args);
				if (option === 'destroy') {
                	$this.removeData('form.img');
				}
			}
			if (!object) {
				$this.data('form.img', (new FormImg($this, options)));
			}
        });
		 
		return typeof value === 'undefined' || value === null ? this : value;
	};
	 
	/**
	 * 定义默认配置信息
	 */
	$.fn.formImg.defaults = {
	    uploadUrl : appPath + '/base/file/',    //上传图片URL
        acceptFileTypes : /([.\/])(gif|jpe?g|png)$/i,    //文件格式限制gif/jpg/jpeg/png
        maxNumberOfFiles : 1,   //最大上传文件数目
        maxFileSize : 1000000,   //文件不超过1M
        emptyImgUrl : 'imgs/nobody.png',    //无文件显示
        searchIdUrl : appPath + '/base/fileIds',    //查询图片ID URL
        loadImgUrl : appPath + '/base/file/%s/inline',  	//加载图片Url
        downloadImgUrl : appPath + '/base/file/%s/attachment',  	//下载图片Url
        prefix : '_form_img_',	    //组件id前缀
        businessKey : '',		    //业务类型Key
        businessDataId : '',		//业务数据ID
        folderId : '',		            //文件夹Id
        width : '100%',             //组件宽
        height : '100%',             //组件高
		readonly : false,			//只读
		onBeforeLoad : function(param) {},  //param 请求参数
		onSuccess : function(data) {},  //data加载成功后返回的数据
		onError : function() {}
	};
	
	/**
	 * 控件方法
	 */
	$.fn.formImg.methods = {
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
		 * 设置业务数据
		 */
		setBusinessData : function(target, options, businessKey, businessDataId) {
		    this.setBusinessKey(target, options, businessKey);
		    this.setBusinessDataId(target, options, businessDataId);
		},
        /**
         * 加载业务数据
         */
        load : function(target, options, businessDataId) {
            this.setBusinessDataId(target, options, businessDataId);
            var $this = $(target).data('form.img');
            $this.load();
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
            var $this = $(target).data('form.img');
            $this.options.folderId = options.folderId;
            $this.options.businessKey = options.businessKey;
            $this.options.businessDataId = options.businessDataId;
        },
        /**
         *  是否只读
         */
		readonly : function(target, options, readonly) {
            var $this = $(target).data('form.img');
            $this.options.readonly = readonly;
            $this.$fileInput.attr("readonly", readonly);
            if(readonly) {
            } else {
            }
        },
        /**
        *  提交数据
        */
        submit : function(target) {
            var $this = $(target).data('form.img');

            if(!$this.options.folderId) {   //参数检查
                if(!$this.options.businessKey || !$this.options.businessDataId) {
                    return false;
                }
            }
            if($this.fileId &&
                ($this.$img.prop('src').indexOf($this.options.emptyImgUrl) > -1
                    || $this.$previewContainer.children('img').length > 0)) {
                //文件Id存在，文件图片为空图，认定为已有文件被删除
                $.fn.http({
                    method : "DELETE",
                    url : appPath + '/base/file/' + $this.fileId,
                    success : function() {
                        $this.fileId = null;
                    }
                });
            }
            if($this.predata) {
                $this.predata.submit();
                $this.load();
            }
        },
        /**
         *  清空数据
         */
        clear : function(target) {
            var $this = $(target).data('form.img');
            $this.empty();
        }
	};

    /**
     * 组件对象
     * @param parent 组件所挂接的html元素
     * @param options   设置项集合
     * @constructor
     */
	var FormImg = function (parent, options) {
        this.options = options;	    //配置项
        this.$parent = parent;	                //组件载体
        this.parentId = this.$parent.attr('id');    //组件载体Id
        this.$el = null;
        this.id = null;
        this.name = 'file';    //name
        this.$fileInput = null;	    //上传文件input
        this.$img = null;	    //图片对象
        this.$imgContainer = null;		//图片容器
        this.$previewContainer = null;		//预览容器
        this.fileId = null;	    //已存储文件ID
        this.predata = null;      //待处理数据
        this.init();
    };

    /**
     * 初始化控件
     */
    FormImg.prototype.init = function() {
        var $this = this;

        $this.id = $this.parentId + '-component'; //组件自身ID

        var contentHtml =
            '<div class="fileinput fileinput-new" id="' + $this.id + '" data-provides="fileinput" style="text-align: center;">' +
            '   <span class="btn default btn-file" style="padding: 0;">' +
            '	    <div id="' + $this.id + '-img-container" class="fileinput-new thumbnail" style="margin-bottom: 0; width: ' + $this.options.width + '; height: ' + $this.options.height + ';">' +
            '		    <img id="' + $this.id + '-img" src="' + $this.options.emptyImgUrl + '" style="height: 100%; width: 100%;" />' +
            '       </div>' +
            // '       <style>' +
            // '           .fileinput .thumbnail > img { width : 100%; height : 100%; }' +
            // '       </style>' +
            '       <div id="' + $this.id + '-preview-container" class="fileinput-preview fileinput-exists thumbnail" style="margin-bottom: 0; width: ' + $this.options.width + '; height: ' + $this.options.height  + ';"></div>' +
            '	    <input type="file" accept="image/gif, image/jpeg, image/png, image/jpg" name="' + $this.name + '" id="' + $this.id + '-fileinput" />' +
            '   </span>' +
            '</div>';
        $(contentHtml).appendTo($this.$parent); //写入组件内容

        $this.$el = $('#' + $this.id);  //组件对象

        $this.$imgContainer = $('#' + $this.id + '-img-container'); //图片容器
        $this.$previewContainer = $('#' + $this.id + '-preview-container'); //预览容器
        $this.$fileInput = $('#' + $this.id + '-fileinput');

        $this.$img = $('#' + $this.id + '-img');

        $this.initContextMenu();
        $this.load();

        if(!$this.options.readonly) {   //如果只读，不初始化文件上传组件
            $this.initFileupload();
            $this.$fileInput.attr("disabled", false);
        } else {
            $this.$fileInput.attr("disabled", true);
        }
    };

    /**
     * 初始化表格
     */
    FormImg.prototype.initFileupload = function() {
        var $this = this;
        $this.$fileInput.fileupload({
            dataType : 'json',
            url : this.options.uploadUrl,
            formData : {
                folderId : $this.options.folderId,
                businessKey : $this.options.businessKey,
                businessDataId : $this.options.businessDataId
            },
            add : function(e, data) {
                var file, files = data.files;
                for(var i = 0; i < files.length; i ++) {
                    file = files[i];
                    if(!$this.options.acceptFileTypes.test(file.name) && !$this.options.acceptFileTypes.test(file.type)) {
                        SysMessage.alertWarning('只能上传gif/jpg/jpeg/png格式的文件');
                        $this.predata = null;  //清空数据
                        return false;
                    }
                }
                $this.predata = data;   //记录增加的图片数据
            },
            done : function (e, data) {
                $this.options.onSuccess.call($this, data);
            },
            fail : function () {	//服务器响应失败处理函数
                $this.options.onError.call($this);
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
     * 初始化右键菜单
     */
    FormImg.prototype.initContextMenu = function() {
        var $this = this;
        var menuHtml =
            '<div id="' + $this.id + '-context-menu">' +
            '   <ul class="dropdown-menu" role="menu">' +
            '       <li>' +
            '           <a href="javascript:;"  id="' + $this.id + '-download-img-menu"><i class="fa fa-download"></i>下载图片</a>' +
            '       </li>';
        if(!$this.options.readonly) {
            menuHtml +=
            '       <li>' +
            '           <a href="javascript:;" id="' + $this.id + '-remove-img-menu"><i class="fa fa-remove"></i>删除图片</a>' +
            '       </li>';
        }
        menuHtml +=
            '   </ul>' +
            '</div>';
        $(menuHtml).appendTo($this.$parent); //写入组件内容

        $this.$parent.contextmenu({
            target: '#' + $this.id + '-context-menu',
            onItem: function (context, e) {}
        });

        $('#' + $this.id + '-download-img-menu').off('click').on('click', $.proxy($this.download, $this));
        $('#' + $this.id + '-remove-img-menu').off('click').on('click', $.proxy($this.empty, $this));
    };

    /**
     * 清空图片
     */
    FormImg.prototype.empty = function() {
        var $this = this;
        if($this.$el.data('bs.fileinput')) {
            $this.$el.data('bs.fileinput').clear();
        }
        $this.predata = null;
        $this.$img.prop('src', $this.options.emptyImgUrl);
    };

    /**
     *  加载图片
     */
    FormImg.prototype.load = function() {
        var $this = this;
        $.fn.http({ //读取头像
            method : 'GET',
            url : $this.options.searchIdUrl,
            data : {
                businessKey : $this.options.businessKey,
                businessId : $this.options.businessDataId
            },
            success : function(data) {
                if(data) {
                    $this.fileId = data[0];
                    $this.$img.prop('src', sprintf($this.options.loadImgUrl, data[0]));
                } else {
                    $this.fileId = null;
                    $this.empty();
                }
            }
        });
    };

    /**
     * 下载文件
     */
    FormImg.prototype.download = function() {
        var $this = this;
        if(!$this.fileId) {
            SysMessage.alertNoSelection();
            return false;
        }
        window.location.href = sprintf($this.options.downloadImgUrl, $this.fileId);
    };

    /**
     * 删除图片
     */
    FormImg.prototype.remove = function() {
        var $this = this;
        if(!$this.fileId) {
            SysMessage.alertNoSelection();
            return false;
        }
        $this.empty();
    };

})(jQuery);

