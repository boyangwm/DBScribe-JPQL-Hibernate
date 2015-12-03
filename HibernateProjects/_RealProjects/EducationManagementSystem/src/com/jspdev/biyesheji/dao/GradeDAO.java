package com.jspdev.biyesheji.dao;

import org.hibernate.Session;

import com.jspdev.biyesheji.base.BaseGradeDAO;


public class GradeDAO extends BaseGradeDAO implements com.jspdev.biyesheji.dao.iface.GradeDAO {

	public GradeDAO () {}
	
	public GradeDAO (Session session) {
		super(session);
	}


}