package dev.kalinaleksandrov.newsdigest.article.application;

import dev.kalinaleksandrov.newsdigest.article.domain.Article;
import dev.kalinaleksandrov.newsdigest.article.persistence.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Optional<Article> saveIfNew(Article article) {
        if (articleRepository.existsByUrl(article.getUrl())) {
            log.debug("Skipping duplicate article: {}", article.getUrl());
            return Optional.empty();
        }

        Article savedArticle = articleRepository.save(article);

        log.debug(
                "Saved article id={} title={}",
                savedArticle.getId(),
                savedArticle.getTitle()
        );

        return Optional.of(savedArticle);
    }
}
