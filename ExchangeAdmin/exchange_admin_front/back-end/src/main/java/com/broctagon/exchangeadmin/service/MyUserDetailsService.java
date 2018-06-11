package com.broctagon.exchangeadmin.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.broctagon.exchangeadmin.model.Admin;

@Service
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	private AdminService adminService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin admin = adminService.findByUserName(username);
		if(admin == null) {
			return null;
		}
		return new User(username, admin.getPassword(), Collections.emptyList());
	}

}
