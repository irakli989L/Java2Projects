package org.example.Mapper;

import org.example.DTO.PostDto;
import org.example.Entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public PostDto toDto(Post post) {

        PostDto dto = new PostDto();

        dto.setId(post.getId());
        dto.setContent(post.getContent());

        if(post.getImageName() != null) {
            dto.setImageUrl("/images/" + post.getImageName());
        }

        dto.setCreatedAt(post.getCreatedAt());

        return dto;
    }
}
