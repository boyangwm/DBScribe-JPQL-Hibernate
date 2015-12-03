package com.marciani.sample.service;


import java.util.List;

import com.marciani.sample.entity.user.model.User;

import org.springframework.security.access.prepost.PreAuthorize;

public interface UserService {	
	
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
    public void saveUser(User user);
    
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
    public void deleteUser(User user);
	
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
	public List<User> findAll();
	
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
    public User findByUsername(String username);
	
	public User getUser(String username, String password);
}
