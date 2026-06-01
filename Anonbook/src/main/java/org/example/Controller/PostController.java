package org.example.Controller;

import lombok.RequiredArgsConstructor;
import org.example.DTO.CreatePostDto;
import org.example.Service.CommentService;
import org.example.Service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("posts", postService.getAll());

        return "index";
    }

    @PostMapping
    public String create(CreatePostDto dto) {
        postService.create(dto);

        return "redirect:/post";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.getById(id));
        model.addAttribute("comments", commentService.getByPost(id));

        return "post";
    }

    @GetMapping("/create")
    public String createPage() {
        return "create-post";
    }
}