package com.mpakhomov.chat.dao;

import com.mpakhomov.chat.domain.ChatMessage;

import java.util.List;

public interface MessageDao {
    public ChatMessage getMessage(Long messageId);
    public void addMessage(ChatMessage message);
    public List<ChatMessage> getRecentMessages(int limit);
}
