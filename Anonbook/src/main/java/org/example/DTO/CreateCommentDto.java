package org.example.DTO;

import lombok.Data;

@Data
public class CreateCommentDto {
    private Long postId;
    private String content;
}
