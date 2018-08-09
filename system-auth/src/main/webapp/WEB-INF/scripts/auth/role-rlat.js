!function ($) {

    'use strict';
    /**
     * 角色关系全局对象
     */
    var RoleRlatObj = {};
    $.fn.RoleRlatObj = RoleRlatObj;
    /**
     * 基础路径
     */
    RoleRlatObj.baseUrl = appPath + "/auth/role";

    /**
     * 初始化icheck组件,并绑定单击事件
     */
    RoleRlatObj.initICheck = function () {
        $('.role-rlat-check').iCheck({ //初始化icheck组件
            cursor : true,
            checkboxClass : 'icheckbox_flat-green'
        });
        $('.role-rlat-check').on('ifChecked', function(){
            var selectRoleRlatRowId = [];
            selectRoleRlatRowId.push($(this).closest('tr').attr('data-uniqueid'));
            RoleRlatObj.$roleRlatTable.bootstrapTable('checkBy',{field:'id',values:selectRoleRlatRowId});
        });
        $('.role-rlat-check').on('ifUnchecked', function(){
            var trId = $(this).closest('tr').attr('data-uniqueid');
            var isDataChecked = $('#' + trId + 'dataPermission').closest('.icheckbox_flat-green').hasClass('checked');
            var isAuditChecked = $('#' + trId + 'auditPermission').closest('.icheckbox_flat-green').hasClass('checked');
            if(isDataChecked && isAuditChecked){
                return true;
            }
            RoleRlatObj.$roleRlatTable.bootstrapTable('uncheckBy',{field:'id',values:[trId]});
        });
    };

    /**
     * 根据角色设置角色关系选中
     * @param roleRow 角色
     */
    RoleRlatObj.setRoleRlat = function (roleRow) {
        RoleRlatObj.cancelICheckState();
        if(roleRow){
            var selectRoleRlatRowId = [];
            var roleRlats = roleRow.roleRlats;
            if(roleRlats){
                for(var i = 0; i < roleRlats.length; i++){
                    selectRoleRlatRowId.push(roleRlats[i].subordinateId);
                    RoleRlatObj.setICheckState(roleRlats[i]);
                }
            }
            RoleRlatObj.$roleRlatTable.bootstrapTable('checkBy',{field:'id',values:selectRoleRlatRowId})
        }
    };

    /**
     * 设置icheck选中
     * @param roleRlats 角色关系数据
     */
    RoleRlatObj.setICheckState = function (roleRlats) {
        if(roleRlats){
            if(roleRlats.dataPermission === 'T'){
                $('#' + roleRlats.subordinateId + 'dataPermission').closest('.icheckbox_flat-green').removeClass('checked').addClass('checked');
            }
            if(roleRlats.auditPermission === 'T'){
                $('#' + roleRlats.subordinateId + 'auditPermission').closest('.icheckbox_flat-green').removeClass('checked').addClass('checked');
            }
        }
    };

    /**
     * 取消表格、icheck选择
     */
    RoleRlatObj.cancelICheckState = function () {
        $('.role-rlat-check').closest('.icheckbox_flat-green').removeClass('checked');
        RoleRlatObj.$roleRlatTable.bootstrapTable('uncheckAll');
    };

    /**
     * 角色关系附加查询参数，暂时为空，预留
     */
    RoleRlatObj.roleRlatParams = function (params) {
        //预留条件查询：name、code,现在支持模糊查询，search
        var localParams = {};
        return $.extend(localParams, params);
    };

    /**
     * 初始化角色关系表
     */
    RoleRlatObj.initRoleRlatTable = function () {
        RoleRlatObj.$roleRlatTable.bootstrapTable(
            $.extend(
                {
                    url : RoleRlatObj.baseUrl,
                    singleSelect : false,
                    formatSearch : function () {
                        return '搜索角色名称/编码';
                    },
                    onLoadSuccess : function () {
                        RoleRlatObj.initICheck();
                        var roleRow = RoleRlatObj.$roleTable.bootstrapTable('getSelections')[0];
                        RoleRlatObj.setRoleRlat(roleRow);
                    }
                },
                $.extend({}, generalTableOption, { pageList: [1000], pageSize : 1000, queryParams : RoleRlatObj.roleRlatParams })
            )
        )
    };

    /**
     * 构建角色关系数据
     * @param superiorId 上级角色id
     * @param subordinates 下级角色对象数组
     * @return roleRlats 角色关系数组对象
     */
    RoleRlatObj.biuldRoleRlat = function (superiorId, subordinates) {
        var roleRlats = [];
        for(var i = 0; i < subordinates.length; i++){
            var roleRlat = {};
            roleRlat.superiorId = superiorId;
            roleRlat.subordinateId = subordinates[i].id;
            roleRlat.dataPermission = $('#' + subordinates[i].id + 'dataPermission').closest('.icheckbox_flat-green').hasClass('checked') ? 'T' : 'F';
            roleRlat.auditPermission = $('#' + subordinates[i].id + 'auditPermission').closest('.icheckbox_flat-green').hasClass('checked') ? 'T' : 'F';
            roleRlats.push(roleRlat);
        }
        return roleRlats;
    };
    /**
     * 保存角色关系
     */
    $.fn.bindRoleRlat = function () {
        var roleRow = RoleRlatObj.$roleTable.bootstrapTable('getSelections')[0];//上级角色对象
        var roleRlat = RoleRlatObj.$roleRlatTable.bootstrapTable('getSelections');//下级角色对象组
        if(!roleRow){
            SysMessage.alertInfo('请选择上级角色！');
            return false;
        }
        if(!roleRlat || roleRlat.length < 1){
            SysMessage.alertInfo('请选择下级角色！');
            return false;
        }
        var request = {
            method : 'PUT',
            url : RoleRlatObj.baseUrl + '/' + roleRow.id + '/roleRlat',
            data : JSON.stringify({
                roleRlats: RoleRlatObj.biuldRoleRlat(roleRow.id,roleRlat)
            }),
            success : function () {
                RoleRlatObj.$roleTable.bootstrapTable('refresh');
                RoleRlatObj.$roleRlatTable.bootstrapTable('refresh');
            }
        };
        $.fn.http(request);
    };

    /**
     * 角色权限格式化
     */
    $.fn.isDataPermission = function (value, row) {
        var str = '<input type="checkbox" id="'+ row.id +'dataPermission" class="role-rlat-check">';
        if (RoleRlatObj.$roleTable){
            var roleRow = RoleRlatObj.$roleTable.bootstrapTable('getSelections')[0];
            if(roleRow){
                var roleRlats = roleRow.roleRlats;
                if (roleRlats && roleRlats.length > 0){
                    for(var i = 0; i < roleRlats.length; i++){
                        if(roleRlats[i].subordinateId === row.id){
                            if(roleRlats[i].dataPermission === 'T'){
                                str = '<input type="checkbox" id="'+ row.id +'dataPermission" class="role-rlat-check" checked>';
                            }
                        }
                    }
                }
            }
        }
        return str;
    };

    /**
     * 角色审核权限格式化
     */
    $.fn.isAuditPermission = function (value, row) {
        var str = '<input type="checkbox" id="'+ row.id +'auditPermission" class="role-rlat-check">';
        if (RoleRlatObj.$roleTable){
            var roleRow = RoleRlatObj.$roleTable.bootstrapTable('getSelections')[0];
            if(roleRow){
                var roleRlats = roleRow.roleRlats;
                if (roleRlats && roleRlats.length > 0){
                    for(var i = 0; i < roleRlats.length; i++){
                        if(roleRlats[i].subordinateId === row.id){
                            if(roleRlats[i].auditPermission === 'T'){
                                str = '<input type="checkbox" id="'+ row.id +'auditPermission" class="role-rlat-check" checked>';
                            }
                        }
                    }
                }
            }
        }
        return str;
    };
    /**
     * 初始化
     */
    $(function () {
        //角色关系table
        RoleRlatObj.$roleRlatTable = $('#roleRlatTable');
        //角色table
        RoleRlatObj.$roleTable = $('#roleTable');
        //初始化角色关系table
        RoleRlatObj.initRoleRlatTable();
    });

}(window.jQuery);

