package com.restkind.model;

import java.io.Serializable;

import com.restaurant.model.RestaurantVO;
import com.kindlist.model.KindlistVO;
@SuppressWarnings("serial")
public class RestKindVO implements Serializable{
	
	private Integer KindID;
	private Integer RestID;
	private RestaurantVO RestaurantVO;
	private KindlistVO KindlistVO;
	
	public RestaurantVO getRestaurantVO() {
		return RestaurantVO;
	}
	public void setRestaurantVO(RestaurantVO restaurantVO) {
		RestaurantVO = restaurantVO;
	}
	public KindlistVO getKindlistVO() {
		return KindlistVO;
	}
	public void setKindlistVO(KindlistVO kindlistVO) {
		KindlistVO = kindlistVO;
	}

	
	public Integer getKindID() {
		return KindID;
	}
	public void setKindID(Integer kindID) {
		KindID = kindID;
	}
	public Integer getRestID() {
		return RestID;
	}
	public void setRestID(Integer restID) {
		RestID = restID;
	}
	
	
	

}
