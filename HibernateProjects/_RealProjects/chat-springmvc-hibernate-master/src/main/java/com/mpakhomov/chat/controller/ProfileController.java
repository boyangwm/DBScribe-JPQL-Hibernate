package com.mpakhomov.chat.controller;

import com.mpakhomov.chat.service.UserService;
import com.mpakhomov.chat.util.OpResult;
import com.mpakhomov.chat.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mpakhomov.chat.domain.User;
import org.springframework.web.bind.annotation.RequestMethod;
import com.mpakhomov.chat.util.ForbiddenException;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping(value="/profile/view/{id}", method=RequestMethod.GET)
    public String viewProfile(@PathVariable("id") long id,
                           ModelMap model) {
        // I DO know that it's possible to view profiles
        // of other users. That's fine for the toy application.
        // Otherwise I should write more code to handle
        // a use case when the user changed their nick.
        // Probably, I should force logout in that case

//        User requestedUser = userService.getUser(id);
//        User currentUser = getCurrentUser();
//        log.info("currentUser = " + getCurrentUser());
//        log.info("requestedUser = "  + requestedUser);
//        if (!currentUser.getId().equals(requestedUser.getId())) {
//            throw new ForbiddenException();
//        }
        User user = userService.getUser(id);
        if (user == null) {
            throw new ResourceNotFoundException();
        }
        //model.addAttribute(user);
        model.put("user", user);
        model.put("page", "profile");
        return "profile/viewProfile";
    }

    @RequestMapping(value="/profile/new", method=RequestMethod.GET)
    public String showRegistrationForm(Map<String, Object> model) {
        //model.addAttribute(new User());
        model.put("user", new User());
        model.put("page", "register");
        return "profile/createProfile";
    }

    @RequestMapping(value="/profile/new", method=RequestMethod.POST)
    public String addUserFromForm(@Valid User user,
                                  BindingResult bindingResult) {
        return createOrUpdateUser(user, bindingResult, true);
    }

    @RequestMapping(value="/profile/edit/{id}", method=RequestMethod.POST)
    public String saveUserProfile(@Valid User user,
                                  BindingResult bindingResult) {
        return createOrUpdateUser(user, bindingResult, false);
    }

    @RequestMapping(value="/profile/edit/{id}", method=RequestMethod.GET)
    public String editUserProfile(@PathVariable("id") long id,
                                  ModelMap model) {
        User requestedUser = userService.getUser(id);
        if (requestedUser == null) {
            throw new ResourceNotFoundException();
        }
        User currentUser = getCurrentUser();
        if (!currentUser.getId().equals(requestedUser.getId())) {
            throw new ForbiddenException();
        }
        //model.addAttribute(requestedUser);
        model.put("user", requestedUser);
        model.put("page", "profile");
        return "profile/editProfile";
    }

    private String createOrUpdateUser(User user,
                                      BindingResult bindingResult,
                                      Boolean create) {
        String viewName = create ? "profile/createProfile" : "profile/editProfile";

        if(bindingResult.hasErrors()) {
            return viewName;
        }

        OpResult result;
        Boolean changedNickName  = false;
        if (create) {
            result = userService.addUser(user);
        } else {
            User existingUser = userService.getUser(user.getId());
            existingUser.setNick(user.getNick());
            existingUser.setPassword(user.getPassword());
            result = userService.saveUser(existingUser);
        }

        user = userService.findUserByNick(user.getNick());

        if (log.isDebugEnabled()) {
            log.debug("createOrUpdateUser: result = " + result);
        }
        if (result.getStatus() != OpResult.Status.SUCCESS) {
            bindingResult.reject("user.exists", result.getMessage());
            return viewName;
        }
        if (create) {
            return "redirect:/login";
        } else {
            return "redirect:/profile/view/" + user.getId();
        }
    }

    private User getCurrentUser() {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findUserByNick(currentUser);
    }
}
