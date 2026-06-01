package org.example.Mapper;

import org.example.DTO.CommentDto;
import org.example.Entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public CommentDto toDto(Comment comment) {

        CommentDto dto = new CommentDto();

        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());

        return dto;
    }
}
