package com.mpakhomov.chat.service;

import com.mpakhomov.chat.domain.User;
import com.mpakhomov.chat.util.OpResult;

import java.util.List;

public interface UserService {
    public OpResult addUser(User user);
    public User getUser(Long userId);
    public OpResult saveUser(User user);
    public OpResult saveUser(User user, boolean newUser);
    public List<User> getAllUsers();
    public OpResult deleteUser(Long userId);
    public User findUserByNick(String nick);
}