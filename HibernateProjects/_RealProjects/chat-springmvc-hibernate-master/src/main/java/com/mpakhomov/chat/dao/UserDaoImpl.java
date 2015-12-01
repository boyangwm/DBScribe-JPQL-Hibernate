package com.mpakhomov.chat.dao;

import com.mpakhomov.chat.domain.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

    private static final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly=true)
    public User getUser(Long userId) {
        User user = (User) sessionFactory.getCurrentSession().get(User.class, userId);
        if (log.isDebugEnabled()) {
            log.debug ("getUser: " + user);
        }
        return user;
    }

    @Transactional
    public User save(User user) {
        if (log.isDebugEnabled()) {
            log.debug ("save: " + user);
        }
        // trim middle name
        if (user.getMiddleName() != null) {
            user.setMiddleName(user.getMiddleName().trim());
        }
        user = (User) sessionFactory.getCurrentSession().merge(user);
        sessionFactory.getCurrentSession().saveOrUpdate(user);
        return user;
    }

    @Transactional(readOnly=true)
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        List<User> userList = sessionFactory.getCurrentSession().createQuery("from User")
                .list();
        if (log.isDebugEnabled()) {
            log.debug ("getAllUsers: " + userList);
        }
        return userList;
    }

    @Transactional
    public void delete(Long userId) {
        User user = getUser(userId);
        if (log.isDebugEnabled()) {
            log.debug ("delete: userId=" + userId + " user=" + user);
        }
        if (user != null) {
            sessionFactory.getCurrentSession().delete(user);
        }
    }

    @Transactional(readOnly=true)
    public User findUserByNick(String nick) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("findUserByNick");
        query.setString("nick", nick);
        User user = null;
        List list = query.list();
        if (list.size() > 0) {
            user = (User) list.get(0);
        }
        if (log.isDebugEnabled()) {
            log.debug("findUserByNick: result user = " + user);
        }
        return user;
    }
}
