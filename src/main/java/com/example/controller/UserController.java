package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ModelAndView find() {

        List<User> users = userService.find();

        ModelAndView view = new ModelAndView();
        view.addObject("users", users);
        view.setViewName("users");

        return view;
    }

    @GetMapping("/users/{id}")
    public ModelAndView find(@PathVariable("id") long id) {

        User user = userService.find(id);

        ModelAndView view = new ModelAndView();
        view.addObject("user", user);
        view.setViewName("user");

        return view;
    }

    @GetMapping("/users/create")
    public ModelAndView create() {

        ModelAndView view = new ModelAndView();
        view.addObject("user", new User());
        view.setViewName("user-create");

        return view;
    }

    @PostMapping("/users")
    public ModelAndView create(@Valid @ModelAttribute("user") User user,

                               BindingResult bindingResult) {
        ModelAndView view = new ModelAndView();

        if (bindingResult.hasErrors()) {
            view.addObject("user", user);
            view.setViewName("user-create");
        } else {

            userService.save(user);
            view.setViewName("redirect:/users");
        }

        return view;
    }

    @GetMapping("/users/{id}/update")
    public ModelAndView update(@PathVariable("id") long id) {

        User user = userService.find(id);

        ModelAndView view = new ModelAndView();
        view.addObject("user", user);
        view.setViewName("user-update");

        return view;
    }

    @PutMapping("/users/{id}")
    public ModelAndView update(@PathVariable("id") long id,
                               @Valid @ModelAttribute("user") User user,
                               BindingResult bindingResult) {

        ModelAndView view = new ModelAndView();
        User updatedUser = userService.find(id);

        if (updatedUser != null) {

            if (bindingResult.hasErrors()) {
                view.addObject("user", user);
                view.setViewName("user-update");
            } else {
                updatedUser.setName(user.getName());
                updatedUser.setAge(user.getAge());
                userService.update(user);
                view.setViewName("redirect:/users/" + id);
            }
        }

        return view;
    }

    @DeleteMapping("/users/{id}")
    public ModelAndView delete(@PathVariable("id") long id) {

        ModelAndView view = new ModelAndView();
        userService.delete(id);
        view.setViewName("redirect:/users");

        return view;
    }
}