package com.marciani.sample.dao;

import java.util.List;

import org.hibernate.criterion.Criterion;

import com.marciani.sample.entity.user.model.Profile;

public interface UserProfileDAO {
	public void saveUserProfile(Profile userProfile);
    public void deleteUserProfile(Profile userProfile);
    public List<Profile> find(Criterion criterion);
    public List<Profile> findAll();
}
