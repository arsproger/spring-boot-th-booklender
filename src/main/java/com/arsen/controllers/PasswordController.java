package com.arsen.controllers;

import com.arsen.models.User;
import com.arsen.services.EmailService;
import com.arsen.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PasswordController {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    public PasswordController(UserService userService, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @GetMapping("/forgotPassword")
    public String showForgotPasswordForm() {
        return "password/forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String processForgotPasswordForm(@RequestParam("email") String email, HttpServletRequest request) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return "password/forgotPassword";
        }

        // generate new password
        String newPassword = passwordEncoder.encode("qwerty1122only");

        // send email with password reset link
        String resetUrl = getResetUrl(request, user.getId(), newPassword);
        emailService.sendSimpleMessage(email,
                "Password reset",
                "Please click the link below to reset your password: " + resetUrl);
        return "password/forgotPasswordSuccess";
    }

    @GetMapping("/resetPassword")
    public String showResetPasswordForm(@RequestParam("id") Long id, @RequestParam("token") String token, Model model) {
        User user = userService.getUserById(id);
        if (user == null || !userService.isValidResetToken(token)) {
            return "redirect:/forgotPassword";
        }
        model.addAttribute("userId", id);
        model.addAttribute("token", token);
        return "password/resetPassword";
    }

    @PostMapping("/resetPassword")
    public String processResetPasswordForm(@RequestParam("userId") Long id,
                                           @RequestParam("token") String token,
                                           @RequestParam("password") String password) {
        User user = userService.getUserById(id);
        if (user == null || !userService.isValidResetToken(token)) {
            return "redirect:/forgotPassword";
        }
        user.setPassword(passwordEncoder.encode(password));
        userService.setResetToken(null);
        userService.saveUser(user);
        return "password/resetPasswordSuccess";
    }

    private String getResetUrl(HttpServletRequest request, Long id, String newPassword) {
        String contextPath = request.getContextPath();
        String resetUrl = request.getScheme()
                + "://" + request.getServerName()
                + ":" + request.getServerPort()
                + contextPath + "/resetPassword?id="
                + id + "&token="
                + newPassword;
        return resetUrl;
    }
}

