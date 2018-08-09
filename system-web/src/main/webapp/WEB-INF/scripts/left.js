$(function() {
    loadAccordionMenu();
});

/**
 * 加载主菜单
 */
function loadAccordionMenu() {
    var requestUrl = appPath + "/auth/menu/leftMenu";

    $.ajax({
        type : "GET",
        url : requestUrl,
        dataType : "json",
        contentType:"application/json",
        data : {
        },
        error : function() { },
        success : function(data) {
            if(!data) {
                return false;
            }
            var menus = JSON.parse(data.data);  //转换数据为JSON
            var $menuBar = $("#yonyou-system-menu"); //菜单容器

            $menuBar.find(".menu_li").remove();  //先清空菜单

            //拼接1级菜单
            var menu;
            // var html;
            for(var i = 0; i < menus.length; i ++) {
                menu = menus[i];

                var liNode = $('<li class="menu_li">').prop('id', menu.id).appendTo($menuBar);
                var aNode;
                if('NULL' === menu.url || '' === menu.url) {    //判断是否可点击
                    aNode = $('<a href="javascript:void(0);">').appendTo(liNode);
                } else {    //根菜单
                    aNode = $('<a href="javascript:void(0);">')
                        .prop('id', menu.id.replace(/-/g, ""))
                        .prop('onClick', 'openMenuUrl(\'' + (menu.url ? menu.url.replace(/\//g,"\/") : null) + '\', \'' + menu.id + '\')')
                        .appendTo(liNode);
                }

                $('<i>').addClass(menu.icon).appendTo(aNode);
                $('<span class="title">').text(menu.name).appendTo(aNode);
                $('<span class="arrow">').appendTo(aNode);
                if(menu.children.length > 0) {
                    var subUl = $('<ul class="sub-menu">').appendTo(liNode);
                    //拼接2级菜单
                    for(var j = 0; j < menu.children.length; j ++) {
                        var subMenu = menu.children[j];
                        var subLi = $('<li>').appendTo(subUl);
                        $('<a onClick="openMenuUrl(\'' + subMenu.url.replace(/\//g,"\/") + '\')" data-url="' + subMenu.url + '">').prop('href', 'javascript:void(0);')
                            .prop('text', ' ' + menu.children[j].name)
                            .prepend($('<i>').addClass('fa fa-angle-right'))    //2级菜单不要icon
                            .appendTo(subLi);
                    }
                }
            }
            Layout.setSidebarMenuActiveLink('set', $("[data-url='" + top.location.pathname.replace(appPath, '') + "']"));
        }
    });
}

/**
 * 打开按钮对应Url
 */
function openMenuUrl(url) {
    top.location.href = appPath + url;
}