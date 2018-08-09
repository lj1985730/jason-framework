!function ($) {

    'use strict';

    var $passWin, $passForm;

    $(function() {

        $passWin = $("#changePassWin");
        $passForm = $("#changePassForm");

        $('#toChangePassword').off('click').on('click', function() {
            $("#changePassForm").form('clear');
            $("#changePassWin").modal("show");
        });

        /**
         * 提交修改密码
         */
        $('#changePassWinSubmitBtn').off('click').on('click', function() {
            changePasswordSubmit();
        });

        $('#toLogout').off('click').on('click', function() {
            logout();
        });

        $passForm.validation({
            rules : {
                againPassword : {
                    equalTo : "#newPassword"
                }
            },
            messages : {
                againPassword : {
                    equalTo : "两次密码输入不一致"
                }
            }
        });
    });

    /**
     * 注销系统
     */
    var changePasswordSubmit = function() {

        if(!$passForm.valid()) {    //表单验证
            return false;
        }

        bootbox.setLocale("zh_CN");
        bootbox.confirm("确定修改密码？", function (callback) {
            if(callback) {
                $.fn.http({
                    method : 'POST',
                    url : appPath + '/auth/account/modifyPassword/',
                    data : JSON.stringify({
                        oldPass : $('#oldPassword').val(),
                        newPass : $('#newPassword').val()
                    }),
                    success : function() {
                        $("#changePassWin").modal("hide");
                    }
                });
            }
        });
    };

    /**
     * 注销系统
     */
    var logout = function() {
        bootbox.setLocale("zh_CN");
        bootbox.confirm("确定退出系统？", function (callback) {
            if(callback) {
                top.location.href = appPath + '/logout';
            }
        });
    };

}(window.jQuery);