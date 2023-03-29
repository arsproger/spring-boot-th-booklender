package com.arsen.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BookDTO {
    private Long id;

    private String name;

    private String author;

    private String description;

//    @Lob
    private MultipartFile image;

}
