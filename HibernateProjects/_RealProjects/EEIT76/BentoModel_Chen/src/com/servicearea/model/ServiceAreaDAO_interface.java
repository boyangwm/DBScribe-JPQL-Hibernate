package com.servicearea.model;

import java.util.*;

public interface ServiceAreaDAO_interface {
          public void insert(ServiceAreaVO serviceAreaVO);
          public void update(ServiceAreaVO serviceAreaVO);
          public void delete(Integer serviceAreaID);
          public ServiceAreaVO getByPrimaryKey(Integer serviceAreaID);
          public List<ServiceAreaVO> getAll();
          public List<ServiceAreaVO> getByArea(String City, String Area);
          public List<ServiceAreaVO> getByRest(Integer restID);
}
