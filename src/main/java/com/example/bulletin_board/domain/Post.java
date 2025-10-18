package com.example.bulletin_board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Lob // 큰 텍스트 데이터를 저장할 때 사용
    @Column(nullable = false)
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();

    // 1:N 관계. Post 하나에 여러 Comment가 달릴 수 있음
    // mappedBy: 연관관계의 주인이 아님을 명시. Comment의 'post' 필드에 의해 매핑됨
    // cascade: Post가 삭제될 때 연관된 Comment도 함께 삭제됨
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
}
