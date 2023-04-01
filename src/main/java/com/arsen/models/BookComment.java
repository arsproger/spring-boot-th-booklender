package com.arsen.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment_books")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private int grade;
    private String title;
    private String description;
    private LocalDateTime createdAt;

}
