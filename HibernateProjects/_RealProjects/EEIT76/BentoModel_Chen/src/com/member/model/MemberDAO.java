package com.member.model;
import hibernate.util.HibernateUtil;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.useraddr.model.UserAddrVO;

public class MemberDAO implements MemberDAO_Interface {
	private static final String GET_ALL_STMT = "from MemberVO order by MemberAcc";
	private static final String GET_BY_NAME = "SELECT MemberAcc FROM MemberVO where MemberLastName=? and MemberFirstName=?";
	private static final String GET_ALL_MEMBERACC = "select MemberAcc from MemberVO ";
	@Override
	public void insert(MemberVO memberVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(memberVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}
	@Override
	public void update(MemberVO memberVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(memberVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}
	@Override
	public void delete(String MemberAcc) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
//			Query query = session.createQuery("delete MemberVO where MemberAcc=?");
//			query.setParameter(0, MemberAcc);
			
			MemberVO memberVO = new MemberVO();
			memberVO.setMemberAcc(MemberAcc);
			session.delete(memberVO);
			
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}
	@Override
	public MemberVO getByPrimaryKey(String MemberAcc) {
		MemberVO memberVO = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			memberVO = (MemberVO) session.get(MemberVO.class, MemberAcc);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return memberVO;
	}
	@Override
	public List<MemberVO> getAll() {
		List<MemberVO> list = null;
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
	
	public List<String> GetMemberAcc(String LastName,String FirstName){
		
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<String> list = null;
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_NAME);
			query.setParameter(0, LastName);
			query.setParameter(1, FirstName);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		
		return list;
	}
	@Override
	public List<Object> getAllMemberAcc() {
		List<Object> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_ALL_MEMBERACC);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}
	
	
	public static void main(String[] args) {
		MemberDAO memberdao = new MemberDAO();
		//�s�W
//		MemberVO membervo1 = new MemberVO();   
//		membervo1.setMemberAcc("Amber");
//		membervo1.setMemberPwd("123456");
//		membervo1.setMemberLastName("���");
//		membervo1.setMemberFirstName("��");
//		membervo1.setMemberPhone("0266316666");
//		membervo1.setMemberCel("0910123123");
//		membervo1.setMemberEmail("Amber@gmail.com");
//		membervo1.setMemberGender(true);
//		System.out.println(membervo1);
//		memberdao.insert(membervo1);
		//�ק�
//		MemberVO membervo2 = new MemberVO();
//		membervo2.setMemberAcc("Amber");
//		membervo2.setMemberPwd("123456");
//		membervo2.setMemberLastName("�p��");
//		membervo2.setMemberFirstName("��");
//		membervo2.setMemberPhone("0266316666");
//		membervo2.setMemberCel("0910123123");
//		membervo2.setMemberEmail("Amber@gmail.com");
//		membervo1.setMemberGender(true);
//		memberdao.update(membervo2);
		//�R��(�p��cascade - �h��emp2.hbm.xml�p�G�]�� cascade="all"��
		// cascade="delete"�N�|�R���Ҧ��������-�]�A���ݳ���P�P����䥦��u�N�|�@�ֳQ�R��)
//		memberdao.delete();
				
		//findByPrimaryKey (�h��emp2.hbm.xml�����]��lazy="false")(�u!)
//		MemberVO membervo3 = memberdao.getByPrimaryKey("Amber");
//		System.out.print(membervo3.getMemberAcc() + ",");
//		System.out.print(membervo3.getMemberPwd() + ",");
//		System.out.print(membervo3.getMemberLastName() + ",");
//		System.out.print(membervo3.getMemberFirstName() + ",");
//		System.out.print(membervo3.getMemberPhone() + ",");
//		System.out.print(membervo3.getMemberCel() + ",");
//		System.out.print(membervo3.getMemberEmail() + ",");
//		System.out.print(membervo3.isMemberGender());
//		System.out.println("\n---------------------");
//		//�L�X����  ��ե�
		List<MemberVO> memberlist = memberdao.getAll();
		for (MemberVO member : memberlist) {
			System.out.print(member.getMemberAcc() + ",");
			System.out.print(member.getMemberPwd() + ",");
			System.out.print(member.getMemberLastName() + ",");
			System.out.print(member.getMemberFirstName() + ",");
			System.out.print(member.getMemberPhone() + ",");
			System.out.print(member.getMemberCel() + ",");
			System.out.print(member.getMemberEmail() + ",");
			System.out.print(member.isMemberGender() + ",");
			for (UserAddrVO vo : member.getUserAddrs()) {
				System.out.print(vo.getCity()+vo.getArea()+vo.getAddr()+",");
			}
			System.out.println();

		}
		List<String> test = memberdao.GetMemberAcc("��", "��");
		
		for (String aAcc : test) {
			System.out.print(aAcc + ",");
			
		}
	}

	
}
