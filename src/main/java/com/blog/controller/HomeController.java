package com.blog.controller;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.entities.Blog;
import com.blog.entities.User;
import com.blog.services.BlogRepository;
import com.blog.services.UserRepository;

@Controller
public class HomeController {
	
	@Autowired
	UserRepository userRepo;
	
	
	@Autowired
	BlogRepository blogRepo;
	
	
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;
	@RequestMapping("/register")
	public String home() {
		return "register";
	}
	
	@RequestMapping("/update")
	public String register(@RequestParam("fname") String fname,@RequestParam("lname") String lname,@RequestParam("email") String email
			,@RequestParam("phno") String phno,@RequestParam("username") String username,@RequestParam("password") String password,Model mv) {
		User user = new User();
		user.setFirstName(fname);
		user.setLastName(lname);
		user.setEmail(email);
		user.setPassword(pwdEncoder.encode(password));
		user.setUsername(username);
		user.setPhNo(phno);
		user.setRole("ROLE_USER");
		User u = userRepo.getUserByEmail(email);
		System.out.println(u);
		if( userRepo.getUserByEmail(email)==null || !user.getEmail().equals(userRepo.getUserByEmail(email).getEmail())) {
			mv.addAttribute("msg","You have successfully Registered");
			userRepo.save(user);
			mv.addAttribute("isActive",true);
			return "success";
		}
		mv.addAttribute("isActive",false);
		mv.addAttribute("msg", "User already Registered");
		System.out.println("User aldready found");
		
		return "success";
	}
	
	@RequestMapping("/valid")
	public String validate(@RequestParam("email") String email,@RequestParam("password") String pwd,Model mv) throws UserPrincipalNotFoundException {
		User user =userRepo.getUserByEmail(email);
		System.out.println(user+user.getEmail());
//		System.out.println(user.getFirstName()+" "+user.getId());
		if(user == null) {
			throw new UserPrincipalNotFoundException("Could not found");
		}
//		System.out.println(pwdEncoder.matches(pwd,user.getPassword()));
		if(pwdEncoder.matches(pwd,user.getPassword())) {
			
			
			List<Blog> blogs = blogRepo.findAll();
		
			mv.addAttribute("email",email);
			mv.addAttribute("blogs",blogs);
			return "dashboard";
		}
		return "Login";
		
	}
	
	
	@RequestMapping("/signin")
	public String login(Model m) {
		boolean visible = false;
		m.addAttribute("admin_button",visible);
		return "Login";
	}
	
	
	@RequestMapping(path="/signin_admin")
	public String adminLogin(Model m) {
		boolean visible = true;
		m.addAttribute("admin_button",visible);
		return "Login";
		
	}
	
	
	
}
