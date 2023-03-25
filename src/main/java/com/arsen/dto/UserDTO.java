package com.arsen.dto;

import com.arsen.models.Book;
import com.arsen.models.Record;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDTO {
    @Column(name = "full_name")
    private String fullName;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateOfBirth;

    private String email;

    private String password;


}
