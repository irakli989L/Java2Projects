package org.example.Controller;

import lombok.RequiredArgsConstructor;
import org.example.DTO.CreateCommentDto;
import org.example.Service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService service;

    @PostMapping
    public String create(CreateCommentDto dto) {
        service.create(dto);

        return "redirect:/post/" + dto.getPostId();
    }
}
