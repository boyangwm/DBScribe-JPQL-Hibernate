package com.jspdev.biyesheji.dao;

import org.hibernate.Session;

import com.jspdev.biyesheji.base.BaseScDAO;


public class ScDAO extends BaseScDAO implements com.jspdev.biyesheji.dao.iface.ScDAO {

	public ScDAO () {}
	
	public ScDAO (Session session) {
		super(session);
	}


}