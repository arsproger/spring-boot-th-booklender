package com.arsen.controllers;

import com.arsen.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserControllerTh {
    private final UserService userService;

    public UserControllerTh(UserService userService) {
        this.userService = userService;
    }

    public String getAll(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "/user/all";
    }
}
