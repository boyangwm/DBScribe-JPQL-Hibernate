package com.restaurant.model;

import java.util.*;

public interface RestaurantDAO_interface {
          public void insert(RestaurantVO restaurantVO);
          public void update(RestaurantVO restaurantVO);
          public void delete(Integer RestID);
          public List<RestaurantVO> getAll();
		  public RestaurantVO getByPrimaryKey(Integer restID);
		  public List<RestaurantVO> getByCity(String RestCity);
		  public List<RestaurantVO> getByArea(String RestArea);
}
