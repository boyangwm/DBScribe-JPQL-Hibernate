package com.dish.model;
import java.sql.Date;

public class DishVO implements java.io.Serializable{
	private Integer DishID;
	private String DishName;
	private Integer Price;
	private Integer SpecialPrice;
	private Date StartDate;
	private Date EndDate;
	private Integer RestID;
	
	public Integer getRestID() {
		return RestID;
	}
	public void setRestID(Integer restID) {
		RestID = restID;
	}
	public Integer getDishID() {
		return DishID;
	}
	public void setDishID(Integer dishID) {
		DishID = dishID;
	}
	public String getDishName() {
		return DishName;
	}
	public void setDishName(String dishName) {
		DishName = dishName;
	}
	public Integer getPrice() {
		return Price;
	}
	public void setPrice(Integer price) {
		Price = price;
	}
	public Integer getSpecialPrice() {
		return SpecialPrice;
	}
	public void setSpecialPrice(Integer specialPrice) {
		SpecialPrice = specialPrice;
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
}
