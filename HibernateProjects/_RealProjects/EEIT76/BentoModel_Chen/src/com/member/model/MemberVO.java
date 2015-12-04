package com.member.model;

import java.util.Set;

import com.useraddr.model.UserAddrVO;

public class MemberVO {
	private String MemberAcc  ;
	private String MemberPwd  ;
	private String MemberLastName  ;
	private String MemberFirstName ;
	private String MemberPhone   ;
	private String MemberCel   ;
	private String MemberEmail ;
	private boolean MemberGender ;
	private boolean MemberOpen;
	private Set<UserAddrVO> userAddrs;
	
	public boolean isMemberGender() {
		return MemberGender;	}
	public void setMemberGender(boolean memberGender) {
		MemberGender = memberGender;	}
	public String getMemberAcc() {
		return MemberAcc;	}
	public void setMemberAcc(String memberAcc) {
		MemberAcc = memberAcc;	}
	public String getMemberPwd() {
		return MemberPwd;	}
	public void setMemberPwd(String memberPwd) {
		MemberPwd = memberPwd;	}
	public String getMemberLastName() {
		return MemberLastName;	}
	public void setMemberLastName(String memberLastName) {
		MemberLastName = memberLastName;	}
	public String getMemberFirstName() {
		return MemberFirstName;	}
	public void setMemberFirstName(String memberFirstName) {
		MemberFirstName = memberFirstName;	}
	public String getMemberPhone() {
		return MemberPhone;	}
	public void setMemberPhone(String memberPhone) {
		MemberPhone = memberPhone;	}
	public String getMemberCel() {
		return MemberCel;	}
	public void setMemberCel(String memberCel) {
		MemberCel = memberCel;	}
	public String getMemberEmail() {
		return MemberEmail;	}
	public void setMemberEmail(String memberEmail) {
		MemberEmail = memberEmail;	}
	public boolean isMemberOpen() {
		return MemberOpen;
	}
	public void setMemberOpen(boolean memberOpen) {
		MemberOpen = memberOpen;
	}
	public Set<UserAddrVO> getUserAddrs() {
		return userAddrs;
	}
	public void setUserAddrs(Set<UserAddrVO> userAddrs) {
		this.userAddrs = userAddrs;
	}
}

