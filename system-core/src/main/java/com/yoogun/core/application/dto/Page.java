package com.yoogun.core.application.dto;

import java.util.List;
import java.util.Map;

/**
 * 核心-基础-服务器到客户端的分页数据封装
 * @param <T> 分页中存储的数据类型。
 * @author Liu jun
 */
public class Page<T> {

	/**
	 * 分页查询开始记录位置
	 */
	private int offset;

	/**
	 * 每页显示记录数
	 */
	private int limit;

	/**
	 * 查询结果总页数
	 */
	private int total;

	/**
	 * 数据集合
	 */
	private List<T> rows;

	/**
	 * 合计行
	 */
	private List<Map<String, String>> footer;

	/**
	 * 构造函数
	 * @param offset	当前首条记录数
	 * @param limit		每页显示记录数
	 */
	public Page(int offset, int limit) {
		this.limit = limit;
		this.offset = offset;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public List<Map<String, String>> getFooter() {
		return footer;
	}

	public void setFooter(List<Map<String, String>> footer) {
		this.footer = footer;
	}

	@Override
	public String toString() {
		return "Page{" +
				"offset=" + offset + ", limit=" + limit +
				", total=" + total + ", rows=" + rows +
				", footer=" + footer + "}";
	}
}