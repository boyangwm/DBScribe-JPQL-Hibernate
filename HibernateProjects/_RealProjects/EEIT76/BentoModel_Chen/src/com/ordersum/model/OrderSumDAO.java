package com.ordersum.model;

import org.hibernate.*;

import com.member.model.MemberVO;
import com.ordercond.model.OrderCondVO;
import com.orderdetail.model.OrderDetailVO;
import com.restaurant.model.RestaurantVO;

import hibernate.util.HibernateUtil;

import java.sql.Date;
import java.sql.Time;
import java.util.*;

public class OrderSumDAO implements OrderSumDAO_Interface {
	private static final String GET_ALL_RSTMT = "from OrderSumVO where RestID=? order by OrderSumID desc";
	private static final String GET_ALL_MSTMT = "from OrderSumVO where MemberAcc=? order by OrderDate desc, OrderTime desc";
	private static final String GET_BY_OSCID = "from OrderSumVO where RestID=? and OrderCondID=? order by OrderSumID desc";
	private static final String GET_BY_MCID = "from OrderSumVO where MemberAcc=? and OrderCondID=? order by OrderSumID desc";
	private static final String GET_BY_OSET = "from OrderSumVO where RestID=? and ExpectDate=? order by ExpectTime";
	private static final String GET_BY_Mtime = "from OrderSumVO where MemberAcc=? and ExpectDate=? order by ExpectTime";
	private static final String GET_BY_OSMP = "from OrderSumVO where RestID=? and MemberPhone=? order by OrderDate desc, OrderTime desc";
	private static final String GET_BY_ROSMA = "from OrderSumVO where RestID=? and MemberAcc=? order by OrderDate desc, OrderTime desc";
	private static final String GET_BY_OSRO = "select count(*) as count from OrderSumVO where RestID=? and OrderDate=?";

