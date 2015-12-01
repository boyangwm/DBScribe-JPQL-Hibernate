package com.marciani.sample.entity.mail.model;

public enum MailTemplate {
	
	USER_SUBSCRIPTION ("User Subscription", "mail_user-registration.vm"),
	USER_REMOVAL ("User Removal", "mail_user-removal.vm"),
	USER_EDIT ("User Edit", "mail_user-edit.vm"),
	USER_PASSWORD_RECOVERY ("User Password Recovery", "mail_user-password-recovery.vm");
	
	private final String name;
	private final String template;
	
	MailTemplate(String name, String template) {
		this.name = name;
		this.template = template;
	}
	
	public String getName() {
		return name;
	}

	public String getTemplate() {
		return template;
	}	

}
