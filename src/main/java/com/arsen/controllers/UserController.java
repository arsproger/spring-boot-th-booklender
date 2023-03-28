package com.arsen.controllers;

import com.arsen.dto.UserDTO;
import com.arsen.mappers.UserMapper;
import com.arsen.models.User;
import com.arsen.services.RecordService;
import com.arsen.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    UserMapper userMapper;
    UserService userService;
    RecordService recordService;

    @PostMapping("/create")
    public UserDTO createUser(@RequestBody UserDTO userDTO){
        User user = userMapper.convertToEntity(userDTO);
        userService.saveUser(user);
        return userMapper.convertToDTO(user);
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id){
        User user = userService.getUserById(id);
        return userMapper.convertToDTO(user);
    }

    @GetMapping("/all")
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers().stream().map(
                userMapper::convertToDTO).collect(Collectors.toList());
    }

    @PutMapping("/update/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO){
        User user = userMapper.convertToEntity(userDTO);
        User updatedBook = userService.updateUser(id, user);
        return userMapper.convertToDTO(updatedBook);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBook(@PathVariable Long id){
        userService.deleteUser(id);
    }
}
