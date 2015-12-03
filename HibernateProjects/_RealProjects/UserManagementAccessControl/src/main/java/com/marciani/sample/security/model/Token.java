package com.marciani.sample.security.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

@Entity
@Table(name = "persistent_logins")
public class Token {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "series", nullable = false, length = 64)
	private String series;
	
	@Column(name = "username", nullable = false, length = 64)
	private String username;
	
	@Column(name = "token", nullable = false, length = 64)
    private String tokenValue;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_used", nullable = false)
    private Date lastUsed;    
 
    public Token(PersistentRememberMeToken persistentRememberMeToken) {
        this.setUsername(persistentRememberMeToken.getUsername());
        this.setSeries(persistentRememberMeToken.getSeries());
        this.setLastUsed(persistentRememberMeToken.getDate());
        this.setTokenValue(persistentRememberMeToken.getTokenValue());
    }
    
    public Token() {}

	public String getSeries() {
		return this.series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTokenValue() {
		return this.tokenValue;
	}

	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}

	public Date getLastUsed() {
		return this.lastUsed;
	}

	public void setLastUsed(Date lastUsed) {
		this.lastUsed = lastUsed;
	}

}
