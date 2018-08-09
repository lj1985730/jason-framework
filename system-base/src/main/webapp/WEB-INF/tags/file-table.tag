<%--
    自定义文件表格组件
    使用方式：
    1.页面头部引入自定义标签声明：
    <%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
    2.在需要位置放置自定义按钮，id必须设置：
    <yonyou:file-table id="XXX" businessKey="XXX" readonly="Boolean" />
--%>
<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>

<%-- 参数 --%>
<%@ attribute name="id" required="true" rtexprvalue="false" type="java.lang.String" description="唯一标识" %>
<%@ attribute name="businessKey" required="true" rtexprvalue="false" type="java.lang.String" description="业务分类" %>
<%@ attribute name="readonly" required="false" rtexprvalue="false" type="java.lang.Boolean" description="是否只读" %>

<link rel="stylesheet" href="plugins/jQuery-File-Upload/css/jquery.fileupload.css">
<link rel="stylesheet" href="plugins/jQuery-File-Upload/css/jquery.fileupload-ui.css">
<script type="text/javascript" src="plugins/jQuery-File-Upload/js/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="plugins/jQuery-File-Upload/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="plugins/jQuery-File-Upload/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="scripts/base/file-table.js"></script>

<div id="${id}">
    <div class="portlet light">
        <div class="portlet-title">
            <div class="caption">
                <i class="fa fa-file-o"></i>
                <span class="caption-subject">附件列表</span>
            </div>
            <div class="tools">
                <a class="collapse"></a>
                <a class="fullscreen"></a>
            </div>
            <div class="actions" id="${id}_btn_group">
                <button type="button" class="btn green" id="${id}_preview_btn"><i class="fa fa-eye"></i>&nbsp;预览</button>
                <button type="button" class="btn green" id="${id}_download_btn"><i class="fa fa-download"></i>&nbsp;下载</button>
                <button type="button" class="btn red" id="${id}_remove_btn"><i class="fa fa-trash-o"></i>&nbsp;删除</button>
            </div>
        </div>
        <div class="portlet-body">
            <div id="${id}_table_toolbar">
                <div class="row">
                    <div class="col-md-12">
                        <input id="${id}_file_viewer" class="form-control" style="float: left; width: 30%;  border-top-right-radius: 0; border-bottom-right-radius: 0; border-right-width: 0; " readonly />
                        <span class="btn btn-default fileinput-button" style="float: left; border-radius: 0; padding: 7px 13px 6px 13px;">
                            <i class="fa fa-plus"></i>
                            <span>选择文件...</span>
                            <input type="file" id="${id}_file_input" name="file" />
                        </span>
                        <button type="button" id="${id}_upload_btn" class="btn green"
                                style="float: left; margin-left: 0; padding: 7px 13px 6px 13px; border-top-left-radius: 0; border-bottom-left-radius: 0; border-left-width: 0;">
                            <i class="fa fa-upload"></i>&nbsp;上传
                        </button>
                    </div>
                </div>
            </div>
            <div class="table-container">
                <table id="${id}_table" data-show-refresh="false" data-show-toggle="false" data-show-columns="false"
                       data-toolbar="#${id}_table_toolbar">
                    <thead>
                        <tr role="row" class="heading">
                            <th data-field="checkbox" data-checkbox="true"></th>
                            <th data-field="fileId" data-formatter="indexFormatter">序号</th>
                            <th data-field="fileName" data-sortable="true">文件名称</th>
                            <th data-field="size" data-sortable="true" data-formatter="fileSizeFormatter">文件大小</th>
                            <th data-field="extension" data-sortable="true">文件类型</th>
                            <th data-field="lastModifyTime" data-sortable="true">上传时间</th>
                        </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>

<script language='javascript'>

    !function ($) {

        'use strict';

        var readonly = false;

        $(function() {

            if('${readonly}' !== '') {
                readonly = ('${readonly}' === 'true');
            }

            $('#${id}').fileTable({ businessKey : '${businessKey}', readonly : readonly });    //初始化附件组件
        });
    }(window.jQuery);
</script>