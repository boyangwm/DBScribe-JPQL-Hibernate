package com.marciani.sample.entity.user.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {
	ADMINISTRATOR ("Administrator", new String[]{"ROLE_ADMINISTRATOR", "ROLE_BRAND_MANAGER", "ROLE_OPERATOR"}),
	BRAND_MANAGER ("Brand Manager", new String[] {"ROLE_BRAND_MANAGER"}),
	OPERATOR ("Operator", new String[] {"ROLE_OPERATOR"});
	
	private final String name;
	private final List<GrantedAuthority> grantedAuthorities;
	
	Role(String name, String[] authorities) {
		this.name = name;
		this.grantedAuthorities = getGrantedAuthorities(authorities);
	}

	public String getName() {
		return this.name;
	}

	public List<GrantedAuthority> getAuthorities() {
		return this.grantedAuthorities;
	}
	
	private final List<GrantedAuthority> getGrantedAuthorities(String[] authorities) {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		for(final String authority : authorities) {
			grantedAuthorities.add(new SimpleGrantedAuthority(authority));
		}
		return grantedAuthorities;
	}

}
