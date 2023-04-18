package com.arsen.controllers;

import com.arsen.dto.UserDTO;
import com.arsen.mappers.UserMapper;
import com.arsen.services.EmailService;
import com.arsen.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final EmailService emailService;

    @Autowired
    public AuthController(UserService userService, UserMapper userMapper, EmailService emailService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.emailService = emailService;
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
        return "redirect:/auth/login"; // TODO validate!
    }

    @PostMapping("/support")
    public String processContactForm(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String message,
            Model model) {
        System.out.println(name + "\n" + email + "\n" + message); // TODO
        model.addAttribute("isSend", true);
        return "/contact";
    }

    @PostMapping("/feedback")
    public String sendFeedbackEmail(@RequestParam("name") String name,
                                    @RequestParam("email") String email,
                                    @RequestParam("message") String message) {

        emailService.sendFeedback(name, email, message);

        return "redirect:/auth/contact?isSend=true";
    }


    @GetMapping("/reset")
    public String resetPassword() {
        return "forgot";
    }

    @GetMapping("/reset/form/{resetToken}")
    public String resetPasswordForm(@PathVariable("resetToken") String resetToken, Model model) {
        model.addAttribute("resetToken", resetToken);
        return "forgot-form";
    }

}
