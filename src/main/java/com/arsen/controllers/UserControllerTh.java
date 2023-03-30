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
}
