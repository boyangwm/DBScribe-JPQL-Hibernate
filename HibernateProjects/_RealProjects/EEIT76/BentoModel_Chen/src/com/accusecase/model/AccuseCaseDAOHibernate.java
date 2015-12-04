package com.accusecase.model;

import hibernate.util.HibernateUtil;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class AccuseCaseDAOHibernate implements AccuseCaseDAO_interface {
	
	private static final String GET_ALL_STMT = "from AccuseCaseVO order by CaseID";

	@Override
	public void insert(AccuseCaseVO accuseCaseVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(accuseCaseVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}

	}

	@Override
	public void update(AccuseCaseVO accuseCaseVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(accuseCaseVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}

	}

	@Override
	public void delete(Integer caseID) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			AccuseCaseVO accuseCaseVO = new AccuseCaseVO();
			accuseCaseVO.setCaseID(caseID);
			session.delete(accuseCaseVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}

	}

	@Override
	public AccuseCaseVO getByPrimaryKey(Integer caseID) {
		AccuseCaseVO accuseCaseVO = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			accuseCaseVO = (AccuseCaseVO) session.get(AccuseCaseVO.class, caseID);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return accuseCaseVO;
	}

	@Override
	public List<AccuseCaseVO> getAll() {
		List<AccuseCaseVO> list = null;
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
		AccuseCaseDAOHibernate dao = new AccuseCaseDAOHibernate();
//		AccuseCaseVO vo = new AccuseCaseVO();
//		vo.setCaseID(2);
//		vo.setCaseRelation("下單不取貨");
//		dao.insert(vo);
		dao.delete(1);
		List<AccuseCaseVO> vo = dao.getAll();
		for(AccuseCaseVO temp : vo){
			System.out.println(temp.getCaseID()+":"+temp.getCaseRelation());
		}
	}

}