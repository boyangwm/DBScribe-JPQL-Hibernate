package com.jspdev.biyesheji.dao;

import org.hibernate.Session;

import com.jspdev.biyesheji.base.BaseNoteDAO;


public class NoteDAO extends BaseNoteDAO implements com.jspdev.biyesheji.dao.iface.NoteDAO {

	public NoteDAO () {}
	
	public NoteDAO (Session session) {
		super(session);
	}


}