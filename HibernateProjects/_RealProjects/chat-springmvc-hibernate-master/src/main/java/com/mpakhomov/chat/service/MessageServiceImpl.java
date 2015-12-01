package com.mpakhomov.chat.service;

import com.mpakhomov.chat.dao.MessageDao;
import com.mpakhomov.chat.domain.ChatMessage;
import org.joda.time.DateTime;
import org.joda.time.DurationFieldType;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service("messageService")
@Transactional(propagation= Propagation.SUPPORTS)
public class MessageServiceImpl implements MessageService{

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private MessageDao messageDao;

    public ChatMessage getMessage(Long messageId) {
        return messageDao.getMessage(messageId);
    }

    public List<ChatMessage> getRecentMessages(int limit) {
        List<ChatMessage> list = messageDao.getRecentMessages(limit);
        Collections.reverse(list);
        return list;
    }

    public void addMessage(ChatMessage message) {
        messageDao.addMessage(message);
    }

    public StringBuilder fetchChatHistory(int limit) {
        StringBuilder chatHistory = new StringBuilder();
        List<ChatMessage> messages = getRecentMessages(limit);

        for (ChatMessage m : messages ) {
            // TODO
            String added = getTimeDiff(m.getTimestamp());
            chatHistory.append(
                    String.format("%s <b>%s</b>: %s<br />",
                            added,
                            m.getAuthor().getNick(),
                            m.getMessage())
            );
        }
        return chatHistory;
    }

    private String getTimeDiff(Date startTime) {
        DateTime now = new DateTime();
        //log.debug("now = " + now);
        //log.debug("startDate = " + new DateTime(startTime) );
        PeriodType type = PeriodType.forFields(new DurationFieldType[] {
                DurationFieldType.minutes(),
                DurationFieldType.hours(),
                DurationFieldType.days(),
                DurationFieldType.months(),
                DurationFieldType.years()
        });

        Period period = new Period(new DateTime(startTime), now, type);
        return String.format("%d hours %d days %d months %d years ago",
                period.getHours(), period.getDays(), period.getMonths(), period.getYears());
    }
}
