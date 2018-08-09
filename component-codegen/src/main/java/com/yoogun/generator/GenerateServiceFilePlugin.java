package com.yoogun.generator;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.mybatis.generator.internal.util.StringUtility;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * service层生成器
 * @author	Liu Jun
 * @since v1.0.0
 */
public class GenerateServiceFilePlugin extends PluginAdapter {

	private String targetProject;
	private String targetPackage;
	private String developer;

	private Boolean isTreeEntity;

	/**
	 * 预校验
	 */
	@Override
	public boolean validate(List<String> warnings) {
		this.targetProject = properties.getProperty("targetProject");
		this.targetPackage = properties.getProperty("targetPackage");
		this.developer = properties.getProperty("developer");
		this.isTreeEntity = Boolean.valueOf(properties.getProperty("treeEntity", "false"));
		String moduleNature = properties.getProperty("moduleNature");

		boolean isValid = StringUtility.stringHasValue(this.targetProject)
				&& StringUtility.stringHasValue(this.targetPackage)
				&& StringUtility.stringHasValue(this.developer)
				&& StringUtility.stringHasValue(moduleNature);
		if (!isValid) {
			warnings.add("GenerateServiceFilePlugin: property not found");
			return false;
		}

		this.targetPackage = this.targetPackage.replace("XXX", moduleNature);	//拼接模块性质包层

		return true;
	}

	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {

		String modelName = JavaBeansUtil.getCamelCaseString(
				introspectedTable.getFullyQualifiedTable().getDomainObjectName(), true);

		// 获取model的配置
		JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = context.getJavaModelGeneratorConfiguration();
		String targetJavaModelPackage = javaModelGeneratorConfiguration.getTargetPackage();
		String entityClass = targetJavaModelPackage + "." + modelName;
		String voClass = targetJavaModelPackage.replace("domain.model", "application.vo.") + modelName + "Vo";

		// 生成Service文件
		ShellCallback shellCallback = new DefaultShellCallback(true);
		File directory;
		try {
			FullyQualifiedJavaType serviceType = new FullyQualifiedJavaType(this.targetPackage + "." + modelName + "Service");
			TopLevelClass thisClass = new TopLevelClass(serviceType);	//类对象

			thisClass.addFileCommentLine("/*");
			thisClass.addFileCommentLine(" * " + modelName + "Service.java");
			thisClass.addFileCommentLine(" * Copyright(C) 2018 大连用友软件有限公司");
			thisClass.addFileCommentLine(" * All right reserved.");
			thisClass.addFileCommentLine(" */");

			thisClass.setVisibility(JavaVisibility.PUBLIC);
			thisClass.addJavaDocLine("");
			thisClass.addJavaDocLine("/**");
			thisClass.addJavaDocLine(" * " + modelName + "-应用业务<br/>");
			thisClass.addJavaDocLine(" * 主要业务逻辑代码");
			thisClass.addJavaDocLine(" * @author " + this.developer + " at " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
			thisClass.addJavaDocLine(" * @since v1.0.0");
			thisClass.addJavaDocLine(" */");

			this.addImportedTypes(thisClass, entityClass, isTreeEntity);		// 增加引用内容

			this.addClassAnnotation(thisClass);	// 增加类级注解

			// 添加继承基类
			if(isTreeEntity) {
				thisClass.setSuperClass(new FullyQualifiedJavaType("com.yoogun.auth.infrastructure.BaseAuthTreeService<" + modelName + ">"));
			} else {
				thisClass.setSuperClass(new FullyQualifiedJavaType("com.yoogun.auth.infrastructure.BaseAuthService<" + modelName + ">"));
			}

			this.addPageSearchMethod(thisClass, entityClass, voClass);

//			this.addGenTotalRowMethod(thisClass);	// 添加genTotalRow函数（合计行）
//			this.addBeforeCreateMethod(thisClass, entityClass);	// 增加新增前置方法
//			this.addBeforeModifyMethod(thisClass, entityClass)	;	//增加更新前置方法
//			this.addBeforeRemoveMethod(thisClass, entityClass);	// 增加删除前置方法
//			this.addBeforeRemoveByIdMethod(thisClass, entityClass);	// 增加删除前置方法

			String source = thisClass.getFormattedContent();
			
			//写文件
			directory = shellCallback.getDirectory(targetProject, targetPackage);
			File targetFile = new File(directory, modelName + "Service.java");
			GeneratorUtil.writeFile(targetFile, source, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 增加默认分页搜索方法
	 * @param serviceClass	service的类对象
	 * @param entityClass	实体名
	 * @param voClass	VO名
	 */
	private void addPageSearchMethod(TopLevelClass serviceClass, String entityClass, String voClass) {
		Method method = new Method("pageSearch");
		method.setVisibility(JavaVisibility.PUBLIC);

		method.addAnnotation("@Transactional(propagation = Propagation.NOT_SUPPORTED)");	// 注解

		method.setReturnType(	//返回值
				new FullyQualifiedJavaType("com.yoogun.core.application.dto.Page<" + entityClass + ">"));

		Parameter param = new Parameter(new FullyQualifiedJavaType(voClass), "vo");	//形参
		method.addParameter(param);

		method.addBodyLine("SQL sql = new SQL().SELECT(\"*\").FROM(tableName);");	//方法体
		method.addBodyLine("sql.WHERE(BaseEntity.DELETE_PARAM + \" = '\" + BooleanTypeHandler.BOOL_FALSE + \"'\");");
		method.addBodyLine("sql.WHERE(\"TENANT_ID = '\" + AuthCache.tenantId() + \"'\");");
		method.addBodyLine("");
		method.addBodyLine("//过滤条件");
		method.addBodyLine("");
		method.addBodyLine("//模糊查询,增加了AND，放在条件最后。否则后续条件还需要先加AND");
		method.addBodyLine("if(StringUtils.isNotBlank(vo.getSearch())) {");
		method.addBodyLine("sql.AND();");
		method.addBodyLine("sql.WHERE(\"XXX like '%\" + vo.getSearch() + \"%' \" +");
		method.addBodyLine("\"OR XXX like '%\" + vo.getSearch() + \"%' \");");
		method.addBodyLine("}");
		method.addBodyLine("");
		method.addBodyLine("//排序");
		method.addBodyLine("if(StringUtils.isNotBlank(vo.getSort()) && StringUtils.isNotBlank(vo.getOrder())) {");
		method.addBodyLine("try {");
		method.addBodyLine("sql.ORDER_BY(this.entityClass.newInstance().getColName(vo.getSort()) + \" \" + vo.getOrder());");
		method.addBodyLine("} catch (Exception e) {");
		method.addBodyLine("throw new BusinessException(\"获取'\" + this.entityClass + \"'的实例出错！\", e);");
		method.addBodyLine("}");
		method.addBodyLine("} else {");
		method.addBodyLine("//默认排序");
		method.addBodyLine("}");
		method.addBodyLine("");
		method.addBodyLine("return this.pageSearch(sql, vo.getOffset(), vo.getLimit());");

		serviceClass.addMethod(method);
	}

	/**
	 * 增加设置合计行方法
	 * @param serviceClass	service的类对象
	 */
	private void addGenTotalRowMethod(TopLevelClass serviceClass) {
		Method method = new Method("genTotalRow");
		method.setVisibility(JavaVisibility.PUBLIC);

		method.addAnnotation("@Override");	// 注解

		method.setReturnType(	//返回值
				new FullyQualifiedJavaType("java.util.List<java.util.Map<java.lang.String, java.lang.String>>"));

		method.addBodyLine("return null;	//自定义合计行内容");	//方法体

		serviceClass.addMethod(method);
	}

	/**
	 * 增加新增前置方法
	 * @param serviceClass	service的类对象
	 * @param entityClass	实体类的全限定名
	 */
	private void addBeforeCreateMethod(TopLevelClass serviceClass, String entityClass) {
		Method method = new Method("beforeCreate");
		method.setVisibility(JavaVisibility.PUBLIC);

		method.addAnnotation("@Override");		// 注解

		Parameter param = new Parameter(new FullyQualifiedJavaType(entityClass), "entity");	//形参
		method.addParameter(param);

		//方法体
		method.addBodyLine("Account account = AuthCache.account();");
		method.addBodyLine("if(account == null) {");
		method.addBodyLine("throw new AuthenticationException(\"用户未登录\");");
		method.addBodyLine("}");
		method.addBodyLine("entity.setLastModifyAccountId(account.getId());");
		method.addBodyLine("entity.setTenantId(AuthCache.tenantId());");

		serviceClass.addMethod(method);
	}

	/**
	 * 增加更新前置方法
	 * @param serviceClass	service的类对象
	 * @param entityClass	实体类的全限定名
	 */
	private void addBeforeModifyMethod(TopLevelClass serviceClass, String entityClass) {
		Method method = new Method("beforeModify");
		method.setVisibility(JavaVisibility.PUBLIC);

		method.addAnnotation("@Override");	// 注解

		Parameter param = new Parameter(new FullyQualifiedJavaType(entityClass), "entity");	//形参
		method.addParameter(param);

		//方法体
		method.addBodyLine("Account account = AuthCache.account();");
		method.addBodyLine("if(account == null) {");
		method.addBodyLine("throw new AuthenticationException(\"用户未登录\");");
		method.addBodyLine("}");
		method.addBodyLine("entity.setLastModifyAccountId(account.getId());");
		method.addBodyLine("entity.setTenantId(AuthCache.tenantId());");

		serviceClass.addMethod(method);
	}

	/**
	 * 增加删除(按对象删除)前置方法
	 * @param serviceClass	service的类对象
	 * @param entityClass	实体类的全限定名
	 */
	private void addBeforeRemoveMethod(TopLevelClass serviceClass, String entityClass) {
		Method method = new Method("beforeRemove");
		method.setVisibility(JavaVisibility.PUBLIC);

		method.addAnnotation("@Override");	// 注解

		Parameter param = new Parameter(new FullyQualifiedJavaType(entityClass), "entity");	//形参
		method.addParameter(param);

		//方法体
		method.addBodyLine("Account account = AuthCache.account();");
		method.addBodyLine("if(account == null) {");
		method.addBodyLine("throw new AuthenticationException(\"用户未登录\");");
		method.addBodyLine("}");
		method.addBodyLine("entity.setLastModifyAccountId(account.getId());");
		method.addBodyLine("entity.setTenantId(AuthCache.tenantId());");

		serviceClass.addMethod(method);
	}

	/**
	 * 增加删除(按id删除)前置方法
	 * @param serviceClass	service的类对象
	 * @param entityClass	实体类的全限定名
	 */
	private void addBeforeRemoveByIdMethod(TopLevelClass serviceClass, String entityClass) {
		Method method = new Method("beforeRemove");
		method.setVisibility(JavaVisibility.PUBLIC);

		method.addAnnotation("@Override");	// 注解

		Parameter param = new Parameter(new FullyQualifiedJavaType("java.lang.String"), "id");	//形参
		method.addParameter(param);

		//方法体
		method.addBodyLine("// 根据业务需求自定义内容");

		serviceClass.addMethod(method);
	}

	/**
	 * 增加import包
	 * @param serviceClass	service的类对象
	 * @param entityClass	实体类的全限定名
	 */
	private void addImportedTypes(TopLevelClass serviceClass, String entityClass, Boolean isTreeEntity) {

		serviceClass.addImportedType("com.yoogun.auth.infrastructure.AuthCache");
		// 添加继承基类
		if(isTreeEntity) {
			serviceClass.addImportedType("com.yoogun.auth.infrastructure.BaseAuthTreeService");
		} else {
			serviceClass.addImportedType("com.yoogun.auth.infrastructure.BaseAuthService");
		}
		serviceClass.addImportedType("com.yoogun.core.application.dto.Page");
		serviceClass.addImportedType("com.yoogun.core.domain.model.BaseEntity");
		serviceClass.addImportedType("com.yoogun.core.infrastructure.BooleanTypeHandler");
		serviceClass.addImportedType("com.yoogun.core.infrastructure.exception.BusinessException;");
		serviceClass.addImportedType("org.apache.commons.lang3.StringUtils");
		serviceClass.addImportedType("org.apache.ibatis.jdbc.SQL");
		serviceClass.addImportedType("org.springframework.stereotype.Service");
		serviceClass.addImportedType("org.springframework.transaction.annotation.Propagation");
		serviceClass.addImportedType("org.springframework.transaction.annotation.Transactional");

		serviceClass.addImportedType(entityClass);
		String simpleEntityName = entityClass.substring(entityClass.lastIndexOf("."));
		serviceClass.addImportedType(targetPackage.replace(".service", ".vo") + simpleEntityName + "Vo");

	}

	/**
	 * 增加类注解
	 * @param thisClass 类对象
	 */
	private void addClassAnnotation(TopLevelClass thisClass) {
		thisClass.addAnnotation("@Service");	// 类注解
	}
}