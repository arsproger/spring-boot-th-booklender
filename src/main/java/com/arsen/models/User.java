package com.arsen.models;

import com.arsen.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;

    @Column(unique = true)
    private String email;

    private String password;

    @Lob
    private byte[] image;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Book> currentBooks; // текущие книги
// хорошие описания к полям сущности, за это плюс
    @OneToMany(mappedBy = "user")
    private List<Book> pastBooks; // книги которые он брал ранее
// хотелось и к другим сущностям описание увидеть в таком виде, как внизу

    /**
        Записи книг которые он брал
    */
    @OneToMany(mappedBy = "user")
    private List<Record> records;
}

