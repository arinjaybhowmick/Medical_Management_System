package com.project.medicalmanagementsystem.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface DefaultUserService extends UserDetailsService{
	public String getStatus(String username);
	public String getUserRole(String userName);
}
