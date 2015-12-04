package com.orderdetail.model;

import java.util.*;

public interface OrderDetailDAO_interface {
	public void insert(OrderDetailVO orderDetailVO);

	public void update(OrderDetailVO orderDetailVO);

	public void deleteByOrderSumID(String orderSumID);

	public void deleteByOrderDetailVO(OrderDetailVO orderDetailVO);

	public List<OrderDetailVO> getByPrimaryKey(String orderSumID);

	public List<OrderDetailVO> getAll();
}
