package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import HibernateUtil.HibernateUtil;
import cfg.hibernate.Beanreadertype;

public class ReaderTypeDAO {
	private static ReaderTypeDAO instance = null;

	private Session session = null;

	public ReaderTypeDAO() {
	}

	public static ReaderTypeDAO getInstance() {
		if (instance == null) {
			instance = new ReaderTypeDAO();
		}
		return instance;
	}

	public void add(Beanreadertype readertype) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		session.save(readertype);
		tx.commit();
	}

	public void update(Beanreadertype readertype) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		session.update(readertype);
		tx.commit();
	}
	

	public void remove(Beanreadertype readertype) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		session.delete(readertype);
		tx.commit();
	}
	
	public void delete(String id){
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query q =session.createQuery("DELETE from Beanreadertype u WHERE u.readerTypeId = ? ");
		q.setString(0, id);
		q.executeUpdate();
		tx.commit();
	}

	public List<Beanreadertype> listAll() {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Beanreadertype> list = session.createQuery("from Beanreadertype").list();
		tx.commit();
		return list;
	}
	
	public List<String> listType(){
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<String> list = session.createSQLQuery("select readerTypeName from Beanreadertype ").list();
		tx.commit();
		return list;
	}

	public  Beanreadertype  loadbyid(int id) {
		Beanreadertype readertype=null;
		session = HibernateUtil.getSession();
		Transaction tx = null;
		Query q=session.createQuery("from Beanreadertype u where u.readerTypeId=?");
		q.setInteger(0, id);
		readertype=(Beanreadertype) q.uniqueResult();
		return readertype;
	}
	
	public  Beanreadertype  loadbyname(String name) {
		Beanreadertype readertype=null;
		session = HibernateUtil.getSession();
		Transaction tx = null;
		Query q=session.createQuery("from Beanreadertype u where u.readerTypeName=?");
		q.setString(0, name);
		readertype=(Beanreadertype) q.uniqueResult();
		return readertype;
	}

}
