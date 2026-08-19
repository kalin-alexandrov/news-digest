package dev.kalinaleksandrov.newsdigest.source.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@Entity
@Table(name = "news_sources")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewsSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private NewsSourceType type;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private boolean enabled;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public NewsSource(
            String name,
            NewsSourceType type,
            String url
    ) {
        this.name = name;
        this.type = type;
        this.url = url;
        this.enabled = true;
        this.createdAt = Instant.now();
    }
}
