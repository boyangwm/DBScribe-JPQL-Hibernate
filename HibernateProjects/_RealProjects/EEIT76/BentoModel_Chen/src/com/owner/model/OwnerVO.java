package com.owner.model;

import java.util.Set;

import com.restaurant.model.RestaurantVO;

public class OwnerVO {
	
	
	private String OwnAcc;
	private String OwnPwd;
	private String OwnEmail;
	private String OwnLastName;
	private String OwnFirstName;
	private boolean OwnGender;
	private boolean OwnOpen;
	private Set<RestaurantVO> restaurants;
	
	public boolean isOwnGender() {
		return OwnGender;
	}
	public void setOwnGender(boolean ownGender) {
		OwnGender = ownGender;
	}
	public String getOwnAcc() {
		return OwnAcc;
	}
	public void setOwnAcc(String ownAcc) {
		OwnAcc = ownAcc;
	}
	public String getOwnPwd() {
		return OwnPwd;
	}
	public void setOwnPwd(String ownPwd) {
		OwnPwd = ownPwd;
	}
	public String getOwnEmail() {
		return OwnEmail;
	}
	public void setOwnEmail(String ownEmail) {
		OwnEmail = ownEmail;
	}
	public String getOwnLastName() {
		return OwnLastName;
	}
	public void setOwnLastName(String ownLastName) {
		OwnLastName = ownLastName;
	}
	public String getOwnFirstName() {
		return OwnFirstName;
	}
	public void setOwnFirstName(String ownFirstName) {
		OwnFirstName = ownFirstName;
	}
	public boolean isOwnOpen() {
		return OwnOpen;
	}
	public void setOwnOpen(boolean ownOpen) {
		OwnOpen = ownOpen;
	}
	public Set<RestaurantVO> getRestaurants() {
		return restaurants;
	}
	public void setRestaurants(Set<RestaurantVO> restaurants) {
		this.restaurants = restaurants;
	}

}
