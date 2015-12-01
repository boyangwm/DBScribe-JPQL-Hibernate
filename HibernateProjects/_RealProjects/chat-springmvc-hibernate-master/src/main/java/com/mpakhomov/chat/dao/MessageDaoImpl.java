package com.mpakhomov.chat.dao;

import com.mpakhomov.chat.domain.ChatMessage;
import com.mpakhomov.chat.domain.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static java.lang.Math.*;

import java.util.List;

@Repository("messageDao")
public class MessageDaoImpl implements MessageDao {

    private static final Logger log = LoggerFactory.getLogger(MessageDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly=true)
    public ChatMessage getMessage(Long messageId) {
        ChatMessage message = (ChatMessage) sessionFactory.getCurrentSession().get(ChatMessage.class, messageId);
        if (log.isDebugEnabled()) {
            log.debug ("getMessage: " + message);
        }
        return message;
    }

    @Transactional(readOnly = false)
    public void addMessage(ChatMessage message) {
        sessionFactory.getCurrentSession().save(message);
    }

    @Transactional(readOnly=true)
    public List<ChatMessage> getRecentMessages(int limit) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("fetchRecentMessages");
        if (limit > 0) {
            query.setMaxResults(limit);
            List<ChatMessage> list = query.list();
            return list.subList(0,
                    min(limit, list.size()));
        } else {
            List<ChatMessage> list = query.list();
            return list;
        }
    }
}
