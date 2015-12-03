package com.marciani.sample.security.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marciani.sample.dao.TokenDAO;
import com.marciani.sample.security.model.Token;

@Repository
public class TokenDAOImpl implements TokenDAO {
	
	@Autowired
	public SessionFactory sessionFactory;
	
	public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

	@Override
	@Transactional(readOnly = false)
	public void createToken(Token token) {
		getCurrentSession().save(token);		
	}
	
	@Override
	@Transactional(readOnly = false)
	public void updateToken(Token token) {
		getCurrentSession().update(token);		
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteToken(Token token) {
		getCurrentSession().delete(token);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Token> find(Criterion criterion) {
		Criteria criteria = getCurrentSession().createCriteria(Token.class);
		criteria.add(criterion);
		return criteria.list();
	}

}
