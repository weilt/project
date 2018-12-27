package com.hwt.domain.entity.mg.scenic.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.hwt.domain.entity.advertisement.vo.HwAdvertisementVo;
import com.hwt.domain.entity.cicerone.vo.HxCiceroneInfoVo;
import com.hwt.domain.entity.es.ESQuery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 首页信息
 * @author Administrator
 *
 */
@ApiModel(value="首页信息")
public class TravelIndexNewVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 推荐信息
	 */
	@ApiModelProperty(value = "推荐信息")
	private List<ESQuery> recommends = new ArrayList<ESQuery>();
	
	/**
	 * 轮播图
	 */
	@ApiModelProperty(value = "轮播图")
	private List<HwAdvertisementVo> advertisements = new ArrayList<>();

	public List<ESQuery> getRecommends() {
		return recommends;
	}

	public void setRecommends(List<ESQuery> recommends) {
		this.recommends = recommends;
	}

	public List<HwAdvertisementVo> getAdvertisements() {
		return advertisements;
	}

	public void setAdvertisements(List<HwAdvertisementVo> advertisements) {
		this.advertisements = advertisements;
	}
	
}
