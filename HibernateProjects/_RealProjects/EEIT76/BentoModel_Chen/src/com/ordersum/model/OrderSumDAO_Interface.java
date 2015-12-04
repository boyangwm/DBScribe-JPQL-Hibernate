package com.ordersum.model;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface OrderSumDAO_Interface {
	public void insert(OrderSumVO orderSumVO);
    public void update(OrderSumVO orderSumVO);
    public void delete(String OrderSumID);
    public OrderSumVO getByPrimaryKey(String OrderSumID);
    public List<OrderSumVO> getByRestOrderCondID(Integer RestID,Integer OrderCondID);
    public List<OrderSumVO> getByRestExpectDate(Integer RestID,Date ExpectDate);
    public List<OrderSumVO> getByRestMemberPhone(Integer RestID,String MemberPhone);
    public List<OrderSumVO> getByRestMemberAcc(Integer RestID,String MemberAcc);
    public long getCountByRestIDAndOrderDate(Integer RestID, Date OrderDate);
    public List<OrderSumVO> getAllByRestID(Integer RestID);
    public List<OrderSumVO> getAllByMemberAcc(String MemberAcc);
    public List<OrderSumVO> getByMemberExpectDate(String MemberAcc,Date ExpectDate);
    public List<OrderSumVO> getByMemberOrderCondID(String MemberAcc,Integer OrderCondID);
}
