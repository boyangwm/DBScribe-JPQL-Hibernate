package com.marciani.sample.entity.role.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role implements Serializable {
	
	private static final long serialVersionUID = 3134327560295875500L;
	
	public static final Integer ROLE_ADMINISTRATOR = 1;
	public static final Integer ROLE_BRAND_MANAGER = 2;
	public static final Integer ROLE_OPERATOR = 3;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "role_id", nullable = false, unique = true)
	private Integer id;
	
	@Column(name = "role_name", nullable = false, unique = true, length = 45)
	private String roleName;
	
	public Role(String roleName) {
		this.roleName = roleName;
	}
	
	public Role() {}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer roleId) {
		this.id = roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	@Override
	public String toString() {
		return "Role[" + 
				this.id + " " + 
				this.roleName + "]";
	}	
	
	@Override
    public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		 
        Role role = (Role) o;
 
        return this.roleName.equals(role.roleName);
    }
 
    @Override
    public int hashCode() {
        return 13 * this.roleName.hashCode();
    }		

}
