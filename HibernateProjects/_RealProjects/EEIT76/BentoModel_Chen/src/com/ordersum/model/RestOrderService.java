package com.ordersum.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.member.model.MemberDAO;
import com.ordercond.model.OrderCondVO;

public class RestOrderService {
		
	private OrderSumDAO_Interface dao;

	public RestOrderService() {
		dao = new OrderSumDAO();
	}
	
	//這裡的update會在修改訂單狀態時用到,會動到的欄位為OrderCondID
	public OrderSumVO updateOrderCond(String OrderSumID, Integer OrderCondID){
		OrderSumVO orderSumVO = dao.getByPrimaryKey(OrderSumID);
		OrderCondVO orderCondVO = new OrderCondVO();
		orderCondVO.setOrderCondID(OrderCondID);
		orderSumVO.setOrderCondVO(orderCondVO);
		dao.update(orderSumVO);
		return dao.getByPrimaryKey(OrderSumID);
	}
	
	//這裡的update會在店家留言給客戶時用到,會動到的欄位為MemoResponse
	public OrderSumVO updateMemoRes(String OrderSumID, String MemoResponse){
		OrderSumVO orderSumVO = dao.getByPrimaryKey(OrderSumID);
		orderSumVO.setMemoResponse(MemoResponse);
		dao.update(orderSumVO);
		return dao.getByPrimaryKey(OrderSumID);
	}
	
	//秀出所有訂單(最新的訂單會排在最上面)
	public List<OrderSumVO> getAll(Integer RestID){
		return dao.getAllByRestID(RestID);
	}
	
	//根據訂單狀態選擇要秀出的訂單
	public List<OrderSumVO> getByOrderCond(Integer RestID,Integer OrderCondID){
		return dao.getByRestOrderCondID(RestID, OrderCondID);
	}
	
	//根據客戶要拿到便當的日期選擇要秀出的訂單(若還要指定時間的話會移到前端另做處理)
	public List<OrderSumVO> getByExpectDate(Integer RestID,Date ExpectDate){
		return dao.getByRestExpectDate(RestID, ExpectDate);
	}
	
	//根據客戶的聯絡電話選擇要秀出的訂單
	public List<OrderSumVO> getByMemberPhone(Integer RestID,String MemberPhone){
		return dao.getByRestMemberPhone(RestID, MemberPhone);
	}
		
	//根據客戶的姓名選擇要秀出的訂單
	public List<OrderSumVO> getByMemberName(Integer RestID, String MemberLastName,String MemberFirstName){
		List<OrderSumVO> result = new ArrayList<OrderSumVO>();
		MemberDAO mdao = new MemberDAO();
		List<String> list = mdao.GetMemberAcc(MemberLastName, MemberFirstName);
		for (String MemberAcc : list) {
			List<OrderSumVO> temp = dao.getByRestMemberAcc(RestID, MemberAcc);
			for (OrderSumVO orderSumVO : temp) {
				result.add(orderSumVO);
			}
		}
		return result;
	}

	public static void main(String[] args) {
		RestOrderService service = new RestOrderService();
		List<OrderSumVO> list = service.getByMemberName(101, "奧", "客");
		for (OrderSumVO vo : list) {
			System.out.println("-------總表--------");
			System.out.print(vo.getOrderSumID() + ",");
			System.out.print(vo.getRestaurantVO().getRestName() + ",");
			System.out.print(vo.getMemberVO().getMemberAcc() + ",");
			System.out.print(vo.getMemberPhone() + ",");
			System.out.print(vo.getCity() + ",");
			System.out.print(vo.getArea() + ",");
			System.out.print(vo.getAddr() + ",");
			System.out.print(vo.getLatitude() + ",");
			System.out.print(vo.getLongitude() + ",");
			System.out.print(vo.getTotalPrice() + ",");
			System.out.print(vo.getOrderDate() + ",");
			System.out.print(vo.getOrderTime() + ",");
			System.out.print(vo.getExpectDate() + ",");
			System.out.print(vo.getExpectTime() + ",");
			System.out.print(vo.getMemo() + ",");
			System.out.print(vo.getOrderCondVO().getOrderCond()+"\n");
		}
	}

}
