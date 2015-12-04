package com.restaurant.model;
import com.restkind.model.*;
import com.servicearea.model.*;

import java.sql.Time;
import java.util.List;

public class RestaurantService {

	
		private RestaurantDAO_interface restdao;
		private ServiceAreaDAO_interface servareadao;
		private RestKindDAO_interface restkinddao;
		
		public RestaurantService() {
			restdao = new RestaurantHibernateDAO();
			servareadao = new ServiceAreaHibernateDAO();
			restkinddao = new RestKindDAO();
		}
		
		public RestaurantVO addRestaurant(//Integer RestID,
										  String RestName,
										  String RestPhone,
										  String RestCel,
										  String RestCity,
										  String RestArea,
										  String RestAddr,
										  Double RestLatitude,
										  Double RestLongitude,
										  Time LastOrder_midday,
										  Time LastOrder_night,
										  String OwnAcc) {

			RestaurantVO restaurantVO = new RestaurantVO();

			restaurantVO.setRestName(RestName);
			restaurantVO.setRestPhone(RestPhone);
			restaurantVO.setRestCel(RestCel);
			restaurantVO.setRestCity(RestCity);
			restaurantVO.setRestArea(RestArea);
			restaurantVO.setRestAddr(RestAddr);
			restaurantVO.setRestLatitude(RestLatitude);
			restaurantVO.setRestLongitude(RestLongitude);
			restaurantVO.setLastOrder_midday(LastOrder_midday);
			restaurantVO.setLastOrder_night(LastOrder_night);
			restaurantVO.setOwnAcc(OwnAcc);
			restdao.insert(restaurantVO);

			return restaurantVO;
		}
	
		public RestaurantVO updateRestaurant(Integer RestID,String RestName,String RestPhone,String RestCel,
				  String RestCity,String RestArea,String RestAddr,Double RestLatitude,
				  Double RestLongitude,Time LastOrder_midday,Time LastOrder_night,String OwnAcc) 
			{
			RestaurantVO restaurantVO = new RestaurantVO();
			
			restaurantVO.setRestID(RestID);
			restaurantVO.setRestName(RestName);
			restaurantVO.setRestPhone(RestPhone);
			restaurantVO.setRestCel(RestCel);
			restaurantVO.setRestCity(RestCity);
			restaurantVO.setRestArea(RestArea);
			restaurantVO.setRestAddr(RestAddr);
			restaurantVO.setRestLatitude(RestLatitude);
			restaurantVO.setRestLongitude(RestLongitude);
			restaurantVO.setLastOrder_midday(LastOrder_midday);
			restaurantVO.setLastOrder_night(LastOrder_night);
			restaurantVO.setOwnAcc(OwnAcc);
			restdao.insert(restaurantVO);	
			
			return restaurantVO;
			}
			
			public void deleteRestaurant(Integer RestID) {
			restdao.delete(RestID);
			}
			
			public RestaurantVO getOneRestaurant(Integer RestID) {
			return restdao.getByPrimaryKey(RestID);
			}
			public String getOneRestaurantName(Integer RestID) {
				RestaurantVO restaurantVO = restdao.getByPrimaryKey(RestID);
				String RestName = restaurantVO.getRestName();
				return RestName ;
			}
			public ServiceAreaVO addServiceArea(Integer RestID, String City, String Area) {
					ServiceAreaVO serviceAreaVO = new ServiceAreaVO();
		
					serviceAreaVO.setRestID(RestID);
					serviceAreaVO.setCity(City);
					serviceAreaVO.setArea(Area);
	
					servareadao.insert(serviceAreaVO);
		
					return serviceAreaVO;
			}
			
			public void deleteServiceArea(Integer serviceAreaID) {
				servareadao.delete(serviceAreaID);			
			}
			
			public RestKindVO addRestKind(Integer RestID, Integer KindID) {
				RestKindVO restKindVO = new RestKindVO();

				restKindVO.setRestID(RestID);
				restKindVO.setKindID(KindID);
				
				restkinddao.insert(restKindVO);

				return restKindVO;
		}
			public void deleteRestKind(Integer KindID, Integer RestID) {
				restkinddao.delete(KindID,RestID);			
			}
			
			public List<ServiceAreaVO> getByArea(String City , String Area){
				return servareadao.getByArea(City,Area);
			}
			
			public List<RestKindVO> getByKind(Integer KindID){
				return restkinddao.getByKind(KindID);
			}
			
}
