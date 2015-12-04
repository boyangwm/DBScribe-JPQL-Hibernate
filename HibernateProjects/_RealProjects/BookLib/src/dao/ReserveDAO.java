package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import HibernateUtil.HibernateUtil;
import cfg.hibernate.Beanbook;
import cfg.hibernate.Beanreader;
import cfg.hibernate.Beanreserve;
import cfg.hibernate.Beanpublisher;

public class ReserveDAO {
	private static ReserveDAO instance = null;

	private Session session = null;

	public ReserveDAO() {
	}

	public static ReserveDAO getInstance() {
		if (instance == null) {
			instance = new ReserveDAO();
		}
		return instance;
	}

	public void add(Beanreserve reserve) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		session.save(reserve);
		tx.commit();
		session.close();
	}

	public void update(Beanreserve reserve) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		session.merge(reserve);
		tx.commit();
		session.close();
	}
	

	public void remove(Beanreserve reserve) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		session.delete(reserve);
		tx.commit();
		session.close();
	}
	

	public List<Beanreserve> listAll() {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Beanreserve> list = session.createQuery("from Beanreserve b where b.cancelDate is null").list();
		tx.commit();
		return list;
	}
	
	public Beanreserve listById(String id){
		Beanreserve reserve=null;
		session = HibernateUtil.getSession();
		Transaction tx = null;
		Query q=session.createQuery("FROM Beanreserve where reserveid=?");
		q.setString(0, id);
		reserve=(Beanreserve) q.uniqueResult();
		session.close();
		return reserve;
	}

	
	public List<Beanreserve> listByReader(Beanreader reader) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Beanreserve> list = session.createQuery("from Beanreserve b where b.beanreader =? and cancelDate is null").setEntity(0, reader).list();
		tx.commit();
		return list;
	}
	
	public List<Beanreserve> listByBook(Beanbook book) {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Beanreserve> list = session.createQuery("from Beanreserve b where b.beanbook =? and cancelDate is null").setEntity(0, book).list();
		tx.commit();
		return list;
	}
	
}
