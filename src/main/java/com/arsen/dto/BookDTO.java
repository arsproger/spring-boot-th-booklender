package com.arsen.dto;

import com.arsen.enums.BookStatus;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;

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
