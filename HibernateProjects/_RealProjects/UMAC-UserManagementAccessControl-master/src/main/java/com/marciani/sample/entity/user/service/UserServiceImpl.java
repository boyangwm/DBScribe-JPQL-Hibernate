package com.marciani.sample.entity.user.service;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.marciani.sample.dao.UserDAO;
import com.marciani.sample.entity.user.model.User;
import com.marciani.sample.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	@Transactional(readOnly = false)
	public void saveUser(User user) {
		userDAO.saveUser(user);	
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteUser(User user) {
		userDAO.deleteUser(user);		
	}

	@Override
	@Transactional(readOnly = true)
	public User findByUsername(String username) {
		Criterion criterion = Restrictions.eq("username", username);
		List<User> list = userDAO.find(criterion);
		return list.get(0);
	}

	@Override
	@Transactional(readOnly = true)
	public User getUser(String username, String password) {
		Criterion criterion = Restrictions.conjunction()
				.add(Restrictions.eq("username", username))
				.add(Restrictions.eq("password", password));
		List<User> list = userDAO.find(criterion);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}		
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return userDAO.findAll();
	}

}
