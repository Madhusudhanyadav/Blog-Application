package com.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blog.entities.User;
import com.blog.helper.UserDetailsEx;
import com.blog.services.UserRepository;

@Controller
public class SwitchController {
	
	@Autowired
	UserRepository userRepo;
	
	@RequestMapping("/switch")
	public String switchControl(Model mv) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username,email;
		boolean switch_control=false;
		if (principal instanceof UserDetailsEx) {
			 email = ((UserDetailsEx)principal).getEmail();
		} else {
			  email = principal.toString();
		}
		User user = userRepo.getUserByEmail(email);
		
		if(user.getRole().equals("ROLE_ADMIN")) {
			switch_control=true;
		}
		mv.addAttribute("switch_control",switch_control);
		return "Switch/switch_control";
	}
}
