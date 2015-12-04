package com.restkind.model;

import java.util.List;



public interface RestKindDAO_interface {
	public void insert(RestKindVO restkindVO);
    public void update(RestKindVO restkindVO);
    public void delete(Integer KindID,Integer RestID);
    public RestKindVO getByPrimaryKey(Integer KindID,Integer RestID);
    public List<RestKindVO> getAll();
    public List<RestKindVO> getByKind(Integer KindID);
    public List<RestKindVO> getByRest(Integer restID);

}
