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
 * vo层生成器
 * @author	Liu Jun
 * @since v1.0.0
 */
public class GenerateVoFilePlugin extends PluginAdapter {

	private String targetProject;
	private String targetPackage;
	private String developer;

	/**
	 * 预校验
	 */
	@Override
	public boolean validate(List<String> warnings) {
		this.targetProject = properties.getProperty("targetProject");
		this.targetPackage = properties.getProperty("targetPackage");
		this.developer = properties.getProperty("developer");
		String moduleNature = properties.getProperty("moduleNature");

		boolean isValid = StringUtility.stringHasValue(this.targetProject)
				&& StringUtility.stringHasValue(this.targetPackage)
				&& StringUtility.stringHasValue(this.developer)
				&& StringUtility.stringHasValue(moduleNature);

		if (!isValid) {
			warnings.add("GenerateVoFilePlugin: property not found");
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

		// 生成Vo文件
		ShellCallback shellCallback = new DefaultShellCallback(true);
		File directory;
		try {
			FullyQualifiedJavaType voType = new FullyQualifiedJavaType(this.targetPackage + "." + modelName + "Vo");
			TopLevelClass thisClass = new TopLevelClass(voType);	//类对象

			thisClass.addFileCommentLine("/*");
			thisClass.addFileCommentLine(" * " + modelName + "Vo.java");
			thisClass.addFileCommentLine(" * Copyright(C) 2018 大连用友软件有限公司");
			thisClass.addFileCommentLine(" * All right reserved.");
			thisClass.addFileCommentLine(" */");

			thisClass.setVisibility(JavaVisibility.PUBLIC);
			thisClass.setFinal(true);
			thisClass.addJavaDocLine("");
			thisClass.addJavaDocLine("/**");
			thisClass.addJavaDocLine(" * " + modelName + "-查询VO<br/>");
			thisClass.addJavaDocLine(" * @author " + this.developer + " at " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
			thisClass.addJavaDocLine(" * @since v1.0.0");
			thisClass.addJavaDocLine(" */");

			this.addImportedTypes(thisClass, entityClass);	// 增加引用内容

			this.addClassAnnotation(thisClass);	// 增加类级注解

			// 添加继承基类
			thisClass.setSuperClass(new FullyQualifiedJavaType("com.yoogun.core.application.vo.TableParam"));

			this.addConstructor(thisClass);

			String source = thisClass.getFormattedContent();
			
			//写文件
			directory = shellCallback.getDirectory(targetProject, targetPackage);
			File targetFile = new File(directory, modelName + "Vo.java");
			GeneratorUtil.writeFile(targetFile, source, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 增加默认分页搜索方法
	 * @param serviceClass	service的类对象
	 */
	private void addConstructor(TopLevelClass serviceClass) {
		Method method = new Method(serviceClass.getType().getShortName());
		method.setConstructor(true);
		method.setVisibility(JavaVisibility.PUBLIC);

		Parameter param = new Parameter(new FullyQualifiedJavaType("javax.servlet.http.HttpServletRequest"), "request");	//形参
		method.addParameter(param);

		//方法体
		method.addBodyLine("super(request);");
		method.addBodyLine("// 初始化其它属性");

		serviceClass.addMethod(method);
	}

	/**
	 * 增加import包
	 * @param serviceClass	service的类对象
	 * @param entityClass	实体类的全限定名
	 */
	private void addImportedTypes(TopLevelClass serviceClass, String entityClass) {
		serviceClass.addImportedType("com.yoogun.core.application.vo.TableParam");
		serviceClass.addImportedType("javax.servlet.http.HttpServletRequest");
	}

	/**
	 * 增加类注解
	 * @param thisClass 类对象
	 */
	private void addClassAnnotation(TopLevelClass thisClass) {
		// Vo无类注解
	}
}