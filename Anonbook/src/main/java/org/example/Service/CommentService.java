package org.example.Service;
import org.example.DTO.CommentDto;
import org.example.DTO.CreateCommentDto;

import java.util.List;

public interface CommentService {
    void create(CreateCommentDto dto);

    List<CommentDto> getByPost(Long postId);
}
