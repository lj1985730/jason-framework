package com.yoogun.core.application.vo;

import com.yoogun.core.domain.model.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 核心-表格查询对象
 * @author Liu Jun at 2017-11-12 19:42:37
 * @since v1.0.0
 */
public class TableParam implements Serializable {

    private Integer offset;  //查询数据起始位置
    private Integer limit;   //查询数据个数
    private String search;  //搜索内容
    private String sort;    //排序列
    private String order;   //正排or倒排

    /**
     * 构造器，根据request获取参数
     * @param request 请求体
     */
    public TableParam(HttpServletRequest request) {
        //获取分页信息
        String offset = request.getParameter("offset");
        String limit = request.getParameter("limit");
        if(offset != null && limit != null) {
            this.offset = Integer.parseInt(offset);
            this.limit = Integer.parseInt(limit);
        }

        //处理排序
        this.sort = request.getParameter("sort");
        this.order = request.getParameter("order");

        //搜索文本
        this.search = request.getParameter("search");
    }

    public Integer getOffset() {
        return offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public String getSearch() {
        return search;
    }

    public String getSort() {
        return sort;
    }

    public String getOrder() {
        return order;
    }

    /**
     * 通用toString方法
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * 通用equals方法
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof BaseEntity && EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * 通用hashCode方法
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
