package com.arsen.controllers;

import com.arsen.dto.UserDTO;
import com.arsen.mappers.UserMapper;
import com.arsen.services.EmailService;
import com.arsen.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final String adminMail;

    @Autowired
    public AuthController(UserService userService, UserMapper userMapper, EmailService emailService, @Value("${spring.mail.admin}") String adminMail) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.emailService = emailService;
        this.adminMail = adminMail;
    }

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/contact")
    public String contactPage(Model model) {
        model.addAttribute("isSend", false);
        return "contact";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("isPresentEmail", false);
        return "login";
    }

    @GetMapping("/register")
    public String registrationPage(@ModelAttribute("user") UserDTO userDTO) {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") UserDTO userDTO, Model model) {
        if (userService.isPresentEmail(userDTO.getEmail())) {
            model.addAttribute("isPresentEmail", true);
            return "register";
        }

        userService.saveUser(userMapper.convertToEntity(userDTO));
        return "redirect:/auth/login";
    }

    @PostMapping("/support")
    public String processContactForm(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String message,
            Model model) {

        emailService.sendSimpleMessage(adminMail, "От пользователя: " + name,
                "\nПочта: " + email + "\n" + message);
        model.addAttribute("isSend", true);
        return "contact";
    }

    @GetMapping("/reset")
    public String resetPassword(Model model) {
        model.addAttribute("isPresent", true);
        return "forgot";
    }

    @GetMapping("/reset/form/{resetToken}")
    public String resetPasswordForm(@PathVariable("resetToken") String resetToken, Model model) {
        model.addAttribute("resetToken", resetToken);
        model.addAttribute("result", true);
        return "forgot-form";
    }

}
