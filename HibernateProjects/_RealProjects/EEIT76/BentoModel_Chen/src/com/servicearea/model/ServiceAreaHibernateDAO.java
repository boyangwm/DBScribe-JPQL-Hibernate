package com.servicearea.model;

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

import hibernate.util.HibernateUtil;

import java.util.*;

public class ServiceAreaHibernateDAO implements ServiceAreaDAO_interface {

	private static final String GET_ALL_STMT = "from ServiceAreaVO order by ServiceAreaID";
	private static final String GET_BY_AREA = "from ServiceAreaVO where City=? and Area=?  order by ServiceAreaID";
	private static final String GET_BY_REST = "from ServiceAreaVO where RestID=? order by ServiceAreaID";
	@Override
	public void insert(ServiceAreaVO serviceAreaVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(serviceAreaVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void update(ServiceAreaVO serviceAreaVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(serviceAreaVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void delete(Integer serviceAreaID) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();

			ServiceAreaVO serviceAreaVO = new ServiceAreaVO();
			serviceAreaVO.setServiceAreaID(serviceAreaID);
			session.delete(serviceAreaVO);

			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public ServiceAreaVO getByPrimaryKey(Integer serviceAreaID) {
		ServiceAreaVO serviceAreaVO = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			serviceAreaVO = (ServiceAreaVO) session.get(ServiceAreaVO.class, serviceAreaID);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return serviceAreaVO;
	}

	@Override
	public List<ServiceAreaVO> getAll() {
		List<ServiceAreaVO> list = null;
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
	public List<ServiceAreaVO> getByArea(String City , String Area) {
		List<ServiceAreaVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_AREA);
			query.setParameter(0,City);
			query.setParameter(1,Area);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}
	
	@Override
	public List<ServiceAreaVO> getByRest(Integer restID ) {
		List<ServiceAreaVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_REST);
			query.setParameter(0,restID);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	public static void main(String[] args) {
		ServiceAreaHibernateDAO dao = new ServiceAreaHibernateDAO();

		
//		ServiceAreaVO empVO1 = new ServiceAreaVO();
//		empVO1.setRestID(3);
//		empVO1.setArea("�s�_��");
//		empVO1.setCity("�s����");

		//dao.insert(empVO1);
		//dao.delete(2);
		
//		ServiceAreaVO empVO2 = new ServiceAreaVO();
//		empVO2.setServiceAreaID(3);
//		empVO2.setArea("��s��");
//		empVO2.setCity("�x�_��");
//		empVO2.setRestID(1);
//		dao.update(empVO2);
//		
		
//		List<ServiceAreaVO> list = dao.getAll();
		List<ServiceAreaVO> list = dao.getByRest(102);
		
		
		for (ServiceAreaVO aEmp : list) {
			System.out.print(aEmp.getServiceAreaID() + ",");
			System.out.print(aEmp.getCity() + ",");
			System.out.print(aEmp.getArea() + ",");
			System.out.print(aEmp.getRestID()+ "," );
			System.out.print(aEmp.getRestaurantVO().getRestName() );
			//System.out.print(aEmp.getRestaurantVO().getRestID());
			System.out.println();
		}
	}
}
