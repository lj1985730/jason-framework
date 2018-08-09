/*
 * Article.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.cms.admin.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yoogun.core.domain.model.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import java.time.LocalDateTime;

/**
 * CMS-文章-实体<br/>
 * @author  Liu Jun at 2018-6-5 13:45:42
 * @since v1.0.0
 */
@Table(name = "CMS_ARTICLE")
public class Article extends BaseEntity {

	/**
	 * 根栏目ID
	 **/
	@Length(max = 36, message = "‘根栏目ID’长度不能超过36!")
	@Column(name = "ROOT_SUBJECT_ID")
	private String rootSubjectId;

	/**
	 * 根栏目
	 **/
	private Subject rootSubject;

	/**
	 * 栏目ID
	 **/
	@Length(max = 36, message = "‘根栏目ID’长度不能超过36!")
	@Column(name = "SUBJECT_ID")
	private String subjectId;

	/**
	 * 栏目
	 **/
	private Subject subject;

	/**
	 *  标题
	 */
	@Length(max = 100, message = "‘标题’长度不能超过100!")
	@Column(name = "TITLE")
	private String title;

	/**
	 * 概要
	 */
	@Length(max = 500, message = "‘概要’长度不能超过500!")
	@Column(name = "SUMMARY")
	private String summary;

	/**
	 * 内容
	 */
	@Length(max = 4000, message = "‘内容’长度不能超过4000!")
	@Column(name = "CONTENT")
	private String content;

	/**
	 * 标题图片uri
	 */
	@Length(max = 200, message = "‘标题图片uri’长度不能超过200!")
	@Column(name = "COVER_IMG_URI")
	private String coverImgUri;

	/**
	 * 标题图片uri
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "CREATE_TIME")
	private LocalDateTime createTime;

	/**
	 * 作者
	 */
	@Length(max = 100, message = "‘作者’长度不能超过100!")
	@Column(name = "AUTHOR")
	private String author;

	/**
	 * 来源
	 */
	@Length(max = 100, message = "‘来源’长度不能超过100!")
	@Column(name = "SOURCE")
	private String source;

	/**
	 * 来源URL
	 */
	@Length(max = 100, message = "‘来源URL’长度不能超过100!")
	@Column(name = "SOURCE_URL")
	private String sourceUrl;

	/**
	 * 发布人
	 */
	@Length(max = 100, message = "‘发布人’长度不能超过100!")
	@Column(name = "PUBLISHER")
	private String publisher;

	/**
	 * 是否置顶，true 是；false 否；
	 */
	@Column(name = "IS_TOP")
	private Boolean isTop;

	/**
	 * 浏览次数
	 */
	@Digits(integer = 9, fraction = 0, message = "‘浏览次数’数值不能超过9位!")
	@Column(name = "VIEW_COUNT")
	private Integer viewCount;

	/**
	 * 模板ID
	 */
	@Length(max = 36, message = "‘模板ID’长度不能超过36!")
	@Column(name = "TEMPLATE_ID")
	private String templateId;

	public String getRootSubjectId() {
		return rootSubjectId;
	}

	public void setRootSubjectId(String rootSubjectId) {
		this.rootSubjectId = rootSubjectId;
	}

	public Subject getRootSubject() {
		return rootSubject;
	}

	public void setRootSubject(Subject rootSubject) {
		this.rootSubject = rootSubject;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCoverImgUri() {
		return coverImgUri;
	}

	public void setCoverImgUri(String coverImgUri) {
		this.coverImgUri = coverImgUri;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Boolean getTop() {
		return isTop;
	}

	public void setTop(Boolean top) {
		isTop = top;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
}