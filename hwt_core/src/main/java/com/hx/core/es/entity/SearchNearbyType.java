package com.hx.core.es.entity;


/**
 * @author Administrator
 *
 */
public class SearchNearbyType {
	 
	/**
	 * 查找距离某个中心点距离在一定范围内的位置
	 */
	public static final String  geo_distance = "geo_distance";
     
	/**
	 * 查找某个长方形区域内的位置
	 */
	public static final String geo_bounding_box = "geo_bounding_box";
     
	 /**
	  * 查找距离某个中心的距离在min和max之间的位置
	  */
	public static final String geo_distance_range = "geo_distance_range";
     
	 /**
	  * 查找位于多边形内的地点。
	  */
	public static final String  geo_polygon = "geo_polygon";
	
}
