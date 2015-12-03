package com.jspdev.biyesheji.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the course table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="course"
 */

public abstract class BaseCourse  implements Serializable {

	public static String REF = "Course";
	public static String PROP_TYPE = "type";
	public static String PROP_TIME = "time";
	public static String PROP_TEACHER = "teacher";
	public static String PROP_ADDRESS = "address";
	public static String PROP_NAME = "name";
	public static String PROP_ID = "id";
	public static String PROP_CREDIT = "credit";


	// constructors
	public BaseCourse () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCourse (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCourse (
		java.lang.String id,
		java.lang.String name,
		java.lang.Long credit,
		java.lang.String teacher,
		java.lang.String time,
		java.lang.String address,
		java.lang.String type) {

		this.setId(id);
		this.setName(name);
		this.setCredit(credit);
		this.setTeacher(teacher);
		this.setTime(time);
		this.setAddress(address);
		this.setType(type);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String address;
	private java.lang.Long credit;
	private java.lang.String name;
	private java.lang.String teacher;
	private java.lang.String time;
	private java.lang.String type;

	// collections
	private java.util.Set<com.jspdev.biyesheji.Student> students;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="number"
     */
	public java.lang.String getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
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
	 * Return the value associated with the column: students
	 */
	public java.util.Set<com.jspdev.biyesheji.Student> getStudents () {
		return students;
	}

	/**
	 * Set the value related to the column: students
	 * @param students the students value
	 */
	public void setStudents (java.util.Set<com.jspdev.biyesheji.Student> students) {
		this.students = students;
	}

	public void addTostudents (com.jspdev.biyesheji.Student student) {
		if (null == getStudents()) setStudents(new java.util.TreeSet<com.jspdev.biyesheji.Student>());
		getStudents().add(student);
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jspdev.biyesheji.Course)) return false;
		else {
			com.jspdev.biyesheji.Course course = (com.jspdev.biyesheji.Course) obj;
			if (null == this.getId() || null == course.getId()) return false;
			else return (this.getId().equals(course.getId()));
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