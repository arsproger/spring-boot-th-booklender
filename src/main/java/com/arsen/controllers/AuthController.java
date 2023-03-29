package com.arsen.controllers;

import com.arsen.dto.AuthDTO;
import com.arsen.dto.UserDTO;
import com.arsen.mappers.UserMapper;
import com.arsen.services.CustomUserDetailsService;
import com.arsen.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService detailsService;

    public AuthController(UserService userService, UserMapper userMapper, AuthenticationManager authenticationManager, CustomUserDetailsService detailsService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.detailsService = detailsService;
    }


    @GetMapping("/register")
    public void register(@ModelAttribute UserDTO userDTO) {
        userService.saveUser(userMapper.convertToEntity(userDTO));
    }

    @GetMapping("/login")
    public void login(@ModelAttribute AuthDTO authDTO) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword())
            );

            detailsService.loadUserByUsername(authDTO.getUsername());
        } catch (UsernameNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }
}
