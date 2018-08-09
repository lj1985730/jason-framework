package com.yoogun.auth.domain.model;

import com.yoogun.core.domain.model.TreeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 权限-租户（企业）-实体
 * @author Liu Jun
 * @since v1.0.0
 */
@Table(name = "AUTH_TENANT")
public class Tenant extends TreeEntity {

    /**
     * 英文名称
     */
    @Length(max = 100, message = "‘英文名称’内容长度不能超过100")
    @Column(name = "EN_NAME")
    private String enName;

    /**
     * 部门简称
     */
    @Length(max = 100, message = "‘部门简称’内容长度不能超过100")
    @Column(name = "SHORT_NAME")
    private String shortName;

    /**
     * 统一社会信用代码
     */
    @Pattern(regexp = "[0-9A-HJ-NPQRTUWXY]{2}\\d{6}[0-9A-HJ-NPQRTUWXY]{10}", message = "‘统一社会信用代码’格式不正确")
    @Column(name = "USCC")
    private String uscc;

    /**
     * 系统内代码
     */
    @Length(max = 100, message = "‘系统内代码’内容长度不能超过100")
    @Column(name = "CODE")
    private String code;

    /**
     * 类型
     */
    @Length(max = 200, message = "‘类型’内容长度不能超过200")
    @Column(name = "CATEGORY")
    private String category;

    /**
     * 类型文本
     */
    private String categoryText;

    /**
     * 住所
     */
    @Length(max = 200, message = "‘住所’内容长度不能超过200")
    @Column(name = "LOCATION")
    private String location;

    /**
     * 法定代表人
     */
    @Length(max = 200, message = "‘法定代表人’内容长度不能超过200")
    @Column(name = "LEGAL_REPRESENTATIVE")
    private String legalRepresentative;

    /**
     * 注册资本
     */
    @Digits(integer = 14, fraction = 1, message = "‘注册资本’数值位数不能超过14")
    @Column(name = "REGISTERED_CAPITAL")
    private BigDecimal registeredCapital;

    /**
     * 成立日期
     */
    @Column(name = "ESTABLISHMENT_DATE")
    private LocalDate establishmentDate;

    /**
     * 营业期限开始日期
     */
    @Column(name = "BUSINESS_TERM_START")
    private LocalDate businessTermStart;

    /**
     * 营业期限截止日期
     */
    @Column(name = "BUSINESS_TERM_END")
    private LocalDate businessTermEnd;

    /**
     * 经营范围
     */
    @Length(max = 1000, message = "‘经营范围’内容长度不能超过1000")
    @Column(name = "BUSINESS_SCOPE")
    private String businessScope;

    /**
     * 联系人
     */
    @Length(max = 100, message = "‘联系人’内容长度不能超过100")
    @Column(name = "LINKMAN")
    private String linkman;

    /**
     * 传真
     */
    @Length(max = 100, message = "‘传真’内容长度不能超过100")
    @Column(name = "FAX")
    private String fax;

    /**
     * 电话
     */
    @Length(max = 100, message = "‘电话’内容长度不能超过100")
    @Column(name = "PHONE")
    private String phone;

    /**
     * 电子邮件
     */
    @Length(max = 100, message = "‘电子邮件’内容长度不能超过100")
    @Column(name = "EMAIL")
    private String email;

    /**
     * 等级
     */
    @Digits(integer = 2, fraction = 0, message = "‘等级’数值位数不能超过2")
    @Column(name = "LEVEL")
    private Integer level;

    /**
     * 备注
     */
    @Length(max = 1000, message = "‘备注’内容长度不能超过1000")
    @Column(name = "REMARK")
    private String remark;

    public Tenant getParent() {
        return (Tenant)parent;
    }

    public <E extends TreeEntity> void setParent(E parent) {
        this.parent = parent;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getUscc() {
        return uscc;
    }

    public void setUscc(String uscc) {
        this.uscc = uscc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryText() {
        return categoryText;
    }

    public void setCategoryText(String categoryText) {
        this.categoryText = categoryText;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
    }

    public BigDecimal getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(BigDecimal registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public LocalDate getEstablishmentDate() {
        return establishmentDate;
    }

    public void setEstablishmentDate(LocalDate establishmentDate) {
        this.establishmentDate = establishmentDate;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public LocalDate getBusinessTermStart() {
        return businessTermStart;
    }

    public void setBusinessTermStart(LocalDate businessTermStart) {
        this.businessTermStart = businessTermStart;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public LocalDate getBusinessTermEnd() {
        return businessTermEnd;
    }

    public void setBusinessTermEnd(LocalDate businessTermEnd) {
        this.businessTermEnd = businessTermEnd;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
