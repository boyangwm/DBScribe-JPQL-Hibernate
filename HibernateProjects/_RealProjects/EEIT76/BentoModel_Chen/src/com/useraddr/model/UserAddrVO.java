package com.useraddr.model;

public class UserAddrVO {
	private Integer UserAddrID   ;
	private String City		;
	private String Area		;
	private String Addr	;
	private String MemberAcc ;
	private Double Latitude;
	private Double Longitude;
	
	public Integer getUserAddrID() {
		return UserAddrID;	}
	public void setUserAddrID(Integer userAddrID) {
		UserAddrID = userAddrID;	}
	public String getCity() {
		return City;	}
	public void setCity(String city) {
		City = city;	}
	public String getArea() {
		return Area;	}
	public void setArea(String area) {
		Area = area;	}
	public String getAddr() {
		return Addr;	}
	public void setAddr(String addr) {
		Addr = addr;
	}
	public String getMemberAcc() {
		return MemberAcc;	}
	public void setMemberAcc(String memberAcc) {
		MemberAcc = memberAcc;	}
	public Double getLatitude() {
		return Latitude;
	}
	public void setLatitude(Double latitude) {
		Latitude = latitude;
	}
	public Double getLongitude() {
		return Longitude;
	}
	public void setLongitude(Double longitude) {
		Longitude = longitude;
	}
}
