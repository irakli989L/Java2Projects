package org.example.Service;

import lombok.RequiredArgsConstructor;
import org.example.DTO.CommentDto;
import org.example.DTO.CreateCommentDto;
import org.example.Entity.Comment;
import org.example.Entity.Post;
import org.example.Mapper.CommentMapper;
import org.example.Repository.CommentRepository;
import org.example.Repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repository;
    private final PostRepository postRepository;
    private final CommentMapper mapper;

    @Override
    public void create(CreateCommentDto dto) {
        Post post = postRepository.findById(dto.getPostId()).orElseThrow();

        Comment comment = new Comment();

        comment.setContent(dto.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setPost(post);

        repository.save(comment);
    }

    @Override
    public List<CommentDto> getByPost(Long postId) {

        return repository
                .findByPostIdOrderByCreatedAtAsc(postId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}
