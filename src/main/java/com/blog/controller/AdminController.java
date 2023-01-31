package com.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.entities.Blog;
import com.blog.entities.User;
import com.blog.helper.UserDetailsEx;
import com.blog.services.BlogRepository;
import com.blog.services.UserRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	BlogRepository blogRepo;
	
	boolean select;
	@RequestMapping("/index")
	public String index(Model m) {
		select=true;
		Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email;
		if(principal instanceof UserDetailsEx) {
			email = ((UserDetailsEx) principal).getEmail();
		}
		else {
			email=principal.toString();
		}
		m.addAttribute("email", email);
		List<Blog> blogs=blogRepo.findAll();
		m.addAttribute("blogs", blogs);
		m.addAttribute("select", select);
		return "Admin/admin_dashboard";
	}
	
	@RequestMapping("/delete/{blogId}")
	public String deleteBlog(@PathVariable("blogId") String bid,Model m) {
		
		select=true;
		blogRepo.deleteById(Integer.parseInt(bid));
		Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email;
		if(principal instanceof UserDetailsEx) {
			email = ((UserDetailsEx) principal).getEmail();
		}
		else {
			email=principal.toString();
		}
		m.addAttribute("email", email);
		List<Blog> blogs=blogRepo.findAll();
		m.addAttribute("blogs", blogs);
		m.addAttribute("select", select);
		return "Admin/admin_dashboard";
	}
	
	@RequestMapping("/editBlog/{blogId}")
	public String editBlog(@PathVariable("blogId") String blogId,@RequestParam("blog") String blogDesc,Model m) {
		select=true;
		Blog blog=blogRepo.getById(Integer.parseInt(blogId));
		blog.setDescription(blogDesc);
		blogRepo.save(blog);
		Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email;
		if(principal instanceof UserDetailsEx) {
			email = ((UserDetailsEx) principal).getEmail();
		}
		else {
			email=principal.toString();
		}
		m.addAttribute("email", email);
		List<Blog> blogs=blogRepo.findAll();
		m.addAttribute("blogs", blogs);
		m.addAttribute("select", select);
		return "Admin/admin_dashboard";
	}
	
	@RequestMapping("/edit_users")
	public String editUsers(Model m) {
		select=false;
		Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email;
		if(principal instanceof UserDetailsEx) {
			email = ((UserDetailsEx) principal).getEmail();
		}
		else {
			email=principal.toString();
		}
		m.addAttribute("email", email);
		List<User> ll =userRepo.findAll();
		m.addAttribute("users", ll);
		m.addAttribute("select", select);
		return "Admin/admin_users";
	}
	
	@RequestMapping("/removeUser/{userId}")
	public String removeUser(@PathVariable("userId") String userId,Model m) {
		select=false;
		Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email;
		if(principal instanceof UserDetailsEx) {
			email = ((UserDetailsEx) principal).getEmail();
		}
		else {
			email=principal.toString();
		}
		
		m.addAttribute("email", email);
		userRepo.deleteById(Integer.parseInt(userId));
		List<User> ll =userRepo.findAll();
		m.addAttribute("users", ll);
		m.addAttribute("select", select);
		return "Admin/admin_users";
	}
	
	
	@RequestMapping("/editUser")
	public String editDetailsOfUser(@RequestParam("FirstName") String first,@RequestParam("LastName") String last,
			@RequestParam("PhNo") String phNo,@RequestParam("Username") String username,
			@RequestParam("email") String em
			,Model m) {
		select=false;
		Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email=null;
		if(principal instanceof UserDetailsEx) {
			email = ((UserDetailsEx) principal).getEmail();
		}
		else {
			email=principal.toString();
		}
		User user = userRepo.getUserByEmail(em);
		user.setFirstName(first);
		user.setLastName(last);
		user.setPhNo(phNo);
		user.setUsername(username);
		
		m.addAttribute("email", email);
		List<User> ll =userRepo.findAll();
		m.addAttribute("users", ll);
		m.addAttribute("select", select);
		return "Admin/admin_users";
	}
}
