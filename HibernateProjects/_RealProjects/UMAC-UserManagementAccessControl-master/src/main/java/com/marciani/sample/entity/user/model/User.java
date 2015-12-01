package com.marciani.sample.entity.user.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = -7351729135012380019L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id", unique = true)
	@NotEmpty
	private Integer id;
	
	@Column(name = "username", unique = true)
	@Length(min = 3, max = 20)
	@NotEmpty
	private String username;
	
	@Column(name = "password", unique = false)
	@Length(min = 3, max = 20)
	@NotEmpty
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private Role role;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "profile_id", nullable = false)
	@ForeignKey(name = "fk_user_profile")
	private Profile profile;
	
	public User(String username, String password, Role role, Profile profile) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.profile = profile;
	}
	
	public User(String username, String password, Role role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
	public User() {}
	
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
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
	
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public Profile getProfile() {
		return this.profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}	
	
	@Override
	public String toString() {
		return "User[" + 
				this.id + " " + 
				this.username + " " + 
				this.password + " " + 
				this.role.getName() + " " + 
				this.profile.getFirstname() + " " + 
				this.profile.getLastname() + " " + 
				this.profile.getEmail() + "]";
	}
	
	@Override
    public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		 
        User user = (User) o;
 
        return this.username.equals(user.username);
    }
 
    @Override
    public int hashCode() {
        return 13 * this.username.hashCode();
    }	

}
