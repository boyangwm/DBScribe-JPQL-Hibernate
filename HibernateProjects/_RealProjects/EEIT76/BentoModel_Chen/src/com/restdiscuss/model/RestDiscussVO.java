package com.restdiscuss.model;

import java.io.Serializable;
import java.sql.Date;

import com.member.model.MemberVO;

public class RestDiscussVO implements Serializable {
	private Integer RestDiscussID;
	private boolean Judge;
	private Date JudgeDate;
	private String Discussion;
	private String Response;
	private Integer RestID;
	private String MemberAcc;
	private String OrderSumID;
	private MemberVO MemberVO;
	
	public String getMemberAcc() {
		return MemberAcc;
	}

	public void setMemberAcc(String memberAcc) {
		MemberAcc = memberAcc;
	}

	public Integer getRestDiscussID() {
		return RestDiscussID;
	}

	public void setRestDiscussID(Integer restDiscussID) {
		RestDiscussID = restDiscussID;
	}

	public boolean isJudge() {
		return Judge;
	}

	public void setJudge(boolean judge) {
		Judge = judge;
	}

	public Date getJudgeDate() {
		return JudgeDate;
	}

	public void setJudgeDate(Date judgeDate) {
		JudgeDate = judgeDate;
	}

	public String getDiscussion() {
		return Discussion;
	}

	public void setDiscussion(String discussion) {
		Discussion = discussion;
	}

	public String getResponse() {
		return Response;
	}

	public void setResponse(String response) {
		Response = response;
	}

	public Integer getRestID() {
		return RestID;
	}

	public void setRestID(Integer restID) {
		RestID = restID;
	}

	public String getOrderSumID() {
		return OrderSumID;
	}

	public void setOrderSumID(String orderSumID) {
		OrderSumID = orderSumID;
	}

	public MemberVO getMemberVO() {
		return MemberVO;
	}

	public void setMemberVO(MemberVO memberVO) {
		MemberVO = memberVO;
	}

}
