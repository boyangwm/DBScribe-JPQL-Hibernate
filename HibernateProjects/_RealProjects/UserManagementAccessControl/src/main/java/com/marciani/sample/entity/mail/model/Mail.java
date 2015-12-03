package com.marciani.sample.entity.mail.model;

import java.util.HashMap;

import javax.activation.DataSource;

public class Mail {
	
	private MailTemplate mailTemplate;
	private String recipient;
	private String subject;
	private String body;
	private HashMap<String, Object> entity;
	private HashMap<String, DataSource> attachments  = new HashMap<String, DataSource>();	
	
	public Mail() {}
	
	public MailTemplate getMailTemplate() {
		return mailTemplate;
	}

	public void setMailTemplate(MailTemplate mailTemplate) {
		this.mailTemplate = mailTemplate;
	}
	
	public String getRecipient() {
		return this.recipient;
	}
	
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	
	public String getSubject() {
		return this.subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getBody() {
		return this.body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public HashMap<String, Object> getEntity() {
		return entity;
	}

	public void setEntity(HashMap<String, Object> entity) {
		this.entity = entity;
	}

	public HashMap<String, DataSource> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(HashMap<String, DataSource> attachments) {
		this.attachments = attachments;
	}
	
	public void addAttachment(String name, DataSource source) {		
		this.attachments.put(name, source);
	}
	
	@Override
	public String toString() {
		return "Mail[" + 
				this.mailTemplate + " " +
				this.recipient + " " + 
				this.subject + " " + 
				this.body + " " + 
				this.attachments.toString() + "]";
	}

}
