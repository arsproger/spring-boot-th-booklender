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

import javax.validation.constraints.Min;
import java.io.IOException;

@Controller
@RequestMapping("/userTh")
public class UserControllerTh {
    private final UserService userService;
    private final UserMapper userMapper;

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        DetailsUser detailsUser = (DetailsUser) authentication.getPrincipal();
        return detailsUser.getUser();
    }

    @Autowired
    public UserControllerTh(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public String main(Model model,
                       @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset) {
        model.addAttribute("isNext",
                (userService.getAllUsers().size() / (3D * (offset.doubleValue() + 1D))) > 1D);
        model.addAttribute("offset", offset);
        model.addAttribute("isAdmin", getUser().getRole().equals(Role.ROLE_ADMIN));
        model.addAttribute("users", userService.findAll(offset));
        return "/user/users";
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

    @GetMapping("/create")
    public String createUSer(@ModelAttribute("user") UserDTO userDTO) {
        return "/user/newUser";
    }

    @PostMapping("/new")
    public String addUser(@ModelAttribute("user") UserDTO userDTO) throws IOException {
        User user = new User();
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
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
