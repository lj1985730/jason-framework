<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
    <yonyou:modal id="editRoleWin" modalClass="modal-md" title="编辑角色" editable="true">
        <form id="editRoleForm" class="form-horizontal form-bordered form-row-stripped" data-toggle="validator">
            <input id="id" name="id" class="switch" type="hidden"/>
            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label class="control-label col-md-2" for="name">名称</label>
                        <div class="col-md-10">
                            <input class="form-control" id="name" name="name" placeholder="角色名称..." required maxlength="30"/>
                            <div class="form-control-focus"></div>
                            <span class="help-block help-info"><b>必填</b>，请输入最多30个字符</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label class="control-label col-md-2" for="code">编码</label>
                        <div class="col-md-10">
                            <input class="form-control" id="code" name="code" placeholder="角色编码..." maxlength="30"/>
                            <div class="form-control-focus"></div>
                            <span class="help-block help-info">可输入最多30个字符</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label class="control-label col-md-2" for="remark">备注</label>
                        <div class="col-md-10">
                            <textarea class="form-control" id="remark" name="remark" rows="3" placeholder="备注..." maxlength="300"></textarea>
                            <div class="form-control-focus"></div>
                            <span class="help-block help-info">可输入最多300个字符</span>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </yonyou:modal>
</html>
