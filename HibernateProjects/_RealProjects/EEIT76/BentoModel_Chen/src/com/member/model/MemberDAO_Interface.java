package com.member.model;

import java.util.*;

public interface MemberDAO_Interface {
    public abstract void insert(MemberVO memberVO);
    public void update(MemberVO memberVO);
    public void delete(String MemberAcc);
    public MemberVO getByPrimaryKey(String MemberAcc);
    public List<MemberVO> getAll();
    public List<Object> getAllMemberAcc();
}
