package com.yoogun.core.application.controller;

import com.yoogun.core.infrastructure.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 控制器的抽象父类，用于处理通用功能。 <br>
 * 目前处理了页面模板的转换。
 * @author Liu Jun
 */
public abstract class BaseController {

	/**
	 * 日志对象
	 */
	private Logger logger = LoggerFactory.getLogger(BaseController.class);

	/**
	 * 页面相对路径
	 */
	private static final String DEFAULT_TEMPLATE = "";

	/**
	 * 返回页面视图
	 * @param page {@link #getTemplatePath(String)}
	 * @return 对象视图字符串
	 */
	protected String pageView(String page) {
		return getTemplatePath(page);
	}

	/**
	 * 返回页面和对象视图
	 * @see #pageView(String)
	 * @param page {@link #getTemplatePath(String)}
	 * @param modelName	对象名称，前台需要用modelName.属性名获取数据
	 * @param modelObject	对象实例
	 * @return 对象视图
	 */
	protected ModelAndView pageView(String page, String modelName, Object modelObject) {
		String template = getTemplatePath(page);
		logger.debug("调用模板路径：" + template);
		return new ModelAndView(template, modelName, modelObject);
	}


	/**
	 * 返回页面和对象视图
	 * @see #pageView(String)
	 * @param page {@link #getTemplatePath(String)}
	 * @param dataMap	数据集合
	 * @return 对象视图
	 */
	protected ModelAndView pageView(String page, Map<String, ?> dataMap) {
		String template = getTemplatePath(page);
		return new ModelAndView(template, dataMap);
	}
	
	/**
	 * 获取模板视图统一路径
	 * @param page 需要提供第一个/用来表示模板目录的根目录,例如：<br>
	 *            /baseinfo/hello表示路径：WEB-INF/templates/jsp/baseinfo/hello,
	 *            其中jsp需要根据 用户的自定义模板来确定。
	 * @return 模板视图路径
	 */
	private String getTemplatePath(String page) {
		return DEFAULT_TEMPLATE + page; // 模板名(根据用户不同设定可变)
	}
	
	/**
	 * 重定向页面
	 * @param relativePath 视图路径
	 * @return 重定向
	 */
	protected String redirectPageView(String relativePath) {
		return "redirect:" + relativePath;
	}

	/**
	 *  创建文件下载形式返回对象
	 * @param fileContent 文件内容
	 * @param fileName 文件名称
	 * @return response对象
	 */
	protected ResponseEntity<byte[]> createFileAttachmentResponse(byte[]  fileContent, String fileName) {
		return this.createFileResponse(fileContent, fileName, "attachment");
	}

	/**
	 *  创建文件预览形式返回对象
	 * @param fileContent 文件内容
	 * @param fileName 文件名称
	 * @return response对象
	 */
	protected ResponseEntity<byte[]> createFileInlineResponse(byte[]  fileContent, String fileName) {
		return this.createFileResponse(fileContent, fileName, "inline");
	}

	/**
	 *  创建文件返回对象
	 * @param fileContent 文件内容
	 * @param fileName 文件名称
	 * @param mode 文件返回形式 attachment/inline
	 * @return response对象
	 */
	private ResponseEntity<byte[]> createFileResponse(byte[]  fileContent, String fileName, String mode) {
		HttpHeaders headers = new HttpHeaders();
		try {
			headers.setContentDispositionFormData(mode,  new String(fileName.getBytes("UTF-8"),"iso-8859-1"));
		} catch (UnsupportedEncodingException e) {
			throw new BusinessException("转换文件编码出错！");
		}
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
	}
}