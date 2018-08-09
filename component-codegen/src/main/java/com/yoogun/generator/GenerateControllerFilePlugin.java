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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 控制层生成器
 * @author Liu Jun
 * @since v1.0.0
 */
public class GenerateControllerFilePlugin extends PluginAdapter {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	private String targetProject;
	private String targetPackage;
	private String moduleName;	//模块名称
	private String developer;
	private String moduleNature;

	private Parameter request = new Parameter(new FullyQualifiedJavaType("javax.servlet.http.HttpServletRequest"), "request");
	private Parameter response = new Parameter(new FullyQualifiedJavaType("javax.servlet.http.HttpServletResponse"), "response");

	/**
	 * 预校验
	 */
	@Override
	public boolean validate(List<String> warnings) {
		this.targetProject = properties.getProperty("targetProject");
		this.targetPackage = properties.getProperty("targetPackage");
		this.moduleName = properties.getProperty("moduleName");
		this.developer = properties.getProperty("developer");
		this.moduleNature = properties.getProperty("moduleNature");

		boolean isValid = StringUtility.stringHasValue(this.targetProject)
				&& StringUtility.stringHasValue(this.targetPackage)
				&& StringUtility.stringHasValue(this.moduleName)
				&& StringUtility.stringHasValue(this.developer)
				&& StringUtility.stringHasValue(this.moduleNature);

		if (!isValid) {
			warnings.add("GenerateControllerFilePlugin: property not found");
			return false;
		}

		this.targetPackage = this.targetPackage.replace("XXX", this.moduleNature);	//拼接模块性质包层

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

		String voClass = this.targetPackage.replace(".controller", ".vo.") + modelName + "Vo";
		String serviceClass = this.targetPackage.replace(".controller", ".service.") + modelName + "Service";

		// 生成Controller文件
		ShellCallback shellCallback = new DefaultShellCallback(true);
		File directory;
		try {
			FullyQualifiedJavaType controllerType = new FullyQualifiedJavaType(this.targetPackage + "." + modelName + "Controller");
			TopLevelClass thisClass = new TopLevelClass(controllerType);

			thisClass.addFileCommentLine("/*");
			thisClass.addFileCommentLine(" * " + modelName + "Controller.java");
			thisClass.addFileCommentLine(" * Copyright(C) 2018 大连用友软件有限公司");
			thisClass.addFileCommentLine(" * All right reserved.");
			thisClass.addFileCommentLine(" */");

			thisClass.addJavaDocLine("");
			thisClass.addJavaDocLine("/**");
			thisClass.addJavaDocLine(" * " + modelName + "-控制器<br/>");
			thisClass.addJavaDocLine(" * @author " + this.developer + " at " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
			thisClass.addJavaDocLine(" * @since v1.0.0");
			thisClass.addJavaDocLine(" */");

			this.addImportedTypes(thisClass, entityClass);	// 添加导入包

			this.addClassAnnotation(thisClass, modelName);	// 增加类级注解

			// 添加抽象类
			thisClass.setSuperClass(new FullyQualifiedJavaType("com.yoogun.auth.infrastructure.BaseCurdController<" + modelName + ">"));
			
			//注入对应service
			Field service = new Field("service", new FullyQualifiedJavaType(serviceClass));
			service.setVisibility(JavaVisibility.PRIVATE);
			service.addAnnotation("@Resource");
			thisClass.addField(service);

			String lowerModelName = JavaBeansUtil.getCamelCaseString(modelName, false);
			
			this.addHomeMethod(thisClass, lowerModelName);	// home
			this.addSearchPageMethod(thisClass, modelName);	// searchPage
//			this.addCreateMethod(thisClass, entityClass, lowerModelName);	// create
//			this.addModifyMethod(thisClass, entityClass, lowerModelName);	// modify
//			this.addRemoveMethod(thisClass, entityClass, lowerModelName);	// remove

			String source = thisClass.getFormattedContent();

			//写文件
			directory = shellCallback.getDirectory(targetProject, targetPackage);
			File targetFile = new File(directory, modelName + "Controller.java");
			GeneratorUtil.writeFile(targetFile, source, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 写入home方法
	 * @param controllerClass	控制层类对象
	 * @param lowerModelName	首字母小写模块名称
	 */
	private void addHomeMethod(TopLevelClass controllerClass, String lowerModelName) {
		// 方法声明
		Method method = new Method("home");
		method.setVisibility(JavaVisibility.PUBLIC);
		// 注释
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" * 页面的首页");
		method.addJavaDocLine(" */");

		// 注解
		method.addAnnotation("@RequiresPermissions(\"" + this.moduleName + ":" + lowerModelName + ":view\")");
		method.addAnnotation("@RequestMapping(value = {\"/homeView\" }, method = RequestMethod.GET)");
		//返回值
		method.setReturnType(new FullyQualifiedJavaType("java.lang.String"));
		// 方法体
		method.addBodyLine("return pageView(\"/" + this.moduleName + "/" + this.moduleNature + "/" + lowerModelName+ "\");");
		// 写入类
		controllerClass.addMethod(method);
	}

	/**
	 * 增加查询分页列表方法
	 * @param controllerClass	当前controller类对象
	 * @param modelName		实体类名称
	 */
	private void addSearchPageMethod(TopLevelClass controllerClass, String modelName) {
		// 方法声明
		Method method = new Method("searchPage");
		method.setVisibility(JavaVisibility.PUBLIC);
		// 注释
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" * 分页查询列表");
		method.addJavaDocLine(" * @return 分页数据");
		method.addJavaDocLine(" */");
		// 注解
		method.addAnnotation("@RequestMapping(value = { \"/\" }, method = RequestMethod.GET)");
		method.addAnnotation("@ResponseBody");
		// 参数、返回值
		method.addParameter(this.request);
		method.setReturnType(new FullyQualifiedJavaType("com.yoogun.core.application.dto.JsonResult"));
		// 方法体
		method.addBodyLine(modelName + "Vo vo = new " + modelName + "Vo(request);");
		method.addBodyLine("return new JsonResult(service.pageSearch(vo));");
		// 写入类
		controllerClass.addMethod(method);
	}

	/**
	 * 增加create方法
	 * @param controllerClass	当前controller类对象
	 * @param entityClass		实体类全限定名
	 * @param lowerModelName		首字母小写模块名称
	 */
	private void addCreateMethod(TopLevelClass controllerClass, String entityClass, String lowerModelName) {
		// 方法声明
		Method method = new Method("create");
		method.setVisibility(JavaVisibility.PUBLIC);
		// 注释
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" * 新增数据");
		method.addJavaDocLine(" * @param entity 待新增PO");
		method.addJavaDocLine(" * @return 操作结果");
		method.addJavaDocLine(" */");
		// 注解
		method.addAnnotation("@RequiresPermissions(\"" + this.moduleName + ":" + lowerModelName + ":create\")");
		method.addAnnotation("@RequestMapping(value = { \"/\" }, method = RequestMethod.POST)");
		method.addAnnotation("@ResponseBody");
		// 参数、返回值
		method.addParameter(new Parameter(new FullyQualifiedJavaType(entityClass), "entity", "@RequestBody"));
		method.setReturnType(new FullyQualifiedJavaType("com.yoogun.core.application.dto.JsonResult"));
		// 方法体
		method.addBodyLine("service.create(entity);");
		method.addBodyLine("return new JsonResult(JsonResult.ResultType.CREATE_SUCCEED);");
		// 写入类
		controllerClass.addMethod(method);
	}
	
	/**
	 * 增加modify方法
	 * @param controllerClass	当前controller类对象
	 * @param entityClass		实体类全限定名
	 * @param lowerModelName		首字母小写模块名称
	 */
	private void addModifyMethod(TopLevelClass controllerClass, String entityClass, String lowerModelName) {
		// 方法声明
		Method method = new Method("modify");
		method.setVisibility(JavaVisibility.PUBLIC);
		// 注释
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" * 更新数据");
		method.addJavaDocLine(" * @param entity 通过注解自动注入");
		method.addJavaDocLine(" * @return 操作结果");
		method.addJavaDocLine(" */");
		// 注解
		method.addAnnotation("@RequiresPermissions(\"" + this.moduleName + ":" + lowerModelName + ":update\")");
		method.addAnnotation("@RequestMapping(value = { \"/\" }, method = RequestMethod.PUT)");
		method.addAnnotation("@ResponseBody");
		// 参数、返回值
		method.addParameter(new Parameter(new FullyQualifiedJavaType(entityClass), "entity", "@RequestBody"));
		method.setReturnType(new FullyQualifiedJavaType("com.yoogun.core.application.dto.JsonResult"));
		// 方法体
		method.addBodyLine("service.modify(entity);");
		method.addBodyLine("return new JsonResult(JsonResult.ResultType.MODIFY_SUCCEED);");
		// 写入类
		controllerClass.addMethod(method);
	}

