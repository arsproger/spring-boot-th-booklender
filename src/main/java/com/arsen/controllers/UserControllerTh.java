package com.arsen.controllers;

import com.arsen.dto.UserDTO;
import com.arsen.mappers.UserMapper;
import com.arsen.models.User;
import com.arsen.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;

@Controller
@RequestMapping("/userTh")
@EnableWebMvc
public class UserControllerTh {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserControllerTh(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public String getAll(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "/user/all";
    }
    @GetMapping("/create")
    public String createUSer(@ModelAttribute("user")UserDTO userDTO){
        return "/user/newUser";
    }

    @GetMapping
    public String main(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "/user/users";
    }
    @PostMapping("/new")
    public String addUser(@ModelAttribute("user") UserDTO userDTO) throws IOException {
        User user = new User();
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setImage(userDTO.getImage().getBytes());
        userService.saveUser(user);

        return "redirect:/userTh";
    }
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(user.getImage());
    }
}
