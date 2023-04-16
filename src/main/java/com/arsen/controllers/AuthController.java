package com.arsen.controllers;

import com.arsen.dto.UserDTO;
import com.arsen.mappers.UserMapper;
import com.arsen.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public AuthController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/main")
    public String mainPage() {
        return "/main";
    }

    @GetMapping("/contact")
    public String contactPage(Model model) {
        model.addAttribute("isSend", false);
        return "/contact";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "/login";
    }

    @GetMapping("/register")
    public String registrationPage(@ModelAttribute("user") UserDTO userDTO) {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") UserDTO userDTO) {
        userService.saveUser(userMapper.convertToEntity(userDTO));
        return "redirect:/auth/login";
    }

    @PostMapping("/support")
    public String processContactForm(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String message,
            Model model) {
        System.out.println(name + "\n" + email + "\n" + message);
        model.addAttribute("isSend", true);
        return "/contact";
    }

}
