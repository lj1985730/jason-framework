package com.yoogun.generator;

import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.mybatis.generator.internal.util.StringUtility;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * JSP生成器
 * @author Liu Jun
 * @since v1.0.0
 */
public class GenerateJspFilePlugin extends PluginAdapter {

	private String targetProject;
	private String targetPackage;
	private String moduleName;
	private String moduleNature;
	private String developer;

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
	
	/**
	 * 主键属性名
	 */
	private String pkProperty;
	
	@Override
	public boolean validate(List<String> warnings) {
		targetProject = properties.getProperty("targetProject");
		targetPackage = properties.getProperty("targetPackage");
		moduleName = properties.getProperty("moduleName");
		moduleNature = properties.getProperty("moduleNature");
		developer = properties.getProperty("developer");

		boolean isValid = StringUtility.stringHasValue(this.targetProject)
				&& StringUtility.stringHasValue(this.targetPackage)
				&& StringUtility.stringHasValue(this.moduleName)
				&& StringUtility.stringHasValue(this.moduleNature)
				&& StringUtility.stringHasValue(this.developer);

		if (!isValid) {
			warnings.add("GenerateJspFilePlugin: property not found");
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
			this.generateMainJsp(introspectedTable, directory, modelName);	// 生成jsp页面
			this.generateEditJsp(introspectedTable, directory, modelName);	// 生成jsp页面
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
		// 获取主键字段
		List<IntrospectedColumn> pkCols = introspectedTable.getPrimaryKeyColumns();
		if(pkCols != null && pkCols.size() > 0) {
			this.pkProperty = pkCols.get(0).getJavaProperty();
		}
		this.gridId = lowerModelName + "Table";
		this.editWinId = lowerModelName + "EditWin";
		this.editWinFormId = this.editWinId + "Form";
	}

	/**
	 * 生成主JSP文件
	 * @param introspectedTable 表对象
	 * @param directory	文件基本路径
	 * @param modelName	功能名称
	 */
	private void generateMainJsp(IntrospectedTable introspectedTable, File directory, String modelName) {
		//文件及路径准备
		File jspPath = new File(directory + "/" + this.moduleName + "/" + this.moduleNature + "/");
		if(!jspPath.exists()) {
			jspPath.mkdirs();
		}
		File targetFile = new File(jspPath.getPath(), this.lowerModelName + ".jsp");
		
		Document document = new Document();	//总内容

		// 根标签 html
		XmlElement html = new XmlElement("html");
		document.setRootElement(html);

		// 导航条 html > yonyou:nav
		html.addElement(new TextElement("<yonyou:nav father=\"模块名\" model=\"功能名\" />"));

		// 主界面布局  html > row
		XmlElement rowDiv = new XmlElement("div");
		rowDiv.addAttribute(new Attribute("class", "row"));
		html.addElement(rowDiv);

		// html > row > col-md-12
		XmlElement mainCol = new XmlElement("div");
		mainCol.addAttribute(new Attribute("class", "col-md-12"));
		rowDiv.addElement(mainCol);

		// html > row > col-md-12 > portlet
		XmlElement portlet = new XmlElement("div");
		portlet.addAttribute(new Attribute("class", "portlet box blue-dark"));
		mainCol.addElement(portlet);
		
		/*
		 * title区域
		 */
		// html > row > col-md-12 > portlet > portlet-title
		XmlElement portletTitle = new XmlElement("div");
		portletTitle.addAttribute(new Attribute("class", "portlet-title"));
		portlet.addElement(portletTitle);

		// html > row > col-md-12 > portlet > portlet-title > caption
		XmlElement caption = new XmlElement("div");
		caption.addAttribute(new Attribute("class", "caption"));
		portletTitle.addElement(caption);

		// html > row > col-md-12 > portlet > portlet-title > caption > fa
		XmlElement captionIcon = new XmlElement("i");
		captionIcon.addAttribute(new Attribute("class", "fa fa-list"));
		captionIcon.addElement(new TextElement(""));
		caption.addElement(0, captionIcon);
		caption.addElement(1, new TextElement("XX列表"));

		// html > row > col-md-12 > portlet > portlet-title > tools
		XmlElement tools = new XmlElement("div");
		tools.addAttribute(new Attribute("class", "tools"));
		portletTitle.addElement(tools);

		//新增按钮
		tools.addElement(0, new TextElement("<a class=\"collapse\"></a>"));
		tools.addElement(1, new TextElement("<a class=\"fullscreen\"></a>"));

		//按钮集 html > row > col-md-12 > portlet > portlet-title > actions
		XmlElement actions = new XmlElement("div");
		actions.addAttribute(new Attribute("class", "actions"));
		portletTitle.addElement(actions);
		//新增按钮
		actions.addElement(0, new TextElement("<yonyou:create id=\"create" + modelName + "\" permission=\"" + moduleName + ":" + lowerModelName + ":create\" />"));
		//修改按钮
		actions.addElement(1, new TextElement("<yonyou:update id=\"update" + modelName + "\" permission=\"" + moduleName + ":" + lowerModelName + ":update\" />"));
		//删除按钮
		actions.addElement(2, new TextElement("<yonyou:delete id=\"delete" + modelName + "\" permission=\"" + moduleName + ":" + lowerModelName + ":delete\" />"));

		/*
		 * body区域
		 */
		// html > row > col-md-12 > portlet > portlet-body
		XmlElement portletBody = new XmlElement("div");
		portletBody.addAttribute(new Attribute("class", "portlet-body"));
		portlet.addElement(portletBody);
		// html > row > col-md-12 > portlet > portlet-body > table-container
		XmlElement tableContainer = new XmlElement("div");
		tableContainer.addAttribute(new Attribute("class", "table-container"));
		portletBody.addElement(tableContainer);
		// html > row > col-md-12 > portlet > portlet-body > table-container > table
		XmlElement grid = new XmlElement("table");
		grid.addAttribute(new Attribute("id", this.gridId));
		grid.addAttribute(new Attribute("class", "table table-striped table-bordered table-hover"));
		grid.addAttribute(new Attribute("data-search", "true"));
		grid.addAttribute(new Attribute("data-show-refresh", "true"));
		grid.addAttribute(new Attribute("data-show-toggle", "true"));
		grid.addAttribute(new Attribute("data-show-columns", "true"));
		grid.addAttribute(new Attribute("data-single-select", "true"));
		grid.addAttribute(new Attribute("data-click-to-select", "true"));
		tableContainer.addElement(grid);

		// html > row > col-md-12 > portlet > portlet-body > table-container > table > thead
		XmlElement thead = new XmlElement("thead");
		grid.addElement(thead);

		// html > row > col-md-12 > portlet > portlet-body > table-container > table > thead > tr
		XmlElement tr = new XmlElement("tr");
		tr.addAttribute(new Attribute("role", "row"));
		tr.addAttribute(new Attribute("class", "heading"));
		thead.addElement(tr);

		// html > row > col-md-12 > portlet > portlet-body > table-container > table > thead > tr > th
		XmlElement checkboxTh = new XmlElement("th");
		checkboxTh.addAttribute(new Attribute("data-field", "checkbox"));
		checkboxTh.addAttribute(new Attribute("data-checkbox", "true"));
		tr.addElement(checkboxTh);
		XmlElement indexTh = new XmlElement("th");
		indexTh.addAttribute(new Attribute("data-field", "id"));
		indexTh.addAttribute(new Attribute("data-formatter", "indexFormatter"));
		indexTh.addElement((new TextElement("序号")));
		tr.addElement(indexTh);

		//轮询写入grid列
		// html > row > col-md-12 > portlet > portlet-body > table-container > table > thead > tr > th
		for (IntrospectedColumn column : introspectedTable.getAllColumns()) {
			XmlElement colTh = new XmlElement("th");
			String prop = column.getJavaProperty();
			colTh.addAttribute(new Attribute("data-field", prop));
			if(this.pkProperty.equals(prop)) {
//				colTh.addAttribute(new Attribute("data-visible", "false"));
				continue;	//不显示ID列 by liu jun at 2018-3-20 10:22:01
			} else {
				colTh.addAttribute(new Attribute("data-sortable", "true"));
			}
			String colName = column.getRemarks();
			colTh.addElement(new TextElement(colName == null ? "" : colName));
			tr.addElement(colTh);
		}

		/*
		 * 添加修改区域 html > jsp:include
		 */
		html.addElement(new TextElement("<jsp:include page=\"" + lowerModelName + "-edit.jsp\" />"));

		/*
		 * 模块js引用 html > script
		 */
		html.addElement(new TextElement(
				"<script type=\"text/javascript\" src=\"scripts/" + this.moduleName  + "/" + this.moduleNature + "/" + this.lowerModelName + ".js\"></script>"));

		String source = document.getFormattedContent();

		String page = this.addComments(modelName)	//页面注释
				+ this.addTop()    //页面头引用
				+ source;	//页面体内容

		try {
			GeneratorUtil.writeFile(targetFile, page, "UTF-8");	//写文件
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成编辑JSP文件
	 * @param introspectedTable 表对象
	 * @param directory	文件基本路径
	 * @param modelName	功能名称
	 */
	private void generateEditJsp(IntrospectedTable introspectedTable, File directory, String modelName) {
		//文件及路径准备
		File jspPath = new File(directory + "/" + this.moduleName + "/" + this.moduleNature + "/");
		if(!jspPath.exists()) {
			jspPath.mkdirs();
		}
		File targetFile = new File(jspPath.getPath(), this.lowerModelName + "-edit.jsp");

		Document document = new Document();	//总内容

		XmlElement html = new XmlElement("html");	//根标签html
		document.setRootElement(html);

		// 外部modal html > modal
		XmlElement modal = new XmlElement("yonyou:modal");
		html.addElement(modal);
		modal.addAttribute(new Attribute("id", editWinId));
		modal.addAttribute(new Attribute("modalClass", "modal-lg modal-xl modal-xxl modal-xxxl modal-xxxxl"));
		modal.addAttribute(new Attribute("title", "编辑数据"));
		modal.addAttribute(new Attribute("editable", "true"));

		// 表单 html > modal > form
		XmlElement form = new XmlElement("form");
		form.addAttribute(new Attribute("id", this.editWinFormId));
		form.addAttribute(new Attribute("class", "form-horizontal form-bordered form-row-stripped"));
		form.addAttribute(new Attribute("data-toggle", "validator"));
		modal.addElement(form);
		// 实体Id隐藏标签
		XmlElement idInput = new XmlElement("input");
		idInput.addAttribute(new Attribute("type", "hidden"));
		idInput.addAttribute(new Attribute("id", "id"));
		idInput.addAttribute(new Attribute("name", this.pkProperty));
		form.addElement(idInput);

		/*
		 * 遍历生成输入框，方便起见，按照单列布局，需要后期手动修改
		 */
		for (IntrospectedColumn column : introspectedTable.getAllColumns()) {
			String field = column.getJavaProperty();
			if(this.pkProperty.equals(field)) {		//id属性不生成
				continue;
			}

			// html > modal > form > row
			XmlElement editRow = new XmlElement("div");
			editRow.addAttribute(new Attribute("class", "row"));
			form.addElement(editRow);

			// html > modal > form > row > col-md-12
			XmlElement col12Div = new XmlElement("div");
			col12Div.addAttribute(new Attribute("class", "col-md-12"));
			editRow.addElement(col12Div);

			// html > modal > form > row > col-md-12 > form-group
			XmlElement formGroup = new XmlElement("div");
			formGroup.addAttribute(new Attribute("class", "form-group"));
			col12Div.addElement(formGroup);

			// html > modal > form > row > col-md-12 > form-group > control-label
			String colName = column.getRemarks();
			TextElement controlLabel = new TextElement("<label class=\"control-label col-md-4\">" + (colName == null ? "" : colName) + "</label>");
			formGroup.addElement(controlLabel);

			// html > modal > form > row > col-md-12 > form-group > col-md-8
			XmlElement col8Div = new XmlElement("div");
			col8Div.addAttribute(new Attribute("class", "col-md-8"));
			formGroup.addElement(col8Div);

			int length;	//输入长度限制
			if (!column.isIdentity()) {
				length = (int) Math.ceil(column.getLength() / 2.0);
			} else {
				length = column.getLength();
			}
			//拼接输入框
			String sb = "<input class=\"form-control\" id=\"edit_" + column.getJavaProperty() +
					"\" name=\"" + field + "\" placeholder=\"" + colName + "\" required maxlength=\"" + length + "\" />";
			TextElement editInput = new TextElement(sb);
			col8Div.addElement(editInput);
		}

		String source = document.getFormattedContent();

		String page = this.addTop() + source;    //页面头引用 + 页面体内容

		try {
			GeneratorUtil.writeFile(targetFile, page, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 增加注释
	 * @return 注释字符串
	 */
	private String addComments(String modelName) {
		return "<%-- 作 者：" + this.developer + " --%>" +
				System.getProperty("line.separator") +
				"<%-- 本页说明：--%>" +
				System.getProperty("line.separator") +
				"<%-- 本页地址:" + this.moduleName + "/" + this.moduleNature + "/" + modelName + "Controller/homeView --%>" +
				System.getProperty("line.separator");
	}

	/**
	 * 增加头声明
	 * @return 头声明字符串
	 */
	private String addTop() {
		return "<%@ page contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\"%>" +
				System.getProperty("line.separator") +
				"<%@ taglib prefix=\"yonyou\" tagdir=\"/WEB-INF/tags\" %>" +
				System.getProperty("line.separator");
	}
}