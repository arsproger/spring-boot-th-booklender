package com.arsen.mappers;

import com.arsen.dto.UserDTO;
import com.arsen.models.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userToUserDTO(User user);
    User userDTOtoUser(UserDTO userDTO);
    List<UserDTO> userListToUserDTOList(List<User> userList);
}