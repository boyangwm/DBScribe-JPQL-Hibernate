package com.marciani.sample.dao;

import java.util.List;

import org.hibernate.criterion.Criterion;

import com.marciani.sample.entity.user.model.User;

public interface UserDAO {
	public void saveUser(User user);
    public void deleteUser(User user);
    public List<User> find(Criterion criterion);
    public List<User> findAll();
}
