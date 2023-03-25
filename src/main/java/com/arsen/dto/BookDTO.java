package com.arsen.dto;

import com.arsen.enums.BookStatus;
import com.arsen.models.Record;
import com.arsen.models.User;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
public class BookDTO {
    private String name;

    private String author;

    @Lob
    private byte[] image;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "book")
    private List<Record> records;
}
