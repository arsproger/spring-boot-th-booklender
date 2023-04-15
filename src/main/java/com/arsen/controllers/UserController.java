package com.arsen.controllers;

import com.arsen.dto.UserDTO;
import com.arsen.enums.Role;
import com.arsen.mappers.UserMapper;
import com.arsen.models.User;
import com.arsen.security.DetailsUser;
import com.arsen.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        DetailsUser detailsUser = (DetailsUser) authentication.getPrincipal();
        return detailsUser.getUser();
    }

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/profile")
    public String myBooks(Model model) {
        model.addAttribute("user", userService.getUserById(getUser().getId()));
        model.addAttribute("isAdmin", getUser().getRole().equals(Role.ROLE_ADMIN));
        model.addAttribute("curBooks",
                userService.currentBooks(userService.getUserById(getUser().getId())));
        model.addAttribute("pastBooks",
                userService.pastBooks(userService.getUserById(getUser().getId())));

        return "/user/profile";
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(user.getImage());
    }

    @GetMapping("/update")
    public String updateForm(Model model) {
        model.addAttribute("id", getUser().getId());
        model.addAttribute("user", userMapper.convertToDTO(
                userService.getUserById(getUser().getId())));
        model.addAttribute("isAdmin", getUser().getRole().equals(Role.ROLE_ADMIN));
        return "/user/setting";
    }

    @PostMapping("/updated")
    public String update(@ModelAttribute("user") UserDTO userDTO) throws IOException {
        User user = new User();
        user.setFullName(userDTO.getFullName());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.setImage(userDTO.getImage().getBytes());

        userService.updateUser(getUser().getId(), user);
        return "redirect:/user/profile";
    }

}
