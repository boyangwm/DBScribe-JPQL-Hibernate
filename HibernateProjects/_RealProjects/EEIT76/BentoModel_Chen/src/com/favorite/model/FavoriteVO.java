package com.favorite.model;

import java.io.Serializable;

import com.restaurant.model.RestaurantVO;

public class FavoriteVO implements Serializable {
	private String MemberAcc;
	private Integer RestID;
	
	private RestaurantVO RestaurantVO;

	public RestaurantVO getRestaurantVO() {
		return RestaurantVO;
	}

	public void setRestaurantVO(RestaurantVO restaurantVO) {
		RestaurantVO = restaurantVO;
	}

	public String getMemberAcc() {
		return MemberAcc;
	}

	public void setMemberAcc(String memberAcc) {
		MemberAcc = memberAcc;
	}

	public Integer getRestID() {
		return RestID;
	}

	public void setRestID(Integer restID) {
		RestID = restID;
	}

}
