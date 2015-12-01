package com.jspdev.biyesheji.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the teacher table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="teacher"
 */

public abstract class BaseTeacher  implements Serializable {

	public static String REF = "Teacher";
	public static String PROP_PASSWORD = "password";
	public static String PROP_SEX = "sex";
	public static String PROP_REMARK = "remark";
	public static String PROP_POST = "post";
	public static String PROP_NAME = "name";
	public static String PROP_DEPARTMENT = "department";
	public static String PROP_AGE = "age";
	public static String PROP_ID = "id";


	// constructors
	public BaseTeacher () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTeacher (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseTeacher (
		java.lang.String id,
		java.lang.String name,
		java.lang.String password,
		java.lang.String sex,
		java.lang.Long age,
		java.lang.String department,
		java.lang.String post) {

		this.setId(id);
		this.setName(name);
		this.setPassword(password);
		this.setSex(sex);
		this.setAge(age);
		this.setDepartment(department);
		this.setPost(post);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String name;
	private java.lang.String password;
	private java.lang.String sex;
	private java.lang.Long age;
	private java.lang.String department;
	private java.lang.String post;
	private java.lang.String remark;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
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
	 * Return the value associated with the column: password
	 */
	public java.lang.String getPassword () {
		return password;
	}

	/**
	 * Set the value related to the column: password
	 * @param password the password value
	 */
	public void setPassword (java.lang.String password) {
		this.password = password;
	}



	/**
	 * Return the value associated with the column: sex
	 */
	public java.lang.String getSex () {
		return sex;
	}

	/**
	 * Set the value related to the column: sex
	 * @param sex the sex value
	 */
	public void setSex (java.lang.String sex) {
		this.sex = sex;
	}



	/**
	 * Return the value associated with the column: age
	 */
	public java.lang.Long getAge () {
		return age;
	}

	/**
	 * Set the value related to the column: age
	 * @param age the age value
	 */
	public void setAge (java.lang.Long age) {
		this.age = age;
	}



	/**
	 * Return the value associated with the column: department
	 */
	public java.lang.String getDepartment () {
		return department;
	}

	/**
	 * Set the value related to the column: department
	 * @param department the department value
	 */
	public void setDepartment (java.lang.String department) {
		this.department = department;
	}



	/**
	 * Return the value associated with the column: post
	 */
	public java.lang.String getPost () {
		return post;
	}

	/**
	 * Set the value related to the column: post
	 * @param post the post value
	 */
	public void setPost (java.lang.String post) {
		this.post = post;
	}



	/**
	 * Return the value associated with the column: remark
	 */
	public java.lang.String getRemark () {
		return remark;
	}

	/**
	 * Set the value related to the column: remark
	 * @param remark the remark value
	 */
	public void setRemark (java.lang.String remark) {
		this.remark = remark;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jspdev.biyesheji.Teacher)) return false;
		else {
			com.jspdev.biyesheji.Teacher teacher = (com.jspdev.biyesheji.Teacher) obj;
			if (null == this.getId() || null == teacher.getId()) return false;
			else return (this.getId().equals(teacher.getId()));
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