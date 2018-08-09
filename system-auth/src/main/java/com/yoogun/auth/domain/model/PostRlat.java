package com.yoogun.auth.domain.model;

import com.yoogun.core.domain.model.BaseEntity;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * 权限-岗位上下级关系-实体
 * @author Liu Jun at 2018-4-2 10:53:53
 * @since v1.0.0
 */
@Table(name = "AUTH_POST_RLAT")
public class PostRlat extends BaseEntity {

    /**
     * 上级岗位ID
     **/
    @NotBlank(message = "‘上级岗位ID’不能为空")
    @Length(max = 36, message = "‘上级岗位ID’内容长度不能超过36")
    @Column(name = "SUPERIOR_ID")
    private String superiorId;

    /**
     * 上级岗位
     */
    private Post superior;

    /**
     * 下级岗位ID
     **/
    @NotBlank(message = "‘下级岗位ID’不能为空")
    @Length(max = 36, message = "‘下级岗位ID’内容长度不能超过36")
    @Column(name = "SUBORDINATE_ID")
    private String subordinateId;

    /**
     * 下级岗位
     */
    private Post subordinate;

    /**
     * 上级对下级有数据权限
     */
    @NotNull(message = "‘是否数据权限’不能为空")
    @Column(name = "DATA_PERMISSION")
    private Boolean dataPermission;

    /**
     * 上级对下级有审批权限
     */
    @NotNull(message = "‘是否审批权限’不能为空")
    @Column(name = "AUDIT_PERMISSION")
    private Boolean auditPermission;

    /**
     *  备注
     */
    private String remark;

    public String getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(String superiorId) {
        this.superiorId = superiorId;
    }

    public Post getSuperior() {
        return superior;
    }

    public void setSuperior(Post superior) {
        this.superior = superior;
    }

    public String getSubordinateId() {
        return subordinateId;
    }

    public void setSubordinateId(String subordinateId) {
        this.subordinateId = subordinateId;
    }

    public Post getSubordinate() {
        return subordinate;
    }

    public void setSubordinate(Post subordinate) {
        this.subordinate = subordinate;
    }

    public Boolean getDataPermission() {
        return dataPermission;
    }

    public void setDataPermission(Boolean dataPermission) {
        this.dataPermission = dataPermission;
    }

    public Boolean getAuditPermission() {
        return auditPermission;
    }

    public void setAuditPermission(Boolean auditPermission) {
        this.auditPermission = auditPermission;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 重写equals方法，除了判断Id外，还可判断上下级id
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != PostRlat.class) return false;
        PostRlat entity = (PostRlat) o;
        if(StringUtils.isNotBlank(entity.getId()) && StringUtils.isNotBlank(id)) {
            return Objects.equals(entity.getId(), id);  //如果两对像Id都存在则比较Id
        } else {
            return Objects.equals(superiorId, entity.getSuperiorId())   //否则比较上下级
                    && Objects.equals(subordinateId, entity.getSubordinateId());
        }
    }
}