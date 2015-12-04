package com.restkind.model;

import hibernate.util.HibernateUtil;

import java.sql.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;







public class RestKindDAO implements RestKindDAO_interface {
	private static final String GET_ALL_STMT = "from RestKindVO order by RestID";
	private static final String GET_BY_KIND = "from RestKindVO where KindID = ? order by RestID";
	private static final String GET_BY_REST = "from RestKindVO where RestID = ? order by KindID";
	private static final String GET_COUNT = "select count(*) as count from RestKindVO where RestID=? and KindID=?";
	@Override
	public void insert(RestKindVO restkindVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(restkindVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}

	}

	@Override
	public void update(RestKindVO restkindVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(restkindVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}

	}

	@Override
	public void delete(Integer KindID, Integer RestID) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		RestKindVO restkindVO = new RestKindVO();
			restkindVO.setKindID(KindID);
			restkindVO.setRestID(RestID);
		try {
			session.beginTransaction();
		    restkindVO = (RestKindVO) session.get(RestKindVO.class, restkindVO);
			session.delete(restkindVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}

	}

	@Override
	public RestKindVO getByPrimaryKey(Integer KindID, Integer RestID) {
		RestKindVO restkindVO = new RestKindVO();
		restkindVO.setKindID(KindID);
		restkindVO.setRestID(RestID);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		try {
			session.beginTransaction();
			restkindVO = (RestKindVO)session.load(RestKindVO.class, restkindVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return restkindVO;
	

	}

	@Override
	public List<RestKindVO> getAll() {
		List<RestKindVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_ALL_STMT);
			list = query.list();//研究<------------
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}
	
	@Override
	public List<RestKindVO> getByKind(Integer KindID) {
		List<RestKindVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_KIND);
			query.setParameter(0,KindID);
			list = query.list();//研究<------------
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}
	
	@Override
	public List<RestKindVO> getByRest(Integer restID) {
		List<RestKindVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_REST);
			query.setParameter(0,restID);
			list = query.list();//研究<------------
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}
	
	public int getCount(Integer restID, Integer kindID) {
		int count = 0;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_COUNT);
			query.setParameter(0, restID);
			query.setParameter(1, kindID);
			count = (int)query.list().get(0);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return count;
	}
	
	public static void main(String[] args){
		RestKindDAO dao = new RestKindDAO();
		
		
		
//		dao.delete(1, 2);
//		RestKindVO restVO = dao.getByPrimaryKey(1,2);
		
	
		
//	System.out.println(restVO.getKindID());
//	System.out.println(restVO.getRestID());
//		
//
//     	RestKindVO AdVO1 = new RestKindVO();
//		AdVO1.setRestID(2);
//		AdVO1.setKindID(1);
//		
//		dao.insert(AdVO1);
		
	// 修改
//		AdVO adVO2 = new AdVO();
//		adVO2.setRestID(2);
//		adVO2.setContext("財務部2");
//		
//		dao.update(adVO2);
		
	
	List<RestKindVO> list1 = dao.getByKind(1);
	
	for (RestKindVO aDept : list1) {
		System.out.print(aDept.getRestID() + ",");
		System.out.print(aDept.getKindID() + ",");
		System.out.print(aDept.getKindlistVO().getKindName() + ",");
		System.out.print(aDept.getRestaurantVO().getRestName() + ",");
		System.out.println();
	}
		
	}

}
