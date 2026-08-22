package dev.kalinaleksandrov.newsdigest.article.persistence;

import dev.kalinaleksandrov.newsdigest.article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    boolean existsByUrl(String url);
}
