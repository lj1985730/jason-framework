<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
    <yonyou:modal id="editAccountWin" modalClass="modal-md" title="编辑账户信息" editable="true">
        <form id="editAccountForm" class="form-horizontal form-bordered form-row-stripped" data-toggle="validator">
            <input type="hidden" id="id" name="id" class="switch" />
            <input type="hidden" id="updateNum" name="updateNum" />
            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label class="control-label col-md-2" for="name">用户名</label>
                        <div class="col-md-10">
                            <input class="form-control" id="name" name="name" placeholder="用户名..." required maxlength="40" />
                            <div class="form-control-focus"></div>
                            <span class="help-block help-info"><b>必填</b>，请输入最多40个字符</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label class="control-label col-md-2" for="personId">关联人员</label>
                        <div class="col-md-10">
                            <input type="hidden" id="personId" name="personId">
                            <div class="input-icon"  id="personSelector" style="cursor: pointer;">
                                <i class="fa fa-list"></i>
                                <input class="form-control" style="cursor: pointer;" id="personName" name="personName" placeholder="选择人员..." required readonly />
                            </div>
                            <div class="form-control-focus"></div>
                            <span class="help-block help-info"><b>必填</b>，请输入选择账户关联人员</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label class="control-label col-md-2" for="phone">电话号</label>
                        <div class="col-md-10">
                            <input type="tel" class="form-control" id="phone" name="phone" placeholder="电话号..." maxlength="20" />
                            <div class="form-control-focus"></div>
                            <span class="help-block help-info">可输入最多20个字符</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label class="control-label col-md-2" for="email">邮箱</label>
                        <div class="col-md-10">
                            <input type="email" class="form-control" id="email" name="email" placeholder="邮箱..." maxlength="40" />
                            <div class="form-control-focus"></div>
                            <span class="help-block help-info">可输入最多40个字符</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label class="control-label col-md-2" for="level">用户等级</label>
                        <div class="col-md-10">
                            <input type="number" class="form-control" id="level" name="level" placeholder="用户等级..." maxlength="9" max="999999999" min="0" />
                            <div class="form-control-focus"></div>
                            <span class="help-block help-info">可输入最大9位数字</span>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </yonyou:modal>
    <jsp:include page="person-select-modal-new.jsp" />
</html>
