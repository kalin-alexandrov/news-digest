package dev.kalinaleksandrov.newsdigest.source.persistence;

import dev.kalinaleksandrov.newsdigest.source.domain.NewsSource;
import dev.kalinaleksandrov.newsdigest.source.domain.NewsSourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsSourceRepository extends JpaRepository<NewsSource, Long> {
    List<NewsSource> findAllByEnabledTrue();

    List<NewsSource> findAllByEnabledTrueAndType(NewsSourceType type);
}
