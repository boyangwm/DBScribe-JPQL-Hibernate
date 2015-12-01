package com.marciani.sample.util.form;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.marciani.sample.entity.user.model.Role;
import com.marciani.sample.entity.user.model.User;
import com.marciani.sample.entity.user.model.Profile;

@Component("userForm")
public class UserForm {
	
	@Length(min = 2, max = 30)
	@NotEmpty
	private String firstname;
	
	@Length(min = 2, max = 30)
	@NotEmpty
	private String lastname;
	
	@Email
	@NotEmpty
	private String email;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/mm/yyy")
	@Past
	@NotEmpty
	private Date birth;
	
	@Length(min = 3, max = 20)
	@NotEmpty
	private String username;
	
	@Length(min = 3, max = 20)
	@NotEmpty
	private String password;
	
	private String role;
	
	public User toUser() {
		String username = this.username;
		String password = this.password;
		String firstname = this.firstname;
		String lastname = this.lastname;
		String email = this.email;
		Date birth = this.birth;
		Role role = Role.valueOf(this.role);
		Profile profile = new Profile(firstname, lastname, email, birth);
		User user = new User(username, password, role, profile);
		return user;
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
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRole() {
		return this.role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}	
	
	@Override
	public String toString() {
		return "UserForm[" + 
				this.getFirstname() + " " + 
				this.getLastname() + " " + 
				this.getEmail() + " " + 
				this.getUsername() + " " +
				this.getPassword() + " " + 
				this.getRole() + "]";
	}

}
