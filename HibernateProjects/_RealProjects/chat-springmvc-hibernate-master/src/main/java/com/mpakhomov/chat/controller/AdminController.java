package com.mpakhomov.chat.controller;

import com.mpakhomov.chat.service.UserService;
import com.mpakhomov.chat.util.OpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mpakhomov.chat.domain.User;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Handles requests for the application admin page
 */
@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping(value = {"/admin", "/admin/index", "/admin/list", "/admin/users"}, method = RequestMethod.GET)
    public String index(Map<String, Object> model) {
        Logger log = LoggerFactory.getLogger(HomeController.class);

        //log.info("Creating a new user");
        //User newUser = new User("James", null, "Madison", "jmadison", "jmadison@us.gov", "111111");
        //userService.addUser(newUser);

        List<User> users = userService.getAllUsers();
        log.info("index: " + users);

        model.put("users", userService.getAllUsers());
        return "admin/listUsers";
    }

    @RequestMapping(value="/admin/users/{id}", method=RequestMethod.GET)
    public String viewUser(@PathVariable("id") long id,
                           Model model) {
        model.addAttribute(userService.getUser(id));
        return "admin/viewUser";
    }

    @RequestMapping(value="/admin/users/new", method=RequestMethod.GET)
    public String createUser(Model model) {
        model.addAttribute(new User());
        return "admin/editUser";
    }

    @RequestMapping(value="/admin/users/new", method=RequestMethod.POST)
    public String addUserFromForm(@Valid User user,
                                  BindingResult bindingResult) {
        return createOrUpdateUser(user, bindingResult, true);
    }

    @RequestMapping(value="/admin/users/edit/{id}", method=RequestMethod.POST)
    public String saveUserProfile(@Valid User user,
                                  BindingResult bindingResult) {
        return createOrUpdateUser(user, bindingResult, false);
    }

    private String createOrUpdateUser(User user,
                                      BindingResult bindingResult,
                                      Boolean create) {
        if(bindingResult.hasErrors()) {
            return "admin/editUser";
        }

        OpResult result;
        if (create) {
            result = userService.addUser(user);
        } else {
            result = userService.saveUser(user);
        }

        user = userService.findUserByNick(user.getNick());

        if (log.isDebugEnabled()) {
            log.debug("createOrUpdateUser: result = " + result);
        }
        if (result.getStatus() != OpResult.Status.SUCCESS) {
            bindingResult.reject("user.exists", result.getMessage());
            return "admin/editUser";
        }
        return "redirect:/admin/users/" + user.getId();
    }


    @RequestMapping(value="/admin/users/edit/{id}", method=RequestMethod.GET)
    public String editUserProfile(@PathVariable("id") long id,
                                  Model model) {
        model.addAttribute(userService.getUser(id));
        return "admin/editUser";
    }

    @RequestMapping(value="/admin/users/delete/{id}", method=RequestMethod.POST)
    public String deleteUser(@PathVariable("id") long id) {
        // Probably need to logout here, too...if the deleted user is the authenticated user
        if (log.isDebugEnabled()) {
            log.debug("deleteUser: id = " + id);
        }
        OpResult result = userService.deleteUser(id);

        log.debug("MP: deleteUser: result = " + result);
        return "redirect:/admin/users";
    }

}
