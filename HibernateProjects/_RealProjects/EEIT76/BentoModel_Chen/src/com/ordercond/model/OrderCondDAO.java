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
		//�s�W
		OrderCondVO ordercondvo1 = new OrderCondVO();
		ordercondvo1.setOrderCondID(1);
		ordercondvo1.setOrderCond("���T�{");
		orderconddao.insert(ordercondvo1);
		
		//�ק�
		OrderCondVO ordercondvo2 = new OrderCondVO();
		ordercondvo2.setOrderCondID(2);
		ordercondvo2.setOrderCond("�T�{");
		orderconddao.update(ordercondvo2);
		/*
		//�R��(�p��cascade - �h��emp2.hbm.xml�p�G�]�� cascade="all"��
		// cascade="delete"�N�|�R���Ҧ��������-�]�A���ݳ����P�P�������䥦���u�N�|�@�ֳQ�R��)
//		memberdao.delete();
		*/		
		//findByPrimaryKey (�h��emp2.hbm.xml�����]��lazy="false")(�u!)
		OrderCondVO ordercondvo3 = orderconddao.getByPrimaryKey(2);
		System.out.print(ordercondvo3.getOrderCondID() + ",");
		System.out.print(ordercondvo3.getOrderCond() + ",");
		System.out.println("\n---------------------");

		//�L�X����  ���ե�
		List<OrderCondVO> ordercondlist = orderconddao.getAll();
		for (OrderCondVO ordercond : ordercondlist) {
			System.out.print(ordercond.getOrderCondID() + ",");
			System.out.print(ordercond.getOrderCond() + ",");
			System.out.println();
		}
	}
}
