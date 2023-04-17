package com.arsen.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
public class UserDTO {
    @Column(name = "full_name") // это же дтоЮ зачем здесь эта аннотация ?
    private String fullName;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateOfBirth;

    private String email;

    private String password;
    private MultipartFile image;


}
