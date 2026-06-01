package org.example.Service;

import org.example.DTO.CreatePostDto;
import org.example.DTO.PostDto;

import java.util.List;

public interface PostService {
    void create(CreatePostDto dto);

    List<PostDto> getAll();

    PostDto getById(Long id);
}
