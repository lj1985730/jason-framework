package com.yoogun.base.domain.model;

import com.yoogun.core.domain.model.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

/**
 * 系统-文件-实体
 * @author Liu Jun at 2017-12-19 09:57:16
 * @since V1.0.0
 */
@Table(name = "SYS_FILE")
public class SysFile extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 名称（保存实际文件的名称）
	 */
	@Length(max = 500, message = "‘名称’内容长度不能超过500")
    @Column(name = "NAME")
	private String name;

	/**
	 * 扩展名
	 */
	@Length(max = 50, message = "‘扩展名’内容长度不能超过50")
    @Column(name = "EXTENSION")
	private String extension;

	/**
	 * MD5
	 */
    @Length(max = 50, message = "‘MD5’内容长度不能超过50")
    @Column(name = "MD5")
	private String md5;

    /**
     * 文件大小(B)
     */
    @Digits(integer = 20, fraction = 0, message = "‘文件大小(B)’整数位数不能超过20")
    @Column(name = "SIZE")
    private Long size;

    /**
     * 保存根地址
     */
    @Length(max = 2000, message = "‘保存根地址’内容长度不能超过2000")
    @Column(name = "ROOT_ADDRESS")
    private String rootAddress;

    /**
     * 保存相对地址
     */
    @Length(max = 2000, message = "‘保存相对地址’内容长度不能超过2000")
    @Column(name = "RELATIVE_ADDRESS")
    private String relativeAddress;

    /**
     * 上传方式，1 系统导入；2 外部程序导入；3 数据库初始化
     */
    @Length(max = 1, message = "‘上传方式’内容长度不能超过2000")
    @Column(name = "UPLOAD_MODE")
    private String uploadMode;

    /**
     * 权限字符串
     */
    @Length(max = 500, message = "‘权限字符串’内容长度不能超过500")
    @Column(name = "PERMISSION")
    private String permission;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getRootAddress() {
        return rootAddress;
    }

    public void setRootAddress(String rootAddress) {
        this.rootAddress = rootAddress;
    }

    public String getRelativeAddress() {
        return relativeAddress;
    }

    public void setRelativeAddress(String relativeAddress) {
        this.relativeAddress = relativeAddress;
    }

    public String getUploadMode() {
        return uploadMode;
    }

    public void setUploadMode(String uploadMode) {
        this.uploadMode = uploadMode;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}