<%--
  User: Sheng Baoyu
  Date: 2017-12-25
  Time: 16:23
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
    <yonyou:nav father="会议管理" model="会议安排" />
    <div class="row">
        <div class="col-md-12">
            <div class="portlet box blue-dark">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="fa fa-globe"></i>会议列表
                    </div>
                    <div class="actions">
                        <yonyou:create id="createMeeting" permission="system:meeting:create" />
                        <yonyou:update id="updateMeeting" permission="system:meeting:update" />
                        <yonyou:delete id="deleteMeeting" permission="system:meeting:delete" />
                    </div>
                </div>
                <div class="portlet-body">
                    <div id="tableToolbar">
                        <div class="col-md-12" style="padding-right:0;">
                            <div class="row">
                                <div class="col-md-6 pull-right" style="width: 200px;">
                                    <div class="form-group">
                                            <yonyou:date id="searchDate" name="searchDate" placeholder="会议日期"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <table id="meetingTable" data-toolbar="#tableToolbar">
                        <thead>
                        <tr role="row" class="heading">
                            <th data-field="checkbox" data-checkbox="true"></th>
                            <th data-field="id" data-formatter="indexFormatter">序号</th>
                            <th data-field="name" data-sortable="true">会议名称</th>
                            <th data-field="startTime" >开始时间</th>
                            <th data-field="endTime" >结束时间</th>
                            <th data-field="location">开会地点</th>
                            <th data-field="isArchived" data-formatter="booleanFormatter">是否归档</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="meeting-edit.jsp" />

    <script src="scripts/meeting/admin/meeting.js"></script>
</html>
