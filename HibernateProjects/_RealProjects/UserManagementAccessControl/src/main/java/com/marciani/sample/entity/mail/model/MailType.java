package com.marciani.sample.entity.mail.model;

public enum MailType {	
	PLAIN ("PLAIN"),
	HTML ("HTML"),
	TEMPLATE ("TEMPLATE");
	
	private final String name;
	
	MailType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
