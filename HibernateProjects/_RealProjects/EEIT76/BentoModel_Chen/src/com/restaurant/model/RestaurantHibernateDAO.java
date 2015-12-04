package com.restaurant.model;

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

import com.dish.model.DishHibernateDAO;
import com.dish.model.DishVO;
import com.orderdetail.model.OrderDetailVO;
import com.restkind.model.RestKindVO;
import com.servicearea.model.ServiceAreaVO;

import hibernate.util.HibernateUtil;

import java.util.*;

public class RestaurantHibernateDAO implements RestaurantDAO_interface {

	private static final String GET_BY_NAME = "SELECT RestID FROM RestaurantVO where RestName=?";
	private static final String GET_ALL_STMT = "from RestaurantVO order by RestID";
	private static final String GET_BY_CITY = "from RestaurantVO where RestCity=?  order by RestID";
	private static final String GET_BY_AREA = "from RestaurantVO where RestArea=?  order by RestID";
	@Override
	public void insert(RestaurantVO restaurantVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(restaurantVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void update(RestaurantVO restaurantVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(restaurantVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void delete(Integer restID) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();

			RestaurantVO restaurantVO = new RestaurantVO();
			restaurantVO.setRestID(restID);
			session.delete(restaurantVO);

			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public RestaurantVO getByPrimaryKey(Integer restID) {
		RestaurantVO restaurantVO = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			restaurantVO = (RestaurantVO) session.get(RestaurantVO.class, restID);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return restaurantVO;
	}

	@Override
	public List<RestaurantVO> getAll() {
		List<RestaurantVO> list = null;
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
	public List<RestaurantVO> getByCity(String RestCity) {
		List<RestaurantVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_CITY);
			query.setParameter(0, RestCity);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}
	
	@Override
	public List<RestaurantVO> getByArea(String RestArea) {
		List<RestaurantVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_AREA);
			query.setParameter(0, RestArea);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	
	
	public List<Integer> GetRestID(String RestName){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<Integer> list = null;
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_NAME);
			query.setParameter(0, RestName);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		
		return list;
	}
	
	
	public static void main(String[] args) {
		RestaurantHibernateDAO dao = new RestaurantHibernateDAO();
		List<RestaurantVO> list = dao.getAll();
		
		List<Integer> aaa = dao.GetRestID("晶美自助餐");

		for(Integer a:aaa){
			System.out.println("從名子抓ID:"+a);
		}
//		List<RestaurantVO> list = dao.getByCity("�x�_��");
		
//		List<RestaurantVO> list = dao.getByArea("�j�w��");
		
		for (RestaurantVO aEmp : list) {
//			System.out.print(aEmp.getRestID() + ",");
//			System.out.print(aEmp.getRestPhone() + ",");
//			System.out.print(aEmp.getRestCel() + ",");
//			System.out.print(aEmp.getRestCity() + ",");
//			System.out.print(aEmp.getRestArea() + ",");
//			System.out.print(aEmp.getRestAddr() + ",");
//			System.out.print(aEmp.getLastOrder_midday() + ",");
//			System.out.print(aEmp.getLastOrder_night() + ",");
//			System.out.print(aEmp.getRestName() + ",");
//			System.out.print(aEmp.getServiceAreaVO().getCity());
//			System.out.println();
		}
		RestaurantVO restaurantVO = dao.getByPrimaryKey(104);
		Set<ServiceAreaVO> serviceAreas =restaurantVO.getServiceAreas();
		for (ServiceAreaVO serviceAreaVO : serviceAreas) {
			System.out.print(serviceAreaVO.getServiceAreaID()+ ",");
			System.out.print(serviceAreaVO.getRestID()+ ",");
			System.out.print(serviceAreaVO.getCity()+ ",");
			System.out.print(serviceAreaVO.getArea()+ ",");
			System.out.println();
		}
		
		Set<RestKindVO> restKinds = restaurantVO.getRestKinds();
		for (RestKindVO restKindVO : restKinds) {
			System.out.print(restKindVO.getKindID()+ ",");
			System.out.print(restKindVO.getRestID()+ ",");
			System.out.println();
		}
		
		Set<DishVO> dishes = restaurantVO.getDishes();
		for (DishVO dishVO : dishes) {
			System.out.print(dishVO.getRestID()+ ",");
			System.out.print(dishVO.getDishID()+ ",");
			System.out.print(dishVO.getDishName()+ ",");
			System.out.print(dishVO.getPrice()+ ",");
			System.out.print(dishVO.getSpecialPrice()+ ",");
			System.out.print(dishVO.getStartDate()+ ",");
			System.out.print(dishVO.getEndDate()+ ",");
			System.out.println();
		}
		
	}
}
