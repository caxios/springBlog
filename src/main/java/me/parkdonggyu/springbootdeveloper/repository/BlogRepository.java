package me.parkdonggyu.springbootdeveloper.repository;

import me.parkdonggyu.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

// 데이터베이스에 접근하기 위한 인터페이스
public interface BlogRepository extends JpaRepository<Article, Long> {
}
