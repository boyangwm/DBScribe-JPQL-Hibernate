package com.jspdev.biyesheji;

import java.util.Collection;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.jspdev.biyesheji.base.BaseTeacher;
import com.yourcompany.struts.form.LoginForm;
import com.yourcompany.struts.form.PasswordForm;
import com.yourcompany.struts.form.TeaInfoForm;



public class Teacher extends BaseTeacher {
	private static final long serialVersionUID = 1L;
	private static Session session;
	private static Transaction tr;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Teacher () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Teacher (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Teacher (
		java.lang.String id,
		java.lang.String name,
		java.lang.String password,
		java.lang.String sex,
		java.lang.Long age,
		java.lang.String department,
		java.lang.String post) {

		super (
			id,
			name,
			password,
			sex,
			age,
			department,
			post);
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
		String hql="from Teacher t where t.id='"+id+"'";
		Query query=session.createQuery(hql);
		List list=query.list();
		if(list.size()>0) return true;
		else return false;
	}
	
//添加一个教师
	public static void addTeacher(Teacher teacher)throws Exception{
		session=HibernateSessionFactory.getSession();
		tr=session.beginTransaction();
		session.save(teacher);
		tr.commit();
	}
	
//删除一个教师
	public static void deleteTeacher(String id)throws Exception{
		session=HibernateSessionFactory.getSession();
		tr=session.beginTransaction();
		session.delete(session.load(Teacher.class,id));
		tr.commit();
	}
	
//返回指定职工号的教师	
	public static Teacher getTeacher(String id) throws Exception{
		Teacher teacher=(Teacher) session.get(Teacher.class, id);
		return teacher;
	}

//返回所有教师信息
	public Collection getAllTeachers()throws Exception{
		String hql="from Teacher t";
		return queryOperator(hql);
	}
//验证老师用户的登陆信息
	public boolean isTeacher(LoginForm teacher) throws Exception{
		String hql="from Teacher t where t.id='"+teacher.getId()+"'and t.password='"+teacher.getPassword()+"'";
		List list=(List) queryOperator(hql);
		if(list.size()>0)
		   return true;
		else 
		   return false;
	}

//返回指定职工号的教师信息    
    public Collection getInformation(String id)throws Exception{
        String hql="from Teacher t where t.id='"+id+"'";
        return queryOperator(hql);
    }

//返回指定姓名的老师所开设的所有课程
    public Collection getTeaCourses(String teacher)throws Exception{
        String hql="from Course c where c.teacher='"+teacher+"'";
        return queryOperator(hql);
    }
//修改个人信息
    public void chahgeTeaInfo(TeaInfoForm teacher)throws Exception{
    	session=HibernateSessionFactory.getSession();
    	tr=session.beginTransaction();
    	Teacher tea=Teacher.getTeacher(teacher.getNumber());
    	tea.setId(teacher.getNumber());
    	tea.setName(teacher.getName());
    	tea.setSex(teacher.getSex());
    	tea.setAge(Long.parseLong(teacher.getAge()));
    	tea.setDepartment(teacher.getDepartment());
    	tea.setPost(teacher.getPost());
    	tea.setRemark(teacher.getRemark());
    	session.update(tea);
    	tr.commit();  
    }

//修改密码    
    public static void changePassword(PasswordForm passwordForm)throws Exception{
    	session=HibernateSessionFactory.getSession();
    	tr=session.beginTransaction();
    	Teacher teacher=Teacher.getTeacher(passwordForm.getTeaId());
    	teacher.setPassword(passwordForm.getPassword1());
    	session.update(teacher);
    	tr.commit();	
    }
}