package com.yoogun.generator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.sql.Types;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * entity生成器
 * @author Liu Jun
 * @since v1.0.0
 */
public class GenerateEntityFilePlugin extends PluginAdapter {

	/**
	 *  TreeEntity属性，如果isTreeEntity为true，则不生成属性
	 */
	private final String[] treeProps = new String[] {"NAME", "PARENT_ID", "SORT_NUMBER", "ENABLED"};

	/**
	 * 不生成属性的通用字段
	 */
	private final String[] ignoreProp = new String[] { "ID", "DELETED", "TENANT_ID", "LAST_MODIFY_ACCOUNT_ID", "LAST_MODIFY_TIME" };

	private Boolean isTreeEntity;

	/**
	 * 校验
	 */
	@Override
	public boolean validate(List<String> warnings) {
		isTreeEntity = Boolean.valueOf(properties.getProperty("treeEntity", "false"));
		return true;
	}

	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass thisClass, IntrospectedTable introspectedTable) {

		String modelName = JavaBeansUtil.getCamelCaseString(
				introspectedTable.getFullyQualifiedTable().getDomainObjectName(), true);

		//类注释
		thisClass.addJavaDocLine("");
		thisClass.addJavaDocLine("/**");
		thisClass.addJavaDocLine(" * " + modelName + "-实体<br/>");
		thisClass.addJavaDocLine(" * @author  " + properties.getProperty("developer") + " at " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
		thisClass.addJavaDocLine(" * @since v1.0.0");
		thisClass.addJavaDocLine(" */");

		this.addImportedTypes(thisClass);		// 增加引用内容

		this.addClassAnnotation(thisClass, introspectedTable);	// 增加类级注解

		// 添加继承基类
		if(isTreeEntity) {
			thisClass.setSuperClass(new FullyQualifiedJavaType("com.yoogun.core.domain.model.TreeEntity"));
		} else {
			thisClass.setSuperClass(new FullyQualifiedJavaType("com.yoogun.core.domain.model.BaseEntity"));
		}

		// 增加序列化ID
		Field serialVersionUID = new Field("serialVersionUID", new FullyQualifiedJavaType("long"));
		serialVersionUID.setVisibility(JavaVisibility.PUBLIC);
		serialVersionUID.setStatic(true);
		serialVersionUID.setFinal(true);
		serialVersionUID.setInitializationString("1L");
		thisClass.addField(serialVersionUID);

		return true;
	}

	/**
	 * 属性生成
	 */
	@Override
	public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {

		//过滤忽略属性
		if(Arrays.asList(ignoreProp).contains(introspectedColumn.getActualColumnName())) {
			return false;
		}

		//如果是TreeEntity，将TreeEntity的属性过滤
		if(isTreeEntity && Arrays.asList(treeProps).contains(introspectedColumn.getActualColumnName())) {
			return false;
		}

		addFieldComment(field, introspectedColumn.getRemarks());	//增加注释

		addFieldAnnotation(field, introspectedColumn);	//增加注解

		return true;
	}

	/**
	 * getting方法生成
	 */
	@Override
	public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
		
		//过滤忽略属性
		if(Arrays.asList(ignoreProp).contains(introspectedColumn.getActualColumnName())) {
			return false;
		}

		//如果是TreeEntity，将TreeEntity的属性过滤
		if(isTreeEntity && Arrays.asList(treeProps).contains(introspectedColumn.getActualColumnName())) {
			return false;
		}

		addGetterComment(method, introspectedColumn.getRemarks());//增加注释

		addGetterAnnotation(method, introspectedColumn);	//增加注解

		return true;
	}

	/**
	 * setting方法生成
	 */
	@Override
	public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
		
		//过滤忽略属性
		if(Arrays.asList(ignoreProp).contains(introspectedColumn.getActualColumnName())) {
			return false;
		}

		//如果是TreeEntity，将TreeEntity的属性过滤
		if(isTreeEntity && Arrays.asList(treeProps).contains(introspectedColumn.getActualColumnName())) {
			return false;
		}
		
		addSetterComment(method, introspectedColumn.getRemarks());	//增加注释

		addSetterAnnotation(method, introspectedColumn);	//增加注解

		return true;
	}

	/**
	 * 增加引用内容
	 * @param topLevelClass 当前类
	 */
	private void addImportedTypes(TopLevelClass topLevelClass) {
		// 添加包导入
		topLevelClass.addImportedType(new FullyQualifiedJavaType("javax.persistence.Column"));
		topLevelClass.addImportedType(new FullyQualifiedJavaType("javax.persistence.Table"));
		topLevelClass.addImportedType(new FullyQualifiedJavaType("javax.validation.constraints.Digits"));
		topLevelClass.addImportedType(new FullyQualifiedJavaType("javax.validation.constraints.NotBlank"));
		topLevelClass.addImportedType(new FullyQualifiedJavaType("javax.validation.constraints.NotNull"));
		topLevelClass.addImportedType(new FullyQualifiedJavaType("org.hibernate.validator.constraints.Length"));
		topLevelClass.addImportedType(new FullyQualifiedJavaType("com.fasterxml.jackson.annotation.JsonFormat"));
		if(isTreeEntity) {
			topLevelClass.addImportedType("com.yoogun.core.domain.model.TreeEntity");
		} else {
			topLevelClass.addImportedType("com.yoogun.core.domain.model.BaseEntity");
		}
	}

	/**
	 * 增加类注解
	 * @param thisClass 类对象
	 */
	private void addClassAnnotation(TopLevelClass thisClass, IntrospectedTable introspectedTable) {
		thisClass.addAnnotation("@Table(name = \"" + introspectedTable.getFullyQualifiedTable().getIntrospectedTableName() + "\")");
	}
	
	/**
	 * 增加属性注释
	 * @param field 属性对象
	 * @param remarks 数据字段定义
	 */
	private void addFieldComment(Field field, String remarks) {
		field.addJavaDocLine("/**\n\t * "  + (remarks != null ? remarks : field.getName()) + "\n\t **/");		//属性注释
	}

	/**
	 * 增加getter方法注释
	 * @param method getter方法对象
	 * @param remarks 数据字段定义
	 */
	private void addGetterComment(Method method, String remarks) {
		method.addJavaDocLine("/**\n\t * 获取"  + (remarks != null ? remarks : method.getReturnType().getShortName()) + "\n\t **/");		//属性注释
	}
	
	/**
	 * 增加setter方法注释
	 * @param method setter方法对象
	 * @param remarks 数据字段定义
	 */
	private void addSetterComment(Method method, String remarks) {
		method.addJavaDocLine("/**\n\t * 设置"  + (remarks != null ? remarks : method.getParameters().get(0).getName()) + "\n\t **/");
	}

	/**
	 * 增加属性注解
	 * @param field 属性对象
	 * @param introspectedColumn 字段对象
	 */
	private void addFieldAnnotation(Field field, IntrospectedColumn introspectedColumn) {
		String remarks = introspectedColumn.getRemarks();

		// 非空注解
		if (!introspectedColumn.isNullable()) {
			if(introspectedColumn.isJdbcCharacterColumn()) {	//char相关类型
				field.addAnnotation("@NotBlank(message = \"" + (remarks != null ? remarks : field.getName()) + "不能为空！\")");
			} else {
				field.addAnnotation("@NotNull(message = \"" + (remarks != null ? remarks : field.getName()) + "不能为空！\")");
			}
		}

		// 长度注解
		if (introspectedColumn.getLength() > 0 && !introspectedColumn.isJDBCDateColumn() && !introspectedColumn.isJDBCTimeColumn() && introspectedColumn.getJdbcType() != Types.TIMESTAMP) {
			//字符串长度
			if (introspectedColumn.isJdbcCharacterColumn() || introspectedColumn.isStringColumn()) {
				int length;
				if(!introspectedColumn.isIdentity()) {
					length = (int) Math.ceil(introspectedColumn.getLength());
				} else {
					length = introspectedColumn.getLength();
				}
				field.addAnnotation("@Length(max = " + length + (remarks != null ? ", message = \"‘" + remarks + "’长度不能超过" + length + "!\"" : "") + ")");
			//数值类型
			} else if(introspectedColumn.getJdbcType() == Types.DECIMAL) {
				int length = introspectedColumn.getLength();
				int fre = introspectedColumn.getScale();
				field.addAnnotation("@Digits(integer = " + length + ",fraction = " + fre + (remarks != null ? ", message = \"‘" + remarks + "’长度不能超过(" + length+", "+fre + ")\"" : "") + ")");
			}
		}

		//映射注解
		field.addAnnotation("@Column(name = \"" + introspectedColumn.getActualColumnName() + "\")");
	}
	
	/**
	 * 增加getter注解
	 * @param method getter方法对象
	 * @param introspectedColumn  字段对象
	 */
	private void addGetterAnnotation(Method method, IntrospectedColumn introspectedColumn) {
		if(introspectedColumn.getJdbcType() == Types.TIMESTAMP || introspectedColumn.isJDBCDateColumn() || introspectedColumn.isJDBCTimeColumn()) {
			method.addAnnotation("@JsonFormat(pattern = \"yyyy-MM-dd\", timezone = \"GMT+8\")");
		}
	}
	
	/**
	 * 增加setter注解
	 * @param method setter方法对象
	 * @param introspectedColumn  字段对象
	 */
	private void addSetterAnnotation(Method method, IntrospectedColumn introspectedColumn) {
		//do nothing
	}
	
	@Override
	public void initialized(IntrospectedTable introspectedTable) {
		JavaTypeResolverDefaultImpl jtdi = new JavaTypeResolverDefaultImpl();
		for (IntrospectedColumn column : introspectedTable.getAllColumns()) {
			if(column.isJDBCDateColumn() || column.isJDBCTimeColumn() || column.getJdbcType() == Types.TIMESTAMP){
				column.setJdbcType(Types.TIMESTAMP);
				column.setJdbcTypeName(jtdi.calculateJdbcTypeName(column));
			}
		}
	}
}