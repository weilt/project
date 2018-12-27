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
public class TravelIndexVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 导游
	 */
	@ApiModelProperty(value = "导游")
	private List<ESQuery> cicerones = new ArrayList<ESQuery>();
	
	/**
	 * 线路
	 */
	@ApiModelProperty(value = "线路")
	private List<ESQuery> lines = new ArrayList<ESQuery>();
	
	/**
	 * 轮播图
	 */
	@ApiModelProperty(value = "轮播图")
	private List<HwAdvertisementVo> advertisements = new ArrayList<>();
	

	public List<ESQuery> getCicerones() {
		return cicerones;
	}

	public void setCicerones(List<ESQuery> cicerones) {
		this.cicerones = cicerones;
	}

	public List<ESQuery> getLines() {
		return lines;
	}

	public void setLines(List<ESQuery> lines) {
		this.lines = lines;
	}

	public List<HwAdvertisementVo> getAdvertisements() {
		return advertisements;
	}

	public void setAdvertisements(List<HwAdvertisementVo> advertisements) {
		this.advertisements = advertisements;
	}
	
}
