package org.example.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreatePostDto {
    private String content;
    private MultipartFile image;
}
