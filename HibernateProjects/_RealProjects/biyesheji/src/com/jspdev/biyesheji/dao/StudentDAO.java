package com.jspdev.biyesheji.dao;

import org.hibernate.Session;

import com.jspdev.biyesheji.base.BaseStudentDAO;


public class StudentDAO extends BaseStudentDAO implements com.jspdev.biyesheji.dao.iface.StudentDAO {

	public StudentDAO () {}
	
	public StudentDAO (Session session) {
		super(session);
	}


}