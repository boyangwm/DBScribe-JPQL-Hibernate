package com.marciani.sample.entity.role.service;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marciani.sample.dao.RoleDAO;
import com.marciani.sample.entity.role.model.Role;
import com.marciani.sample.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleDAO roleDAO;

	@Override
	@Transactional(readOnly = true)
	public List<Role> findAll() {
		return roleDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Role findById(Integer roleId) {
		Criterion criterion = Restrictions.eq("id", roleId.intValue());
		List<Role> list = roleDAO.find(criterion);
		return list.get(0);
	}

}
