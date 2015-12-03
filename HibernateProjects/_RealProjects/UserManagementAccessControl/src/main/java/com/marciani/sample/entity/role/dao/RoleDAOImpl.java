package com.marciani.sample.entity.role.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marciani.sample.dao.RoleDAO;
import com.marciani.sample.entity.role.model.Role;

@Repository("roleDAO")
public class RoleDAOImpl implements RoleDAO {
	
	@Autowired
	public SessionFactory sessionFactory;
	
	public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }	

	@Override
	public void saveRole(Role role) {
		getCurrentSession().saveOrUpdate(role);;		
	}

	@Override
	public void deleteRole(Role role) {
		getCurrentSession().delete(role);
	}

	@Override
	public List<Role> find(Criterion criterion) {
		Criteria criteria = getCurrentSession().createCriteria(Role.class);
		criteria.add(criterion);
		return criteria.list();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Role> findAll() {
		Criteria criteria = getCurrentSession().createCriteria(Role.class);
		return criteria.list();
	}

}
