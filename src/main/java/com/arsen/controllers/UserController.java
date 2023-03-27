package com.arsen.controllers;

import com.arsen.dto.UserDTO;
import com.arsen.mappers.UserMapper;
import com.arsen.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private UserMapper userMapper;
    private UserService userService;

    @PostMapping("/create")
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return userMapper.userToUserDTO(userService.saveUser(userMapper.userDTOtoUser(userDTO)));
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return userMapper.userToUserDTO(userService.getUserById(id));
    }

    @GetMapping("/all")
    public List<UserDTO> getAllUsers() {
        return userMapper.userListToUserDTOList(userService.getAllUsers());
    }

    @PutMapping("/update/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return userMapper.userToUserDTO(userService.updateUser(id, userMapper.userDTOtoUser(userDTO)));
    }

    @DeleteMapping("/delete/{id}")
    public Long deleteBook(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
