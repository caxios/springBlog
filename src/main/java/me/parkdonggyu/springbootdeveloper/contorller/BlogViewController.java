package me.parkdonggyu.springbootdeveloper.contorller;

import me.parkdonggyu.springbootdeveloper.domain.Article;
import me.parkdonggyu.springbootdeveloper.dto.ArticleListViewResponse;
import me.parkdonggyu.springbootdeveloper.dto.ArticleViewResponse;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import me.parkdonggyu.springbootdeveloper.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {

    private final BlogService blogService;

    @GetMapping("/articles")
    public String getArticles(Model model) {
        // Article 객체를 ArticleListViewResponse로 매핑 후, 최종적으로 ArticleListViewResponse 객체들의 리스트를 생성
        // ArticleListViewResponse::new == new ArticleListViewResponse(article)
        List<ArticleListViewResponse> articles = blogService.findAll().stream().map(ArticleListViewResponse::new).toList();
        // 모델에 "articles"가 키인 articles 데이터를 저장
        model.addAttribute("articles", articles);

        // articleList.html라는 뷰를 찾아라
        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        Article article = blogService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));
        return "article";
    }

    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("article", new ArticleViewResponse());
        } else {
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }

        return "newArticle";
    }
}
