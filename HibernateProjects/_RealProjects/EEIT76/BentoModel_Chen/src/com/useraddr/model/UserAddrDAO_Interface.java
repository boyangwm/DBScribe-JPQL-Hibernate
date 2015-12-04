package com.useraddr.model;

import java.util.List;

public interface UserAddrDAO_Interface {
	public void insert(UserAddrVO userAddrVO);
    public void update(UserAddrVO userAddrVO);
    public void delete(Integer userAddrID);
    public UserAddrVO getByPrimaryKey(Integer userAddrID);
    public long getCountByMemberAcc(String MemberAcc);
    public List<UserAddrVO> getAllByMemberAcc(String MemberAcc);
    public List<UserAddrVO> getAll();
}
