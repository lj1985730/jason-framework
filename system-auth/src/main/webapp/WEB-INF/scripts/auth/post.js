!function ($) {

    'use strict';

	var baseUrl = appPath + "/auth/post";
	var $tree, $subordinateTree;
    var $editWin, $editForm, $infoForm;

	$(function() {

        /* 初始化左侧部门岗位树 */
        initTree();

        $infoForm = $('#postInfoForm');

        /* 初始化管辖岗位树 */
        initSubordinateTree();

        /**
         * 按钮初始化
         */
        $('#createPost').off('click').on('click', function() { showEditWin(0); }).addClass('hidden');
        $('#updatePost').off('click').on('click', function() { showEditWin(1); }).addClass('hidden');
        $('#deletePost').off('click').on('click', function() { remove(); }).addClass('hidden');

        /**
		 * 编辑页初始化
         */
        $editWin = $("#editWin");
        $editForm = $("#editForm");
        $('#editWinSubmitBtn').off('click').on('click', function() { submit(); });

        /* 绑定管辖岗位按钮 */
        $('#bindSubordinates').off('click').on('click', function() { submitSubordinate(); }).prop('disabled', true);

        $editForm.validation(); //初始化表单验证，需要在各种三方组件之后初始化，以保证监听正确的元素
	});

    /**
     *  初始化左侧部门岗位树
     */
    var initTree = function() {

        $tree = $('#departmentPostTree');

        var options = { //通用配置
            levels : 1,
            showTags : true
        };

        $.fn.http({
            method : 'GET',
            url :  baseUrl,
            success : function(data) {
                $tree.treeview($.extend(options, { data : data }));   //新创建树组件
                $tree.on('nodeSelected', function(e, node) {
                    if(node.hasOwnProperty('type') && node['type'] === 2) { //选择了岗位节点
                        postNodeOnSelected(node);
                    } else {
                        departmentNodeOnSelected();
                    }
                });
                $tree.on('nodeUnselected', function() {
                    onNodeUnselected();
                });
            }
        });
    };

    /**
     *  选中部门节点触发方法
     */
    var departmentNodeOnSelected = function() {
        $('#createPost').removeClass('hidden');   //启用新增按钮
        $('#updatePost').addClass('hidden');   //禁用修改按钮
        $('#deletePost').addClass('hidden');   //禁用删除按钮
        $('#dropdownBtn').prop('disabled', false);
        $('#bindSubordinates').prop('disabled', true);   //禁用保存管辖岗位按钮
        clearInfoForm();    //清空信息面板
        clearSubordinatePost();  //清空管辖岗位
    };

    /**
     *  选中岗位节点触发方法
     * @param node 选中的节点
     */
    var postNodeOnSelected = function(node) {
        $('#createPost').addClass('hidden');    //禁用新增按钮
        $('#updatePost').removeClass('hidden');   //启用修改按钮
        $('#deletePost').removeClass('hidden');   //启用删除按钮
        $('#dropdownBtn').prop('disabled', false);
        $('#bindSubordinates').prop('disabled', false);   //启用保存管辖岗位按钮
        loadInfoForm(node.data);    //赋值信息面板
        loadSubordinatePost(node.id);   //赋值管辖岗位
    };

    /**
     *  取消选中节点触发方法
     */
    var onNodeUnselected = function() {
        $('#createPost').addClass('hidden');   //禁用新增按钮
        $('#updatePost').addClass('hidden');   //禁用修改按钮
        $('#deletePost').addClass('hidden');   //禁用删除按钮
        $('#dropdownBtn').prop('disabled', true);
        $('#bindSubordinates').prop('disabled', true);   //禁用保存管辖岗位按钮
        clearInfoForm();    //清空信息面板
        clearSubordinatePost();  //清空管辖岗位
    };

    /**
     *  初始化管辖岗位树
     */
    var initSubordinateTree = function() {

        $subordinateTree = $('#subordinatePostTree');

        var options = { //通用配置
            levels : 2,
            showIcon : false,
            showCheckbox : true,
            hierarchicalCheck : true,
            propagateCheckEvent : true,
            checkedIcon : 'icheckbox_flat-green checked',
            uncheckedIcon : 'icheckbox_flat-green unchecked',
            partiallyCheckedIcon : 'iradio_flat-green checked',
            onNodeChecked : function(event, node) {
                node.$el.find('.post-subordinate-auth').iCheck('check'); // check岗位时，默认check全部权限
            },
            onNodeUnchecked : function(event, node) {
                node.$el.find('.post-subordinate-auth').iCheck('uncheck');   // uncheck岗位时，默认uncheck全部权限
            }
        };

        $.fn.http({
            method : 'GET',
            url :  baseUrl + '/subordinateTree',
            success : function(data) {
                $subordinateTree.treeview($.extend(options, { data : data }));   //新创建树组件
                $subordinateTree.treeview('expandAll', { levels: 2, silent: true }); //展开二级节点
                var authCheckboxes = $('.post-subordinate-auth'); //初始化icheck组件
                authCheckboxes.iCheck({
                    cursor : true,
                    checkboxClass : 'icheckbox_flat-green'
                });

                //监听权限的check事件，勾选权限时，自动勾选对应岗位
                authCheckboxes.off('ifChecked').on('ifChecked', function() {
                    var postId = $(this).closest('.icheck-inline').parent().parent().prop('id');
                    if(postId) {
                        var postNode = $subordinateTree.treeview('findNodes', [ postId, "id" ]);
                        if(postNode && !postNode[0].state.checked) {
                            $subordinateTree.treeview('toggleNodeChecked', [ postNode, {silent: true} ]);  //使用toggleNodeChecked触发级联勾选
                        }
                    }
                });
            }
        });
    };

    /**
     * 清空信息面板
     */
    var clearInfoForm = function() {
        $infoForm.form('clear');
    };

    /**
     * 加载信息面板
     * @param post 岗位
     */
    var loadInfoForm = function(post) {
        if(!post) {
            return false;
        }

        $infoForm.form('load', post);

        $('#postInfo_department').text($.fn.isNull(post.department.name) ? '' : post.department.name);
        $('#postInfo_name').text($.fn.isNull(post.name) ? '' : post.name);
        $('#postInfo_code').text($.fn.isNull(post.code) ? '' : post.code);
        $('#postInfo_isLeader').text($.fn.isNull(post.isLeader) ? '' : (post.isLeader ? '是' : '否'));
        $('#postInfo_remark').text($.fn.isNull(post.remark) ? '' : post.remark);
    };

    /**
     * 显示编辑窗口
     * @param saveOrUpdate	0 新增；1 修改
     */
    var sOrU;
    var showEditWin = function(saveOrUpdate) {
        sOrU = saveOrUpdate;
        try {
            $editForm.form('clear');
            var selectedNodes = $tree.treeview('getSelected');
            if (!selectedNodes || selectedNodes.length === 0) {
                SysMessage.alertNoSelection();
                return false;
            }
            var selectedNode = selectedNodes[0];
            if(sOrU === 1) {
                if(selectedNode.type !== 2) {
                    SysMessage.alertWarning('请选择岗位节点！');
                    return false;
                }
                $editForm.form('load', selectedNode.data);
            } else if(sOrU === 0) {
                if(selectedNode.type !== 1) {
                    SysMessage.alertWarning('请选择部门节点新增岗位！');
                    return false;
                }
                // 赋值部门
                $('#departmentId').val(selectedNode.id);
                $('#departmentName').text(selectedNode.text);
            }
            $editWin.modal("show");
        } catch(e) {
            SysMessage.alertError(e.message);
        }
    };

    /**
     * 提交表单
     */
	var submit = function() {

	    if(!$editForm.valid()) {    //表单验证
	        return false;
        }

        var method = (sOrU === 0 ? "POST" : "PUT");
        $.fn.http({
            method : method,
            url : baseUrl,
            data : JSON.stringify($editForm.serializeJson()),
            success : function() {
                $editWin.modal("hide");
                clearInfoForm();
                initTree();
                initSubordinateTree();
            }
        });
    };

	/**
	 * 执行删除动作的操作
	 */
	var remove = function() {
        try {
            var selectedNodes = $tree.treeview('getSelected');
            if (!selectedNodes || selectedNodes.length === 0) {
                SysMessage.alertNoSelection();
                return false;
            }
            bootbox.confirm("确定要删除吗？", function(callback) {
                if (callback) {
                    $.fn.http({
                        method : "DELETE",
                        url : baseUrl + '/' + selectedNodes[0].id,
                        success : function() {
                            clearInfoForm();
                            initTree();
                            initSubordinateTree();
                        }
                    });
                }
            });
        } catch (e) {
            SysMessage.alertError(e.message);
        }
	};

    /**
     *  加载岗位绑定的管辖岗位
     *  @param postId 岗位ID
     */
    var loadSubordinatePost = function(postId) {

        clearSubordinatePost();

        $.fn.http({
            method : 'GET',
            url :  baseUrl + '/' + postId + '/subordinates',
            success : function(datas) {
                if(datas) {
                    datas.forEach(function(data) {
                        var node = $subordinateTree.treeview('findNodes', [ data.subordinateId, "id" ]);
                        $subordinateTree.treeview('toggleNodeChecked', [ node, {silent: true} ]);  //使用toggleNodeChecked触发级联勾选
                        if(data.dataPermission) {   //数据权限
                            $('#' + data.subordinateId + '-data-permission').iCheck('check');
                        }
                        if(data.auditPermission) {  //审批权限
                            $('#' + data.subordinateId + '-audit-permission').iCheck('check');
                        }
                    });
                }
            }
        });
    };

    /**
     * 清空全部选中的下属岗位
     */
    var clearSubordinatePost = function() {
        $subordinateTree.treeview('uncheckAll');   //uncheck菜单
        $('.post-subordinate-auth').iCheck('uncheck');   //uncheck按钮
    };

    /**
     * 提交表单
     */
    var submitSubordinate = function() {

        var selectedPosts = $tree.treeview('getSelected');
        if(!selectedPosts || selectedPosts.length !== 1) {
            SysMessage.alertInfo('请选择要操作的岗位！');
            return false;
        }

        var selectedPost = selectedPosts[0];
        if(selectedPost.type !== 2) {
            SysMessage.alertInfo('只能为岗位设置管辖岗位！');
            return false;
        }

        var superiorId = selectedPost.id;   //上级岗位ID

        var postRlats = [];   //管辖岗位关系集合
        var postRlat;
        var $selectedDataAuth;   //被check的数据权限
        var $selectedAuditAuth;   //被check的审批权限
        var selectedDatas = $subordinateTree.treeview('getChecked');
        var selectedData, $selectedCheckContainer;
        for(var i = 0; i < selectedDatas.length; i++) {
            selectedData = selectedDatas[i];
            if(selectedData.type === 2) {    //只处理岗位节点
                postRlat = {};   //组合成关系对象结构
                postRlat.superiorId = superiorId;   //上级
                postRlat.subordinateId = selectedData.id;    //下级

                //检索权限check区域勾选情况
                $selectedCheckContainer = $('#' + selectedData.id, '#postTab').find('.input-group').find('.icheck-inline');
                $selectedDataAuth = $selectedCheckContainer.find('.checked').find('.data-permission');    //从checked的菜单中检索按钮
                $selectedAuditAuth = $selectedCheckContainer.find('.checked').find('.audit-permission');    //从checked的菜单中检索按钮

                postRlat.dataPermission = $selectedDataAuth.length > 0; //数据权限
                postRlat.auditPermission = $selectedAuditAuth.length > 0; //审批权限

                postRlats.push(postRlat);
            }
        }

        $.fn.http({ //发送请求
            method : 'PUT',
            url : baseUrl + '/' + superiorId + '/subordinates',
            data : JSON.stringify({
                postRlats : postRlats
            })
        });

    };

}(window.jQuery);
