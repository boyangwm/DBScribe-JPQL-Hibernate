package com.marciani.sample.entity.mail.service;

import java.util.Map;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.marciani.sample.entity.mail.model.Mail;
import com.marciani.sample.service.MailService;

@Service("mailService")
public class MailServiceImpl implements MailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	@Override
	public void sendPlainMail(final Mail mail) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();		
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");		
		helper.setTo(mail.getRecipient());
		helper.setSubject(mail.getSubject());
		helper.setText(mail.getBody());
		for (Map.Entry<String, DataSource> attachment : mail.getAttachments().entrySet()) {
			String name = attachment.getKey();
			DataSource source = attachment.getValue();
			helper.addAttachment(name, source);
		}		
		mailSender.send(message);		
	}
	
	@Override
	public void sendHtmlMail(final Mail mail) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();		
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");		
		helper.setTo(mail.getRecipient());
		helper.setSubject(mail.getSubject());
		helper.setText(mail.getBody(), true);
		for (Map.Entry<String, DataSource> attachment : mail.getAttachments().entrySet()) {
			String name = attachment.getKey();
			DataSource source = attachment.getValue();
			helper.addAttachment(name, source);
		}	
		mailSender.send(message);		
	}
	
	@Override
	public void sendTemplateMail(final Mail mail) throws MessagingException { 
		MimeMessage message = mailSender.createMimeMessage();		
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");		
		helper.setTo(mail.getRecipient());
		helper.setSubject(mail.getSubject());
		for (Map.Entry<String, DataSource> attachment : mail.getAttachments().entrySet()) {
			String name = attachment.getKey();
			DataSource source = attachment.getValue();
			helper.addAttachment(name, source);
		}			
		ModelMap model = new ModelMap();
        for (Map.Entry<String, Object> entity : mail.getEntity().entrySet()) {
        	model.put(entity.getKey(), entity.getValue());
        }
        String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, mail.getMailTemplate().getTemplate(), "UTF-8", model);
        helper.setText(text, true);
		mailSender.send(message);
	}

}
