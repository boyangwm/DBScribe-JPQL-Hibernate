package com.randompwd.model;
import hibernate.util.HibernateUtil;

import org.hibernate.Query;
import org.hibernate.Session;

public class RandomPwdHibernateDAO implements RandomPwd_interfce {
	@Override
	public void insert(RandomPwdVO randompwdVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(randompwdVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}
	@Override
	public RandomPwdVO SelectOne(String randompwd) {
		RandomPwdVO randompwdVO = null ;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			randompwdVO =  (RandomPwdVO) session.get(RandomPwdVO.class, randompwd);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return randompwdVO;
	}
}
