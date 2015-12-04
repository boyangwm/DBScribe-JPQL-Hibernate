package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import HibernateUtil.HibernateUtil;
import cfg.hibernate.Beanbook;
import cfg.hibernate.Beanbooklendrecord;
import cfg.hibernate.Beanpublisher;
import cfg.hibernate.Beanreader;

public class BookDAO {
	private static BookDAO instance = null;

	private Session session = null;

	public BookDAO() {
	}

	public static BookDAO getInstance() {
		if (instance == null) {
			instance = new BookDAO();
		}
		return instance;
	}

	public void add(Beanbook book) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		session.save(book);
		tx.commit();
		session.close();
	}

	public void update(Beanbook book) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		session.update(book);
		tx.commit();
		session.close();
	}
	

	public void remove(Beanbook book) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		session.delete(book);
		tx.commit();
		session.close();
	}
	
	public void delete(String barcode){
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query q =session.createQuery("DELETE from Beanbook u WHERE u.barcode = ? ");
		q.setString(0, barcode);
		q.executeUpdate();
		tx.commit();
		session.close();
	}

	public List<Beanbook> listAll() {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Beanbook> list = session.createQuery("from Beanbook b where b.state !='已删除'").list();
		tx.commit();
		return list;
	}

	public  Beanbook  loadbyid(String barcode) {
		Beanbook book=null;
		session = HibernateUtil.getSession();
		Transaction tx = null;
		Query q=session.createQuery("from Beanbook u where u.barcode=?");
		q.setString(0, barcode);
		book=(Beanbook) q.uniqueResult();
		session.close();
		return book;
	}
	
	public  Beanbook  loadbyname(String name) {
		Beanbook book=null;
		session = HibernateUtil.getSession();
		Transaction tx = null;
		Query q=session.createQuery("from Beanbook u where u.bookname=?");
		q.setString(0, name);
		book=(Beanbook) q.uniqueResult();
		session.close();
		return book;
	}
	
	public List<Beanbook> loadByStateAndName(String name,String state){
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Beanbook> list= session.createQuery("from Beanbook b where b.bookname=? and b.state=?").setString(0, name).setString(1, state).list();
		tx.commit();
		return list;
	}

	public List<Beanbook> loadByState(String state) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Beanbook> list = session.createQuery("from Beanbook b where b.state=? ").setString(0, state).list();
		tx.commit();
		return list;
	}
	
	public Long count(Beanpublisher pub){
		Long book=null;
		session = HibernateUtil.getSession();
		Transaction tx = null;
		Query q=session.createQuery("select count(*) from Beanbook u where u.beanpublisher=?");
		q.setEntity(0, pub);
		book=(Long) q.uniqueResult();
		session.close();
		return book;
	}
	
	public List<Long> countBook() {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Long> list = session.createQuery("SELECT count(*) FROM Beanbooklendrecord group by beanbook").list();
		tx.commit();
		return list;
	}
	
	public List<Beanbook> listBookRecord() {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Beanbook> list = session.createQuery("select distinct beanbook FROM Beanbooklendrecord").list();
		tx.commit();
		return list;
	}
}
