package com.duzhuo.common.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用查询
 * @author wanhy
 * @date 2020-01-05
 */
public class CustomSearch<T> implements Serializable {

	private static final long serialVersionUID = -3930180379790344299L;

	/**
	 * 默认第一页
	 */
	private static final int DEFAULT_PAGE_NUMBER = 1;

	/**
	 * 默认每页20条
	 */
	private static final int DEFAULT_PAGE_SIZE = 10;

	/**
	 * 每页最多999999条
	 */
	private static final int MAX_PAGE_SIZE = 999999;

	private int pageNumber = DEFAULT_PAGE_NUMBER;

	private int pageSize = DEFAULT_PAGE_SIZE;
	/**
	 * 搜索属性
	 */
	private String searchProperty;
	/**
	 * 搜索值
	 */
	private String searchValue;
	/**
	 * 过滤
	 */
	List<Filter> filters = new ArrayList<>();
	/**
	 * 排序
	 */
	List<Sort.Order> orders = new ArrayList<>();

	private String orderProperty;
	private Sort.Direction orderDirection;


	private Page<T> pagedata;

	public CustomSearch() {

	}

	public CustomSearch(Integer pageNumber, Integer pageSize) {
		if (pageNumber != null && pageNumber >= 1) {
			this.pageNumber = pageNumber;
		}
		if (pageSize != null && pageSize >= 1 && pageSize <= MAX_PAGE_SIZE) {
			this.pageSize = pageSize;
		}
	}
	@JsonProperty
	public List<Sort.Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Sort.Order> orders) {
		this.orders = orders;
	}
	@JsonProperty
	public Page<T> getPagedata() {
		return pagedata;
	}
	public void setPagedata(Page<T> pagedata) {
		this.pagedata = pagedata;
	}
	@JsonProperty
	public String getSearchProperty() {
		return searchProperty;
	}
	public void setSearchProperty(String searchProperty) {
		this.searchProperty = searchProperty;
	}
	@JsonProperty
	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	@JsonProperty
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		if (pageNumber < 1) {
			pageNumber = DEFAULT_PAGE_NUMBER;
		}
		this.pageNumber = pageNumber;
	}
	@JsonProperty
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize < 1 || pageSize > MAX_PAGE_SIZE) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}
	@JsonProperty
	public String getOrderProperty() {
		return orderProperty;
	}

	public void setOrderProperty(String orderProperty) {
		this.orderProperty = orderProperty;
	}
	@JsonProperty
	public Sort.Direction getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(Sort.Direction orderDirection) {
		this.orderDirection = orderDirection;
	}

	@JsonProperty
	public List<Filter> getFilters() {
		return filters;
	}

	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}

}