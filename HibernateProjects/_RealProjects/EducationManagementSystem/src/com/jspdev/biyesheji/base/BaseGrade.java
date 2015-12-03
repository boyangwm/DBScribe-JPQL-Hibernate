package com.jspdev.biyesheji.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the grade table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="grade"
 */

public abstract class BaseGrade  implements Serializable {

	public static String REF = "Grade";
	public static String PROP_GRADE = "grade";
	public static String PROP_YEAR = "year";
	public static String PROP_CHONG_XIU = "chongXiu";
	public static String PROP_ID = "id";
	public static String PROP_TERM = "term";


	// constructors
	public BaseGrade () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseGrade (com.jspdev.biyesheji.GradePK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseGrade (
		com.jspdev.biyesheji.GradePK id,
		java.lang.String year,
		java.lang.Long term,
		java.lang.Float grade,
		java.lang.String chongXiu) {

		this.setId(id);
		this.setYear(year);
		this.setTerm(term);
		this.setGrade(grade);
		this.setChongXiu(chongXiu);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.jspdev.biyesheji.GradePK id;

	// fields
	private java.lang.String chongXiu;
	private java.lang.Float grade;
	private java.lang.Long term;
	private java.lang.String year;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.jspdev.biyesheji.GradePK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.jspdev.biyesheji.GradePK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: chongXiu
	 */
	public java.lang.String getChongXiu () {
		return chongXiu;
	}

	/**
	 * Set the value related to the column: chongXiu
	 * @param chongXiu the chongXiu value
	 */
	public void setChongXiu (java.lang.String chongXiu) {
		this.chongXiu = chongXiu;
	}



	/**
	 * Return the value associated with the column: grade
	 */
	public java.lang.Float getGrade () {
		return grade;
	}

	/**
	 * Set the value related to the column: grade
	 * @param grade the grade value
	 */
	public void setGrade (java.lang.Float grade) {
		this.grade = grade;
	}



	/**
	 * Return the value associated with the column: term
	 */
	public java.lang.Long getTerm () {
		return term;
	}

	/**
	 * Set the value related to the column: term
	 * @param term the term value
	 */
	public void setTerm (java.lang.Long term) {
		this.term = term;
	}



	/**
	 * Return the value associated with the column: year
	 */
	public java.lang.String getYear () {
		return year;
	}

	/**
	 * Set the value related to the column: year
	 * @param year the year value
	 */
	public void setYear (java.lang.String year) {
		this.year = year;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jspdev.biyesheji.Grade)) return false;
		else {
			com.jspdev.biyesheji.Grade grade = (com.jspdev.biyesheji.Grade) obj;
			if (null == this.getId() || null == grade.getId()) return false;
			else return (this.getId().equals(grade.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}