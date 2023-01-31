package com.blog.helper;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsEx extends UserDetails{


	public String getEmail();
	public int getUserId();
}
