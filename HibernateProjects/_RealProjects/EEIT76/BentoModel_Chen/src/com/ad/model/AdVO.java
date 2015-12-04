package com.ad.model;

import java.sql.Date;

import com.restaurant.model.RestaurantVO;
import com.treatcase.model.TreatCaseVO;

public class AdVO {
	
	private Integer RestID;
	private String Context;
	private byte[] Image;
	private Date StartDate;
	private Date EndDate;
	private String TreatDetail;
	private Integer TreatID;
	private TreatCaseVO TreatCaseVO;
	private RestaurantVO RestaurantVO;

	
	
	public Integer getTreatID() {
		return TreatID;
	}
	public void setTreatID(Integer treatID) {
		TreatID = treatID;
	}
	public Integer getRestID() {
		return RestID;
	}
	public void setRestID(Integer restID) {
		RestID = restID;
	}
	public String getContext() {
		return Context;
	}
	public void setContext(String context) {
		Context = context;
	}
	public byte[] getImage() {
		return Image;
	}
	public void setImage(byte[] image) {
		Image = image;
	}
	public Date getStartDate() {
		return StartDate;
	}
	public void setStartDate(Date startDate) {
		StartDate = startDate;
	}
	public Date getEndDate() {
		return EndDate;
	}
	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}
	public String getTreatDetail() {
		return TreatDetail;
	}
	public void setTreatDetail(String treatDetail) {
		TreatDetail = treatDetail;
	}
	public TreatCaseVO getTreatCaseVO() {
		return TreatCaseVO;
	}
	public void setTreatCaseVO(TreatCaseVO treatCaseVO) {
		TreatCaseVO = treatCaseVO;
	}
	public RestaurantVO getRestaurantVO() {
		return RestaurantVO;
	}
	public void setRestaurantVO(RestaurantVO restaurantVO) {
		RestaurantVO = restaurantVO;
	}

}
