/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.yourcompany.struts.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/** 
 * MyEclipse Struts
 * Creation date: 04-21-2008
 * 
 * XDoclet definition:
 * @struts.form name="stuInfoForm"
 */
public class StuInfoForm extends ActionForm {
	/*
	 * Generated fields
	 */

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** phone property */
	private String phone;

	/** sex property */
	private String sex;

	/** college property */
	private String college;

	/** age property */
	private String age;

	/** address property */
	private String address;

	/** outTime property */
	private String outTime;

	/** name property */
	private String name;

	/** inTime property */
	private String inTime;

	/** department property */
	private String department;

	/** number property */
	private String number;

	/*
	 * Generated Methods
	 */

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// TODO Auto-generated method stub
	}

	/** 
	 * Returns the phone.
	 * @return String
	 */
	public String getPhone() {
		return phone;
	}

	/** 
	 * Set the phone.
	 * @param phone The phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/** 
	 * Returns the sex.
	 * @return String
	 */
	public String getSex() {
		return sex;
	}

	/** 
	 * Set the sex.
	 * @param sex The sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/** 
	 * Returns the college.
	 * @return String
	 */
	public String getCollege() {
		return college;
	}

	/** 
	 * Set the college.
	 * @param college The college to set
	 */
	public void setCollege(String college) {
		this.college = college;
	}

	/** 
	 * Returns the age.
	 * @return String
	 */
	public String getAge() {
		return age;
	}

	/** 
	 * Set the age.
	 * @param age The age to set
	 */
	public void setAge(String age) {
		this.age = age;
	}

	/** 
	 * Returns the address.
	 * @return String
	 */
	public String getAddress() {
		return address;
	}

	/** 
	 * Set the address.
	 * @param address The address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/** 
	 * Returns the outTime.
	 * @return String
	 */
	public String getOutTime() {
		return outTime;
	}

	/** 
	 * Set the outTime.
	 * @param outTime The outTime to set
	 */
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}

	/** 
	 * Returns the name.
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/** 
	 * Set the name.
	 * @param name The name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/** 
	 * Returns the inTime.
	 * @return String
	 */
	public String getInTime() {
		return inTime;
	}

	/** 
	 * Set the inTime.
	 * @param inTime The inTime to set
	 */
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	/** 
	 * Returns the department.
	 * @return String
	 */
	public String getDepartment() {
		return department;
	}

	/** 
	 * Set the department.
	 * @param department The department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/** 
	 * Returns the number.
	 * @return String
	 */
	public String getNumber() {
		return number;
	}

	/** 
	 * Set the number.
	 * @param number The number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}
}