//弹出修改密码窗口
function changePassword() {
	$("#changePassForm").form('clear');
	$("#changePassWin").modal("show");
}

/**
 * 提交修改密码
 */
$('#changePassWinSubmitBtn').on('click', function() {
    bootbox.setLocale("zh_CN");
    bootbox.confirm("确定退出系统？", function (callback) {
        if(callback) {
            $.ajax({
                dataType : "json",
                contentType : "application/json",
                method : "PUT",
                url : "/auth/account/modifyPassword/" + $('#oldPassword').val() + "/" + $('#newPassword').val(),
                callback : function() {
                    $("#changePassWin").modal("hide");
                }
            });
		}
    });
});

/**
 * 注销系统
 */
function logout() {
	bootbox.setLocale("zh_CN");
	bootbox.confirm("确定退出系统？", function (callback) {
		if(callback) {
            top.location.href = appPath + '/logout';
        }
    });
}