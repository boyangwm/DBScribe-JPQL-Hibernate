package com.mpakhomov.chat.controller;

import com.mpakhomov.chat.domain.ChatMessage;
import com.mpakhomov.chat.service.MessageService;
import com.mpakhomov.chat.service.UserService;
import com.mpakhomov.chat.util.OpResult;
import com.mpakhomov.chat.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mpakhomov.chat.domain.User;
import com.mpakhomov.chat.dao.UserDao;
import com.mpakhomov.chat.util.ForbiddenException;

import javax.validation.Valid;
import java.security.Principal;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.util.*;

/**
 * Handles requests for the application home page.
 */
@Controller
@Scope("session")
public class HomeController {

    private int messagesPerPage = 50;

    private void setMessagePerPage(int limit) {
        this.messagesPerPage = limit;
    }

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = {"/"}, method=RequestMethod.GET)
    public String index(Map<String, Object> model) {
        log.info("messagesPerPage = " + messagesPerPage);
        StringBuilder chatHistory = messageService.fetchChatHistory(messagesPerPage);
        User user = getCurrentUser();

        model.put("page", "home");
        model.put("authenticated", user != null ? true : false);
        model.put("user", user);
        model.put("chatHistory", chatHistory.toString());
        // FIXME: remove
        if (userService.findUserByNick("gwashington") == null) {
            createTestData();
        }
        return "home";
    }

    @RequestMapping(value = {"home"}, method=RequestMethod.GET)
    public String home(Map<String, Object> model) {
        // I had problems on redhat's openshift with mapping to the root URI [/]
        return "redirect:/";
    }


    @RequestMapping(value = "/loadChatHistoryAJAX.json", method=RequestMethod.GET)
    public @ResponseBody OpResult loadChatHistory() {
        log.info("messagesPerPage = " + messagesPerPage);
        StringBuilder chatHistory = messageService.fetchChatHistory(messagesPerPage);
        OpResult result = new OpResult(OpResult.Status.SUCCESS, "", chatHistory.toString());
        return result;
    }

    @RequestMapping(value = "/postMessageAJAX.json", method=RequestMethod.POST)
    @Secured("ROLE_USER")
    public @ResponseBody OpResult postChatMessage(@RequestParam(value="text") String text) {
        User user = getCurrentUser();
        if (user == null) {
            throw new ForbiddenException();
        }
        ChatMessage chatMessage = new ChatMessage(text, user);
        messageService.addMessage(chatMessage);
        return new OpResult(OpResult.Status.SUCCESS);
    }

    @RequestMapping(value="/setMessagesPerPagePropAJAX.json",method=RequestMethod.POST)
    public @ResponseBody String setMessagesPerPageProp(@RequestParam(value = "limit", required = false) int limit) {
        log.info("Setting messagesPerPage to " + limit);
        setMessagePerPage(limit);
        return new OpResult(OpResult.Status.SUCCESS).toString();
    }

    @RequestMapping(value="/register", method=RequestMethod.GET)
    public String register(Model model) {
        User user = getCurrentUser();
        model.addAttribute(new User());
        return "redirect:/profile/new";
    }

    private void createTestData() {
        User[] users =  {
            new  User("George", null, "Washington", "gwashington", "gwashington@us.gov", "12345"),
            new  User("John", null, "Adams", "jadams", "jadams@us.gov", "12345"),
            new  User("Thomas", null, "Jefferson", "tjefferson", "tjefferson@us.gov", "12345"),
            new  User("James", null, "Madison", "jmadison", "jmadison@us.gov", "12345"),
            new  User("James", null, "Monroe", "jmonroe", "jmonroe@us.gov", "12345"),
            new  User("John", "Quincy", "Adams", "jqadams", "jmonroe@us.gov", "12345"),
            new  User("Andrew", null, "Jackson", "ajackson", "ajackson@us.gov", "12345"),
        };
        List<User> usersInDb = new ArrayList<User>();
        for (User user : users) {
            userService.addUser(user);
            usersInDb.add(userService.findUserByNick(user.getNick()));
        }
        String[] messages = {
            "Lorem ipsum dolor sit amet",
            "consectetur adipiscing elit",
            "Quisque sit amet dui turpis",
            "id auctor lorem. Aliquam adipiscing tempus",
            "Phasellus a nisi a justo pellentesque",
            "Sed venenatis commodo vulputate",
            "In dapibus justo sit amet est tincidunt",
            "Nam imperdiet adipiscing lorem"
        };
        Random random = new Random();
        for (int i = 0; i < 101; i++) {
            int usersSize = users.length;
            int messagesSize = messages.length;
            ChatMessage chatMessage = new ChatMessage(messages[random.nextInt(messagesSize)],
                usersInDb.get(random.nextInt(usersSize)));
            messageService.addMessage(chatMessage);
        }
    }

    private User getCurrentUser() {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findUserByNick(currentUser);
    }
}
