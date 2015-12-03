package com.marciani.sample.security.service;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import com.marciani.sample.dao.TokenDAO;
import com.marciani.sample.security.model.Token;

@Component("tokenService")
public class TokenService implements PersistentTokenRepository {
	
	@Autowired
	private TokenDAO tokenDAO;
	
	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		Token domainToken = new Token(token);
		tokenDAO.createToken(domainToken);		
	}
	
	@Override
	public void updateToken(String series, String tokenValue, Date lastUsed) {
		Criterion criterion = Restrictions.eq("series", series);
		List<Token> list = tokenDAO.find(criterion);
		Token domainToken = list.get(0);
		domainToken.setSeries(series);
		domainToken.setTokenValue(tokenValue);
		domainToken.setLastUsed(lastUsed);
		tokenDAO.updateToken(domainToken);
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String series) {
		Criterion criterion = Restrictions.eq("series", series);
		List<Token> list = tokenDAO.find(criterion);
		if (list.size() > 0) {
			Token domainToken = list.get(0);
			return new PersistentRememberMeToken(
					domainToken.getUsername(),
					domainToken.getSeries(), 
					domainToken.getTokenValue(), 
					domainToken.getLastUsed());
		} else return null;
	}

	@Override
	public void removeUserTokens(String username) {
		Criterion criterion = Restrictions.eq("username", username);
		List<Token> list = tokenDAO.find(criterion);
		if (list.size() > 0) {
			Token token = list.get(0);
			tokenDAO.deleteToken(token);
		}		
	}		

}
