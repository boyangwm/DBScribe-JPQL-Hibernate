package com.ordercond.model;

import java.util.List;

public class OrderCondService {
	
	private OrderCondDAO_Interface dao;
	
	public OrderCondService(){
		dao = new OrderCondDAO();
	}
	
	public List<OrderCondVO> getAll(){
		return dao.getAll();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
