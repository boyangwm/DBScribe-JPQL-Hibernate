package com.owner.model;

import java.util.List;

public interface OwnerDAO_interface {
    public void insert(OwnerVO ownerVO);
    public void update(OwnerVO ownerVO);
    public void delete(String OwnAcc);
    public OwnerVO getByPrimaryKey(String OwnAcc);
    public List<OwnerVO> getAll();
    public List<Object> getAllOwnAcc();
    
}
