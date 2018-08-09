package com.yoogun.auth.domain.model;

import com.yoogun.core.domain.model.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 权限-角色按钮关系-实体
 * @author Liu Jun at 2016-7-31 14:14:44
 * @since v1.0.0
 */
@Table(name = "AUTH_ROLE_BUTTON")
public class RoleButton extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 角色ID
     */
    @NotBlank(message = "‘角色ID’不能为空")
    @Length(max = 36, message = "‘角色ID’内容长度不能超过36")
	@Column(name = "ROLE_ID")
	private String roleId;

	/**
	 * 菜单
	 */
	@NotNull(message = "‘菜单’不能为空")
	private Button button;

    /**
     * 按钮ID
     */
	@NotBlank(message = "‘按钮ID’不能为空")
    @Length(max = 36, message = "‘按钮ID’内容长度不能超过36")
	@Column(name = "BUTTON_ID")
    private String buttonId;

    public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public Button getButton() {
		return button;
	}

	public void setButton(Button button) {
		this.button = button;
	}

	public String getButtonId() {
		return buttonId;
	}

	public void setButtonId(String buttonId) {
		this.buttonId = buttonId;
	}
}