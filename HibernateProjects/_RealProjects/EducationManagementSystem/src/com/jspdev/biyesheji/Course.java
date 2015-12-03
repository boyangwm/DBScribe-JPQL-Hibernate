package com.jspdev.biyesheji;

import java.util.*;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.jspdev.biyesheji.base.BaseCourse;
import com.yourcompany.struts.form.CourseForm;

public class Course extends BaseCourse {
	private static final long serialVersionUID = 1L;
    private static Session session;
    private static Transaction tr;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Course () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Course (java.lang.String id) {
		super(id);
		
	}

	/**
	 * Constructor for required fields
	 */
	public Course (
		java.lang.String id,
		java.lang.String name,
		java.lang.Long credit,
		java.lang.String teacher,
		java.lang.String time,
		java.lang.String address,
		java.lang.String type) {

		super (
			id,
			name,
			credit,
			teacher,
			time,
			address,
			type);
	}

/*[CONSTRUCTOR MARKER END]*/

//查询方法	
	public Collection queryOperator(String hql) throws Exception{
		session=HibernateSessionFactory.getSession();
		Query query=session.createQuery(hql);
    	List list=query.list();
    	return list;
    }
	
//判断一个课程对象是否存在
	public Boolean isTrue(String id) throws Exception{
		session=HibernateSessionFactory.getSession();
		String hql="from Course c where c.id='"+id+"'";
		Query query=session.createQuery(hql);
		List list=query.list();
		if(list.size()>0) return true;
		else return false;
	}

//返回指定课程号的课程
	public static Course getCourse(String id) throws Exception{
		session=HibernateSessionFactory.getSession();
		Course course=(Course)session.get(Course.class,id);
		return course;
	}
	
//添加课程
	public static void addCourse(Course course)throws Exception{
		session=HibernateSessionFactory.getSession();
		tr=session.beginTransaction();
		session.save(course);
		tr.commit();
	}
	
//删除课程
	public static void deleteCourse(String id)throws Exception{
		session=HibernateSessionFactory.getSession();
		tr=session.beginTransaction();
		session.delete(session.load(Course.class, id));
		tr.commit();
	}
	
//返回所有课程信息
	public Collection getCourses()throws Exception{
		String hql="from Course c";
		return queryOperator(hql);
	}
	
//返回所有公共选修课信息
	public Collection getChoseCourse()throws Exception{
		String hql="from Course c where c.type='公共选修课'";
		return queryOperator(hql);
	}
	
//修改课程信息
	public void changeCourseInfo(String id,Course course)throws Exception{
		session=HibernateSessionFactory.getSession();
		tr=session.beginTransaction();
		Course cour=Course.getCourse(id);
		cour.setId(course.getId());
		cour.setName(course.getName());
		cour.setCredit(course.getCredit());
		cour.setType(course.getType());
		cour.setTeacher(course.getTeacher());
		cour.setTime(course.getTime());
		cour.setAddress(course.getAddress());
		session.update(cour);
		tr.commit();
	}
}