package com.arsen.dto;

import com.arsen.enums.BookStatus;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;

@Data
public class BookDTO {
    private Long id;

    private String name;

    private String author;

    private String description;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    @Lob
    private byte[] image;

}
