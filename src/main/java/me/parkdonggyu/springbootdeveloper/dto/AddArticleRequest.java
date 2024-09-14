package me.parkdonggyu.springbootdeveloper.dto;

/* 데이터를 전달하는 용도로만 쓰이는 DTO */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.parkdonggyu.springbootdeveloper.domain.Article;

@NoArgsConstructor // 기본생성자 추가해주는 어노테이션
@AllArgsConstructor // 모든 필드값을 파라미터로 받는 생성자를 추가해주는 어노테이션
@Getter // 필드값을 가져오는 getter 메서드를 추가해주는 어노테이션
public class AddArticleRequest {

    private String title;
    private String content;

    public Article toEntity(String author){ // 생성자를 사용해 Article 객체 반환
        return Article.builder().title(title).content(content).author(author).build();
    }
}
