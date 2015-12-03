package com.marciani.sample.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "index";
	}
	
	@RequestMapping(value = "/simple-welcome", method = RequestMethod.GET)
	public String simpleWelcome(ModelMap model) {
		model.addAttribute("message", "Welcome to SpringHibernateSample App");
		return "simple-welcome";
	}
	
	@RequestMapping(value = "/failure", method = RequestMethod.GET)
	public String failure(@RequestParam(value = "errno", defaultValue = "Unknown") String errno, ModelMap model) {
		model.addAttribute("errno", errno);
		return "failure";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(ModelMap model, Principal principal) {
		String username = principal.getName();
		model.addAttribute("username", username);
		return "home";
	}
	
}
