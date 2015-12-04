package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import HibernateUtil.HibernateUtil;
import cfg.hibernate.Beanpublisher;

public class PublisherDAO {
	private static PublisherDAO instance = null;

	private Session session = null;

	public PublisherDAO() {
	}

	public static PublisherDAO getInstance() {
		if (instance == null) {
			instance = new PublisherDAO();
		}
		return instance;
	}

	public void add(Beanpublisher publisher) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		session.save(publisher);
		tx.commit();
	}

	public void update(Beanpublisher publisher) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		session.update(publisher);
		tx.commit();
	}

	public void remove(Beanpublisher publisher) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		session.delete(publisher);
		tx.commit();
	}

	public void delete(String id) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query q = session
				.createQuery("DELETE from Beanpublisher u WHERE u.pubid = ? ");
		q.setString(0, id);
		q.executeUpdate();
		tx.commit();
	}

	public List<Beanpublisher> listAll() {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Beanpublisher> list = session.createQuery("from Beanpublisher").list();
		tx.commit();
		return list;
	}

	public Beanpublisher loadbyid(String id) {
		Beanpublisher publisher = null;
		session = HibernateUtil.getSession();
		Transaction tx = null;
		Query q = session.createQuery("from Beanpublisher u where u.pubid=?");
		q.setString(0, id);
		publisher = (Beanpublisher) q.uniqueResult();
		return publisher;
	}

	public Beanpublisher loadbyname(String name) {
		Beanpublisher publisher = null;
		session = HibernateUtil.getSession();
		Transaction tx = null;
		Query q = session
				.createQuery("from Beanpublisher u where u.publisherName=?");
		q.setString(0, name);
		publisher = (Beanpublisher) q.uniqueResult();
		return publisher;
	}

}
