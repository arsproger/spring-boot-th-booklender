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
    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;

    private String email;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<Book> currentBooks; // текущие книги

    @OneToMany(mappedBy = "user")
    private List<Book> pastBooks; // книги которые он брал ранее

    @OneToMany(mappedBy = "user")
    private List<Record> records; // записи книг которые он брал
}
