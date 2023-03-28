package com.arsen.mappers;

import com.arsen.dto.UserDTO;
import com.arsen.models.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userToUserDTO(User user);
    User userDTOtoUser(UserDTO userDTO);
    List<UserDTO> userListToUserDTOList(List<User> userList);
}
