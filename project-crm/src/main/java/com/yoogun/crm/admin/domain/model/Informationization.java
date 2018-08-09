package com.yoogun.crm.admin.domain.model;

import com.yoogun.core.domain.model.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

/**
 * CRM-客户-信息化明细
 * @author  Liu Jun at 2018-7-20 09:54:32
 * @since v1.0.0
 */
@Table(name = "CRM_INFORMATIONIZATION")
public class Informationization extends BaseEntity {

    /**
     * 客户ID
     **/
    @Length(max = 36, message = "‘客户ID’长度不能超过36！")
    @Column(name = "CUSTOMER_ID")
    private String customerId;

    /**
     * 系统科目
     **/
    @Digits(integer = 9, fraction = 0, message = "‘系统科目’位数不能超过9！")
    @Column(name = "SUBJECT")
    private Integer subject;

    private String subjectText;

    /**
     *  是否已购买
     */
    @Column(name = "PURCHASED")
    private Boolean purchased;

    /**
     * 品牌
     **/
    @Digits(integer = 9, fraction = 0, message = "‘品牌’位数不能超过9！")
    @Column(name = "BRAND")
    private Integer brand;

    private String brandText;

    /**
     * 产品线
     **/
    @Digits(integer = 9, fraction = 0, message = "‘产品线’位数不能超过9！")
    @Column(name = "PRODUCT_LINE")
    private Integer productLine;

    private String productLineText;

    /**
     * 满意度
     **/
    @Digits(integer = 9, fraction = 0, message = "‘满意度’位数不能超过9！")
    @Column(name = "SATISFACTION")
    private Integer satisfaction;

    private String satisfactionText;

    /**
     * 运行状况
     **/
    @Digits(integer = 9, fraction = 0, message = "‘运行状况’位数不能超过9！")
    @Column(name = "STATE")
    private Integer state;

    private String stateText;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Integer getSubject() {
        return subject;
    }

    public void setSubject(Integer subject) {
        this.subject = subject;
    }

    public String getSubjectText() {
        return subjectText;
    }

    public void setSubjectText(String subjectText) {
        this.subjectText = subjectText;
    }

    public Boolean getPurchased() {
        return purchased;
    }

    public void setPurchased(Boolean purchased) {
        this.purchased = purchased;
    }

    public Integer getBrand() {
        return brand;
    }

    public void setBrand(Integer brand) {
        this.brand = brand;
    }

    public String getBrandText() {
        return brandText;
    }

    public void setBrandText(String brandText) {
        this.brandText = brandText;
    }

    public Integer getProductLine() {
        return productLine;
    }

    public void setProductLine(Integer productLine) {
        this.productLine = productLine;
    }

    public String getProductLineText() {
        return productLineText;
    }

    public void setProductLineText(String productLineText) {
        this.productLineText = productLineText;
    }

    public Integer getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(Integer satisfaction) {
        this.satisfaction = satisfaction;
    }

    public String getSatisfactionText() {
        return satisfactionText;
    }

    public void setSatisfactionText(String satisfactionText) {
        this.satisfactionText = satisfactionText;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStateText() {
        return stateText;
    }

    public void setStateText(String stateText) {
        this.stateText = stateText;
    }
}
