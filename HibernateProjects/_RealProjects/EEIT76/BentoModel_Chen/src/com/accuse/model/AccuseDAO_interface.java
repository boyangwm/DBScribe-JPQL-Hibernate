package com.accuse.model;

import java.util.*;

public interface AccuseDAO_interface {
          public void insert(AccuseVO accuseVO);
          public void update(AccuseVO accuseVO);
          public void delete(Integer accuseID);
          public AccuseVO getByPrimaryKey(Integer accuseID);
          public List<AccuseVO> getAll();
          public List<AccuseVO> getByCaseID(Integer CaseID);
          public List<AccuseVO> getByRestDiscussID(Integer restDiscussID);
          public List<AccuseVO> getByOrderSumID(Integer orderSumID);
          public List<AccuseVO> getByRestID(Integer RestID);
}
