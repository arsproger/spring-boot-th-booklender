package com.arsen.controllers;

import com.arsen.dto.BookDTO;
import com.arsen.models.Book;
import com.arsen.models.User;
import com.arsen.repositories.UserRepository;
import com.arsen.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @GetMapping("/{id}")
//    public User findById(@PathVariable Long id) {
//         return userService.getUserById(id);
//    }

    @GetMapping("/email/{email}")
    public Optional<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user;
    }
    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    @PostMapping("/save")
    void saveUser(@RequestBody User user){
        userService.saveUser(user);

    }

    @GetMapping("/{userId}")
    public List<BookDTO> getBooksByUserId(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        List<Book> books = user.getCurrentBooks();
        List<BookDTO> bookDTOs = new ArrayList<>();

        for (Book book : books) {
            bookDTOs.add(new BookDTO(book.getName(),book.getAuthor()));
        }
        return bookDTOs;
    }
}
