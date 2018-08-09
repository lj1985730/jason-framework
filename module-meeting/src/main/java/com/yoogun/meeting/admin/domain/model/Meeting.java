package com.yoogun.meeting.admin.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yoogun.core.domain.model.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 会议管理-会议-实体
 * @author Sheng Baoyu at 2017-12-25 14:10
 * @since v1.0.0
 */
@Table(name = "MEET_MEETING")
public class Meeting extends BaseEntity {


    private static final long serialVersionUID = 1L;


    /**
     * 会议名称
     **/
    @NotBlank(message = "‘会议名称’不能为空")
    @Length(max = 128, message = "‘会议名称’内容长度不能超过128")
    @Column(name = "NAME")
    private String name;


    /**
     * 开始时间
     */
    @Column(name = "START_TIME")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @Column(name = "END_TIME")
    private LocalDateTime endTime;

    /**
     * 开会地点
     */
    @Column(name = "LOCATION")
    @Length(max = 128, message = "‘开会地点’内容长度不能超过128")
    private String location;

    /**
     * 参会人员
     */
    @Column(name = "JOIN_PERSON")
    @Length(max = 256, message = "‘参会人员’内容长度不能超过256")
    private String joinPerson;

    /**
     * 所属项目
     */
    @Column(name = "BELONGS_PROJECT")
    @Length(max = 256, message = "‘所属项目’内容长度不能超过256")
    private String belongsProject;

    /**
     * 会议内容
     */
    @Column(name = "CONTENT")
    @Length(max = 1024, message = "‘会议内容’内容长度不能超过1024")
    private String content;

    /**
     * 会议纪要
     */
    @Column(name = "MINUTES")
    @Length(max = 2048, message = "‘会议纪要’内容长度不能超过2048")
    private String minutes;

    /**
     * 会议备注
     */
    @Column(name = "REMARK")
    @Length(max = 256, message = "‘会议备注’内容长度不能超过256")
    private String remark;

    /**
     * 是否归档
     */
    @Column(name = "IS_ARCHIVED")
    private Boolean isArchived;

    /**
     * 提醒时间
     */
    @Column(name = "REMIND_TIME")
    private String remindTime;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getJoinPerson() {
        return joinPerson;
    }

    public void setJoinPerson(String joinPerson) {
        this.joinPerson = joinPerson;
    }

    public String getBelongsProject() {
        return belongsProject;
    }

    public void setBelongsProject(String belongsProject) {
        this.belongsProject = belongsProject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }
}
