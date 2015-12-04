package com.treatcase.model;

import java.io.Serializable;

public class TreatCaseVO implements Serializable {
	private Integer TreatID;
	private String TreatRelation;
	public Integer getTreatID() {
		return TreatID;
	}
	public void setTreatID(Integer treatID) {
		TreatID = treatID;
	}
	public String getTreatRelation() {
		return TreatRelation;
	}
	public void setTreatRelation(String treatRelation) {
		TreatRelation = treatRelation;
	}
	
}
