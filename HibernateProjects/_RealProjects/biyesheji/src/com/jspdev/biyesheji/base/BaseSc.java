package com.jspdev.biyesheji.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the sc table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="sc"
 */

public abstract class BaseSc  implements Serializable {

	public static String REF = "Sc";
	public static String PROP_TYPE = "type";
	public static String PROP_TIME = "time";
	public static String PROP_TEACHER = "teacher";
	public static String PROP_ADDRESS = "address";
	public static String PROP_SNO = "sno";
	public static String PROP_NAME = "name";
	public static String PROP_SELECTED = "selected";
	public static String PROP_ID = "id";
	public static String PROP_CREDIT = "credit";
	public static String PROP_CNO = "cno";


	// constructors
	public BaseSc () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseSc (java.lang.Long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseSc (
		java.lang.Long id,
		java.lang.String sno,
		java.lang.String cno,
		java.lang.String name,
		java.lang.Long credit,
		java.lang.String teacher,
		java.lang.String time,
		java.lang.String address,
		java.lang.String type,
		java.lang.String selected) {

		this.setId(id);
		this.setSno(sno);
		this.setCno(cno);
		this.setName(name);
		this.setCredit(credit);
		this.setTeacher(teacher);
		this.setTime(time);
		this.setAddress(address);
		this.setType(type);
		this.setSelected(selected);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Long id;

	// fields
	private java.lang.String sno;
	private java.lang.String cno;
	private java.lang.String name;
	private java.lang.Long credit;
	private java.lang.String teacher;
	private java.lang.String time;
	private java.lang.String address;
	private java.lang.String type;
	private java.lang.String selected;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="id"
     */
	public java.lang.Long getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.Long id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: sno
	 */
	public java.lang.String getSno () {
		return sno;
	}

	/**
	 * Set the value related to the column: sno
	 * @param sno the sno value
	 */
	public void setSno (java.lang.String sno) {
		this.sno = sno;
	}



	/**
	 * Return the value associated with the column: cno
	 */
	public java.lang.String getCno () {
		return cno;
	}

	/**
	 * Set the value related to the column: cno
	 * @param cno the cno value
	 */
	public void setCno (java.lang.String cno) {
		this.cno = cno;
	}



	/**
	 * Return the value associated with the column: name
	 */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: name
	 * @param name the name value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}



	/**
	 * Return the value associated with the column: credit
	 */
	public java.lang.Long getCredit () {
		return credit;
	}

	/**
	 * Set the value related to the column: credit
	 * @param credit the credit value
	 */
	public void setCredit (java.lang.Long credit) {
		this.credit = credit;
	}



	/**
	 * Return the value associated with the column: teacher
	 */
	public java.lang.String getTeacher () {
		return teacher;
	}

	/**
	 * Set the value related to the column: teacher
	 * @param teacher the teacher value
	 */
	public void setTeacher (java.lang.String teacher) {
		this.teacher = teacher;
	}



	/**
	 * Return the value associated with the column: time
	 */
	public java.lang.String getTime () {
		return time;
	}

	/**
	 * Set the value related to the column: time
	 * @param time the time value
	 */
	public void setTime (java.lang.String time) {
		this.time = time;
	}



	/**
	 * Return the value associated with the column: address
	 */
	public java.lang.String getAddress () {
		return address;
	}

	/**
	 * Set the value related to the column: address
	 * @param address the address value
	 */
	public void setAddress (java.lang.String address) {
		this.address = address;
	}



	/**
	 * Return the value associated with the column: type
	 */
	public java.lang.String getType () {
		return type;
	}

	/**
	 * Set the value related to the column: type
	 * @param type the type value
	 */
	public void setType (java.lang.String type) {
		this.type = type;
	}



	/**
	 * Return the value associated with the column: selected
	 */
	public java.lang.String getSelected () {
		return selected;
	}

	/**
	 * Set the value related to the column: selected
	 * @param selected the selected value
	 */
	public void setSelected (java.lang.String selected) {
		this.selected = selected;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jspdev.biyesheji.Sc)) return false;
		else {
			com.jspdev.biyesheji.Sc sc = (com.jspdev.biyesheji.Sc) obj;
			if (null == this.getId() || null == sc.getId()) return false;
			else return (this.getId().equals(sc.getId()));
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