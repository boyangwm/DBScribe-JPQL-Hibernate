package dao;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cfg.hibernate.*;
import HibernateUtil.HibernateUtil;

public class UserDAO {
	private static UserDAO instance = null;

	private Session session = null;

	public UserDAO() {
	}

	public static UserDAO getInstance() {
		if (instance == null) {
			instance = new UserDAO();
		}
		return instance;
	}

	public void add(Beansystemuser user) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		session.save(user);
		tx.commit();
	}

	public void update(Beansystemuser user) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		session.update(user);
		tx.commit();
	}
	
	public void resetPwd(String id,String pwd){
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query q =session.createQuery("UPDATE Beansystemuser u SET u.pwd = ? WHERE userid = ? ");
		q.setString(0, pwd);
		q.setString(1, id);
		q.executeUpdate();
		tx.commit();
	}

	public void remove(Beansystemuser user) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		session.delete(user);
		tx.commit();
	}
	
	public void delete(String id){
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query q =session.createQuery("DELETE from Beansystemuser u WHERE u.userid = ? ");
		q.setString(0, id);
		q.executeUpdate();
		tx.commit();
	}

	public List<Beansystemuser> listAll() {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Beansystemuser> list = session.createQuery("from Beansystemuser where removeDate is null").list();
		tx.commit();
		return list;
	}

	public  Beansystemuser  loadbyuserid(String id) {
		Beansystemuser user=null;
		session = HibernateUtil.getSession();
		Transaction tx = null;
		Query q=session.createQuery("from Beansystemuser u where u.userid=?");
		q.setString(0, id);
		user=(Beansystemuser) q.uniqueResult();
		return user;

	}

	
	public Beansystemuser checkLogin(String uname,String pwd){
		Beansystemuser user=null;
		Session session=null;
		Transaction tx=null;
		try{
			session = HibernateUtil.getSession();
		Query q=session.createQuery("from Beansystemuser u where u.userid=? and u.pwd=?");
		q.setString(0, uname);
		q.setString(1, pwd);
		user=(Beansystemuser)q.uniqueResult();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return user;
	}

}
