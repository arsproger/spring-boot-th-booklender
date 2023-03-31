package com.arsen.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private int grade;
    private String title;
    private String description;
    private LocalDateTime createdAt;

}
