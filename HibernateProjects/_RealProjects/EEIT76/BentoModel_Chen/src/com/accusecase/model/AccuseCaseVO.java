package com.accusecase.model;

import java.io.Serializable;

public class AccuseCaseVO implements Serializable {
	private Integer CaseID;
	private String CaseRelation;
	public Integer getCaseID() {
		return CaseID;
	}
	public void setCaseID(Integer caseID) {
		CaseID = caseID;
	}
	public String getCaseRelation() {
		return CaseRelation;
	}
	public void setCaseRelation(String caseRelation) {
		CaseRelation = caseRelation;
	}
}
