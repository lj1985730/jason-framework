!function ($) {
    /**
     * 签到全局对象
     */
    var SignObject = {};

    /**
     * 基础路径
     */
    SignObject.baseUrl="appPath + /crm/sign";
    /**
     * 初始化日历插件方法
     */
    SignObject.initCalendar = function () {
        if( typeof ($.fn.fullCalendar) === 'undefined'){ return; }

        SignObject.calendar.fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                // right: 'month,agendaWeek,agendaDay,listMonth'//月视图、周视图、日视图、列表
                right: ''
            },
            selectable: true,
            selectHelper: true,
            // select: function(startDate) {
            //     var year = new Date(startDate).getFullYear();
            //     var month = new Date(startDate).getMonth() + 1;
            //     var day = new Date(startDate).getDate();
            //     var time = year + '-' + month + '-' + day;
            //     $('#signTitle').empty().html('<i class="fa fa-list"></i>签到页面&nbsp;&nbsp;' + time);
            //     $('#sign-panel').css('left', '0');
            // },
            unselect : function () {
                // $('#sign-panel').css('left', '100%');
            },
            editable: true,
            dayClick: function(date) {
                // $(this).css('background-color', 'red');
                //解决手机端点击高亮显示问题
                SignObject.highlight($(this));

                var year = new Date(date).getFullYear();
                var month = new Date(date).getMonth() + 1;
                var day = new Date(date).getDate();
                $('#signTitle').empty().html('<i class="fa fa-list"></i>签到页面&nbsp;&nbsp;' + SignObject.formatData(year,month,day));
                $('#sign-panel').css('left', '0');
                //日历时间小于当前时间标识为补签到（true），正常为（fase）
                if (new Date(SignObject.formatData(year,month,day + 1)) < new Date()){
                    SignObject.isRepair = true;
                    SignObject.signDate = SignObject.formatData(year,month,day);
                } else {
                  SignObject.isRepair = false;
                }
                //刷新签到面板
                SignObject.getSignData(SignObject.formatData(year,month,day));
                SignObject.refreshSignPanel(SignObject.signData,SignObject.formatData(year,month,day));
            }
        });
    };
    /**
     * 格式化输出时间，例如2017-2-3格式化为2017-02-03
     */
    SignObject.formatData = function (year,month,day) {
        var time;
        if (month < 10){
            if (day < 10){
                time = year + '-' + '0' +  month + '-' + '0' + day;
            }else {
                time = year + '-' + '0' +  month + '-' + day;
            }

        }else {
            if (day < 10){
                time = year + '-' +  month + '-' + '0' + day;
            }else {
                time = year + '-' +  month + '-' + day;
            }
        }
        return time;
    };
    /**
     * 解决手机端高亮显示
     * @param $el 日期td的juqery对象
     */
    SignObject.highlight = function ($el) {
        if(SignObject.dataTd){
            SignObject.dataTd.removeClass('fc-highlight')
        }
        SignObject.dataTd = $el;
        $el.addClass('fc-highlight');
    };
    /**
     * 格式化日期td背景颜色
     * 规则：日期td小于当前日期，signInTime、signOutTime有值，变为绿色，否则变为红色
     * @param signDate 日期td代表的日期
     * @param el 日期td
     * @param signData 数据库数据
     */
    SignObject.formatTdStyle = function (signDate,el,signData) {

        //超过当前日期不做格式化
        if (new Date(signDate) > new Date()){
            return ;
        }

        if (signDate && el && signData && new Date(signDate) < new Date() ){
            if(signData.signInTime !== '' && signData.signOutTime !== ''){

            }
        }
    };
    /**
     * 隐藏表单属性行方法
     * @param els 需要隐藏元素jquery对象的数组
     */
    SignObject.hideRows = function (els) {
        if(!els || els.length === 0){
            return false;
        }
        for (var i = 0; i < els.length; i++){
            els[i].closest('.row').hide();
        }
    };
    /**
     * 显示表单属性行方法
     * @param els 需要显示元素jquery对象的数组
     */
    SignObject.showRows = function (els) {
        if(!els || els.length === 0){
            return false;
        }
        for (var i = 0; i < els.length; i++){
            els[i].closest('.row').show();
        }
    };
    /**
     * 显示签到页面
     */
    SignObject.showInPanel = function (signDate) {
        SignObject.signForm.form('clear');
        SignObject.getSignInfo(0);
        if (SignObject.isRepair){
            SignObject.signInfo.signDate = signDate;
            SignObject.signInfo.signInTime = '09:00:00';
        }
        SignObject.signForm.form('load',SignObject.signInfo);
        SignObject.signPanel.modal('show');
        SignObject.isSigned(SignObject.signInfo.signDate);
    };
    /**
     * 显示签退页面
     */
    SignObject.showOutPanel = function (signDate) {
        SignObject.signForm.form('clear');
        SignObject.getSignInfo(1);
        if (SignObject.isRepair){
            SignObject.signInfo.signDate = signDate;
            SignObject.signInfo.signOutTime = '17:00:00';
        }
        SignObject.signForm.form('load',SignObject.signInfo);
        SignObject.signPanel.modal('show');
        SignObject.isSigned(SignObject.signInfo.signDate);
    };
    /**
     * 构造数据，往表单赋值
     * @param data ajax异步获取的sign数据
     * @return 符合表单赋值的对象
     */
    SignObject.buildSignObject = function (data) {

        var signInfo = {};
        signInfo.signCorp = data.tenant.id;
        signInfo.signCorpName = data.tenant.name;
        signInfo.signDept = data.department.id;
        signInfo.signDeptName = data.department.name;
        signInfo.signUser = data.person.id;
        signInfo.signUserName = data.person.name;
        signInfo.signDate = data.signDate;
        signInfo.signInTime = data.signInTime;
        signInfo.signOutTime = data.signOutTime;
        //将组建好的对象赋值给signInfo
        SignObject.signInfo = signInfo;
    };
    /**
     * 用签到日期异步验证是否签到
     */
    SignObject.isSigned = function (signDate) {
        SignObject.getSignData(signDate);
        if (SignObject.signData !== null){
            SignObject.sOrU = true;
            return;
        }
        SignObject.sOrU = false;
    };
    /**
     * 异步从数据库获取签到信息，用于显示签到面板，及判断是否签到过
     */
    SignObject.getSignData = function (signDate) {
        $.ajax({
            method: 'get',
            url: SignObject.baseUrl + "/isSigned",
            data : {signDate:signDate},
            dataType: "json",
            contentType: "application/json",
            async : false,
            success: function (data) {
                SignObject.signData = data.data;
            }
        });
    };
    /**
     * 更新签到面板的地理位置、签到时间
     * @param data 赋值数组，分别为：签到地点、签到时间、签退地点、签退时间
     */
    SignObject.updateSignPanel = function (data) {
        if (data && data.length === 4){
            if (data[0] !== ''){
                $('#signInAdd').html(data[0]);
            }else {
                $('#signInAdd').html('- - -');
            }
            if (data[1] !== ''){
                $('#signInTime').html(data[1]);
            }else {
                $('#signInTime').html('- - -');
            }
            if (data[2] !== ''){
                $('#signOutAdd').html(data[2]);
            }else {
                $('#signOutAdd').html('- - -');
            }
            if (data[3] !== ''){
                $('#signOutTime').html(data[3]);
            }else {
                $('#signOutTime').html('- - -');
            }
        }
    };
    /**
     * 表单提交
     */
    SignObject.submit = function () {
        if(!SignObject.signForm.valid()) {    //表单验证
            return false;
        }

        var method = (SignObject.sOrU ? 'PUT' : 'POST');
        $.fn.http({
            method : method,
            url : SignObject.baseUrl,
            data : JSON.stringify(SignObject.signForm.serializeJson()),
            success : function() {
                SignObject.signPanel.modal('hide');
                SignObject.getSignData($('#edit_signDate').val());
                SignObject.refreshSignPanel(SignObject.signData);
            }
        });
    };
    /**
     * ajax动态获取签到公司、签到部门、签到人、签到时间
     * @param inOrOut 0代表签到，1代表签退
     */
    SignObject.getSignInfo = function (inOrOut) {
        $.ajax({
            method: 'get',
            url: SignObject.baseUrl + "/getSignInfo",
            data : {inOrOut : inOrOut},
            dataType: "json",
            contentType: "application/json",
            async : false,
            success: function (data) {
                SignObject.buildSignObject(data.data);
            }
        });
    };
    /**
     * 后台获取数据刷新签到面板
     * @param signData 数据库签到信息
     * @param signTime 签到时间，用于判定是否为补签到
     */
    SignObject.refreshSignPanel = function (signData, signTime) {
        //大于当前时间，不让填报
        if (new Date(signTime) > new Date()){
            $('#signInBut').attr('disabled', true);
            $('#signOutBut').attr('disabled', true);
        } else {
            $('#signInBut').attr('disabled', false);
            $('#signOutBut').attr('disabled', false);
        }

        if ($('#signInBut').hasClass('btn-danger')){
            $('#signInBut').removeClass('btn-danger').addClass('grey-cascade').html('签到');
        }
        if ($('#signOutBut').hasClass('btn-danger')){
            $('#signOutBut').removeClass('btn-danger').addClass('grey-cascade').html('签退');
        }
        var signSimInfo;
        if (signData){
            signSimInfo = [signData.signInAddress,signData.signInTime,signData.signOutAddress,signData.signOutTime];
            if (signData.signInAddress === '' && signData.signInTime === ''){
                if (signTime && new Date(signTime) < new Date()){
                    $('#signInBut').removeClass('grey-cascade').addClass('btn-danger').html('补签到');
                }
            }
            if (signData.signOutAddress === '' && signData.signOutTime === ''){
                if (signTime && new Date(signTime) < new Date()){
                    $('#signOutBut').removeClass('grey-cascade').addClass('btn-danger').html('补签退');
                }
            }
        }else {
            if(signTime && new Date(signTime) < new Date()){
                $('#signInBut').removeClass('grey-cascade').addClass('btn-danger').html('补签到');
                $('#signOutBut').removeClass('grey-cascade').addClass('btn-danger').html('补签退');
            }
            signSimInfo = ['','','',''];
        }
        SignObject.updateSignPanel(signSimInfo);
    };

    $(function () {

        /**
         * 初始化需要操作的元素，日历、签到按钮、签退按钮、填报窗口、填报表单
         */
        SignObject.calendar = $('#calendar');
        SignObject.signInBut = $('#signInBut');
        SignObject.signOutBut = $('#signOutBut');
        SignObject.signPanel = $('#signEditWin');
        SignObject.signForm = $('#signEditWinForm');

        /**
         * 初始化按钮事件
         */
        SignObject.signInBut.on('click',function () {
            SignObject.showRows([$('#edit_signInTime'),$('#edit_signInAddress'),$('#edit_signInLatitude'),$('#edit_signInLongitude')]);
            SignObject.hideRows([$('#edit_signOutTime'),$('#edit_signOutAddress'),$('#edit_signOutLatitude'),$('#edit_signOutLongitude')]);
            SignObject.showInPanel(SignObject.signDate);
        });
        SignObject.signOutBut.on('click',function () {
            SignObject.showRows([$('#edit_signOutTime'),$('#edit_signOutAddress'),$('#edit_signOutLatitude'),$('#edit_signOutLongitude')]);
            SignObject.hideRows([$('#edit_signInTime'),$('#edit_signInAddress'),$('#edit_signInLatitude'),$('#edit_signInLongitude')]);
            SignObject.showOutPanel(SignObject.signDate);
        });
        $('#signEditWinSubmitBtn').on('click', function() { SignObject.submit() });

        /**
         * 初始化日历插件
         */
        SignObject.initCalendar();
        /**
         * 初始显示当前日期签到面板
         */
        var year = new Date().getFullYear();
        var month = new Date().getMonth() + 1;
        var day = new Date().getDate();
        $('#signTitle').empty().html('<i class="fa fa-list"></i>签到页面&nbsp;&nbsp;' + SignObject.formatData(year,month,day));
        /**
         * 初始化显示当前时间数据库签到信息
         */
        SignObject.getSignData(SignObject.formatData(year,month,day));
        SignObject.refreshSignPanel(SignObject.signData);

        console.log($('.fc-day-grid .fc-bg td'));
    });

}(window.jQuery);