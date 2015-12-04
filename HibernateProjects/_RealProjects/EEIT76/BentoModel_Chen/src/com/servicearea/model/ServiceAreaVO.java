package com.servicearea.model;

import java.sql.Date;
import com.restaurant.model.RestaurantVO;
public class ServiceAreaVO implements java.io.Serializable{
	private Integer ServiceAreaID;
	private String City;
	private String Area;
	private RestaurantVO RestaurantVO;
	private Integer RestID;
	
	
	
	public Integer getRestID() {
		return RestID;
	}
	public void setRestID(Integer restID) {
		RestID = restID;
	}
	public Integer getServiceAreaID() {
		return ServiceAreaID;
	}
	public void setServiceAreaID(Integer serviceAreaID) {
		ServiceAreaID = serviceAreaID;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getArea() {
		return Area;
	}
	public void setArea(String area) {
		Area = area;
	}
	public RestaurantVO getRestaurantVO() {
		return RestaurantVO;
	}
	public void setRestaurantVO(RestaurantVO restaurantVO) {
		RestaurantVO = restaurantVO;
	}
	
	
	
}
