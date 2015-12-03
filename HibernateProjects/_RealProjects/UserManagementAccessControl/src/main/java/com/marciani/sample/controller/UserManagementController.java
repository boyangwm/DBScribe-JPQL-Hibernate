package com.marciani.sample.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.marciani.sample.entity.user.model.Role;
import com.marciani.sample.entity.user.model.User;
import com.marciani.sample.exception.MailException;
import com.marciani.sample.service.BusinessMailService;
import com.marciani.sample.service.UserService;
import com.marciani.sample.util.form.UserForm;

@Controller
public class UserManagementController {
	
	@Autowired
	public UserService userService;
	
	@Autowired
	private BusinessMailService businessMailService;
	
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
	public String saveUser(@Valid UserForm userForm, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("roles", Role.values());
			return "new-user";
		}
		
		User user = userForm.toUser();
		userService.saveUser(user);		
		try {
			businessMailService.sendRegistrationMail(user);
		} catch (MailException e) {
			model.addAttribute("errno", "Mail Error");
			return "failure";
		}
		model.addAttribute("message", "Success");
		return "result";
	}
	
	@RequestMapping(value = "/user-management/delete", method = RequestMethod.POST)
	public String deleteUser(@ModelAttribute("user") User user, ModelMap model) {		
		userService.deleteUser(user);	
		try {
			businessMailService.sendRemovalMail(user);
		} catch (MailException e) {
			model.addAttribute("errno", "Mail Error");
			return "failure";
		}
		model.addAttribute("message", "Success");
		return "result";
	}
	
	@RequestMapping(value = "/user-management/edit", method = RequestMethod.GET)
	public String editUser(@ModelAttribute("user") User user, ModelMap model) {
		model.addAttribute("user", user);
		return "edit-user";
	}
	
	@RequestMapping(value = "/user-management/save-edited", method = RequestMethod.POST)
	public String saveEditedUser(@ModelAttribute("userForm") UserForm userForm, ModelMap model) {
		User user = userForm.toUser();
		userService.saveUser(user);		
		try {
			businessMailService.sendEditedProfileMail(user);
		} catch (MailException e) {
			model.addAttribute("errno", "Mail Error");
			return "failure";
		}
		model.addAttribute("message", "Success");
		return "result";
	}

}
