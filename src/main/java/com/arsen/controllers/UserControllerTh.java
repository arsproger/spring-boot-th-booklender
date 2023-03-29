package com.arsen.controllers;

import com.arsen.models.User;
import com.arsen.services.BookService;
import com.arsen.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    public String getAll(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "/user/all";
    }

    @GetMapping("/mybooks/{id}")
    public String myBooks(@PathVariable Long id, Model model) {

        User user = userService.getUserById(id);
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("curBooks", bookService.findByUser(user));

        return "/user/myBooks";
    }
}
