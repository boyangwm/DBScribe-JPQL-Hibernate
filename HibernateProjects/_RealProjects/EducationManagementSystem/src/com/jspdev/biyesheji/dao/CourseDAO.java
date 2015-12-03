package com.jspdev.biyesheji.dao;

import org.hibernate.Session;

import com.jspdev.biyesheji.base.BaseCourseDAO;


public class CourseDAO extends BaseCourseDAO implements com.jspdev.biyesheji.dao.iface.CourseDAO {

	public CourseDAO () {}
	
	public CourseDAO (Session session) {
		super(session);
	}


}