package com.jspdev.biyesheji;

import java.util.*;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.jspdev.biyesheji.base.BaseSc;




public class Sc extends BaseSc {
	private static final long serialVersionUID = 1L;
	private static Session session;
    private static Transaction tr;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Sc () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Sc (java.lang.Long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Sc (
		java.lang.Long id,
		java.lang.String sno,
		java.lang.String cno,
		java.lang.String name,
		java.lang.Long credit,
		java.lang.String teacher,
		java.lang.String time,
		java.lang.String address,
		java.lang.String type,
		java.lang.String selected) {

		super (
			id,
			sno,
			cno,
			name,
			credit,
			teacher,
			time,
			address,
			type,
			selected);
	}

/*[CONSTRUCTOR MARKER END]*/

//查询方法	
	public static Collection queryOperator(String hql) throws Exception{
		session=HibernateSessionFactory.getSession();
		Query query=session.createQuery(hql);
    	List list=query.list();
    	return list;
    }
	
//返回一条指定的选课纪录
	public Sc getSc(String sno,String cno) throws Exception{
		session=HibernateSessionFactory.getSession();
		String hql="select s.id from Sc s where s.sno='"+sno+"'and s.cno='"+cno+"'";
		String id1=((List)queryOperator(hql)).get(0).toString();
		Long id=Long.parseLong(id1);
		Sc sc=null;
	    sc=(Sc)session.get(Sc.class, id);
		return sc;
	}

//判断一条选课纪录是否存在
	public Boolean isTrue(String sno,String cno) throws Exception{
		session=HibernateSessionFactory.getSession();
		String hql="from Sc s where s.sno='"+sno+"' and s.cno='"+cno+"'";
		Query query=session.createQuery(hql);
		List list=query.list();
		if(list.size()>0) return true;
		else return false;
	}
	
//增加一条选课纪录	
	public static void addSc(Sc sc)throws Exception{
		session=HibernateSessionFactory.getSession();
		tr=session.beginTransaction();
		session.save(sc);
		tr.commit();
	}
	
//删除一条选课纪录	
	public static void deleteSc(String sno,String cno)throws Exception{
		session=HibernateSessionFactory.getSession();
		tr=session.beginTransaction();
		String hql="delete Sc s where s.sno='"+sno+"'and s.cno='"+cno+"'";
		session.createQuery(hql).executeUpdate();
		tr.commit();
	}

//返回指定学生所选修的所有课程
	public Collection getScs(String sno)throws Exception{
		String hql="from Sc s where s.sno='"+sno+"'";
		return queryOperator(hql);
	}
	
//返回选修指定课程的所有学生	
	public Collection getStudents(String cno)throws Exception{
		String hql="from Student st where st.id in(select s.sno from Sc s where s.cno='"+cno+"'and s.selected='false')";
		return queryOperator(hql);
	}
	
//返回指定教师开设的选修课
	public Collection getTeaScs(String teaName)throws Exception{
		String hql="from Course c where c.teacher='"+teaName+"' and c.type='公共选修课'";
		return queryOperator(hql);
	}
	
//返回选修指定课程并且已经入选的学生
	public Collection getSelectedStudents(String cno)throws Exception{
		String hql="from Student st where st.id in(select s.sno from Sc s where s.cno='"+cno+"'and s.selected='true'"+")";
		return queryOperator(hql);
	}
	
//选择指定学生选修指定课程(即让其从未选中状态变成选中状态)
	public void f_tSc(String sno,String cno)throws Exception{
		String hql="update Sc s set s.selected='true' where s.sno='"+sno+"'and s.cno='"+cno+"'";
		session=HibernateSessionFactory.getSession();
	    tr=session.beginTransaction();
	    session.createQuery(hql).executeUpdate();
	    tr.commit();
	}
	
//取消指定学生选修指定课程(即让其从选中状态变成未选中状态)
	public void t_fSc(String sno,String cno)throws Exception{
		String hql="update Sc s set s.selected='false' where s.sno='"+sno+"'and s.cno='"+cno+"'";
		session=HibernateSessionFactory.getSession();
	    tr=session.beginTransaction();
	    session.createQuery(hql).executeUpdate();
	    tr.commit();
	}
}