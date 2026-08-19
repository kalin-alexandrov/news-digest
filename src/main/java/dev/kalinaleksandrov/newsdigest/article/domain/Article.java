package dev.kalinaleksandrov.newsdigest.article.domain;

import dev.kalinaleksandrov.newsdigest.source.domain.NewsSource;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "articles")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(nullable = false, unique = true)
    private String url;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id", nullable = false)
    private NewsSource source;

    @Column(name = "published_at")
    private Instant publishedAt;

    @Column(name = "fetched_at", nullable = false)
    private Instant fetchedAt;

    public Article(
            String title,
            String url,
            String content,
            NewsSource source,
            Instant publishedAt,
            Instant fetchedAt
    ) {
        this.title = title;
        this.url = url;
        this.content = content;
        this.source = source;
        this.publishedAt = publishedAt;
        this.fetchedAt = fetchedAt;
    }
}
