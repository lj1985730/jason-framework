package com.yoogun.addrlist.site.domain.model;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import com.yoogun.core.domain.model.BaseEntity;
import org.hibernate.validator.constraints.Length;


/**
 * 通讯录-联系人-实体
 * @author Liu Jun at 2016-7-31 14:14:44
 * @since v1.0.0
 */
@Table(name = "ADDRLIST_CONTACT")
public class Contact extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 联系人名称
     **/
    @NotBlank(message = "‘联系人名称’不能为空")
    @Length(max = 32, message = "‘联系人名称’内容长度不能超过32")
    @Column(name = "NAME")
    private String name;

    /**
     * 头像
     **/
    @Column(name = "PHOTO")
    private byte[] photo;

    /**
     * 地址
     **/
    @Length(max =  256, message  = "'地址'内容长度不能超过256")
    @Column(name = "ADDRESS")
    private String address;

    /**
     * 工作单位及职务
     **/
    @Length(max =  256, message  = "'工作单位及职务'内容长度不能超过256")
    @Column(name = "UNIT")
    private String unit;

    /**
     * 办公电话
     **/
    @Length(max =  16, message  = "'办公电话'内容长度不能超过16")
    @Column(name = "TELEPHONE")
    private String telephone;

    /**
     * 手机号
     **/
    @Length(max =  32, message  = "'手机号'内容长度不能超过32")
    @Column(name = "MOBILE_PHONE")
    private String mobilePhone;

    /**
     * 邮箱
     **/
    @Length(max =  256, message  = "'邮箱'内容长度不能超过256")
    @Column(name = "EMAIL")
    private String email;

    /**
     * 备注
     **/
    @Length(max =  256, message  = "'备注'内容长度不能超过512")
    @Column(name = "REMARK")
    private String remark;

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUnit() { return unit; }

    public void setUnit(String unit) { this.unit = unit; }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
