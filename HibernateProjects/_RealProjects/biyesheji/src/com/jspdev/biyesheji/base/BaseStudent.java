package com.jspdev.biyesheji.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the student table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="student"
 */

public abstract class BaseStudent  implements Serializable {

	public static String REF = "Student";
	public static String PROP_PASSWORD = "password";
	public static String PROP_SEX = "sex";
	public static String PROP_COLLEGE = "college";
	public static String PROP_OUT_TIME = "outTime";
	public static String PROP_ADDRESS = "address";
	public static String PROP_IN_TIME = "inTime";
	public static String PROP_NAME = "name";
	public static String PROP_DEPARTMENT = "department";
	public static String PROP_AGE = "age";
	public static String PROP_ID = "id";
	public static String PROP_PHONE = "phone";


	// constructors
	public BaseStudent () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseStudent (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseStudent (
		java.lang.String id,
		java.lang.String name,
		java.lang.String sex,
		java.lang.Long age,
		java.lang.String department,
		java.lang.String address,
		java.lang.String phone,
		java.lang.String inTime,
		java.lang.String outTime,
		java.lang.String password,
		java.lang.String college) {

		this.setId(id);
		this.setName(name);
		this.setSex(sex);
		this.setAge(age);
		this.setDepartment(department);
		this.setAddress(address);
		this.setPhone(phone);
		this.setInTime(inTime);
		this.setOutTime(outTime);
		this.setPassword(password);
		this.setCollege(college);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String name;
	private java.lang.String sex;
	private java.lang.Long age;
	private java.lang.String department;
	private java.lang.String address;
	private java.lang.String phone;
	private java.lang.String inTime;
	private java.lang.String outTime;
	private java.lang.String password;
	private java.lang.String college;

	// collections
	private java.util.Set<com.jspdev.biyesheji.Course> courses;



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
	 * Return the value associated with the column: phone
	 */
	public java.lang.String getPhone () {
		return phone;
	}

	/**
	 * Set the value related to the column: phone
	 * @param phone the phone value
	 */
	public void setPhone (java.lang.String phone) {
		this.phone = phone;
	}



	/**
	 * Return the value associated with the column: inTime
	 */
	public java.lang.String getInTime () {
		return inTime;
	}

	/**
	 * Set the value related to the column: inTime
	 * @param inTime the inTime value
	 */
	public void setInTime (java.lang.String inTime) {
		this.inTime = inTime;
	}



	/**
	 * Return the value associated with the column: outTime
	 */
	public java.lang.String getOutTime () {
		return outTime;
	}

	/**
	 * Set the value related to the column: outTime
	 * @param outTime the outTime value
	 */
	public void setOutTime (java.lang.String outTime) {
		this.outTime = outTime;
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
	 * Return the value associated with the column: college
	 */
	public java.lang.String getCollege () {
		return college;
	}

	/**
	 * Set the value related to the column: college
	 * @param college the college value
	 */
	public void setCollege (java.lang.String college) {
		this.college = college;
	}



	/**
	 * Return the value associated with the column: courses
	 */
	public java.util.Set<com.jspdev.biyesheji.Course> getCourses () {
		return courses;
	}

	/**
	 * Set the value related to the column: courses
	 * @param courses the courses value
	 */
	public void setCourses (java.util.Set<com.jspdev.biyesheji.Course> courses) {
		this.courses = courses;
	}

	public void addTocourses (com.jspdev.biyesheji.Course course) {
		if (null == getCourses()) setCourses(new java.util.TreeSet<com.jspdev.biyesheji.Course>());
		getCourses().add(course);
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jspdev.biyesheji.Student)) return false;
		else {
			com.jspdev.biyesheji.Student student = (com.jspdev.biyesheji.Student) obj;
			if (null == this.getId() || null == student.getId()) return false;
			else return (this.getId().equals(student.getId()));
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