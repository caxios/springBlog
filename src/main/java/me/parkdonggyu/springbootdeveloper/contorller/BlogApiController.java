package me.parkdonggyu.springbootdeveloper.contorller;

/* URL을 매핑하기 위해 쓰이는 controller */

import lombok.RequiredArgsConstructor;
import me.parkdonggyu.springbootdeveloper.domain.Article;
import me.parkdonggyu.springbootdeveloper.dto.AddArticleRequest;
import me.parkdonggyu.springbootdeveloper.dto.ArticleResponse;
import me.parkdonggyu.springbootdeveloper.dto.UpdateArticleRequest;
import me.parkdonggyu.springbootdeveloper.service.BlogService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController // HTTP 응답으로 body에 JSON형식의 응답 데이터를 반환하는 컨트롤러 어노테이션
public class BlogApiController {

    private final BlogService blogService;

    //HTTP 메서드가 POST일 때 전달받은 URL과 동일하면 메서드로 매핑
    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request, Principal principal){// @RequestBody로 요청 본문 매핑
        Article savedArticle = blogService.save(request, principal.getName());

        // 응답이 성공적으로 생성되었으면 저장된 블로그 글 정보를 응답 객체에 담아 전송
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAll(){
        List<ArticleResponse> articles = blogService.findAll().stream().map(ArticleResponse::new).toList();
        return ResponseEntity.ok().body(articles);

    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id){
        Article article = blogService.findById(id);
        return ResponseEntity.ok().body(new ArticleResponse(article));
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id){
        blogService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id, @RequestBody UpdateArticleRequest request){
        Article updateArticle = blogService.update(id, request);
        return ResponseEntity.ok().body(updateArticle);
    }
}

