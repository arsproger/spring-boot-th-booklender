package com.arsen.controllers;

import com.arsen.dto.UserDTO;
import com.arsen.mappers.UserMapper;
import com.arsen.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

//    @GetMapping("/login")
//    public void login(@ModelAttribute AuthDTO authDTO) {
//
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword())
//            );
//
//            detailsService.loadUserByUsername(authDTO.getUsername());
//        } catch (UsernameNotFoundException e) {
//            System.out.println(e.getMessage());
//        }
//        return "redirect"
//    }
    // Опять же мертвый код ?
}
