package com.marciani.sample.entity.user.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users_profile")
public class UserProfile implements Serializable {

	private static final long serialVersionUID = 3977944889257599040L;
	
	@Id		
	@GeneratedValue
	@Column(name = "profile_id", nullable = false, unique = true)
	private Integer id;
	
	@Column(name = "firstname", nullable = false, unique = false, length = 25)
	private String firstname;
	
	@Column(name = "lastname", nullable = false, unique = false, length = 25)
	private String lastname;
	
	@Column(name = "email", nullable = false, unique = false, length = 50)
	private String email;
	
	public UserProfile(String firstname, String lastname, String email) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
	}
	
	public UserProfile() {}

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
		 
        UserProfile info = (UserProfile) o;
 
        return this.id == info.id;
    }
 
    @Override
    public int hashCode() {
        return 13 * this.id.hashCode();
    }	

}
