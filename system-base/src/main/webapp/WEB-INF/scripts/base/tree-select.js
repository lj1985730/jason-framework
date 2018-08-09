/**
 * 树形选择插件
 *  以输入框的形式，选择继承TreeEntity的实体数据
 * @author Liu Jun at 2018-1-12 14:18:49
 * @version 1.0
 */
(function($){

	'use strict';   //开启严格模式
	 
	/**
	 * 调用入口:$('#XXX').treeSelect({    --XXX必须为input标签
	 *  entity : BaseEntity   --实体类名称（必须）
	 *  multiSelect : Boolean   --是否多选（默认false）
	 *  rootId :  根节点ID       --默认最根节点
	 * });
     * 加载树：$('#XXX').treeSelect( 'load', entity[, rootId[, defaultSelectId]] );
     */
	$.fn.treeSelect = function(option) {
		var value = null;
		var args = Array.prototype.slice.call(arguments, 1);
		this.each(function () {
			var $this = $(this);
			var data = $this.data('tree.select');
			var options = $.extend({}, $.fn.treeSelect.defaults, $this.data(), typeof option === 'object' && option);

			if (typeof option === 'string') {
				if (!data) {
                	return;
				}
				value = $.fn.treeSelect.methods[option](this, data.options, args);

				if (option === 'destroy') {
                	$this.removeData('tree.select');
				}
			}
			if (!data) {
				$this.data('tree.select', (new TreeSelect($this, options)));
			}
        });

		return typeof value === 'undefined' || value === null ? this : value;
	};

	/**
	 * 定义默认配置信息
	 */
	$.fn.treeSelect.defaults = {
        url : appPath + '/base/treeDatas',  	//数据搜索Url
        label : '选择数据',		//标题
        prefix : '_tree_select_',	    //组件id前缀
        entity : '',		    //实体名称
        rootId : '',		//根节点ID
        multiSelect : false,
        onSelect : function() {},	//  param 请求参数
        onUnselect : function() {},  	//  data加载成功后返回的数据
		onBeforeLoad : function() {},	//  param 请求参数
		onLoadSuccess : function(data) {},  	//  data加载成功后返回的数据
		onLoadError : function(errorThrown) {}
	};

	/**
	 * 控件方法属性
	 */
	$.fn.treeSelect.methods = {
        /**
         * 获取选中数据ID
         */
        getSelectedId : function(target) {
            var $this = $(target).data('tree.select');
            if(!$this.$el.data('treeview')) {
                return [];
            }
            var selected = $this.$el.treeview('getSelected');
            if(!selected || selected.length === 0) {
                return [];
            }
            var ids = [];
            for(var i = 0; i < selected.length; i ++) {
                ids.push(selected[i].id);
            }
            return ids;
        },
        /**
         * 获取选中数据文本
         */
        getSelectedText : function(target) {
            var $this = $(target).data('tree.select');
            if(!$this.$el.data('treeview')) {
                return [];
            }
            var selected = $this.$el.treeview('getSelected');
            if(!selected || selected === 0) {
                return null;
            }
            var texts = [];
            for(var i = 0; i < selected.length; i ++) {
                texts.push(selected[i].text);
            }
            return texts;
        },
        /**
         * 获取选中数据
         */
        getData : function(target) {
            var $this = $(target).data('tree.select');
            if(!$this.$el.data('treeview')) {
                return [];
            }
            return $this.$el.treeview('getSelected');
        },
        /**
         * 加载业务数据
         * @param target    当前数据对象
         * @param options    当前配置对象
         * @param args  数据数组[rootId(加载的根节点), defaultSelectId(默认选中的数据)]
         */
        load : function(target, options, args) {
            var rootId = args[0];
            if(typeof(rootId) === 'undefined' || !rootId) {
                options.rootId = '';
            } else {
                options.rootId = rootId;
            }

            var defaultSelectId = '';
            if(args.length > 1) {
                defaultSelectId = args[1];
            }

            var $this = $(target).data('tree.select');
            $this.loadTree(defaultSelectId);
        }
	};

    /**
     * 组件对象
     * @param el 组件所挂接的html元素
     * @param options   设置项集合
     * @constructor
     */
	var TreeSelect = function (el, options) {
        this.options = options;	    //配置项
        this.$el = el;	                //组件载体
        this.id = el.attr('id');    //ID
        this.contentId = null;  //组件ID
        this.$tree = null;
        this.loadTree();
    };

    /**
     * 组装treeview的配置项对象
     */
    TreeSelect.prototype.treeOptions = function(treeData) {
        var $this = this;
        return {
            data : treeData,
            showTags : false,
            levels : (typeof($this.options.levels) !== 'undefined' ) ? $this.options.levels : 2,
            selectedBackColor : "#26a69a",
            selectedColor : "#ffffff",
            multiSelect : $this.options.multiSelect,
            showCheckbox : $this.options.multiSelect,
            selectedIcon : "fa fa-check"
        };
    };

    /**
     * 向服务器请求树状数据并加载
     */
    TreeSelect.prototype.loadTree = function(defaultSelectId) {
        var $this = this;
        $.ajax({
            type : "GET",
            url : $this.options.url + '?entityName=' + $this.options.entity + '&rootId=' + $this.options.rootId,
            dataType : "json",
            contentType : "application/json",
            beforeSend : function() {
                SysBlock.block(); //loading效果
                $this.options.onBeforeLoad.call($this);
            },
            success : function (result, textStatus) {
                switch (textStatus) {
                    case "timeout" :
                        SysMessage.alertError('请求超时！请稍后再次尝试。');
                        break;
                    case "error" :
                        SysMessage.alertError('请求错误！请稍后再次尝试。');
                        break;
                    case "success" :
                        if (result.success) {
                            $this.$el.treeview($this.treeOptions(result.data));   //新创建树组件
                            $this.$el.treeview('expandAll', { levels: $this.options.levels, silent: true }); //展开节点
                            //初始化成功后，绑定事件
                            $this.$el.on("nodeSelected", function () {  //选中节点
                                //增加下级节点，增加同级节点暂时没定义
                                $this.options.onSelect.call($this);
                            }).on("nodeUnselected", function () {   //取消选中节点
                                $this.options.onUnselect.call($this);
                            });

                            $this.options.onLoadSuccess.call($this, result.data);

                            //默认选中新增的数据
                            if(defaultSelectId) {
                                // todo 选中一个节点
                                // var node = $this.$el.treeview('findNode', $('[data-businessid="' + defaultSelectId + '"]'));
                                // if(node) {
                                //     $this.$el.treeview('selectNode', node, {silent: true});
                                // }
                            }

                        } else {
                            SysMessage.alertError(result.message);
                        }
                        break;
                    default:
                        SysMessage.alertError('未知错误，请尝试刷新页面或者重新登录！');
                }
                SysBlock.unblock();
            },
            error : function (XMLHttpRequest, textStatus, errorThrown) {
                SysMessage.alertError("加载树出错！");
                SysBlock.unblock();
                $this.options.onLoadError.call($this, errorThrown);
            }
        });
    };

})(jQuery);

