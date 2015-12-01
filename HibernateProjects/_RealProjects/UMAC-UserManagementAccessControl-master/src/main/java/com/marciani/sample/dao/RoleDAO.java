package com.marciani.sample.dao;

import java.util.List;

import org.hibernate.criterion.Criterion;

import com.marciani.sample.entity.role.model.Role;

public interface RoleDAO {
	public void saveRole(Role role);
    public void deleteRole(Role role);
    public List<Role> find(Criterion criterion);
    public List<Role> findAll();
}
