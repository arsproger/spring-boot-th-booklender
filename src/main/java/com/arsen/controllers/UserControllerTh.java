package com.arsen.controllers;

import com.arsen.dto.UserDTO;
import com.arsen.models.User;
import com.arsen.services.BookService;
import com.arsen.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/userTh")
public class UserControllerTh {
    private final UserService userService;
    private final BookService bookService;

    @Autowired
    public UserControllerTh(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }

    //    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "/user/all";
    }

    @GetMapping("/books/{id}")
    public String myBooks(@PathVariable Long id, Model model) {
        model.addAttribute("curBooks",
                userService.currentBooks(userService.getUserById(id)));
        model.addAttribute("pastBooks",
                userService.pastBooks(userService.getUserById(id)));

//        userService.getUserById(id).getCurrentBooks();
//        userService.getUserById(id).getPastBooks();

//        model.addAttribute("curBooks", userService.cur(userService.getUserById(id)));
//        model.addAttribute("pastBooks", userService.cur(userService.getUserById(id)));
        return "/user/myBooks";
    }

    @GetMapping("/create")
    public String createUSer(@ModelAttribute("user") UserDTO userDTO) {
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
//        user.setImage(userDTO.get().getBytes());
        userService.saveUser(user);

        return "redirect:/userTh";
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(user.getImage());
    }
}
