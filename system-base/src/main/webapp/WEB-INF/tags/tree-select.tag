<%--
    自定义树形弹窗选择组件
    使用方式：
    1.页面头部引入自定义标签声明：
    <%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
    2.在需要位置放置自定义按钮，id必须设置：
    <yonyou:tree-select entity="XXX" rootId="XXX" multiSelect="Boolean" />
--%>
<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 参数 --%>
<%@ attribute name="id" required="true" rtexprvalue="false" type="java.lang.String" description="ID" %>
<%@ attribute name="name" required="false" rtexprvalue="false" type="java.lang.String" description="表单name" %>
<%@ attribute name="entity" required="true" rtexprvalue="false" type="java.lang.String" description="实体名称" %>
<%@ attribute name="rootId" required="false" rtexprvalue="true" type="java.lang.String" description="根节点ID" %>
<%@ attribute name="multiselect" required="false" rtexprvalue="false" type="java.lang.Boolean" description="是否多选" %>
<%@ attribute name="required" required="false" rtexprvalue="false" type="java.lang.String" description="是否必填" %>

<script type="text/javascript" src="scripts/base/tree-select.js"></script>

<div class="input-group">
    <div class="input-group-btn">
        <button type="button" id="${id}-open" class="btn green"><i class="fa fa-list-alt"></i> 选择</button>
    </div>
    <input class="form-control" id="${id}-text" title="选择" readonly <c:if test="${not empty required}">required</c:if> />
    <input type="hidden" class="tree-select" id="${id}-hidden" name="${name}" title="选择" />
</div>

<!-- modal-lg(大) -->
<div id="${id}-modal" class="modal fade" role="dialog" data-backdrop="static">
    <div class="modal-md modal-dialog">
        <div class="modal-content">
            <div class="modal-header" style="background-color: dimgray; border-top-left-radius: 5px; border-top-right-radius: 5px;">
                <h4 class="modal-title" style="color: white;">
                    <i class="icon-search"></i>
                    <span style="font-weight: bold;">选择数据</span>
                    <i class="fa fa-remove" style="float: right; padding-top: 4px; padding-right: 4px; color: #FFFFFF; transform: scale(1.5);"
                       onmouseover="this.style.cssText='float: right; padding-top: 3px; padding-right: 3px; color: #FFFFFF; transform: scale(2);'"
                       onmouseout="this.style.cssText='float: right; padding-top: 4px; padding-right: 4px; color: color: #FFFFFF; transform: scale(1.5);'" data-dismiss="modal" data-target="#${id}"></i>
                </h4>
            </div>

            <div class="modal-body">
                <div id="${id}"></div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn green" id="${id}-confirm-btn"><i class="fa fa-check"></i>&nbsp;选择</button>
                <button type="button" class="btn default" id="${id}-cancel-btn"><i class="fa fa-remove"></i>&nbsp;关闭</button>
            </div>
        </div>
    </div>
</div>

<script language='javascript'>

    !function ($) {

        'use strict';

        var multiselect = false;

        if('${multiselect}' !== '') {
            multiselect = ('${multiselect}' === 'true');
        }

        $(function() {
            //初始化tree组件
            $('#${id}').treeSelect({
                entity : '${entity}',
                multiSelect : multiselect,
                rootId : '${rootId}'
            });

            $('#${id}-modal').appendTo($('body'));

            $('#${id}-open').on('click', function() {    //打开弹框
                <%--$('#${id}').treeSelect({});--%>
                $('#${id}-modal').modal('show');
            });

            $('#${id}-cancel-btn').on('click', function() {    //关闭
                $('#${id}-modal').modal('hide');
            });

            $('#${id}-confirm-btn').on('click', function() {    //确认选择
                var getSelectedId = $('#${id}').treeSelect('getSelectedId');
                var getSelectedText = $('#${id}').treeSelect('getSelectedText');
                if(getSelectedId) {
                    $('#${id}-hidden').val(getSelectedId.toString());
                    $('#${id}-text').val(getSelectedText.toString());
                }
                $('#${id}-modal').modal('hide');
            });
        });
    }(window.jQuery);
</script>