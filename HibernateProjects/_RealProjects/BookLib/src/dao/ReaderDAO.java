package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import HibernateUtil.HibernateUtil;
import cfg.hibernate.Beanreader;

public class ReaderDAO {
	private static ReaderDAO instance = null;

	private Session session = null;

	public ReaderDAO() {
	}

	public static ReaderDAO getInstance() {
		if (instance == null) {
			instance = new ReaderDAO();
		}
		return instance;
	}

	public void add(Beanreader reader) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		session.save(reader);
		tx.commit();
		session.close();
	}

	public void update(Beanreader reader) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		session.update(reader);
		tx.commit();
		session.close();
	}
	

	public void remove(Beanreader reader) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		session.delete(reader);
		tx.commit();
		session.close();
	}
	
	public void delete(String id){
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query q =session.createQuery("DELETE from Beanreader u WHERE u.readerid = ? ");
		q.setString(0, id);
		q.executeUpdate();
		tx.commit();
		session.close();
	}

	public List<Beanreader> listAll() {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Beanreader> list = session.createQuery("from Beanreader r where r.removeDate is null ").list();
		tx.commit();
		session.close();
		return list;
	}

	public  Beanreader  loadbyid(String id) {
		Beanreader reader=null;
		session = HibernateUtil.getSession();
		Transaction tx = null;
		Query q=session.createQuery("from Beanreader u where u.readerid=?");
		q.setString(0, id);
		reader=(Beanreader) q.uniqueResult();
		session.close();
		return reader;
	}
	
	public  Beanreader  loadbyname(String name) {
		Beanreader reader=null;
		session = HibernateUtil.getSession();
		Transaction tx = null;
		Query q=session.createQuery("from Beanreader u where u.readerName=?");
		q.setString(0, name);
		reader=(Beanreader) q.uniqueResult();
		session.close();
		return reader;
	}
	
	public List<Beanreader> loadByTypeAndName(int type,String name){
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Beanreader> list= session.createQuery("from Beanreader u where u.readerName=? and u.readerTypeId=? and u.removeDate is null").setString(0, name).setInteger(1, type).list();
		tx.commit();
		session.close();
		return list;
	}

	public List<Beanreader> loadByType(int type) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Beanreader> list = session.createQuery("from Beanreader r where r.readerTypeId=? and r.removeDate is null ").setInteger(0, type).list();
		tx.commit();
		session.close();
		return list;
	}
	
	public List<Long> countReader() {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Long> list = session.createQuery("SELECT count(*) FROM Beanbooklendrecord group by beanreader").list();
		tx.commit();
		return list;
	}
	
	public Long countLimit(Beanreader reader) {
		Long sum=null;
		session = HibernateUtil.getSession();
		Transaction tx = null;
		Query q=session.createQuery("SELECT count(*) FROM Beanbooklendrecord where beanreader=? and returnDate is null ");
		q.setEntity(0, reader);
		sum=(Long) q.uniqueResult();
		return sum;
	}
	
	public List<Beanreader> listReaderRecord() {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Beanreader> list = session.createQuery("select distinct beanreader FROM Beanbooklendrecord").list();
		tx.commit();
		return list;
	}
}
