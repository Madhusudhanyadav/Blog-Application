package com.blog.controller;


import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.entities.Blog;
import com.blog.entities.User;
import com.blog.helper.UserDetailsEx;
import com.blog.services.BlogRepository;
import com.blog.services.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	BCryptPasswordEncoder pwdEncoder;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	BlogRepository blogRepo;
	
	
	
	@RequestMapping("/index")
	public String dashboard(Model m) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username,email;
		boolean isActive=true;
		if (principal instanceof UserDetailsEx) {
		  email = ((UserDetailsEx)principal).getEmail();
		} else {
		   email = principal.toString();
		}
		User user = userRepo.getUserByEmail(email);
		
//		System.out.println(email);
		m.addAttribute("user",user);
		List<Blog> blogs=blogRepo.findAll();
		m.addAttribute("blogs", blogs);
		m.addAttribute("isActive",isActive);
		
		return "dashboard";
	}
	
	
	@RequestMapping(path="/updateBlog",method=RequestMethod.POST)
	public String UpdateBlog(@RequestParam("Blog") String blog,Model mv) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username,email;
		boolean isActive=true;
		if (principal instanceof UserDetailsEx) {
		  email = ((UserDetailsEx)principal).getEmail();
		} else {
		   email = principal.toString();
		}
		User user = userRepo.getUserByEmail(email);
		
		Blog blog_user = new Blog();
		blog_user.setUser(user);
		blog_user.setDescription(blog);
		Date date =new Date();
	
		blog_user.setDate(date);
		blogRepo.save(blog_user);
//		System.out.println("updated");
		
		mv.addAttribute("user",user);
		mv.addAttribute("isActive",isActive);
		List<Blog> blogs=blogRepo.findAll();
		
		mv.addAttribute("blogs", blogs);
		
		return "dashboard";
	}
	
	
	@RequestMapping("/myblog")
	public String myBlogs(Model mv) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int id=1;
		String email = null;
		boolean isActive=false;
		if (principal instanceof UserDetailsEx) {
		  id = ((UserDetailsEx)principal).getUserId();
		  email=((UserDetailsEx)principal).getEmail();
		} else {
			try {
				id = Integer.parseInt(principal.toString());
				email=principal.toString();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		List<Blog> blogs=blogRepo.getBlogsByUserId(id);
		User user = userRepo.getUserByEmail(email);
		
		mv.addAttribute("blogs", blogs);
		mv.addAttribute("user", user);
		mv.addAttribute("isActive",isActive);
		
		return "dashboard";
	}
	
	
	
	
}
