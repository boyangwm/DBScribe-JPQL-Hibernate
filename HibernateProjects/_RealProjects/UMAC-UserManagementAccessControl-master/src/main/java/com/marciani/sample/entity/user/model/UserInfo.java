package com.marciani.sample.entity.user.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "users_info")
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 3977944889257599040L;
	
	@Id		
	private Integer userId;
	
	@Column(name = "firstname", nullable = false, unique = false, length = 25)
	private String firstname;
	
	@Column(name = "lastname", nullable = false, unique = false, length = 25)
	private String lastname;
	
	@Column(name = "email", nullable = false, unique = false, length = 50)
	private String email;
	
	//@OneToOne(optional=false, fetch=FetchType.EAGER)
	@OneToOne
	@MapsId
	private User user;
	
	public UserInfo(String firstname, String lastname, String email) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
	}
	
	public UserInfo() {}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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
		return "UserInfo[" + 
				this.userId + " " + 
				this.firstname + " " + 
				this.lastname + " " + 
				this.email + "]";
	}	
	
	@Override
    public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		 
        UserInfo info = (UserInfo) o;
 
        return this.userId == info.userId;
    }
 
    @Override
    public int hashCode() {
        return 13 * this.userId.hashCode();
    }

}