	@Override
	public void insert(OrderSumVO orderSumVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(orderSumVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void update(OrderSumVO orderSumVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(orderSumVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void delete(String OrderSumID) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			OrderSumVO orderSumVO = new OrderSumVO();
			orderSumVO.setOrderSumID(OrderSumID);
			session.delete(orderSumVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public OrderSumVO getByPrimaryKey(String OrderSumID) {
		OrderSumVO orderSumVO = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			orderSumVO = (OrderSumVO) session.get(OrderSumVO.class, OrderSumID);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return orderSumVO;
	}

	@Override
	public List<OrderSumVO> getByRestExpectDate(Integer RestID,Date ExpectDate) {
		List<OrderSumVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_OSET);
			query.setParameter(0, RestID);
			query.setParameter(1, ExpectDate);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}
	
	public List<OrderSumVO> getByMemberExpectDate(String MemberAcc,Date ExpectDate) {
		List<OrderSumVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_Mtime);
			query.setParameter(0, MemberAcc);
			query.setParameter(1, ExpectDate);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}
	
	@Override
	public List<OrderSumVO> getByMemberOrderCondID(String MemberAcc,Integer OrderCondID) {
		List<OrderSumVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_MCID);
			query.setParameter(0, MemberAcc);
			query.setParameter(1, OrderCondID);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	@Override
	public List<OrderSumVO> getByRestOrderCondID(Integer RestID,Integer OrderCondID) {
		List<OrderSumVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_OSCID);
			query.setParameter(0, RestID);
			query.setParameter(1, OrderCondID);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	@Override
	public List<OrderSumVO> getByRestMemberPhone(Integer RestID,String MemberPhone) {
		List<OrderSumVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_OSMP);
			query.setParameter(0, RestID);
			query.setParameter(1, MemberPhone);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	@Override
	public List<OrderSumVO> getByRestMemberAcc(Integer RestID,String MemberAcc) {
		List<OrderSumVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_ROSMA);
			query.setParameter(0, RestID);
			query.setParameter(1, MemberAcc);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}
	
	@Override
	public long getCountByRestIDAndOrderDate(Integer RestID, Date OrderDate) {
		long count = 0;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_OSRO);
			query.setParameter(0, RestID);
			query.setParameter(1, OrderDate);
			count = (Long)query.list().get(0);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return count;
	}
	
	@Override
	public List<OrderSumVO> getAllByRestID(Integer RestID) {
		List<OrderSumVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_ALL_RSTMT);
			query.setParameter(0, RestID);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	@Override
	public List<OrderSumVO> getAllByMemberAcc(String MemberAcc) {
		List<OrderSumVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_ALL_MSTMT);
			query.setParameter(0, MemberAcc);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	public static void main(String[] args) {
		OrderSumDAO ordersumdao = new OrderSumDAO();
		RestaurantVO restaurantVO = new RestaurantVO();
		restaurantVO.setRestID(101);
		MemberVO memberVO = new MemberVO();
		memberVO.setMemberAcc("Bad7788");
		OrderCondVO orderCondVO = new OrderCondVO();
		orderCondVO.setOrderCondID(2);
		Set<OrderDetailVO> set = new LinkedHashSet<OrderDetailVO>();
		// OrderDetail
		OrderDetailVO orderDetail1 = new OrderDetailVO();
		OrderDetailVO orderDetail2 = new OrderDetailVO();
		// 新增
				OrderSumVO ordersumvo1 = new OrderSumVO();
				String[] temp = new Date(new java.util.Date().getTime()).toString().split("-");
				long re = ordersumdao.getCountByRestIDAndOrderDate(101, java.sql.Date.valueOf("2014-12-24"));
				ordersumvo1.setOrderSumID("101"+temp[0]+temp[1]+temp[2]+(((re+1)<10)?"00"+(re+1):((re+1)<100)?"0"+(re+1):(re+1)));
				ordersumvo1.setRestaurantVO(restaurantVO);
				ordersumvo1.setMemberVO(memberVO);
				ordersumvo1.setMemberPhone("0266316666");
				ordersumvo1.setCity("台北市");
				ordersumvo1.setArea("信義區");
				ordersumvo1.setAddr("信義路");
				ordersumvo1.setLatitude(0.0);
				ordersumvo1.setLongitude(0.0);
				ordersumvo1.setTotalPrice(500);
				ordersumvo1.setOrderDate(new Date(new java.util.Date().getTime()));
				ordersumvo1.setOrderTime(new Time(new java.util.Date().getTime()));
				ordersumvo1.setOrderCondVO(orderCondVO);
				orderDetail1.setOrderSumID("101"+temp[0]+temp[1]+temp[2]+(((re+1)<10)?"00"+(re+1):((re+1)<100)?"0"+(re+1):(re+1)));
				orderDetail1.setDishID(2);
				orderDetail1.setPrice(100);
				orderDetail1.setQuantity(1);
				orderDetail1.setSubtotal(100);
				orderDetail2.setOrderSumID("101"+temp[0]+temp[1]+temp[2]+(((re+1)<10)?"00"+(re+1):((re+1)<100)?"0"+(re+1):(re+1)));
				orderDetail2.setDishID(1);
				orderDetail2.setPrice(85);
				orderDetail2.setQuantity(1);
				orderDetail2.setSubtotal(85);
				set.add(orderDetail1);
				set.add(orderDetail2);
				ordersumvo1.setOrderDetails(set);
				ordersumdao.insert(ordersumvo1);
				
				List<OrderSumVO> ordersumlist = ordersumdao.getAllByMemberAcc("Bad5566");
				for (OrderSumVO ordersum : ordersumlist) {
					System.out.println("\n-------總表--------");
					System.out.print(ordersum.getOrderSumID() + ",");
					System.out.print(ordersum.getRestaurantVO().getRestName() + ",");
					System.out.print(ordersum.getMemberVO().getMemberAcc() + ",");
					System.out.print(ordersum.getMemberPhone() + ",");
					System.out.print(ordersum.getAddr() + ",");
					System.out.print(ordersum.getLatitude() + ",");
					System.out.print(ordersum.getLongitude() + ",");
					System.out.print(ordersum.getTotalPrice() + ",");
					System.out.print(ordersum.getOrderTime() + ",");
					System.out.print(ordersum.getExpectTime() + ",");
					System.out.print(ordersum.getMemo() + ",");
					System.out.print(ordersum.getOrderCondVO().getOrderCond());
					System.out.println("\n-------明細--------");
			Set<OrderDetailVO> set1 = ordersum.getOrderDetails();
			for (OrderDetailVO orderDetailVO : set1) {
				System.out.print(orderDetailVO.getOrderSumID()+ ",");
				System.out.print(orderDetailVO.getDishVO().getDishName()+ ",");
				System.out.print(orderDetailVO.getPrice()+ ",");
				System.out.print(orderDetailVO.getQuantity()+ ",");
				System.out.println(orderDetailVO.getSubtotal());

			}
		}
	}

	
	
}