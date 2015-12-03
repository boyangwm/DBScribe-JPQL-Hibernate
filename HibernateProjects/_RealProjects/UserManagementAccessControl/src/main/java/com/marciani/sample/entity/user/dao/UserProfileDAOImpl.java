package com.marciani.sample.entity.user.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

import com.marciani.sample.dao.UserProfileDAO;
import com.marciani.sample.entity.user.model.User;
import com.marciani.sample.entity.user.model.Profile;

@Repository("userProfileDAO")
public class UserProfileDAOImpl implements UserProfileDAO {
	
	@Autowired
	public SessionFactory sessionFactory;
	
	public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }	

	@Override
	public void saveUserProfile(Profile userInfo) {
		getCurrentSession().saveOrUpdate(userInfo);;		
	}

	@Override
	public void deleteUserProfile(Profile userInfo) {
		getCurrentSession().delete(userInfo);
	}

	@Override
	public List<Profile> find(Criterion criterion) {
		Criteria criteria = getCurrentSession().createCriteria(User.class);
		criteria.add(criterion);
		return criteria.list();
	}

	@Override
	public List<Profile> findAll() {
		Criteria criteria = getCurrentSession().createCriteria(User.class);
		return criteria.list();
	}

}
