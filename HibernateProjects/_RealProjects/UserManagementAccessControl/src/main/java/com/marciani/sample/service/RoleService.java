package com.marciani.sample.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.marciani.sample.entity.role.model.Role;

public interface RoleService {
	
	public Role findById(Integer roleId);
	
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
	public List<Role> findAll();
}
