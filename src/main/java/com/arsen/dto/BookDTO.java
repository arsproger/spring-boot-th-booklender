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

    private String description;

    @Lob
    private byte[] image;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

}
