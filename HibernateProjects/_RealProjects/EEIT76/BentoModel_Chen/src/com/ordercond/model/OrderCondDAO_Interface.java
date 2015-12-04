package com.ordercond.model;

import java.util.*;

public interface OrderCondDAO_Interface {
    public void insert(OrderCondVO orderCondVO);
    public void update(OrderCondVO orderCondVO);
    public void delete(Integer OrderCondID);
    public OrderCondVO getByPrimaryKey(Integer OrderCondID);
    public List<OrderCondVO> getAll();

}
