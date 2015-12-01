package com.marciani.sample.dao;

import java.util.List;

import org.hibernate.criterion.Criterion;

import com.marciani.sample.entity.user.model.UserInfo;

public interface UserInfoDAO {
	public void saveUserInfo(UserInfo userInfo);
    public void deleteUserInfo(UserInfo userInfo);
    public List<UserInfo> find(Criterion criterion);
}
