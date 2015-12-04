package com.accuse.model;

import java.sql.Date;
import com.restdiscuss.model.RestDiscussVO;
import com.restaurant.model.RestaurantVO;

public class AccuseVO implements java.io.Serializable{
	private Integer AccuseID;
	private String OrderSumID;
	private Integer RestDiscussID;
	private String Reason;
	private RestaurantVO restaurantVO;
	//private MemberVO memberVO;
	//private AccuseCaseVO accuseCaseVO;
	private Integer RestID;
	private Integer CaseID;
	private Boolean DealCond;
	private String DealDetail;
	private String  MemberAcc;
	
	public Integer getRestID() {
		return RestID;
	}
	public void setRestID(Integer restID) {
		RestID = restID;
	}
	public Integer getCaseID() {
		return CaseID;
	}
	public void setCaseID(Integer caseID) {
		CaseID = caseID;
	}
	public String getMemberAcc() {
		return MemberAcc;
	}
	public void setMemberAcc(String memberAcc) {
		MemberAcc = memberAcc;
	}
	public Integer getAccuseID() {
		return AccuseID;
	}
	public void setAccuseID(Integer accuseID) {
		AccuseID = accuseID;
	}
	public String getReason() {
		return Reason;
	}
	public void setReason(String reason) {
		Reason = reason;
	}
	public RestaurantVO getRestaurantVO() {
		return restaurantVO;
	}
	public void setRestaurantVO(RestaurantVO restaurantVO) {
		this.restaurantVO = restaurantVO;
	}
	public String getOrderSumID() {
		return OrderSumID;
	}
	public void setOrderSumID(String orderSumID) {
		OrderSumID = orderSumID;
	}
	public Integer getRestDiscussID() {
		return RestDiscussID;
	}
	public void setRestDiscussID(Integer restDiscussID) {
		RestDiscussID = restDiscussID;
	}
	public Boolean getDealCond() {
		return DealCond;
	}
	public void setDealCond(Boolean dealCond) {
		DealCond = dealCond;
	}
	public String getDealDetail() {
		return DealDetail;
	}
	public void setDealDetail(String dealDetail) {
		DealDetail = dealDetail;
	}
	
	/*public MemberVO getMemberVO() {
		return memberVO;
	}
	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}*/
	/*public AccuseCaseVO getAccuseCaseVO() {
		return accuseCaseVO;
	}
	public void setAccuseCaseVO(AccuseCaseVO accuseCaseVO) {
		this.accuseCaseVO = accuseCaseVO;
	}*/
	
	
}
