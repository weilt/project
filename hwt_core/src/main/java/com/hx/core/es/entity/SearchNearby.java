package com.hx.core.es.entity;

import java.io.Serializable;

/**
 * 搜附近
 * @author Administrator
 *
 */
public class SearchNearby implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final static Integer ASC = 1;
	public final static Integer DESC = -1;


    
	/**
	 * 最大距离   默认取这个值  单位米
	 */
	private Long max_distance;
	
	/**
	 * 最小距离
	 */
	private Long nim_distance;
	
	/**
	 * 纬度
	 */
	private Double lat;
	
	/**
	 * 经度
	 */
	private Double lon;
	
	/**
	 * 在es中的字段
	 */
	private String filed;
	
	/**
	 * 排序   1升序 ，-1降序
	 */
	private Integer sort = 1;
	
	/**
	 * 	方式
	 *  geo_distance: 查找距离某个中心点距离在一定范围内的位置
     *	geo_bounding_box: 查找某个长方形区域内的位置
     *	geo_distance_range: 查找距离某个中心的距离在min和max之间的位置
     *	geo_polygon: 查找位于多边形内的地点。
	 */
	private String type = "geo_distance";


	

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public String getFiled() {
		return filed;
	}

	public void setFiled(String filed) {
		this.filed = filed;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getMax_distance() {
		return max_distance;
	}

	public void setMax_distance(Long max_distance) {
		this.max_distance = max_distance;
	}

	public Long getNim_distance() {
		return nim_distance;
	}

	public void setNim_distance(Long nim_distance) {
		this.nim_distance = nim_distance;
	}
	
}
