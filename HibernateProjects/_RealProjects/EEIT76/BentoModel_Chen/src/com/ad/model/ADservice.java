package com.ad.model;
import java.io.*;
import java.sql.Date;
import java.util.*;
import javax.servlet.*;
import com.restaurant.model.RestaurantService;
import com.restaurant.model.RestaurantVO;
import com.treatcase.model.TreatCaseVO;

public class ADservice {
	private AdDAO_interface ADdao;
	private RestaurantService restsvc ;

	public ADservice() throws ServletException, IOException{
		ADdao = new AdDAO();
	}
	public String getOneRestaurant(AdVO advo) {
		Integer RestID = advo.getRestID();
		String RestName = restsvc.getOneRestaurantName(RestID);
 		return RestName;
	}
	public AdVO addAD(String Context,Date StartDate,Date EndDate,byte[] Image,
			TreatCaseVO TreatCaseVO,Integer RestID,RestaurantVO restaurantVO,Integer TreatID){
		AdVO advo = new AdVO();
		advo.setRestaurantVO(restaurantVO);
		advo.setContext(Context);
		advo.setStartDate(StartDate);
		advo.setEndDate(EndDate);
		advo.setImage(Image);
		advo.setTreatCaseVO(TreatCaseVO);
		advo.setRestID(RestID);
		advo.setTreatID(TreatID);
		ADdao.insert(advo);
		return advo;
	}
	public List<AdVO> getByTreatID(Integer TreatID){
		return ADdao.getByTreatID(TreatID);
	}
	public AdVO getByRestID(Integer RestID){
		return ADdao.getByPrimaryKey(RestID);
	}
	public AdVO CheckSuccess(AdVO adVO){
		AdVO adVOnew = new AdVO();
		TreatCaseVO  tcvonew = new TreatCaseVO();
		tcvonew.setTreatID(2);
		adVOnew.setTreatID(2);
		adVOnew.setRestID(adVO.getRestID());
		adVOnew.setContext(adVO.getContext());
		adVOnew.setImage(adVO.getImage());
		adVOnew.setStartDate(adVO.getStartDate());
		adVOnew.setEndDate(adVO.getEndDate());
		adVOnew.setTreatCaseVO(tcvonew);
		adVOnew.setRestaurantVO(adVO.getRestaurantVO());
		ADdao.insert(adVOnew);
		return adVOnew ;
	}
	public AdVO CheckFailure(AdVO adVO){
		AdVO adVOnew = new AdVO();
		TreatCaseVO  tcvonew = new TreatCaseVO();
		tcvonew.setTreatID(3);
		adVOnew.setTreatID(3);
		System.out.println(adVO.getTreatDetail());
		adVOnew.setTreatDetail(adVO.getTreatDetail());
		adVOnew.setRestID(adVO.getRestID());
		adVOnew.setContext(adVO.getContext());
		adVOnew.setImage(adVO.getImage());
		adVOnew.setStartDate(adVO.getStartDate());
		adVOnew.setEndDate(adVO.getEndDate());
		adVOnew.setTreatDetail(adVO.getTreatDetail());
		adVOnew.setTreatCaseVO(tcvonew);
		adVOnew.setRestaurantVO(adVO.getRestaurantVO());
		System.out.println(adVOnew.getTreatDetail());
		ADdao.insert(adVOnew);
		return adVOnew ;
	}
	public AdVO deleteAD(AdVO adVO) {
		AdVO adVOnew = new AdVO();
		TreatCaseVO  tcvonew = new TreatCaseVO();
		tcvonew.setTreatID(3);
		adVOnew.setTreatID(3);
		System.out.println(adVO.getTreatDetail());
		adVOnew.setTreatDetail(adVO.getTreatDetail());
		adVOnew.setRestID(adVO.getRestID());
		adVOnew.setContext(adVO.getContext());
		adVOnew.setImage(adVO.getImage());
		adVOnew.setStartDate(adVO.getStartDate());
		adVOnew.setEndDate(adVO.getEndDate());
		adVOnew.setTreatDetail(adVO.getTreatDetail());
		adVOnew.setTreatCaseVO(tcvonew);
		adVOnew.setRestaurantVO(adVO.getRestaurantVO());
		System.out.println(adVOnew.getTreatDetail());
		ADdao.insert(adVOnew);
		return adVOnew ;
	}
}
