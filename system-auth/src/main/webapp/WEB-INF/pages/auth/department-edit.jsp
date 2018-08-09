<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
    <div class="portlet box blue-dark">
        <div class="portlet-title">
            <div class="caption">
                <i class="fa fa-info-circle"></i>部门详情
            </div>
            <div class="tools">
                <a class="collapse"></a>
                <a class="fullscreen"></a>
            </div>
            <div class="actions">
                <yonyou:btn-default id="formSubmitBtn" label="保存" icon="fa fa-check" />
            </div>
        </div>
        <div class="portlet-body form">
            <form id="editForm" class="form-horizontal" data-toggle="validator">
                <input type="hidden" id="id" name="id" />
                <input type="hidden" id="parentId" name="parentId" />
                <input type="hidden" id="level" name="level" />
                <div class="form-body">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-md-4 control-label" for="name">部门名称</label>
                                <div class="col-md-8">
                                    <input class="form-control" id="name" name="name" maxlength="40" required />
                                    <div class="form-control-focus"></div>
                                    <span class="help-block help-info">必填，请输入最多40个字符</span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-md-4 control-label" for="shortName">简称</label>
                                <div class="col-md-8">
                                    <input class="form-control" id="shortName" name="shortName" maxlength="30" />
                                    <div class="form-control-focus"></div>
                                    <span class="help-block help-info">请输入最多30个字符</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-md-4 control-label" for="enName">英文名字</label>
                                <div class="col-md-8">
                                    <input class="form-control" id="enName" name="enName" maxlength="40" />
                                    <div class="form-control-focus"></div>
                                    <span class="help-block help-info">请输入最多40个字符</span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-md-4 control-label" for="code">部门编码</label>
                                <div class="col-md-8">
                                    <input class="form-control" id="code" name="code" maxlength="30" />
                                    <div class="form-control-focus"></div>
                                    <span class="help-block help-info">请输入最多30个字符</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-md-4 control-label" for="phone">联系电话</label>
                                <div class="col-md-8">
                                    <input class="form-control" id="phone" name="phone" maxlength="30" />
                                    <div class="form-control-focus"></div>
                                    <span class="help-block help-info">请输入最多30个字符</span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-md-4 control-label" for="fax">传真</label>
                                <div class="col-md-8">
                                    <input class="form-control" id="fax" name="fax" maxlength="30" />
                                    <div class="form-control-focus"></div>
                                    <span class="help-block help-info">请输入最多30个字符</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-md-4 control-label" for="linkman">联系人</label>
                                <div class="col-md-8">
                                    <input class="form-control" id="linkman" name="linkman" maxlength="30" />
                                    <div class="form-control-focus"></div>
                                    <span class="help-block help-info">请输入最多30个字符</span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label col-md-4" for="email">邮箱</label>
                                <div class="col-md-8">
                                    <input class="form-control" type="email" id="email" name="email" maxlength="40" />
                                    <div class="form-control-focus"></div>
                                    <span class="help-block help-info">请输入最多40个字符</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-md-4 control-label" for="category">部门类型</label>
                                <div class="col-md-8">
                                    <select class="form-control" id="category" name="category" style="width: 100%;"></select>
                                    <div class="form-control-focus"></div>
                                    <span class="help-block help-info">请选择部门类型</span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-md-4 control-label" for="sortNumber">排序号</label>
                                <div class="col-md-8">
                                    <input class="form-control" type="number" id="sortNumber" name="sortNumber" max="99" step="1" />
                                    <div class="form-control-focus"></div>
                                    <span class="help-block help-info">请输入最大99的数字</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-md-4 control-label" for="email">是否启用</label>
                                <div class="col-md-8">
                                    <div class="md-radio-inline">
                                        <div class="md-radio">
                                            <input type="radio" id="enabled_true" name="enabled" value="true" class="md-radiobtn" checked>
                                            <label for="enabled_true">
                                                <span></span><span class="check"></span><span class="box"></span>是
                                            </label>
                                        </div>
                                        <div class="md-radio">
                                            <input type="radio" id="enabled_false" name="enabled" value="false" class="md-radiobtn">
                                            <label for="enabled_false">
                                                <span></span><span class="check"></span><span class="box"></span>否
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label class="col-md-2 control-label" for="remark">备注</label>
                                <div class="col-md-10">
                                    <textarea class="form-control" id="remark" name="remark" rows="3" maxlength="300"></textarea>
                                    <div class="form-control-focus"></div>
                                    <span class="help-block help-info">请输入最多300个字符</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</html>
