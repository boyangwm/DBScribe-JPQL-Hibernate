package com.orderdetail.model;

import java.io.Serializable;

import com.dish.model.DishVO;
import com.ordersum.model.OrderSumVO;

public class OrderDetailVO implements Serializable {
	private String OrderSumID;
	private Integer DishID;
	private Integer Quantity;
	private Integer Price;
	private Integer Subtotal;
	private DishVO DishVO;

	public String getOrderSumID() {
		return OrderSumID;
	}

	public void setOrderSumID(String orderSumID) {
		OrderSumID = orderSumID;
	}

	public Integer getDishID() {
		return DishID;
	}

	public void setDishID(Integer dishID) {
		DishID = dishID;
	}

	public Integer getQuantity() {
		return Quantity;
	}

	public void setQuantity(Integer quantity) {
		Quantity = quantity;
	}

	public Integer getPrice() {
		return Price;
	}

	public void setPrice(Integer price) {
		Price = price;
	}

	public Integer getSubtotal() {
		return Subtotal;
	}

	public void setSubtotal(Integer subtotal) {
		Subtotal = subtotal;
	}

	public DishVO getDishVO() {
		return DishVO;
	}

	public void setDishVO(DishVO dishVO) {
		DishVO = dishVO;
	}

}
