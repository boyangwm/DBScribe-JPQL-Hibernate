package com.marciani.sample.controller;


import java.util.HashMap;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.marciani.sample.entity.mail.model.Mail;
import com.marciani.sample.entity.mail.model.MailType;
import com.marciani.sample.entity.user.model.Role;
import com.marciani.sample.entity.user.model.User;
import com.marciani.sample.service.MailService;
import com.marciani.sample.service.UserService;
import com.marciani.sample.util.form.UserForm;

@Controller
public class UserController {
	
	@Autowired
	public UserService userService;
	
	@Autowired
	private MailService mailService;
	
	@RequestMapping(value = "/user-management", method = RequestMethod.GET)
	public String manageUsers(ModelMap model) {
		model.addAttribute("user", new User());
		return "user-management";
	}
	
	@RequestMapping(value = "/user-management/new-user", method = RequestMethod.GET)
	public String addNewUser(ModelMap model) {
		model.addAttribute("userForm", new UserForm());
		model.addAttribute("roles", Role.values());
		return "new-user";
	}
	
	@RequestMapping(value = "/user-management/save", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("userForm") UserForm userForm, BindingResult result) {	
		if (result.hasErrors()) {
			System.err.println("### Errors ###");
			return "new-user";
		}
		System.err.println("### UserForm: " + userForm.toString() + " ###");
		User user = userForm.toUser();
		System.err.println("### User: " + user.toString() + " ###");
		userService.saveUser(user);		
		sendMail(user);
		return "result";
	}
	
	@RequestMapping(value = "/user-management/delete", method = RequestMethod.POST)
	public String deleteUser(String username, ModelMap model) {		
		userService.deleteUser(username);		
		model.addAttribute("message", "Success");
		return "result";
	}
	
	private void sendMail(User user) {
		Mail mail = new Mail();
		mail.setMailType(MailType.TEMPLATE);
		mail.setRecipient(user.getUserProfile().getEmail());
		mail.setSubject("SER User Added");
		mail.setBody("registration.vm");
		HashMap<String, Object> entity = new HashMap<String, Object>();
		entity.put("user", user);
		mail.setEntity(entity);
		mail.addAttachment("SampleAttachment.pdf", "C:\\Users\\Giacomo\\Documents\\Workspace\\Sample\\SpringSecurityHibernateSample\\src\\main\\resources\\repo\\SampleAttachment.pdf");
		try {
			mailService.sendMail(mail);
		} catch (MessagingException e) {
			System.err.println("### Mail Service: Failure for: " + mail.toString() + " ###");
		}
	}

}
