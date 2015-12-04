package com.accuse.model;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.servlet.ServletException;
import com.restaurant.model.RestaurantVO;

public class AccuseService {
	private AccuseDAO_interface Accusedao;
	public AccuseService() throws ServletException, IOException{
		Accusedao = new AccuseHibernateDAO();
	}
	public AccuseVO addAccuse(String OrderSumID,Integer RestDiscussID,String Reason,RestaurantVO restaurantVO,
			Integer RestID , Integer CaseID , String DealDetail ,String  MemberAcc){
		AccuseVO accusevo = new AccuseVO();
		accusevo.setOrderSumID(OrderSumID);
		accusevo.setRestDiscussID(RestDiscussID);
		accusevo.setReason(Reason);
		accusevo.setRestaurantVO(restaurantVO);
		accusevo.setRestID(RestID);
		accusevo.setCaseID(CaseID);
		accusevo.setDealCond(false);
		accusevo.setDealDetail(DealDetail);
		accusevo.setMemberAcc(MemberAcc);
		Accusedao.insert(accusevo);
		return accusevo;
	}
	public List<AccuseVO> getByCaseID(Integer CaseID){
		return Accusedao.getByCaseID(CaseID);
	}
	public List<AccuseVO> getByRestID(Integer RestID){
		return Accusedao.getByRestID(RestID);
	}
	public AccuseVO getByPrimaryKey(Integer AccuseID){
		return Accusedao.getByPrimaryKey(AccuseID);
	}
	public AccuseVO CheckSuccess(AccuseVO accusevo , String DealDetail){
		AccuseVO accusevonew = new AccuseVO();
		accusevonew.setAccuseID(accusevo.getAccuseID());
		accusevonew.setOrderSumID(accusevo.getOrderSumID());
		accusevonew.setRestDiscussID(accusevo.getRestDiscussID());
		accusevonew.setReason(accusevo.getReason());
		accusevonew.setRestaurantVO(accusevo.getRestaurantVO());
		accusevonew.setRestID(accusevo.getRestID());
		accusevonew.setCaseID(accusevo.getCaseID());
		accusevonew.setDealCond(true);
		accusevonew.setDealDetail(DealDetail);
		accusevonew.setMemberAcc(accusevo.getMemberAcc());
		Accusedao.update(accusevonew);
		return accusevonew ;
	}
	public AccuseVO CheckFailure(AccuseVO accusevo , String DealDetail){
		AccuseVO accusevonew = new AccuseVO();
		accusevonew.setAccuseID(accusevo.getAccuseID());
		accusevonew.setOrderSumID(accusevo.getOrderSumID());
		accusevonew.setRestDiscussID(accusevo.getRestDiscussID());
		accusevonew.setReason(accusevo.getReason());
		accusevonew.setRestaurantVO(accusevo.getRestaurantVO());
		accusevonew.setRestID(accusevo.getRestID());
		accusevonew.setCaseID(accusevo.getCaseID());
		accusevonew.setDealCond(true);
		accusevonew.setDealDetail(DealDetail);
		accusevonew.setMemberAcc(accusevo.getMemberAcc());
		Accusedao.update(accusevonew);
		return accusevonew ;
	}
}
