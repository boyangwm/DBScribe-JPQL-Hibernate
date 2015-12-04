package com.ad.model;

import hibernate.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class AdDAO implements AdDAO_interface {
	
	private static final String GET_BY_TID = "from AdVO where TreatID=?";

	@Override
	public void insert(AdVO adVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(adVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}

	}

	@Override
	public void update(AdVO adVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(adVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}

	}

	@Override
	public void delete(Integer RestID) {
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
					
			
			
			// �A�R������-1 (by HQL)
//			Query query2 = session.createQuery(DELETE_AD);
//			query2.setParameter(0, RestID);
//			query2.executeUpdate();
			
			// �A�R������-2 (��  by session - cascade)
			AdVO adVO = (AdVO) session.get(AdVO.class, RestID);
			session.delete(adVO);
			
			session.getTransaction().commit();			
		
			
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}

	}

	@Override
	public AdVO getByPrimaryKey(Integer RestID) {
		AdVO adVO = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			adVO = (AdVO) session.get(AdVO.class, RestID);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return adVO;
	}

	@Override
	public List<AdVO> getByTreatID(Integer TreatID) {
		List<AdVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_TID);
			query.setParameter(0, TreatID);
			list = query.list();//��s<------------
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}
	public static void main(String[] args) {

		AdDAO dao = new AdDAO();

		// �s�W
//     	AdVO AdVO1 = new AdVO();
//		AdVO1.setRestID(2);
//		
//		dao.insert(AdVO1);
		
	// �ק�
//		AdVO adVO2 = new AdVO();
//		adVO2.setRestID(2);
//		adVO2.setContext("�]�ȳ�2");
//		
//		dao.update(adVO2);

	// �R��
//		dao.delete(2);

	// �d��
//		AdVO AdVO1 = dao.getByPrimaryKey(2);
//	    System.out.println(AdVO1.getRestID());
	    
		List<AdVO> advo = dao.getByTreatID(1);
		for (AdVO aDept : advo) {
			System.out.print(aDept.getRestID() + ","+aDept.getRestaurantVO().getRestName());
			System.out.print(aDept.getContext() + ","+aDept.getTreatDetail()+aDept.getTreatCaseVO().getTreatRelation());
			
			System.out.println();
		}
	}
	



}
