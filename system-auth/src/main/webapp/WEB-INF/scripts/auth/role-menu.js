!function ($) {

    'use strict';

    var baseUrl = appPath + "/auth/role";
    var $roleTable, $tree;

    $(function() {

        $roleTable = $('#roleTable');

        $tree = $('#treePanel');

        initTree();

        /* 点击角色列表刷新已绑定的角色 */
        $roleTable.on('check.bs.table', function (event, row) {
            loadRoleMenuAndButton(row.id);
        });

        /* 取消点击账户列表清除已绑定的角色 */
        $roleTable.on('uncheck.bs.table', function () {
            uncheckAllMenuAndButton();
        });

        /**
         * 按钮初始化
         */
        /* 绑定菜单按钮 */
        $('#bindMenu').on('click', function() { submit(); });
    });

    /**
     *  初始化菜单树
     */
    var initTree = function() {

        var options = { //通用配置
            levels : 2,
            hierarchicalCheck : true,
            propagateCheckEvent : true,
            multiSelect : true,
            showCheckbox : true,
            checkedIcon : 'icheckbox_flat-green checked',
            uncheckedIcon : 'icheckbox_flat-green unchecked',
            partiallyCheckedIcon : 'iradio_flat-green checked',
            onNodeChecked : function(event, node) {
                node.$el.find('.menu-btn').iCheck('check'); // check菜单时，默认check全部按钮
            },
            onNodeUnchecked : function(event, node) {
                // uncheck菜单时，默认uncheck全部按钮
                node.$el.find('.menu-btn').iCheck('uncheck');
            }
        };

        $.fn.http({
            method : 'GET',
            url :  baseUrl + '/menusAndButtons',
            success : function(data) {
                $tree.treeview($.extend(options, { data : data }));   //新创建树组件
                $tree.treeview('expandAll', { levels: 1, silent: true }); //展开一级节点

                $('.menu-btn').iCheck({ //初始化icheck组件
                    cursor : true,
                    checkboxClass : 'icheckbox_flat-green'
                });

                $('.menu-btn').on('ifChecked', function(event) {
                    var menuId = $(this).closest('.icheck-inline').parent().parent().prop('id');
                    if(menuId) {
                        var menuNode = $tree.treeview('findNodes', [ menuId, "id" ]);
                        if(menuNode && !menuNode[0].state.checked) {
                            $tree.treeview('toggleNodeChecked', [ menuNode, {silent: true} ]);  //使用toggleNodeChecked触发级联勾选
                        }
                    }
                });
            }
        });
    };

    /**
     *  加载角色绑定的菜单和按钮
     */
    var loadRoleMenuAndButton = function(roleId) {

        uncheckAllMenuAndButton();

        $.fn.http({
            method : 'GET',
            url :  baseUrl + '/' + roleId + '/menusAndButtons',
            success : function(data) {
                var menus = data.menus;
                if(menus) { //勾选菜单
                    menus.forEach(function(menuId) {
                        var node = $tree.treeview('findNodes', [ menuId, "id" ]);
                        $tree.treeview('toggleNodeChecked', [ node, {silent: true} ]);  //使用toggleNodeChecked触发级联勾选
                    });
                }

                var buttons = data.buttons;
                if(buttons) { //勾选按钮
                    buttons.forEach(function (buttonId) {
                        $('#' + buttonId).iCheck('check');
                    });
                }
            }
        });
    };

    /**
     * 清空全部选中的菜单和按钮
     */
    var uncheckAllMenuAndButton = function() {
        $tree.treeview('uncheckAll');   //uncheck菜单
        $('.menu-btn').iCheck('uncheck');   //uncheck按钮
    };

    /**
     * 提交表单
     */
    var submit = function() {

        var selectedRole = $roleTable.bootstrapTable('getSelections')[0];
        if(!selectedRole) {
            SysMessage.alertInfo('请选择要操作的角色！');
            return false;
        }

        var selectedMenuIds = [];   //选中的菜单ID数组
        var selectedButtonIds = [];   //选中的按钮ID数组
        var selectedButtons;   //被check的按钮集合
        var selectedMenus = $('#treePanel').find('.list-group').find('.node-checked,.node-checked-partial');  //被check或者partial check的菜单节点集合
        selectedMenus.each(function() { //循环保存菜单ID
            selectedMenuIds.push($(this).prop('id'));
            selectedButtons = $(this).find('.icheck-inline').find('.checked').find('.menu-btn');    //从checked的菜单中检索按钮
            selectedButtons.each(function() {    //循环保存按钮ID
                selectedButtonIds.push($(this).prop('id'));
            });
        });

        $.fn.http({ //发送请求
            method : 'PUT',
            url : baseUrl + '/' + selectedRole.id + '/menusAndButtons',
            data : JSON.stringify({
                menuIds : selectedMenuIds,
                buttonIds : selectedButtonIds
            }),
            success : function(newDataId) {
            }
        });

    };

}(window.jQuery);