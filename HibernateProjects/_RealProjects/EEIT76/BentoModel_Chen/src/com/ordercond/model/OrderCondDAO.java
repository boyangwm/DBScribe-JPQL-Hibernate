package com.ordercond.model;

import org.hibernate.*;

import com.member.model.MemberDAO;
import com.member.model.MemberVO;

import hibernate.util.HibernateUtil;

import java.util.*;

public class OrderCondDAO implements OrderCondDAO_Interface {
	private static final String GET_ALL_STMT = "from OrderCondVO order by OrderCondID";
	@Override
	public void insert(OrderCondVO orderCondVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(orderCondVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void update(OrderCondVO orderCondVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(orderCondVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void delete(Integer orderCondID) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
//			Query query = session.createQuery("delete OrderCondVO where orderCondID=?");
//			query.setParameter(0, orderCondID);
			
			OrderCondVO orderCondVO = new OrderCondVO();
			orderCondVO.setOrderCondID(orderCondID);
			session.delete(orderCondVO);
			
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public OrderCondVO getByPrimaryKey(Integer OrderCondID) {
		OrderCondVO orderCondVO = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			orderCondVO = (OrderCondVO) session.get(OrderCondVO.class, OrderCondID);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return orderCondVO;
	}

	@Override
	public List<OrderCondVO> getAll() {
		List<OrderCondVO> list = null;
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

	public static void main(String[] args) {
		OrderCondDAO orderconddao = new OrderCondDAO();
		//新增
		OrderCondVO ordercondvo1 = new OrderCondVO();
		ordercondvo1.setOrderCondID(1);
		ordercondvo1.setOrderCond("未確認");
		orderconddao.insert(ordercondvo1);
		
		//修改
		OrderCondVO ordercondvo2 = new OrderCondVO();
		ordercondvo2.setOrderCondID(2);
		ordercondvo2.setOrderCond("確認");
		orderconddao.update(ordercondvo2);
		/*
		//刪除(小心cascade - 多方emp2.hbm.xml如果設為 cascade="all"或
		// cascade="delete"將會刪除所有相關資料-包括所屬部門與同部門的其它員工將會一併被刪除)
//		memberdao.delete();
		*/		
		//findByPrimaryKey (多方emp2.hbm.xml必須設為lazy="false")(優!)
		OrderCondVO ordercondvo3 = orderconddao.getByPrimaryKey(2);
		System.out.print(ordercondvo3.getOrderCondID() + ",");
		System.out.print(ordercondvo3.getOrderCond() + ",");
		System.out.println("\n---------------------");

		//印出全部  測試用
		List<OrderCondVO> ordercondlist = orderconddao.getAll();
		for (OrderCondVO ordercond : ordercondlist) {
			System.out.print(ordercond.getOrderCondID() + ",");
			System.out.print(ordercond.getOrderCond() + ",");
			System.out.println();
		}
	}
}
