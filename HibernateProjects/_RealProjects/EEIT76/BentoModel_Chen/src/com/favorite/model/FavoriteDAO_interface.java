package com.favorite.model;

import java.util.*;

public interface FavoriteDAO_interface {
	public void insert(FavoriteVO favoriteVO);

	public void update(FavoriteVO favoriteVO);

	public void delete(FavoriteVO FavoriteVO);

	public List<FavoriteVO> getByMemberAcc(String memberAcc);
	
	public List<FavoriteVO> getByRestID(Integer restID);

	public List<FavoriteVO> getAll();
}
