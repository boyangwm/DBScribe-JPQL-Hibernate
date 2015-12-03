package com.jspdev.biyesheji.dao;

import org.hibernate.Session;

import com.jspdev.biyesheji.base.BaseMessageDAO;


public class MessageDAO extends BaseMessageDAO implements com.jspdev.biyesheji.dao.iface.MessageDAO {

	public MessageDAO () {}
	
	public MessageDAO (Session session) {
		super(session);
	}


}