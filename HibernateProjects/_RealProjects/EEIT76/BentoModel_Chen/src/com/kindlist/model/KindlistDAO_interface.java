package com.kindlist.model;

import java.util.List;



public interface KindlistDAO_interface {
    public void insert(KindlistVO kindlistVO);
    public void update(KindlistVO kindlistVO);
    public void delete(Integer KindID);
    public KindlistVO getByPrimaryKey(Integer KindID);
    public List<KindlistVO> getAll();

}
