package com.owner.model;
import hibernate.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class OwnerDAO implements OwnerDAO_interface {
	private static final String GET_ALL_STMT = "from OwnerVO order by OwnAcc";
	private static final String GET_ALL_OwnAcc = "select OwnAcc from OwnerVO ";
	@Override
	public void insert(OwnerVO ownerVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(ownerVO);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		}
	}

	@Override
	public void update(OwnerVO ownerVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(ownerVO);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		}
	}

	@Override
	public void delete(String OwnAcc) {
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			OwnerVO ownerVO = (OwnerVO)session.get(OwnerVO.class, OwnAcc);
			session.delete(ownerVO);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		}
	}

	@Override
	public OwnerVO getByPrimaryKey(String OwnAcc) {
		OwnerVO ownerVO = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			ownerVO = (OwnerVO) session.get(OwnerVO.class,OwnAcc);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return ownerVO;
	}

	@Override
	public List<OwnerVO> getAll() {
		List<OwnerVO> list = null;
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
	public List<Object> getAllOwnAcc() {
		List<Object> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_ALL_OwnAcc);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}
	
	public static void main(String[] args){
		OwnerDAO dao = new OwnerDAO();

//		OwnerVO vo = dao.getByPrimaryKey("maid4102");
//		System.out.println(vo.getOwnFirstName());
//		for (RestaurantVO restVO : vo.getRestaurants()) {
//			System.out.println(restVO.getRestName());
//		}
		List<Object> list = dao.getAllOwnAcc();
		for (Object OwnAcc : list) {
			System.out.println(OwnAcc);
		}
	}
}
