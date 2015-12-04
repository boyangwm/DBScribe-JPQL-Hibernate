package com.accuse.model;

/*
 Hibernate is providing a factory.getCurrentSession() method for retrieving the current session. A
 new session is opened for the first time of calling this method, and closed when the transaction is
 finished, no matter commit or rollback. But what does it mean by the ��current session��? We need to
 tell Hibernate that it should be the session bound with the current thread.

 <hibernate-configuration>
 <session-factory>
 ...
 <property name="current_session_context_class">thread</property>
 ...
 </session-factory>
 </hibernate-configuration>

 */

import org.hibernate.*;

import com.ordercond.model.OrderCondVO;

import hibernate.util.HibernateUtil;

import java.util.*;

public class AccuseHibernateDAO implements AccuseDAO_interface {

	private static final String GET_ALL_STMT = "from AccuseVO order by AccuseID";
	private static final String GET_BY_RESTDISCUSSID = "from AccuseVO where RestDiscussID = ? order by AccuseID";
	private static final String GET_BY_ORDERSUMID = "from AccuseVO where OrderSumID = ? order by AccuseID";
	private static final String GET_BY_ORDERCASEID = "from AccuseVO where CaseID = ? order by AccuseID";
	private static final String GET_BY_RESTID = "from AccuseVO where RestID = ? order by AccuseID";
	@Override
	public void insert(AccuseVO accuseVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(accuseVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void update(AccuseVO accuseVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(accuseVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void delete(Integer accuseID) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			AccuseVO accuseVO = new AccuseVO();
			accuseVO.setAccuseID(accuseID);
			session.delete(accuseVO);

			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public AccuseVO getByPrimaryKey(Integer accuseID) {
		AccuseVO accuseVO = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			accuseVO = (AccuseVO) session.get(AccuseVO.class, accuseID);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return accuseVO;
	}

	@Override
	public List<AccuseVO> getAll() {
		List<AccuseVO> list = null;
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
	
	@Override
	public List<AccuseVO> getByRestDiscussID(Integer restDiscussID) {
		List<AccuseVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_RESTDISCUSSID);
			query.setParameter(0, restDiscussID);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}
	
	@Override
	public List<AccuseVO> getByOrderSumID(Integer orderSumID) {
		List<AccuseVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_ORDERSUMID);
			query.setParameter(0, orderSumID);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}
	@Override
	public List<AccuseVO> getByCaseID(Integer CaseID) {
		List<AccuseVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_ORDERCASEID);
			query.setParameter(0, CaseID);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}
	
	@Override
	public List<AccuseVO> getByRestID(Integer RestID) {
		List<AccuseVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_RESTID);
			query.setParameter(0, RestID);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}
	
	public static void main(String[] args) {
		AccuseHibernateDAO dao = new AccuseHibernateDAO();
		List<AccuseVO> accuseVOs = dao.getByRestID(102);
		
		for (AccuseVO accuseVO : accuseVOs) {
			System.out.print(accuseVO.getRestID() + ",");
			System.out.print(accuseVO.getMemberAcc() + ",");
			System.out.print(accuseVO.getReason() + ",");
			System.out.println();
		}
		
		
	}
}
