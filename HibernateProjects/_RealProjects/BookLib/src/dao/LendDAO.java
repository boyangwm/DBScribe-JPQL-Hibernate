package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cfg.hibernate.Beanbook;
import cfg.hibernate.Beanbooklendrecord;
import cfg.hibernate.Beanreader;
import HibernateUtil.HibernateUtil;

public class LendDAO {
	private static LendDAO instance = null;

	private Session session = null;

	public LendDAO() {
	}

	public static LendDAO getInstance() {
		if (instance == null) {
			instance = new LendDAO();
		}
		return instance;
	}

	public void add(Beanbooklendrecord lend) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		session.save(lend);
		tx.commit();
		session.close();
	}

	public void update(Beanbooklendrecord lend) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		session.update(lend);
		tx.commit();
		session.close();
	}
	

	public void remove(Beanbooklendrecord lend) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		session.delete(lend);
		tx.commit();
		session.close();
	}
	
	public void delete(int id){
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query q =session.createQuery("DELETE from Beanbooklendrecord u WHERE u.id = ? ");
		q.setInteger(0, id);
		q.executeUpdate();
		tx.commit();
		session.close();
	}

	public List<Beanbooklendrecord> listAll() {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Beanbooklendrecord> list = session.createQuery("from Beanbooklendrecord b where b.returnDate is null").list();
		tx.commit();
		return list;
	}
	
	public List<Beanbooklendrecord> listByReader(Beanreader id ) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Beanbooklendrecord> list = session.createQuery("from Beanbooklendrecord b where b.beanreader=? and b.returnDate is null").
				setEntity(0, id).list();
		tx.commit();
		return list;
	}
	
	public List<Beanbooklendrecord> listByReaderid(Beanreader id ) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Beanbooklendrecord> list = session.createQuery("from Beanbooklendrecord b where b.beanreader=?").
				setEntity(0, id).list();
		tx.commit();
		return list;
	}
	
	public List<Beanbooklendrecord> listByBook(Beanbook id ) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Beanbooklendrecord> list = session.createQuery("from Beanbooklendrecord b where b.beanbook=? and b.returnDate is null").setEntity(0, id).list();
		tx.commit();
		session.close();
		return list;
	}
	
	public List<Beanbooklendrecord> listByBook2(Beanbook id ) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Beanbooklendrecord> list = session.createQuery("from Beanbooklendrecord b where b.beanbook=? and b.returnDate is null").setEntity(0, id).list();
		tx.commit();
		return list;
	}
	
	public List<Beanbooklendrecord> listByBookid(Beanbook id ) {
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Beanbooklendrecord> list = session.createQuery("from Beanbooklendrecord b where b.beanbook=?").setEntity(0, id).list();
		tx.commit();
		return list;
	}
	
	public Double countPenalSum(Beanreader reader){
		Double sum=null;
		session = HibernateUtil.getSession();
		Transaction tx = null;
		Query q=session.createQuery("SELECT sum(penalSum) FROM Beanbooklendrecord where beanreader=?");
		q.setEntity(0, reader);
		sum=(Double) q.uniqueResult();
		return sum;
	}
}
