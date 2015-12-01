package com.jspdev.biyesheji;

import java.util.*;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.jspdev.biyesheji.base.BaseStudent;
import com.yourcompany.struts.form.LoginForm;
import com.yourcompany.struts.form.PasswordForm;
import com.yourcompany.struts.form.StuInfoForm;



public class Student extends BaseStudent {
	private static final long serialVersionUID = 1L;
    private static Session session;
    private static Transaction tr;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Student () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Student (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Student (
		java.lang.String id,
		java.lang.String name,
		java.lang.String sex,
		java.lang.Long age,
		java.lang.String department,
		java.lang.String address,
		java.lang.String phone,
		java.lang.String inTime,
		java.lang.String outTime,
		java.lang.String password,
		java.lang.String college) {

		super (
			id,
			name,
			sex,
			age,
			department,
			address,
			phone,
			inTime,
			outTime,
			password,
			college);
	}

/*[CONSTRUCTOR MARKER END]*/
	
//查询方法	
	public Collection queryOperator(String hql) throws Exception{
		session=HibernateSessionFactory.getSession();
      /*ThreadLocal Session(查看HibernateSessionFactory.java)模式下面，只要提交了事务，那么session就自动关闭了,因此不必再调用session.close()方法.*/		
		Query query=session.createQuery(hql);
    	List list=query.list();
    	return list;
    }
	
//添加一个学生
	public static void addStudent(Student student)throws Exception{
		session=HibernateSessionFactory.getSession();
		tr=session.beginTransaction();
		session.save(student);
		tr.commit();
	}
	
//删除一个学生
	public static void deleteStudent(String id)throws Exception{
		session=HibernateSessionFactory.getSession();
		tr=session.beginTransaction();
		session.delete(session.load(Student.class,id));
		tr.commit();
	}	

//判断一个学生对象是否存在
	public Boolean isTrue(String id) throws Exception{
		session=HibernateSessionFactory.getSession();
		String hql="from Student s where s.id='"+id+"'";
		Query query=session.createQuery(hql);
		List list=query.list();
		if(list.size()>0) return true;
		else return false;
	}
//返回指定学号的学生对象
	public static Student getStudent(String id) throws Exception{
		session=HibernateSessionFactory.getSession();
		Student student=(Student) session.get(Student.class, id);
		return student;
	}
	
//验证学生用户的登陆信息
	public boolean isStudent(LoginForm student) throws Exception{
		String hql="from Student s where s.id='"+student.getId()+"' and s.password='"+student.getPassword()+"'";		
		List list=(List) queryOperator(hql);
		if(list.size()>0)
			return true;
		else
		    return false;
	}

//返回所有学生信息
	public Collection getAllStudents()throws Exception{
		String hql="from Student s";
		return queryOperator(hql);
	}
//返回指定学号的学生信息    
    public Collection getInformation(String id)throws Exception{
    	String hql="from Student s where s.id='"+id+"'";
        return queryOperator(hql);
    }

//返回指定学院的所有学生
	public Collection getCollegeStudents(String college)throws Exception{
		String hql="from Student s where s.college='"+college+"'";
		return queryOperator(hql);
	}

//返回指定专业的所有学生
	public Collection getDepartmentStudents(String department)throws Exception{
		String hql="from Student s where s.department='"+department+"'";
		return queryOperator(hql);
	}

//返回指定年级的所有学生
	public Collection getInTimeStudents(String inTime)throws Exception{
		String hql="from Student s where s.inTime='"+inTime+"'";
		return queryOperator(hql);
	}
//修改个人信息
    public void chahgeStuInfo(StuInfoForm student)throws Exception{
    	session=HibernateSessionFactory.getSession();
    	tr=session.beginTransaction();
    	Student stu=Student.getStudent(student.getNumber());
    	stu.setId(student.getNumber());
    	stu.setName(student.getName());
    	stu.setSex(student.getSex());
    	stu.setAge(Long.parseLong(student.getAge()));
    	stu.setDepartment(student.getDepartment());
    	stu.setAddress(student.getAddress());
    	stu.setPhone(student.getPhone());
    	stu.setInTime(student.getInTime());
    	stu.setOutTime(student.getOutTime());
    	stu.setCollege(student.getCollege());
    	session.update(stu);
    	tr.commit();
    }
 
//修改密码
    public static void changePassword(PasswordForm passwordForm)throws Exception{
    	session=HibernateSessionFactory.getSession();
    	tr=session.beginTransaction();
    	Student student=Student.getStudent(passwordForm.getStuId());
    	student.setPassword(passwordForm.getPassword1());
    	session.update(student);
    	tr.commit();	
    }
}