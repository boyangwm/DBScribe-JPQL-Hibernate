package com.restdiscuss.model;

import java.util.*;

public interface RestDiscussDAO_interface {
	public void insert(RestDiscussVO restDiscussVO);

	public void update(RestDiscussVO restDiscussVO);

	public void delete(Integer restDiscussID);

	public RestDiscussVO getByPrimaryKey(Integer restDiscussID);

	public List<RestDiscussVO> getAll();
	
	public List<RestDiscussVO> getByRestID(Integer RestID);
}