	/**
	 * 增加remove方法
	 * @param controllerClass	当前controller类对象
	 * @param entityClass		实体类全限定名
	 * @param lowerModelName		首字母小写模块名称
	 */
	private void addRemoveMethod(TopLevelClass controllerClass, String entityClass, String lowerModelName) {
		// 方法声明
		Method method = new Method("remove");
		method.setVisibility(JavaVisibility.PUBLIC);
		// 注释
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" * 删除数据");
		method.addJavaDocLine(" * @param id 待删除数据主键");
		method.addJavaDocLine(" * @return 操作结果");
		method.addJavaDocLine(" */");
		// 注解
		method.addAnnotation("@RequiresPermissions(\"" + this.moduleName + ":" + lowerModelName + ":delete\")");
		method.addAnnotation("@RequestMapping(value = { \"/{id}\" }, method = RequestMethod.DELETE)");
		method.addAnnotation("@ResponseBody");
		// 参数、返回值
		method.addParameter(new Parameter(new FullyQualifiedJavaType("java.lang.String"), "id", "@PathVariable(\"id\")"));
		method.setReturnType(new FullyQualifiedJavaType("com.yoogun.core.application.dto.JsonResult"));
		// 方法体
		method.addBodyLine("service.remove(id, AuthCache.accountId());");
		method.addBodyLine("return new JsonResult(JsonResult.ResultType.REMOVE_SUCCEED);");
		// 写入类
		controllerClass.addMethod(method);
	}

	/**
	 * 增加import包
	 * @param controllerClass	controller的类对象
	 * @param entityClass	实体类的全限定名
	 */
	private void addImportedTypes(TopLevelClass controllerClass, String entityClass) {

		controllerClass.addImportedType("com.yoogun.auth.infrastructure.BaseCurdController");
		controllerClass.addImportedType("com.yoogun.core.application.dto.JsonResult");
		controllerClass.addImportedType("org.apache.shiro.authz.annotation.RequiresPermissions");
		controllerClass.addImportedType("org.springframework.stereotype.Controller");
		controllerClass.addImportedType("org.springframework.web.bind.annotation.*");
		controllerClass.addImportedType("javax.annotation.Resource");
		controllerClass.addImportedType("javax.servlet.http.HttpServletRequest");

		controllerClass.addImportedType(entityClass);
		String simpleEntityName = entityClass.substring(entityClass.lastIndexOf("."));
		controllerClass.addImportedType(targetPackage.replace(".controller", ".service") + simpleEntityName + "Service");
		controllerClass.addImportedType(targetPackage.replace(".controller", ".vo") + simpleEntityName + "Vo");
	}

	/**
	 * 增加类注解
	 * @param thisClass 类对象
	 */
	private void addClassAnnotation(TopLevelClass thisClass, String modelName) {
		thisClass.addAnnotation("@Controller");	// 类注解
		thisClass.addAnnotation("@RequestMapping(value = \"**/" + this.moduleName + "/"
				+ JavaBeansUtil.getCamelCaseString(modelName, false)+  "\")");
	}
}