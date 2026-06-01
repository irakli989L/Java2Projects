package org.example.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {
    private Long id;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;
}
