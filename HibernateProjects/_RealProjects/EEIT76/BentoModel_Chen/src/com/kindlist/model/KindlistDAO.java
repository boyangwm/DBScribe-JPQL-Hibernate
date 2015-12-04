package com.kindlist.model;

import hibernate.util.HibernateUtil;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.ad.model.AdDAO;
import com.ad.model.AdVO;


public class KindlistDAO implements KindlistDAO_interface {
	
	private static final String GET_ALL_STMT = "from KindlistVO order by KindID";
	@Override
	public void insert(KindlistVO kindlistVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(kindlistVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}


	}

	@Override
	public void update(KindlistVO kindlistVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		try {
			session.beginTransaction();
			session.saveOrUpdate(kindlistVO);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		}
		

	}

	@Override
	public void delete(Integer KindID) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		try {
			session.beginTransaction();
			Object a = session.get(KindlistVO.class, KindID);
			KindlistVO kindlistVO = (KindlistVO)session.get(KindlistVO.class, KindID);
			System.out.println(a);
			session.delete(kindlistVO);
			session.getTransaction().commit();
			
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		}

	}

	@Override
	public KindlistVO getByPrimaryKey(Integer KindID) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		KindlistVO kindlistVO = null;
		try {
			session.beginTransaction();
			kindlistVO = (KindlistVO)session.get(KindlistVO.class,KindID);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return kindlistVO;
	}

	@Override
	public List<KindlistVO> getAll() {
		
		List<KindlistVO> list = null;
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

		KindlistDAO dao = new KindlistDAO();

//		 �s�W
//		KindlistVO KindlistVO1 = new KindlistVO();
//		KindlistVO1.setKindID(123);
//		
//		dao.insert(KindlistVO1);
		//�R��
	//	dao.delete(123);
		//��
		
//		KindlistVO KindlistVO1 = new KindlistVO();
//		KindlistVO1.setKindID(123);
//		KindlistVO1.setKindName("999");
//		dao.insert(KindlistVO1);
		
		KindlistVO KindlistVO1 = dao.getByPrimaryKey(123);
		System.out.println(KindlistVO1.getKindName());
		System.out.println(KindlistVO1.getKindID());
		
		
		List<KindlistVO> list = dao.getAll();
		
		for (KindlistVO aDept : list) {
			System.out.print(aDept.getKindName() + ",");
			System.out.print(aDept.getKindID() + ",");
			
		}
   }

}
