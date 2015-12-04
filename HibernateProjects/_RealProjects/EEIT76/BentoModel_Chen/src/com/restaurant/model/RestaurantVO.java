package com.restaurant.model;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;

import com.accuse.model.AccuseVO;
import com.ad.model.AdVO;
import com.dish.model.DishVO;
import com.restdiscuss.model.RestDiscussVO;
import com.restkind.model.RestKindVO;
import com.servicearea.model.ServiceAreaVO;

public class RestaurantVO implements java.io.Serializable{
	private Integer RestID;
	private String RestName;
	private String RestPhone;
	private String RestCel;
	private String RestCity;
	private String RestArea;
	private String RestAddr;
	private Double RestLatitude;
	private Double RestLongitude;
	private Time LastOrder_midday;
	private Time LastOrder_night;
	
	private Set<ServiceAreaVO> serviceAreas;
	private Set<RestKindVO> restKinds;
	private Set<AdVO> ads;
	private Set<RestDiscussVO> restDiscusses;
	private Set<DishVO> dishes;
	private Set<AccuseVO> accuses;
	
	
	
	public Set<AccuseVO> getAccuses() {
		return accuses;
	}
	public void setAccuses(Set<AccuseVO> accuses) {
		this.accuses = accuses;
	}
	//private OwnerVO ownerVO;
	private String OwnAcc;
	
	
	
	
	public Set<RestDiscussVO> getRestDiscusses() {
		return restDiscusses;
	}
	public void setRestDiscusses(Set<RestDiscussVO> restDiscusses) {
		this.restDiscusses = restDiscusses;
	}
	public Set<DishVO> getDishes() {
		return dishes;
	}
	public void setDishes(Set<DishVO> dishes) {
		this.dishes = dishes;
	}
	public Set<RestKindVO> getRestKinds() {
		return restKinds;
	}
	public void setRestKinds(Set<RestKindVO> restKinds) {
		this.restKinds = restKinds;
	}
	public Set<AdVO> getAds() {
		return ads;
	}
	public void setAds(Set<AdVO> ads) {
		this.ads = ads;
	}
	
	
	public Set<ServiceAreaVO> getServiceAreas() {
		return serviceAreas;
	}
	public void setServiceAreas(Set<ServiceAreaVO> serviceAreas) {
		this.serviceAreas = serviceAreas;
	}
	public String getOwnAcc() {
		return OwnAcc;
	}
	public void setOwnAcc(String ownAcc) {
		OwnAcc = ownAcc;
	}
	public Integer getRestID() {
		return RestID;
	}
	public void setRestID(Integer restID) {
		RestID = restID;
	}
	public String getRestPhone() {
		return RestPhone;
	}
	public void setRestPhone(String restPhone) {
		RestPhone = restPhone;
	}
	public String getRestCel() {
		return RestCel;
	}
	public void setRestCel(String restCel) {
		RestCel = restCel;
	}
	public String getRestCity() {
		return RestCity;
	}
	public void setRestCity(String restCity) {
		RestCity = restCity;
	}
	public String getRestArea() {
		return RestArea;
	}
	public void setRestArea(String restArea) {
		RestArea = restArea;
	}
	public String getRestAddr() {
		return RestAddr;
	}
	public void setRestAddr(String restAddr) {
		RestAddr = restAddr;
	}
	public Double getRestLatitude() {
		return RestLatitude;
	}
	public void setRestLatitude(Double restLatitude) {
		RestLatitude = restLatitude;
	}
	public Double getRestLongitude() {
		return RestLongitude;
	}
	public void setRestLongitude(Double restLongitude) {
		RestLongitude = restLongitude;
	}
	public Time getLastOrder_midday() {
		return LastOrder_midday;
	}
	public void setLastOrder_midday(Time lastOrder_midday) {
		LastOrder_midday = lastOrder_midday;
	}
	public Time getLastOrder_night() {
		return LastOrder_night;
	}
	public void setLastOrder_night(Time lastOrder_night) {
		LastOrder_night = lastOrder_night;
	}
	public String getRestName() {
		return RestName;
	}
	public void setRestName(String restName) {
		RestName = restName;
	}
	/*public OwnerVO getOwnerVO() {
		return ownerVO;
	}
	public void setOwnerVO(OwnerVO ownerVO) {
		this.ownerVO = ownerVO;
	}*/
	
	
}
