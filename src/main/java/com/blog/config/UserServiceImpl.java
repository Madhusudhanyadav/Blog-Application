package com.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.blog.entities.User;
import com.blog.helper.UserDetailsEx;
import com.blog.services.UserRepository;

public class UserServiceImpl implements UserDetailsService{

	@Autowired
	UserRepository userRepo;
	@Override
	public UserDetailsEx loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepo.getUserByEmail(username);
		UserDetailsEx userDetails = new UserDetailsImpl(user);
		return userDetails;
	}

}
