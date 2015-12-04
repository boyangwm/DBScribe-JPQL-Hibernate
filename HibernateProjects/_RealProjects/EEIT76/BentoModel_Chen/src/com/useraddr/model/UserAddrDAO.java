package com.useraddr.model;

import org.hibernate.*;

import hibernate.util.HibernateUtil;

import java.util.*;

public class UserAddrDAO implements UserAddrDAO_Interface {
	private static final String GET_ALL_MSTMT = "from UserAddrVO where MemberAcc=? order by UserAddrID";
	private static final String GET_ALL_STMT = "from UserAddrVO order by userAddrID";
	private static final String GET_BY_UAMA = "select count(*) as count from UserAddrVO where MemberAcc=?";

	@Override
	public void insert(UserAddrVO userAddrVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.save(userAddrVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void update(UserAddrVO userAddrVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(userAddrVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void delete(Integer userAddrID) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();

			// Query query =
			// session.createQuery("delete UserAddrVO where UserAddrID=?");
			// query.setParameter(0, userAddrID);

			UserAddrVO userAddrVO = new UserAddrVO();
			userAddrVO.setUserAddrID(userAddrID);
			session.delete(userAddrVO);

			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public UserAddrVO getByPrimaryKey(Integer userAddrID) {
		UserAddrVO userAddrVO = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			userAddrVO = (UserAddrVO) session.get(UserAddrVO.class, userAddrID);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return userAddrVO;
	}

	@Override
	public long getCountByMemberAcc(String MemberAcc) {
		long count = 0;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_UAMA);
			query.setParameter(0, MemberAcc);
			count = (Long)query.list().get(0);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return count;
	}

	@Override
	public List<UserAddrVO> getAllByMemberAcc(String MemberAcc) {
		List<UserAddrVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_ALL_MSTMT);
			query.setParameter(0, MemberAcc);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	@Override
	public List<UserAddrVO> getAll() {
		List<UserAddrVO> list = null;
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
		UserAddrDAO useraddrdao = new UserAddrDAO();

		// ● 新增
//		UserAddrVO useraddrvo1 = new UserAddrVO();
//		useraddrvo1.setUserAddrID(1);
//		useraddrvo1.setCity("台北");
//		useraddrvo1.setArea("大安區");
//		useraddrvo1.setAddr("忠孝東路2段");
//		useraddrvo1.setMemberAcc("Amber");
//		useraddrdao.insert(useraddrvo1);

		/*
		 * //● 修改 UserAddrVO useraddrvo2 = new UserAddrVO();
		 * useraddrvo2.setUserAddrID(1); useraddrvo2.setCity("高雄");
		 * useraddrvo2.setArea("仁武區"); useraddrvo2.setAddr("中山路2段");
		 * useraddrvo2.setMemberAcc("Amber"); useraddrdao.update(useraddrvo2);
		 */

		/*
		 * //● 刪除(小心cascade - 多方emp2.hbm.xml如果設為 cascade="all"或 //
		 * cascade="delete"將會刪除所有相關資料-包括所屬部門與同部門的其它員工將會一併被刪除)
		 * useraddrdao.delete(1);
		 * 
		 * 
		 * 
		 * //● 查詢-findByPrimaryKey (多方emp2.hbm.xml必須設為lazy="false")(優!)
		 * UserAddrVO useraddrvo3 = useraddrdao.getByPrimaryKey(1);
		 * System.out.print(useraddrvo3.getUserAddrID() + ",");
		 * System.out.print(useraddrvo3.getCity() + ",");
		 * System.out.print(useraddrvo3.getArea() + ",");
		 * System.out.print(useraddrvo3.getAddr() + ",");
		 * System.out.print(useraddrvo3.getMemberAcc());
		 * System.out.println("\n---------------------");
		 */
		// ● 查詢-getAll (多方emp2.hbm.xml必須設為lazy="false")(優!)
		List<UserAddrVO> useraddrlist = useraddrdao.getAllByMemberAcc("Bad5566");
		for (UserAddrVO useraddr : useraddrlist) {
			System.out.print(useraddr.getUserAddrID() + ",");
			System.out.print(useraddr.getCity() + ",");
			System.out.print(useraddr.getArea() + ",");
			System.out.print(useraddr.getAddr() + ",");
			System.out.print(useraddr.getMemberAcc() + ",");
			System.out.println();
		}
		Long count = useraddrdao.getCountByMemberAcc("Bad5566");
		System.out.println(count);
	}

}
