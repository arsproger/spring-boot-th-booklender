package com.arsen.controllers;

import com.arsen.models.User;
import com.arsen.repositories.UserRepository;
import com.arsen.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

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
//    @GetMapping("/{userId}/books")
//    public List<BookDto> getBookByUserId(@PathVariable Long userId){
//        return userService.getBookByUserId(userId);
//    }
}
