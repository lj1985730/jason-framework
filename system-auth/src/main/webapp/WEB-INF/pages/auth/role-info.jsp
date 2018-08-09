<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
    <form id="roleInfoForm" class="form-horizontal form-bordered form-row-stripped">
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label col-md-4">名称：</label>
                    <div class="col-md-8">
                        <p class="form-control-static" data-property="name"></p>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label col-md-4">编号：</label>
                    <div class="col-md-8">
                        <p class="form-control-static" data-property="code"></p>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label col-md-4">是否启用：</label>
                    <div class="col-md-8">
                        <p class="form-control-static" data-property="enabled"></p>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label class="control-label col-md-2">备注：</label>
                    <div class="col-md-10">
                        <p class="form-control-static" data-property="remark"></p>
                    </div>
                </div>
            </div>
        </div>
    </form>
</html>
