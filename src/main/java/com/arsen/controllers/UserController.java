package com.arsen.controllers;

import com.arsen.dto.UserDTO;
import com.arsen.mappers.UserMapper;
import com.arsen.models.User;
import com.arsen.services.RecordService;
import com.arsen.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    UserMapper userMapper;
    UserService userService;
    RecordService recordService;

    @PostMapping("/create")
    public UserDTO createUser(@RequestBody UserDTO userDTO){
        User user = userMapper.userDTOtoUser(userDTO);
        userService.saveUser(user);
        return userMapper.userToUserDTO(user);
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id){
        User user = userService.getUserById(id);
        return userMapper.userToUserDTO(user);
    }

    @GetMapping("/all")
    public List<UserDTO> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return userMapper.userListToUserDTOList(users);
    }

    @PutMapping("/update/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO){
        User user = userMapper.userDTOtoUser(userDTO);
        User updatedBook = userService.updateUser(id, user);
        return userMapper.userToUserDTO(updatedBook);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBook(@PathVariable Long id){
        userService.deleteUser(id);
    }
}
