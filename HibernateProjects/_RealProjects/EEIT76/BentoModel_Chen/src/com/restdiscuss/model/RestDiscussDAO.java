package com.restdiscuss.model;

import hibernate.util.HibernateUtil;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.member.model.MemberVO;

public class RestDiscussDAO implements RestDiscussDAO_interface {
	
	private static final String GET_ALL_STMT = "from RestDiscussVO order by RestDiscussID";
	private static final String GET_By_RestID = "from RestDiscussVO where RestID=? order by RestDiscussID";

	@Override
	public void insert(RestDiscussVO restDiscussVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(restDiscussVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		
	}

	@Override
	public void update(RestDiscussVO restDiscussVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(restDiscussVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		
	}

	@Override
	public void delete(Integer restDiscussID) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			RestDiscussVO restDiscussVO = new RestDiscussVO();
			restDiscussVO.setRestDiscussID(restDiscussID);
			session.delete(restDiscussVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		
	}

	@Override
	public RestDiscussVO getByPrimaryKey(Integer restDiscussID) {
		RestDiscussVO restDiscussVO = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			restDiscussVO = (RestDiscussVO) session.get(RestDiscussVO.class, restDiscussID);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return restDiscussVO;
	}

	@Override
	public List<RestDiscussVO> getAll() {
		List<RestDiscussVO> list = null;
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
	public List<RestDiscussVO> getByRestID(Integer RestID) {
		List<RestDiscussVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_By_RestID);
			query.setParameter(0, RestID);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}
	public static void main(String[] args){
		RestDiscussDAO dao = new RestDiscussDAO();
		RestDiscussVO vo = new RestDiscussVO();
		MemberVO memberVO = new MemberVO();
//		memberVO.setMemberAcc("aaa123");
//		vo.setRestDiscussID(1);
//		vo.setJudge(true);
//		vo.setDiscussion("�?");
//		vo.setRestID(2);
//		vo.setMemberVO(memberVO);
//		dao.insert(vo);
//		dao.delete(2);
		List<RestDiscussVO> list = dao.getByRestID(102);
		for (RestDiscussVO restDiscussVO : list) {
			System.out.println(restDiscussVO.getRestDiscussID()+","+restDiscussVO.isJudge()+","
					+restDiscussVO.getDiscussion()+","+restDiscussVO.getRestID()+","+restDiscussVO.getMemberVO().getMemberAcc());
		}
	}
}