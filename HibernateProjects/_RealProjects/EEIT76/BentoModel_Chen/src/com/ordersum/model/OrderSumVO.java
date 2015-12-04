package com.ordersum.model;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;

import com.member.model.MemberVO;
import com.ordercond.model.OrderCondVO;
import com.orderdetail.model.OrderDetailVO;
import com.restaurant.model.RestaurantVO;

public class OrderSumVO {
	private String OrderSumID;
	private RestaurantVO RestaurantVO;
	private MemberVO MemberVO;
	private String MemberPhone;
	private String City;
	private String Area;
	private String Addr;
	private Double Latitude;
	private Double Longitude;
	private Integer TotalPrice;
	private Date OrderDate;
	private Time OrderTime;
	private Date ExpectDate;
	private Time ExpectTime;
	private String Memo;
	private String MemoResponse;
	private OrderCondVO OrderCondVO;
	private Set<OrderDetailVO> orderDetails;

	public String getOrderSumID() {
		return OrderSumID;
	}

	public void setOrderSumID(String orderSumID) {
		OrderSumID = orderSumID;
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

	public MemberVO getMemberVO() {
		return MemberVO;
	}

	public void setMemberVO(MemberVO memberVO) {
		MemberVO = memberVO;
	}

	public String getMemberPhone() {
		return MemberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		MemberPhone = memberPhone;
	}

	public String getAddr() {
		return Addr;
	}

	public void setAddr(String addr) {
		Addr = addr;
	}

	public Integer getTotalPrice() {
		return TotalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		TotalPrice = totalPrice;
	}

	public Date getOrderDate() {
		return OrderDate;
	}

	public void setOrderDate(Date orderDate) {
		OrderDate = orderDate;
	}

	public Time getOrderTime() {
		return OrderTime;
	}

	public void setOrderTime(Time orderTime) {
		OrderTime = orderTime;
	}

	public Date getExpectDate() {
		return ExpectDate;
	}

	public void setExpectDate(Date expectDate) {
		ExpectDate = expectDate;
	}

	public Time getExpectTime() {
		return ExpectTime;
	}

	public void setExpectTime(Time expectTime) {
		ExpectTime = expectTime;
	}

	public String getMemo() {
		return Memo;
	}

	public void setMemo(String memo) {
		Memo = memo;
	}

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

	public String getMemoResponse() {
		return MemoResponse;
	}

	public void setMemoResponse(String memoResponse) {
		MemoResponse = memoResponse;
	}

	public OrderCondVO getOrderCondVO() {
		return OrderCondVO;
	}

	public void setOrderCondVO(OrderCondVO orderCondVO) {
		OrderCondVO = orderCondVO;
	}

	public Set<OrderDetailVO> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<OrderDetailVO> orderDetails) {
		this.orderDetails = orderDetails;
	}
}
