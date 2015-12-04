package com.dish.model;
import org.hibernate.*;
import hibernate.util.HibernateUtil;
import java.util.*;

public class DishHibernateDAO implements DishDAO_interface {

	private static final String GET_ALL = "from DishVO order by DishID";
	
	 //where RestID=?   �]����թҥH�g��  ��ɭn�A��
//	private static final String GET_ALL_REST = "from DishVO where RestID=102 order by DishID";
	private static final String GET_ALL_REST = "from DishVO where RestID=? order by DishID";

	@Override
	public void insert(DishVO dishVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(dishVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void update(DishVO dishVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(dishVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void delete(Integer dishID) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();

			DishVO dishVO = new DishVO();
			dishVO.setDishID(dishID);
			session.delete(dishVO);

			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public DishVO getByPrimaryKey(Integer dishID) {
		DishVO dishVO = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			dishVO = (DishVO) session.get(DishVO.class, dishID);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return dishVO;
	}
	
	@Override
	//�n��RestId�i�h  �n�ק�
	public List<DishVO> getAll() {
		List<DishVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_ALL);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}
	@Override
	public List<DishVO> getAllByRestID(Integer RestID) {
		List<DishVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_ALL_REST);
			query.setParameter(0, RestID);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	public static void main(String[] args) {
		DishHibernateDAO dao = new DishHibernateDAO();
		
		List<DishVO> list = dao.getAllByRestID(103);
		for (DishVO aEmp : list) {
			System.out.print(aEmp.getDishID() + ",");
			System.out.print(aEmp.getDishName() + ",");
			System.out.print(aEmp.getPrice() + ",");
			System.out.print(aEmp.getSpecialPrice() + ",");
			System.out.print(aEmp.getStartDate() + ",");
			System.out.print(aEmp.getEndDate() + ",");
//			System.out.print(aEmp.getRestID());
			System.out.println();
		}
		//�� �d��-findByPrimaryKey (�h��emp2.hbm.xml�����]��lazy="false")(�u!)
//		DishVO empVO3 = dao.getByPrimaryKey(2);
//		System.out.print(empVO3.getDishID() + ",");
//		System.out.print(empVO3.getDishName() + ",");
//		System.out.print(empVO3.getPrice() + ",");
//		System.out.print(empVO3.getSpecialPrice() + ",");
//		System.out.print(empVO3.getStartDate() + ",");
//		System.out.print(empVO3.getEndDate() + ",");
//		System.out.print(empVO3.getRestID());
//		System.out.println("\n---------------------");
	}


}
