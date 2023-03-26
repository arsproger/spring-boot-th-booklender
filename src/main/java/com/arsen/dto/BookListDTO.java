package com.arsen.dto;

import com.arsen.enums.BookStatus;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class BookListDTO {
    private String name;

    @Enumerated(EnumType.STRING)
    private BookStatus status;
}
