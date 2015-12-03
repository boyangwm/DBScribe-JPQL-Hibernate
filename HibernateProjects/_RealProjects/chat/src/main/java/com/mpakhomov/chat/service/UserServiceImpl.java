package com.mpakhomov.chat.service;

import com.mpakhomov.chat.dao.UserDao;
import com.mpakhomov.chat.domain.User;
import com.mpakhomov.chat.util.OpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
@Transactional(propagation= Propagation.REQUIRED)
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    public OpResult addUser(User user) {
        if (log.isDebugEnabled()) {
            log.debug("addUser: adding new user = " + user);
        }
        return saveUser(user, true);
    }

    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public User getUser(Long userId) {
        if (log.isDebugEnabled()) {
            log.debug("getUser: fetching user id=" + userId);
        }
        try {
            return userDao.getUser(userId);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return null;
        }
    }

    public OpResult saveUser(User user) {
        return saveUser(user, false);
    }

    public OpResult saveUser(User user, boolean newUser) {
        if (log.isDebugEnabled()) {
            log.debug("saveUser: user = " + user);
        }
        if (user == null) {
            return new OpResult(OpResult.Status.FAILURE, "user == null");
        }

        try {
            User existingUser = userDao.findUserByNick(user.getNick());
            if ( existingUser == null ||
                 (newUser == false && existingUser.getId().equals(user.getId()))) {
                // either the nick is vacant or the user with he same id is updated
                user = userDao.save(user);

                if (log.isDebugEnabled()) {
                    log.debug("saveUser: successfully saved user id=" + user.getId());
                }
                return new OpResult(OpResult.Status.SUCCESS);

            } else {
                // attempt to create a new user with existing nick
                String message = String.format("saveUser: user nick = %s already exists",
                        user.getNick());
                if (log.isDebugEnabled()) {
                    log.debug(message);
                }
                return new OpResult(OpResult.Status.FAILURE, message);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new OpResult(OpResult.Status.FAILURE, ex.getMessage());
        }
    }

    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public List<User> getAllUsers() {
        if (log.isDebugEnabled()) {
            log.debug("getAllUsers: fetching all users.");
        }
        return userDao.getAllUsers();
    }

    public OpResult deleteUser(Long userId) {
        try {
            User user = userDao.getUser(userId);
            String message;
            if (user == null) {
                message = String.format("deleteUser: user id=" + userId + "doesn't exist");
                if (log.isDebugEnabled()) {
                    log.debug(message);
                }
                return new OpResult(OpResult.Status.FAILURE, message);
            }
            userDao.delete(userId);
            if (log.isDebugEnabled()) {
                log.debug("deleteUser: successfully deleted user " + user);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new OpResult(OpResult.Status.FAILURE, ex.getMessage());
        }
        return new OpResult(OpResult.Status.SUCCESS);
    }

    public User findUserByNick(String nick) {
        try {
            return userDao.findUserByNick(nick);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return null;
        }
    }
}
