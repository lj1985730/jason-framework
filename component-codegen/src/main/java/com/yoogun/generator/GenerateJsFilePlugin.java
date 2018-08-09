package com.yoogun.generator;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.mybatis.generator.internal.util.StringUtility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * JS生成器
 * @author Liu Jun
 * @since v1.0.0
 */
public class GenerateJsFilePlugin extends PluginAdapter {

	private String targetProject;
	private String targetPackage;
	private String developer;

	private String moduleName;
	private String moduleNature;

	private String lowerModelName;

	/**
	 * grid表格id
	 */
	private String gridId;
	
	/**
	 * 编辑窗口id
	 */
	private String editWinId;
	
	/**
	 * 编辑窗口表单id
	 */
	private String editWinFormId;

	@Override
	public boolean validate(List<String> warnings) {
		targetProject = properties.getProperty("targetProject");
		targetPackage = properties.getProperty("targetPackage");
		moduleName = properties.getProperty("moduleName");
		developer = properties.getProperty("developer");
		moduleNature = properties.getProperty("moduleNature");

		boolean isValid = StringUtility.stringHasValue(this.targetProject)
				&& StringUtility.stringHasValue(this.targetPackage)
				&& StringUtility.stringHasValue(this.moduleName)
				&& StringUtility.stringHasValue(this.developer)
				&& StringUtility.stringHasValue(this.moduleNature);

		if (!isValid) {
			warnings.add("GenerateJsFilePlugin: property not found");
			return false;
		}
		return true;
	}
	
	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {

		String modelName = JavaBeansUtil.getCamelCaseString(
				introspectedTable.getFullyQualifiedTable().getDomainObjectName(), true);

		this.initElements(introspectedTable, modelName);

		// 在jsp文件中生成
		ShellCallback shellCallback = new DefaultShellCallback(true);
		File directory;
		try {
			//写文件
			directory = shellCallback.getDirectory(targetProject, targetPackage);
			this.generateFile(introspectedTable, directory, modelName);	// 生成js文件
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * 初始化页面固定组件id
	 */
	private void initElements(IntrospectedTable introspectedTable, String modelName) {
		this.lowerModelName = JavaBeansUtil.getCamelCaseString(modelName, false);
		this.gridId = lowerModelName + "Table";
		this.editWinId = lowerModelName + "EditWin";
		this.editWinFormId = this.editWinId + "Form";
	}

	/**
	 * 生成Js文件方法
	 * @param introspectedTable 表对象
	 * @param directory	绝对根路径
	 */
	private void generateFile(IntrospectedTable introspectedTable, File directory, String modelName) {
		//文件及路径准备
		File filePath = new File(directory + "/" + this.moduleName + "/" + this.moduleNature + "/");
		if(!filePath.exists()) {
			filePath.mkdirs();
		}
		File targetFile = new File(filePath.getPath(), this.lowerModelName + ".js");

		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(targetFile, false), "UTF-8"));
			pw.println("/***********************************");
			pw.println(" * 本页代码用于处理对应页面的js，默认生成了基本的CRUD操作，需要根据具体的业务进行调整。");
			pw.println(" * @author " + this.developer + " at " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
			pw.println(" * @since v1.0.0");
			pw.println(" * *********************************/");
			pw.println("!function ($) {");
			pw.print(System.getProperty("line.separator"));
			pw.println("	'use strict';");
			pw.print(System.getProperty("line.separator"));
			pw.println("	var baseUrl=\"appPath + /" + this.moduleName + "/" + this.lowerModelName + "\";");
			pw.println("    var $table, $editWin, $editForm;");
			pw.print(System.getProperty("line.separator"));

			/*
			 * 初始化方法
			 */
			pw.println("	/**");
			pw.println(" 	 * 初始化方法");
			pw.println(" 	 */");
			pw.println("	$(function() {");
			pw.println("		/**");
			pw.println(" 	 	 * 初始化table");
			pw.println(" 	 	 */");
			pw.println("		$table = $('#" + this.gridId + "');");
			pw.println("		$table.bootstrapTable(");
			pw.println("			$.extend(");
			pw.println("				{");
			pw.println("					url : baseUrl,");
			pw.println("					formatSearch : function () {");
			pw.println("						return '搜索XXX';");
			pw.println("					},");
			pw.println("				},");
			pw.println("				$.extend({}, generalTableOption, { queryParams : " + this.lowerModelName + "Params })");
			pw.println("			)");
			pw.println("		);");
			pw.print(System.getProperty("line.separator"));

			pw.println("		/**");
			pw.println("	 	 * 按钮初始化");
			pw.println("	 	 */");
			pw.println("		$('#create" + modelName + "').on('click', function() { showEditWin(0); });");
			pw.println("		$('#update" + modelName + "').on('click', function() { showEditWin(1); });");
			pw.println("		$('#delete" + modelName + "').on('click', function() { remove(); });");
			pw.println("		$('#" + editWinId + "SubmitBtn').on('click', function() { submit() });");
			pw.print(System.getProperty("line.separator"));

			pw.println("		/**");
			pw.println("	 	 * 编辑页初始化");
			pw.println("	 	 */");
			pw.println("	 	$editWin = $('#" + this.editWinId + "');");
			pw.println("	 	$editForm = $('#" + this.editWinFormId + "');");
			pw.print(System.getProperty("line.separator"));

			pw.println("		$editForm.validation(); //初始化表单验证，需要在各种三方组件之后初始化，以保证监听正确的元素");
			pw.println("	});");

			/*
			 * 查询条件
			 */
			pw.println("	/**");
			pw.println(" 	 * 查询条件");
			pw.println(" 	 * @param params 基本查询条件，包含search、sort、order、limit、offset");
			pw.println(" 	 */");
			pw.println("	function " + this.lowerModelName + "Params(params) {");
			pw.println("		var localParams = {");
			pw.println("			// 查询条件");
			pw.println("		}");
			pw.println("		return $.extend(localParams, params);");
			pw.println("	}");
			pw.print(System.getProperty("line.separator"));

			/*
			 * 编辑窗口
			 */
			pw.println("	/**");
			pw.println(" 	 * 显示编辑窗口");
			pw.println(" 	 * @param saveOrUpdate	0 新增；1 修改");
			pw.println(" 	 */");
			pw.println("	var sOrU;");
			pw.println("	function showEditWin(saveOrUpdate) {");
			pw.println("		sOrU = saveOrUpdate;");
			pw.println("		try {");
			pw.println("			$editForm.form('clear');");
			pw.println("			if(sOrU === 1) {");
			pw.println("				var row = $table.bootstrapTable('getSelections')[0];");
			pw.println("				if (!row) {");
			pw.println("					SysMessage.alertNoSelection();");
			pw.println("					return false;");
			pw.println("				}");
			pw.println("				$editForm.form('load', row);");
			pw.println("			}");
			pw.println("			$editWin.modal('show');");
			pw.println("		} catch(e) {");
			pw.println("			SysMessage.alertError(e.message);");
			pw.println("		}");
			pw.println("	}");
			pw.print(System.getProperty("line.separator"));

			/*
			 * 保存触发
			 */
			pw.println("	/**");
			pw.println(" 	 * 提交表单");
			pw.println("	 */");
			pw.println("	function submit() {");
			pw.print(System.getProperty("line.separator"));
			pw.println("		if(!$editForm.valid()) {    //表单验证");
			pw.println("			return false;");
			pw.println("		}");
			pw.print(System.getProperty("line.separator"));
			pw.println("		var method = (sOrU === 0 ? 'POST' : 'PUT');");

			pw.println("		$.fn.http({");
			pw.println("			method : method,");
			pw.println("			url : baseUrl,");
			pw.println("			data : JSON.stringify($editForm.serializeJson()),");
			pw.println("			success : function() {");
			pw.println("				$editWin.modal('hide');");
			pw.println("				$table.bootstrapTable('refresh');");
			pw.println("			}");
			pw.println("		});");
			pw.println("	}");
			pw.print(System.getProperty("line.separator"));

			/*
			 * 执行删除动作的操作
			 */
			pw.println("	/**");
			pw.println("	 * 执行删除动作的操作");
			pw.println("	 */");
			pw.println("	var remove = function() {");
			pw.println("		var row = $table.bootstrapTable('getSelections')[0];");
			pw.println("		if (!row) {");
			pw.println("			SysMessage.alertNoSelection();");
			pw.println("			return false;");
			pw.println("		}");
			pw.println("		bootbox.confirm('确定要删除吗？', function (callback) {");
			pw.println("			if(callback) {");
			pw.println("				$.fn.http({");
			pw.println("					method : 'DELETE',");
			pw.println("					url : baseUrl + '/' + row.id,");
			pw.println("					success : function() {");
			pw.println("						$table.bootstrapTable('refresh');");
			pw.println("					}");
			pw.println("				});");
			pw.println("			}");
			pw.println("		});");
			pw.println("	}");
			pw.print(System.getProperty("line.separator"));
			pw.println("}(window.jQuery);");
		    pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pw != null) {
				pw.close();
			}
		}
	}
}