package com.jspdev.biyesheji.dao;

import org.hibernate.Session;

import com.jspdev.biyesheji.base.BaseTeacherDAO;


public class TeacherDAO extends BaseTeacherDAO implements com.jspdev.biyesheji.dao.iface.TeacherDAO {

	public TeacherDAO () {}
	
	public TeacherDAO (Session session) {
		super(session);
	}


}