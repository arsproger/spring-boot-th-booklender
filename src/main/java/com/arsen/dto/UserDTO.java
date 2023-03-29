package com.arsen.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
public class UserDTO {
    @Column(name = "full_name")
    private String fullName;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateOfBirth;

    private String email;

    private String password;


}
