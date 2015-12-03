package com.marciani.sample.entity.user.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "users_profile")
public class Profile implements Serializable {

	private static final long serialVersionUID = 3977944889257599040L;
	
	@Id		
	@GeneratedValue
	@Column(name = "profile_id", unique = true)
	@NotEmpty
	private Integer id;
	
	@Column(name = "firstname", unique = false)
	@Length(min = 2, max = 30)
	@NotEmpty
	private String firstname;
	
	@Column(name = "lastname", unique = false)
	@Length(min = 2, max = 30)
	@NotEmpty
	private String lastname;
	
	@Column(name = "email", unique = false)
	@Email
	@NotEmpty
	private String email;
	
	@Column(name = "birth", unique = false)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/mm/yyy")
	@Past
	@NotEmpty
	private Date birth;
	
	public Profile(String firstname, String lastname, String email, Date birth) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.birth = birth;
	}
	
	public Profile() {}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}
	
	@Override
	public String toString() {
		return "UserInfo["  + 
				this.id + " " +
				this.firstname + " " + 
				this.lastname + " " + 
				this.email + "]";
	}	
	
	@Override
    public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		 
        Profile info = (Profile) o;
 
        return this.id == info.id;
    }
 
    @Override
    public int hashCode() {
        return 13 * this.id.hashCode();
    }	

}
