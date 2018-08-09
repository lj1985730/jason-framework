package com.yoogun.core.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yoogun.core.infrastructure.Property;
import com.yoogun.core.infrastructure.Reflections;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 实体基类，提供被其他实体类继承<p>
 * @author Liu jun
 */
public class BaseEntity implements Serializable {

	protected static final long serialVersionUID = 1L;

	/**
	 * 删除字段属性名
	 */
	public static final String DELETE_PARAM = "DELETED";

	/**
	 * 主键
	 */
	@Id
	protected String id;

	/**
	 * 租户ID
	 */
	@Column(name = "TENANT_ID")
	private String tenantId;

	/**
	 * 操作人账户Id
	 */
	@Column(name = "LAST_MODIFY_ACCOUNT_ID")
	private String lastModifyAccountId;

	/**
	 * 操作时间
	 */
	@Column(name = "LAST_MODIFY_TIME")
	@NotNull(message = "操作时间不可为空！")
	private LocalDateTime lastModifyTime;

    /**
     * 是否删除
     */
	@Column(name = "DELETED")
	@NotNull(message = "删除标记不可为空！")
	private Boolean deleted;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getLastModifyAccountId() {
		return lastModifyAccountId;
	}

	public void setLastModifyAccountId(String lastModifyAccountId) {
		this.lastModifyAccountId = lastModifyAccountId;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public LocalDateTime getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(LocalDateTime lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * 通用toString方法
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	/**
	 * 通用hashCode方法
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/**
	 * 通用equals方法，只判断ID是否相等
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BaseEntity entity = (BaseEntity) o;
		return Objects.equals(id, entity.id);
	}

	/**
	 * 加载全部关联数据库的属性和数据库字段名称
	 * @return 关联数据库的属性和数据库字段名称
	 */
	public static <T extends BaseEntity> List<Property> loadProperties(Class<T> clazz) {
		List<Property> properties = loadLocalProperties(clazz);
		properties.addAll(loadLocalProperties(BaseEntity.class));

		if(TreeEntity.class.isAssignableFrom(clazz)) {
			properties.addAll(loadLocalProperties(TreeEntity.class));
		}

		return properties;
	}

	/**
	 * 加载全部关联数据库的属性和数据库字段名称
	 * @return 关联数据库的属性和数据库字段名称
	 */
	public List<Property> loadProperties() {
		List<Property> properties = loadLocalProperties(this.getClass());
		properties.addAll(loadLocalProperties(BaseEntity.class));

		if(this instanceof TreeEntity) {
			properties.addAll(loadLocalProperties(TreeEntity.class));
		}

		return properties;
	}

	/**
	 * 读取类的关联字段属性
	 * @param clazz 类型
	 */
	public static <T extends BaseEntity> List<Property> loadLocalProperties(Class<T> clazz) {
		List<Property> properties = new ArrayList<>();
		Field[] fields = clazz.getDeclaredFields();
		Property property;
		for(Field field : fields) {
			if(field.isAnnotationPresent(Column.class)) {
				Column column = field.getAnnotation(Column.class);
				property = new Property();
				property.setField(field.getName());
				property.setColumn(column.name());
				property.setType(field.getType());
				properties.add(property);
			}
		}
		return properties;
	}

	/**
	 * 加载表名
	 * @return 表名
	 */
	@JsonIgnore
	public String getTableName() {
		Object object = Reflections.readClassAnnotationProp(this.getClass(), Table.class, "name");
		return object == null ? "" : object.toString();
	}

	/**
	 * 加载列名
	 * @param fieldName 属性名
	 * @return 表名
	 */
	public String getColName(String fieldName) {
		return Reflections.readFieldAnnotationProp(
				this.getClass(), fieldName, Column.class, "name")
				.toString();
	}

	/**
	 * 判断当前属性值是否为空
	 * @param fieldName 属性名
	 * @return 是否为空
	 */
	public boolean isValueNull(String fieldName) {
		Object fieldValue = Reflections.getFieldValue(this, fieldName);
		return fieldValue == null;
	}
}