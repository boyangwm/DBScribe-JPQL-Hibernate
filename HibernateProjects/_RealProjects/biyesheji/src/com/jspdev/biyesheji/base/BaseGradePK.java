package com.jspdev.biyesheji.base;

import java.io.Serializable;


public abstract class BaseGradePK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.jspdev.biyesheji.Student snumber;
	private com.jspdev.biyesheji.Course cnumber;


	public BaseGradePK () {}
	
	public BaseGradePK (
		com.jspdev.biyesheji.Student snumber,
		com.jspdev.biyesheji.Course cnumber) {

		this.setSnumber(snumber);
		this.setCnumber(cnumber);
	}


	/**
	 * Return the value associated with the column: snumber
	 */
	public com.jspdev.biyesheji.Student getSnumber () {
		return snumber;
	}

	/**
	 * Set the value related to the column: snumber
	 * @param snumber the snumber value
	 */
	public void setSnumber (com.jspdev.biyesheji.Student snumber) {
		this.snumber = snumber;
	}



	/**
	 * Return the value associated with the column: cnumber
	 */
	public com.jspdev.biyesheji.Course getCnumber () {
		return cnumber;
	}

	/**
	 * Set the value related to the column: cnumber
	 * @param cnumber the cnumber value
	 */
	public void setCnumber (com.jspdev.biyesheji.Course cnumber) {
		this.cnumber = cnumber;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jspdev.biyesheji.GradePK)) return false;
		else {
			com.jspdev.biyesheji.GradePK mObj = (com.jspdev.biyesheji.GradePK) obj;
			if (null != this.getSnumber() && null != mObj.getSnumber()) {
				if (!this.getSnumber().equals(mObj.getSnumber())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getCnumber() && null != mObj.getCnumber()) {
				if (!this.getCnumber().equals(mObj.getCnumber())) {
					return false;
				}
			}
			else {
				return false;
			}
			return true;
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			StringBuilder sb = new StringBuilder();
			if (null != this.getSnumber()) {
				sb.append(this.getSnumber().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getCnumber()) {
				sb.append(this.getCnumber().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			this.hashCode = sb.toString().hashCode();
		}
		return this.hashCode;
	}


}