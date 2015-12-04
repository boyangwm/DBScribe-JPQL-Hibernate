package com.orderdetail.model;

import hibernate.util.HibernateUtil;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class OrderDetailDAOHibernate implements OrderDetailDAO_interface {

	private static final String GET_ALL_STMT = "from OrderDetailVO order by OrderSumID";
	private static final String GET_By_OrderSumID_STMT = "from OrderDetailVO where OrderSumID =:OrderSumID";
	private static final String DELETE_By_OrderSumID_STMT = "delete from OrderDetailVO where OrderSumID =:OrderSumID";

	@Override
	public void insert(OrderDetailVO orderDetailVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(orderDetailVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}

	}

	@Override
	public void update(OrderDetailVO orderDetailVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(orderDetailVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}

	}

	@Override
	public void deleteByOrderSumID(String orderSumID) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(DELETE_By_OrderSumID_STMT);
			query.setParameter("OrderSumID", orderSumID);
			query.executeUpdate();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}

	}

	@Override
	public void deleteByOrderDetailVO(OrderDetailVO orderDetailVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.delete(orderDetailVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}

	}

	@Override
	public List<OrderDetailVO> getByPrimaryKey(String orderSumID) {
		List<OrderDetailVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_By_OrderSumID_STMT);
			query.setParameter("OrderSumID", orderSumID);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	@Override
	public List<OrderDetailVO> getAll() {
		List<OrderDetailVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_ALL_STMT);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}
	public static void main(String[] args){
		OrderDetailDAOHibernate dao = new OrderDetailDAOHibernate();
		OrderDetailVO vo = new OrderDetailVO();
		vo.setOrderSumID("10120141225001");
		vo.setDishID(2);
		vo.setPrice(100);
		vo.setQuantity(2);
		vo.setSubtotal(200);
		dao.insert(vo);
//		dao.deleteByOrderSumID(3);
		List<OrderDetailVO> list = dao.getAll();
		for (OrderDetailVO orderDetailVO : list) {
			System.out.println(orderDetailVO.getOrderSumID()+","+orderDetailVO.getDishVO().getDishName()
					+","+orderDetailVO.getPrice()+","+orderDetailVO.getQuantity()+","
					+orderDetailVO.getSubtotal());
		}
	}

}