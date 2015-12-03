package com.marciani.sample.entity.user.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

import com.marciani.sample.dao.UserDAO;
import com.marciani.sample.entity.user.model.User;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {
	
	@Autowired
	public SessionFactory sessionFactory;
	
	public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }	

	@Override
	public void saveUser(User user) {
		getCurrentSession().saveOrUpdate(user);;		
	}

	@Override
	public void deleteUser(User user) {
		getCurrentSession().delete(user);
	}

	@Override
	public List<User> find(Criterion criterion) {
		Criteria criteria = getCurrentSession().createCriteria(User.class);
		criteria.add(criterion);
		return criteria.list();
	}

	@Override
	public List<User> findAll() {
		Criteria criteria = getCurrentSession().createCriteria(User.class);
		return criteria.list();
	}

}
