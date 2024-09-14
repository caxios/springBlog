package me.parkdonggyu.springbootdeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.parkdonggyu.springbootdeveloper.domain.Article;
import me.parkdonggyu.springbootdeveloper.dto.AddArticleRequest;
import me.parkdonggyu.springbootdeveloper.dto.UpdateArticleRequest;
import me.parkdonggyu.springbootdeveloper.repository.BlogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가하는 어노테이션
@Service // 빈으로 등록
public class BlogService {

    private final BlogRepository blogRepository;

    // 블로그 글 추가 메서드
    public Article save(AddArticleRequest request, String userName){
        return blogRepository.save(request.toEntity(userName)); // save메서드는 blogRepository가 상속받는 JpaRepository가 상속받는 CrudRepository의 메서드.
    }

    public List<Article> findAll(){
        return blogRepository.findAll();
    }

    public Article findById(long id){
        return blogRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Not found:"+id));
    }

    public void delete(long id){
        Article article = blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    @Transactional // update 중에 에러가 발생하면 update하던 것에 대한 작업 롤백 할 수 있음.
    public Article update(long id, UpdateArticleRequest request){
        Article article = blogRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found : " + id));
        article.update(request.getTitle(), request.getContent());
        return article;
    }

    // 글의 수정,삭제 실행 전에 현재 인증객체에 담긴 사용자 정보와 글 작성한 사용자 정보를 비교
    private static void authorizeArticleAuthor(Article article) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!article.getAuthor().equals(userName)) {
            throw new IllegalArgumentException("not authorized");
        }
    }
}
