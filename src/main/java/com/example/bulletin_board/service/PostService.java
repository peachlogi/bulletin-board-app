package com.example.bulletin_board.service;

import com.example.bulletin_board.domain.Comment;
import com.example.bulletin_board.domain.Post;
import com.example.bulletin_board.repository.CommentRepository;
import com.example.bulletin_board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동으로 생성
@Transactional(readOnly = true) // 기본적으로 읽기 전용 트랜잭션
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // 모든 게시글 조회
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    // 특정 게시글 조회
    public Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + postId));
    }

    // 게시글 생성
    @Transactional
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    // 댓글 추가
    @Transactional
    public void addComment(Long postId, Comment comment) {
        // 게시글을 찾음
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + postId));
        // 댓글에 게시글 정보 설정
        comment.setPost(post);
        // 게시글의 댓글 목록에도 추가
        post.getComments().add(comment);
        // 댓글 저장 (Post에 cascade 설정이 되어있어 postRepository.save(post)만 해도 됨)
        commentRepository.save(comment);
    }
}
