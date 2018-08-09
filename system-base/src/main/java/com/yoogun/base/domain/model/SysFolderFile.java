package com.yoogun.base.domain.model;

import com.yoogun.core.domain.model.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 系统-文件夹与文件关系-实体
 * @author Liu Jun at 2017-12-19 10:23:52
 * @since V1.0.0
 */
@Table(name = "SYS_FOLDER_FILE")
public class SysFolderFile extends BaseEntity {

	private static final long serialVersionUID = 1L;

    /**
     * 文件ID
     */
    @Length(max = 36, message = "‘文件ID’内容长度不能超过36")
    @Column(name = "FILE_ID")
    private String fileId;

    /**
     * 文件名称（系统显示名称）
     */
    @Length(max = 500, message = "‘文件名称’内容长度不能超过500")
    @Column(name = "FILE_NAME")
    private String fileName;

	/**
	 * 业务数据ID
	 */
	@Length(max = 36, message = "‘业务数据ID’内容长度不能超过36")
    @Column(name = "BUSINESS_DATA_ID")
	private String businessDataId;

    /**
     * 业务键
     */
    @Length(max = 500, message = "‘业务键’内容长度不能超过500")
    @Column(name = "BUSINESS_KEY")
    private String businessKey;

    /**
     * 文件夹ID
     */
    @Length(max = 500, message = "‘文件夹ID’内容长度不能超过36")
    @Column(name = "FOLDER_ID")
    private String folderId;

    /**
     * 权限字符串
     */
    @Length(max = 500, message = "‘权限字符串’内容长度不能超过500")
    @Column(name = "PERMISSION")
    private String permission;

    private String extension;   //扩展名

    private Long size;  //文件大小

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBusinessDataId() {
        return businessDataId;
    }

    public void setBusinessDataId(String businessDataId) {
        this.businessDataId = businessDataId;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}