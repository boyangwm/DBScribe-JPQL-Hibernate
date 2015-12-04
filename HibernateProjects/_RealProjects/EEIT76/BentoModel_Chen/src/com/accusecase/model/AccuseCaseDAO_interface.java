package com.accusecase.model;

import java.util.*;

public interface AccuseCaseDAO_interface {
	public void insert(AccuseCaseVO accuseCaseVO);

	public void update(AccuseCaseVO accuseCaseVO);

	public void delete(Integer caseID);

	public AccuseCaseVO getByPrimaryKey(Integer caseID);

	public List<AccuseCaseVO> getAll();
}
