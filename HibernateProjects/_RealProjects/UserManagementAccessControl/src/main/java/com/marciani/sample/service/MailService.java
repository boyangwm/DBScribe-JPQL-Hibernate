package com.marciani.sample.service;

import javax.mail.MessagingException;

import com.marciani.sample.entity.mail.model.Mail;

public interface MailService {	
	
	public void sendPlainMail(Mail mail) throws MessagingException;
	public void sendHtmlMail(Mail mail) throws MessagingException;
	public void sendTemplateMail(Mail mail) throws MessagingException;

}
