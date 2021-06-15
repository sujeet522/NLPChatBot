package com.ner.service;

import org.slf4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private static final Logger LOGGER=org.slf4j.LoggerFactory.getLogger(UserService.class);
	
	public String getUser() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
}
