<%--
  Created by IntelliJ IDEA.
  User: Sheng Baoyu
  Date: 2017-12-13
  Time: 17:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>联系人信息</title>
    <%@ include file="/WEB-INF/includes/common.inc" %>
    <link rel="stylesheet" href="scripts/addrlist/site/style.css">
</head>
<body>
    <header class="fixed">
        <div class="header">
            <span class="glyphicon glyphicon-chevron-left pull-left" style="line-height: 45px;font-size: large" name="button_back" id="button_back"></span>
            <span>${person.name}</span>
        </div>
    </header>
    <div class="sort_box" >
        <div class="col-xs-4" style="padding-right: 0;">
            <div class="num_photo" >
                <img src="imgs/${person.name}.jpg" alt="" >
            </div>
        </div>
        <div class="col-xs-6" >
            <label class="word">手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机：</label>
            <label class="wordss">
                <a href="tel:${person.telephone}">${person.telephone}</a>
            </label>
            <br/>
            <label class="word">办公电话：</label>
            <label class="wordss">
                <a href="tel:${person.mobilePhone}">${person.mobilePhone}</a>
            </label>
        </div>
    </div>
    <div class="title1">
        个人信息
    </div>
    <div>
        <label class="word">工作单位及职务：</label>
        <div class="words">${person.unit}</div>
        <label class="word">地址：</label>
        <div class="words">${person.address}</div>
        <label class="word">邮箱：</label>
        <div class="words">${person.email}</div>
        <label class="word">备注：</label>
        <div class="words">${person.remark}</div>

    </div>
    <script type="text/javascript" src="scripts/addrlist/site/contact.js"></script>
</body>
</html>


