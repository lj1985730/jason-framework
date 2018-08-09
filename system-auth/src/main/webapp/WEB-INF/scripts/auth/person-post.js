!function ($) {

    'use strict';

    var baseUrl = appPath + "/auth/person";
    var postUrl = appPath + "/auth/post";
    var $tree;
    var timer;
    var selectedPersonId;

    $(function() {
        initTree();  // 初始化部门岗位树
        $('#bindPosts').off('click').on('click', function() { submit(); });
    });

    /**
     *  初始化左侧部门岗位树
     */
    var initTree = function() {

        $tree = $('#departmentPostTree');

        var options = { //通用配置
            levels : 2,
            multiSelect : true,
            showTags : true
        };

        $.fn.http({
            method : 'GET',
            url :  postUrl,
            success : function(data) {
                disableDepartmentNodes(data);   //禁用部门节点
                $tree.treeview($.extend(options, { data : data }));   //新创建树组件
                $tree.treeview('expandAll', { levels: 2, silent: true }); //展开二级节点
                $tree.on('nodeSelected', function(e, node) {
                    clearTimeout(timer);
                    if(node.hasOwnProperty('type') && node['type'] === 2) { //选择了岗位节点
                        postNodeOnSelected(node);
                    } else {
                        departmentNodeOnSelected(node);
                    }
                });
                $tree.on('nodeUnselected', function() {
                    timer = setTimeout(onNodeUnselected, 100);
                });
            }
        });
    };

    /**
     *  禁止选中部门树
     * @param datas 树状数据，包含部门和岗位
     */
    var disableDepartmentNodes = function(datas) {

        if(datas === null || datas.length === 0) {
            return false;
        }
        var data;
        for(var i = 0; i < datas.length; i++) {
            data = datas[i];
            if(data.type === 1) {
                data.selectable = false;
            }
            disableDepartmentNodes(data.nodes);
        }
    };

    $.fn.loadPersonPosts = function(personId) {
        selectedPersonId = personId;
        $tree.treeview('unselectAll');   //uncheck菜单
        $.fn.http({
            method : 'GET',
            url :  baseUrl + '/' + selectedPersonId + '/posts',
            success : function(datas) {
                if(datas) {
                    datas.forEach(function(data) {
                        var node = $tree.treeview('findNodes', [ data.id, "id" ]);
                        $tree.treeview('selectNode', [ node, { silent : true } ]);  //选中岗位
                    });
                }
            }
        });
    };

    $.fn.clearPersonPosts = function() {
        selectedPersonId = null;
        $tree.treeview('unselectAll');   //uncheck菜单
    };

    /**
     *  选中部门节点触发方法
     */
    var departmentNodeOnSelected = function() {
        // refreshTable();
        // $('#createPerson').prop('disabled', true);
        // $('#updatePerson').prop('disabled', true);
        // $('#deletePerson').prop('disabled', true);
    };

    /**
     *  选中岗位节点触发方法
     */
    var postNodeOnSelected = function() {
        // refreshTable();
        // $('#createPerson').prop('disabled', false);
        // $('#updatePerson').prop('disabled', false);
        // $('#deletePerson').prop('disabled', false);
    };

    /**
     *  取消选中节点触发方法
     */
    var onNodeUnselected = function() {
        // refreshTable();
        // $('#createPerson').prop('disabled', true);
        // $('#updatePerson').prop('disabled', true);
        // $('#deletePerson').prop('disabled', true);
    };

    /**
     * 提交表单
     */
    var submit = function() {

        if(!selectedPersonId) {
            SysMessage.alertWarning('请选择人员进行操作！');
            return false;
        }

        var posts = [];   //管辖岗位关系集合
        var selectedDatas = $tree.treeview('getSelected');
        var selectedData;
        for(var i = 0; i < selectedDatas.length; i++) {
            selectedData = selectedDatas[i];
            if(selectedData.type === 2) {    //只处理岗位节点
                posts.push(selectedData.id);
            }
        }

        $.fn.http({
            method : "PUT",
            url : baseUrl + '/' + selectedPersonId + '/posts',
            data : JSON.stringify({
                posts : posts
            })
        });
    };

}(window.jQuery);

/**
 * 格式化-职务
 * @param value 显示值
 * @param row 数据行
 */
var departmentPostFormatter = function (value, row) {
    if(!value) {
        return overSpanFormatter('-');
    }
    if(!row.hasOwnProperty('departmentName') || !row['departmentName']) {
        return overSpanFormatter(value);
    }

    return overSpanFormatter(value + '(' + row['departmentName'] + ')');
};
