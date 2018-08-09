package com.yoogun.auth.domain.model;

import com.yoogun.core.domain.model.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * 权限-岗位-实体
 * @author Liu Jun at 2016-7-31 14:14:44
 * @since v1.0.0
 */
@Table(name = "AUTH_POST")
public class Post extends BaseEntity {

	private static final long serialVersionUID = 1L;

    /**
     * 岗位名称
     **/
    @NotBlank(message = "‘岗位名称’不能为空")
    @Length(max = 100, message = "‘岗位名称’内容长度不能超过100")
    @Column(name = "NAME")
    private String name;

    /**
     * 岗位编码
     **/
    @Length(max = 100, message = "‘岗位编码’内容长度不能超过100")
    @Column(name = "CODE")
    private String code;

    /**
     * 是否部门长
     **/
    @NotBlank(message = "‘是否部门长’不能为空")
    @Column(name = "IS_LEADER")
    private Boolean isLeader;

    /**
     * 部门ID
     **/
    @Length(max = 36, message = "‘部门ID’内容长度不能超过36")
    @Column(name = "DEPARTMENT_ID")
    private String departmentId;

    /**
     * 部门
     **/
    private Department department;

    /**
     * 备注
     */
    @Length(max = 1000, message = "‘备注’内容长度不能超过1000")
    @Column(name = "REMARK")
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getIsLeader() {
		return isLeader;
	}

	public void setIsLeader(Boolean isLeader) {
		this.isLeader = isLeader;
	}

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}