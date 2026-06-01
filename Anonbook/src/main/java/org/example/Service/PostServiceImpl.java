package org.example.Service;

import lombok.RequiredArgsConstructor;
import org.example.DTO.CreatePostDto;
import org.example.DTO.PostDto;
import org.example.Entity.Post;
import org.example.Mapper.PostMapper;
import org.example.Repository.PostRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository repository;
    private final PostMapper mapper;
    private final ImageService imageService;

    @Override
    public void create(CreatePostDto dto) {
        Post post = new Post();

        post.setContent(dto.getContent());
        post.setCreatedAt(LocalDateTime.now());

        try {
            if(dto.getImage() != null && !dto.getImage().isEmpty()) {

                String imageName = imageService.save(dto.getImage());

                post.setImageName(imageName);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        repository.save(post);
    }

    @Override
    public List<PostDto> getAll() {
        return repository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public PostDto getById(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow());
    }
}
