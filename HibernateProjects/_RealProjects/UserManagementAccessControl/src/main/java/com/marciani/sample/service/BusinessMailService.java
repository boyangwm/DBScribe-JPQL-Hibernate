package com.marciani.sample.service;


import org.springframework.security.access.prepost.PreAuthorize;

import com.marciani.sample.entity.user.model.User;
import com.marciani.sample.exception.MailException;

public interface BusinessMailService {
	
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
	public void sendRegistrationMail(User user) throws MailException;
	
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
	public void sendRemovalMail(User user) throws MailException;
	
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
	public void sendPasswordRecoveryMail(User user) throws MailException;
	
	@PreAuthorize("isAuthenticated()")
	public void sendEditedProfileMail(User user) throws MailException;

}
