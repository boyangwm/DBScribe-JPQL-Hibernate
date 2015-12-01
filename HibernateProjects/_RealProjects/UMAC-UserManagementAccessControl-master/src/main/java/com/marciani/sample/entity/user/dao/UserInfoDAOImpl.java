package com.marciani.sample.entity.user.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marciani.sample.dao.UserInfoDAO;
import com.marciani.sample.entity.user.model.User;
import com.marciani.sample.entity.user.model.UserInfo;

@Repository
public class UserInfoDAOImpl implements UserInfoDAO {
	
	@Autowired
	public SessionFactory sessionFactory;
	
	public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }	

	@Override
	@Transactional(readOnly = false)
	public void saveUserInfo(UserInfo userInfo) {
		getCurrentSession().saveOrUpdate(userInfo);;		
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteUserInfo(UserInfo userInfo) {
		getCurrentSession().delete(userInfo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserInfo> find(Criterion criterion) {
		Criteria criteria = getCurrentSession().createCriteria(User.class);
		criteria.add(criterion);
		return criteria.list();
	}

}
