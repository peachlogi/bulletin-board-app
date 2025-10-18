package com.example.bulletin_board.controller;

import com.example.bulletin_board.domain.Comment;
import com.example.bulletin_board.domain.Post;
import com.example.bulletin_board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    // 게시글 목록 페이지
    @GetMapping
    public String listPosts(Model model) {
        model.addAttribute("posts", postService.findAllPosts());
        return "post-list"; // templates/post-list.html 뷰를 렌더링
    }

    // 게시글 상세 페이지
    @GetMapping("/{id}")
    public String viewPost(@PathVariable("id") Long id, Model model) {
        model.addAttribute("post", postService.findPostById(id));
        model.addAttribute("newComment", new Comment()); // 댓글 작성을 위한 빈 Comment 객체
        return "post-detail"; // templates/post-detail.html
    }

    // 새 게시글 작성 폼 페이지
    @GetMapping("/new")
    public String newPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "post-form"; // templates/post-form.html
    }

    // 새 게시글 저장
    @PostMapping
    public String createPost(@ModelAttribute Post post) {
        postService.createPost(post);
        return "redirect:/posts"; // 게시글 목록으로 리다이렉트
    }

    // 댓글 추가
    @PostMapping("/{id}/comments")
    public String addComment(@PathVariable("id") Long postId, @ModelAttribute Comment newComment) {
        postService.addComment(postId, newComment);
        return "redirect:/posts/" + postId; // 해당 게시글 상세 페이지로 리다이렉트
    }
}
