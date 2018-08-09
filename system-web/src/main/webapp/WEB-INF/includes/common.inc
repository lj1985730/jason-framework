<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<base href="<%=basePath%>" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<%-- <meta charset="utf-8"/> --%>
<%-- <meta http-equiv="Content-type" content="text/html; charset=utf-8"> --%>
<%-- <meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests"> --%>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="author" content="yoogun" />
<meta name="description" content="产生于大连用友软件有限公司" />
<link rel="shortcut icon" href="imgs/global/favicon.ico" />

<link type="text/css" rel="stylesheet" href="plugins/font-awesome/css/font-awesome.min.css" />
<link type="text/css" rel="stylesheet" href="plugins/simple-line-icons/simple-line-icons.min.css" />
<link type="text/css" rel="stylesheet" href="plugins/bootstrap/css/bootstrap.min.css" />
<link type="text/css" rel="stylesheet" href="plugins/uniform/css/uniform.default.css" />
<link type="text/css" rel="stylesheet" href="plugins/bootstrap-switch/bootstrap-switch.min.css" />
<link type="text/css" rel="stylesheet" href="plugins/bootstrap-table/bootstrap-table.css" />
<link type="text/css" rel="stylesheet" href="plugins/bootstrap-table/bootstrap-table-group-by.css" />
<link type="text/css" rel="stylesheet" href="plugins/bootstrap-table/bootstrap-editable.css" />
<link type="text/css" rel="stylesheet" href="plugins/bootstrap-datepicker/bootstrap-datepicker3.min.css" />
<link type="text/css" rel="stylesheet" href="plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.min.css" />
<link type="text/css" rel="stylesheet" href="plugins/bootstrap-daterangepicker/daterangepicker-bs3.css" />
<link type="text/css" rel="stylesheet" href="plugins/bootstrap-treeview/bootstrap-treeview.css" />
<link type="text/css" rel="stylesheet" href="plugins/icheck/skins/all.css" />
<link type="text/css" rel="stylesheet" href="css/global/components-md.css" id="style_components" />
<link type="text/css" rel="stylesheet" href="css/global/plugins-md.css" />
<link type="text/css" rel="stylesheet" href="css/error.css" />
<link type="text/css" rel="stylesheet" href="css/layout.css" />
<link type="text/css" rel="stylesheet" href="css/themes/darkblue.css" id="style_color" />
<link type="text/css" rel="stylesheet" href="css/custom.css" />
<link type="text/css" rel="stylesheet" href="css/custom-yonyou.css" />
<link type="text/css" rel="stylesheet" href="plugins/toastr/toastr.css" />
<link type="text/css" rel="stylesheet" href="plugins/select2/css/select2.css" />
<link type="text/css" rel="stylesheet" href="plugins/select2/css/select2-bootstrap.css" />

<script type="text/javascript" src="plugins/jquery.min.js"></script>
<script type="text/javascript" src="plugins/jquery-migrate.min.js"></script>
<%-- IMPORTANT! Load jquery-ui.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip --%>
<script type="text/javascript" src="plugins/jquery-ui/jquery-ui.min.js"></script>
<script type="text/javascript" src="plugins/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="plugins/bootstrap-dropdown/bootstrap-hover-dropdown.min.js"></script>
<script type="text/javascript" src="plugins/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<script type="text/javascript" src="plugins/jquery.blockui.min.js"></script>
<script type="text/javascript" src="plugins/jquery.actual.js"></script>
<script type="text/javascript" src="plugins/bootbox/bootbox.min.js"></script>
<script type="text/javascript" src="plugins/uniform/jquery.uniform.min.js"></script>
<script type="text/javascript" src="plugins/bootstrap-switch/bootstrap-switch.min.js"></script>
<script type="text/javascript" src="plugins/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="plugins/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="plugins/bootstrap-table/bootstrap-table-group-by.js"></script>
<script type="text/javascript" src="plugins/bootstrap-table/bootstrap-editable.js"></script>
<script type="text/javascript" src="plugins/bootstrap-table/bootstrap-table-editable.js"></script>
<script type="text/javascript" src="plugins/bootstrap-datepicker/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="plugins/bootstrap-datepicker/bootstrap-datepicker.zh-CN.min.js"></script>
<script type="text/javascript" src="plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="plugins/bootstrap-daterangepicker/moment.min.js"></script>
<script type="text/javascript" src="plugins/bootstrap-daterangepicker/daterangepicker.js"></script>
<script type="text/javascript" src="plugins/bootstrap-treeview/bootstrap-treeview.js"></script>
<script type="text/javascript" src="plugins/bootstrap-contextmenu/bootstrap-contextmenu.js"></script>
<script type="text/javascript" src="plugins/toastr/toastr.js"></script>
<script type="text/javascript" src="plugins/jquery.cookie.js"></script>
<script type="text/javascript" src="plugins/select2/js/select2.js"></script>
<script type="text/javascript" src="plugins/select2/js/i18n/zh-CN.js"></script>
<script type="text/javascript" src="plugins/jquery-validation/jquery.validate.js"></script>
<script type="text/javascript" src="plugins/jquery-validation/localization/messages_zh.js"></script>
<script type="text/javascript" src="plugins/icheck/icheck.min.js"></script>
<script type="text/javascript" src="scripts/global/metronic.js"></script>
<script type="text/javascript" src="scripts/layout.js"></script>
<script type="text/javascript" src="scripts/quick-sidebar.js"></script>
<script type="text/javascript" src="scripts/global/math.uuid.js"></script>
<script type="text/javascript" src="scripts/custom-yonyou.js"></script>
<script type="text/javascript" src="scripts/table-formatter.js"></script>
<script type="text/javascript" src="scripts/global/sys-message.js"></script>
<script>
    var basePath = '<%=basePath%>';
    var pathArray = top.window.location.href.split('/');
    var protocol = pathArray[0];
    var host = pathArray[2];
    var appName = pathArray[3];
    var appPath = "/" + appName;
    $(function() {
        Metronic.init(); // init metronic core components
        Metronic.setAssetsPath('');
        Metronic.setGlobalImgPath('imgs/global/');
        Layout.init(); // init current layout
        QuickSidebar.init(); // init quick sidebar
    });
    $.fn.modal.Constructor.prototype.enforceFocus =function(){};    //select2 modal bug fix
</script>
