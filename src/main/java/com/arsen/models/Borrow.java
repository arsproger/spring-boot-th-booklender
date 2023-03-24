package com.arsen.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "borrows")
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime borrowedAt;
    
    private LocalDateTime returnedAt;
    
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    // constructors, getters and setters
}
