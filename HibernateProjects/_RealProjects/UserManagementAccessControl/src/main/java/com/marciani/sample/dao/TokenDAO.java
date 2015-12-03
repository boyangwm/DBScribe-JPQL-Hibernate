package com.marciani.sample.dao;

import java.util.List;

import org.hibernate.criterion.Criterion;

import com.marciani.sample.security.model.Token;

public interface TokenDAO {
	public void createToken(Token token);
	public void updateToken(Token token);
    public void deleteToken(Token token);
    public List<Token> find(Criterion criterion);
}
