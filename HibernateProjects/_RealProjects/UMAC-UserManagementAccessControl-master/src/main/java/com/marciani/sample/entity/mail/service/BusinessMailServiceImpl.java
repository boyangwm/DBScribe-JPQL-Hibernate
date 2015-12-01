package com.marciani.sample.entity.mail.service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marciani.sample.entity.mail.model.Mail;
import com.marciani.sample.entity.mail.model.MailTemplate;
import com.marciani.sample.entity.user.model.User;
import com.marciani.sample.exception.MailException;
import com.marciani.sample.exception.ReportGeneratorException;
import com.marciani.sample.service.BusinessMailService;
import com.marciani.sample.service.MailService;
import com.marciani.sample.service.ReportService;

@Service("businessMailService")
public class BusinessMailServiceImpl implements BusinessMailService {
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private ReportService reportService;

	@Override
	public void sendRegistrationMail(User user) throws MailException {
		Mail mail = new Mail();
		mail.setMailTemplate(MailTemplate.USER_SUBSCRIPTION);
		mail.setRecipient(user.getProfile().getEmail());
		mail.setSubject(MailTemplate.USER_SUBSCRIPTION.getName());
		HashMap<String, Object> entity = new HashMap<String, Object>();
		entity.put("user", user);
		mail.setEntity(entity);
		String fileName = MailTemplate.USER_SUBSCRIPTION.getName() + ".pdf";
		try {
			ByteArrayOutputStream attachment = reportService.generateSubscriptionReport(user);
			DataSource datasource = new ByteArrayDataSource(attachment.toByteArray(), "application/pdf");
			mail.addAttachment(fileName, datasource);
			mailService.sendTemplateMail(mail);			
		} catch(ReportGeneratorException | MessagingException e) {
			throw new MailException();
		}
	}

	@Override
	public void sendRemovalMail(User user) throws MailException {
		Mail mail = new Mail();
		mail.setMailTemplate(MailTemplate.USER_REMOVAL);
		mail.setRecipient(user.getProfile().getEmail());
		mail.setSubject(MailTemplate.USER_REMOVAL.getName());
		HashMap<String, Object> entity = new HashMap<String, Object>();
		entity.put("user", user);
		mail.setEntity(entity);
		String fileName = MailTemplate.USER_SUBSCRIPTION.getName() + ".pdf";
		try {
			ByteArrayOutputStream attachment = reportService.generateSubscriptionReport(user);
			DataSource datasource = new ByteArrayDataSource(attachment.toByteArray(), fileName);
			mail.addAttachment(fileName, datasource);
			mailService.sendTemplateMail(mail);			
		} catch(ReportGeneratorException | MessagingException e) {
			throw new MailException();
		}
	}

	@Override
	public void sendPasswordRecoveryMail(User user) throws MailException {
		Mail mail = new Mail();
		mail.setMailTemplate(MailTemplate.USER_PASSWORD_RECOVERY);
		mail.setRecipient(user.getProfile().getEmail());
		mail.setSubject(MailTemplate.USER_PASSWORD_RECOVERY.getName());
		HashMap<String, Object> entity = new HashMap<String, Object>();
		entity.put("user", user);
		mail.setEntity(entity);
		String fileName = MailTemplate.USER_SUBSCRIPTION.getName() + ".pdf";
		try {
			ByteArrayOutputStream attachment = reportService.generateSubscriptionReport(user);
			DataSource datasource = new ByteArrayDataSource(attachment.toByteArray(), fileName);
			mail.addAttachment(fileName, datasource);
			mailService.sendTemplateMail(mail);			
		} catch(ReportGeneratorException | MessagingException e) {
			throw new MailException();
		}
	}

	@Override
	public void sendEditedProfileMail(User user) throws MailException {
		Mail mail = new Mail();
		mail.setMailTemplate(MailTemplate.USER_EDIT);
		mail.setRecipient(user.getProfile().getEmail());
		mail.setSubject(MailTemplate.USER_EDIT.getName());
		HashMap<String, Object> entity = new HashMap<String, Object>();
		entity.put("user", user);
		mail.setEntity(entity);
		String fileName = MailTemplate.USER_SUBSCRIPTION.getName() + ".pdf";
		try {
			ByteArrayOutputStream attachment = reportService.generateSubscriptionReport(user);
			DataSource datasource = new ByteArrayDataSource(attachment.toByteArray(), fileName);
			mail.addAttachment(fileName, datasource);
			mailService.sendTemplateMail(mail);			
		} catch(ReportGeneratorException | MessagingException e) {
			throw new MailException();
		}
	}

}
