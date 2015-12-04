package com.favorite.model;

import hibernate.util.HibernateUtil;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class FavoriteDAO implements FavoriteDAO_interface {

	private static final String GET_ALL_STMT = "from FavoriteVO order by RestID";
	private static final String GET_By_MemberAcc_STMT = "from FavoriteVO where MemberAcc =:MemberAcc";
	private static final String GET_By_RestID_STMT = "from FavoriteVO where RestID =:RestID";

	@Override
	public void insert(FavoriteVO favoriteVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(favoriteVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}

	}

	@Override
	public void update(FavoriteVO favoriteVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(favoriteVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}

	}

	@Override
	public void delete(FavoriteVO favoriteVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.delete(favoriteVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}

	}

	@Override
	public List<FavoriteVO> getByMemberAcc(String memberAcc) {
		List<FavoriteVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_By_MemberAcc_STMT);
			query.setParameter("MemberAcc", memberAcc);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	@Override
	public List<FavoriteVO> getByRestID(Integer restID) {
		List<FavoriteVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_By_RestID_STMT);
			query.setParameter("RestID", restID);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	@Override
	public List<FavoriteVO> getAll() {
		List<FavoriteVO> list = null;
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
		FavoriteDAO dao = new FavoriteDAO();
		FavoriteVO vo = new FavoriteVO();
//		vo.setMemberAcc("aaa123");
//		vo.setRestID(2);
//		dao.insert(vo);
//		vo.setMemberAcc("aaa123");
//		vo.setRestID(3);
//		dao.insert(vo);
//		vo.setMemberAcc("bbb456");
//		vo.setRestID(3);
//		dao.insert(vo);
//		vo.setMemberAcc("ccc789");
//		vo.setRestID(2);
//		dao.insert(vo);
//		vo.setMemberAcc("bbb456");
//		vo.setRestID(2);
//		dao.update(vo);
//		dao.delete(vo);
		List<FavoriteVO> list = dao.getByRestID(102);
		for (FavoriteVO favoriteVO : list) {
			System.out.println(favoriteVO.getMemberAcc()+":"+favoriteVO.getRestID());
		}
	}

}