package com.arsen.dto;

import lombok.Data;

import javax.persistence.Lob;

@Data
public class BookDTO {
    private String name;

    private String author;

    private String description;

    @Lob
    private byte[] image;

}
