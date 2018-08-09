<%--
  Created by IntelliJ IDEA.
  User: Sheng Baoyu
  Date: 2017-12-13
  Time: 17:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>十届政协委员</title>
    <%@ include file="/WEB-INF/includes/common.inc" %>
    <link rel="stylesheet" href="scripts/addrlist/site/style.css">
</head>
<body>
<%--    <div class="container-fluid">--%>
        <header class="fixed">
            <div class="header">
                青泥联络委通讯录
            </div>
        </header>
        <div id="letter" ></div>
        <div class="sort_box">
            <c:forEach items="${contacts}" var="contact">
                <div class="sort_list" name="sortList" id="${contact.id}">
                    <div class="num_logo">
                        <img src="imgs/${contact.name}.jpg" alt="">
                    </div>
                    <div class="num_inf" style="padding-top: 0;margin-top: -10px;">
                        <div class="num_name" style="padding-top: 0;font-size: medium;">
                                ${contact.name}
                        </div>
                        <div >
                                ${contact.telephone}
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="initials">
            <ul>
            </ul>
        </div>
    <%--</div>--%>
    <div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';"></div>
    <script type="text/javascript" src="scripts/addrlist/site/jquery.charfirst.pinyin.js"></script>
    <script type="text/javascript" src="scripts/addrlist/site/sort.js"></script>
    <script type="text/javascript" src="scripts/addrlist/site/contact.js"></script>
</body>
</html>
