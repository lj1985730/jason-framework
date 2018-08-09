!function ($) {

    'use strict';

    var baseUrl = appPath + "/auth/department";
    var $tree, $editWin, $editForm;
    var sOrU;

    $(function() {

        $tree = $('#treePanel');

        /**
         * 初始化tree组件
         */
        var timer;
        $tree.treeSelect({
            entity : 'com.yoogun.auth.domain.model.Department',
            levels : 1, //默认只打开2层
            multiSelect : false,
            onSelect : function() {  //选中节点,显示表单
                clearTimeout(timer);
                showForm(); //显示面板
                loadTreeData(); //加载选中的数据
                sOrU = 1;
                $('#dropdownBtn').prop('disabled', false);
            },
            onUnselect : function() {   //取消选中节点,隐藏表单
                timer = setTimeout(hideForm, 100);
                $('#dropdownBtn').prop('disabled', true);
            }
        });

        /**
         * 按钮初始化
         */
        $('#createDepartment').on('click', function() { create(); });
        $('#deleteDepartment').on('click', function() { remove(); });
        $('#formSubmitBtn').on('click', function() { submit() });

        /**
         * 编辑页初始化
         */
        $editWin = $("#editWin");
        $editForm = $("#editForm");

        /**
         * 下拉框
         */
        $('#category').comboData('COM_DEPT_NATURE', false, null, true);

        $editForm.validation(); //初始化表单验证，需要在各种三方组件之后初始化，以保证监听正确的元素
    });

    /**
     *  显示form
     */
    var showForm = function() {
        $('#form-panel').css('left', 0);    //显示form面板
        var $level = $('#level');
        var parentLevel =$level.val();
        $editForm.form('clear');    //清空form

        $level.val(Number(parentLevel ? parentLevel : 0) + 1);    //将父节点的level+1

        var $parentId = $('#parentId');
        $parentId.val($tree.treeSelect('getSelectedId')[0]);

	};

    /**
     *  隐藏表单
     */
    var hideForm = function() {
        $('#form-panel').css('left', '100%');
    };

    /**
     * 加载数据
     */
    var loadTreeData = function() {
        $.fn.http({
            method : 'GET',
            url : baseUrl + '/' + $tree.treeSelect('getSelectedId'),
            success : function(data) {
                $editForm.form('load', data);  //赋值form
            }
        });
    };

    /**
     * 新增
     */
    var create = function() {
        var selectedIds = $tree.treeSelect('getSelectedId');
        if(selectedIds.length === 0) {
            SysMessage.alertInfo('请选择上级部门！');
            return false;
        }
        hideForm();
        setTimeout(showForm, 300);
        sOrU = 0;
    };

    /**
     * 提交表单
     */
    var submit = function() {

        if(!$editForm.valid()) {    //表单验证
            return false;
        }

        $.fn.http({ //发送请求
            method : sOrU === 0 ? 'POST' : 'PUT',
            url : baseUrl,
            data : JSON.stringify($editForm.serializeJson()),
            success : function(newDataId) {
                //操作成功后需要刷新树
                $tree.treeSelect('load', null, newDataId);
            }
        });

    };

    /**
     * 执行删除动作的操作
     */
    var remove = function() {

        var selectedIds = $tree.treeSelect('getSelectedId');
        if(selectedIds.length === 0 || !selectedIds[0]) {
            SysMessage.alertNoSelection();
            return false;
        }

        bootbox.confirm("确定要删除吗？", function(callback) {
            if (callback) {
                $.fn.http({
                    method : "DELETE",
                    url : baseUrl + '/' + selectedIds[0],
                    success : function() {
                        $tree.treeSelect('load');
                        hideForm();
                    }
                });
            }
        });
    };

}(window.jQuery);