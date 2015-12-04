package com.dish.model;

import java.util.*;

public interface DishDAO_interface {
          public void insert(DishVO dishVO);
          public void update(DishVO dishVO);
          public void delete(Integer dishID);
          public DishVO getByPrimaryKey(Integer dishID);
          public List<DishVO> getAll();
          public List<DishVO> getAllByRestID(Integer RestID);
}
