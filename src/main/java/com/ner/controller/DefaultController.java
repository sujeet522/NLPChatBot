package com.ner.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ner.service.UserService;

@Controller
public class DefaultController {

	
	@Autowired
	UserService userservice;
	
	@GetMapping("/")
	public String home(Model model) {		
		model.addAttribute("uname", userservice.getUser());		
		return "redirect:/chatrequest";
	}
	
	
	@GetMapping("/login")
	public String login() {		
		return "login";
	}
	
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {		
		HttpSession session=request.getSession();
		session.invalidate();
		return "login";
	}
	
	
	@GetMapping("/chatrequest")
	public String chatrequest(HttpServletRequest request, HttpServletResponse response,Model model) {		
		model.addAttribute("uname", userservice.getUser());
		return "chatrequest";
	}
	
	
}
