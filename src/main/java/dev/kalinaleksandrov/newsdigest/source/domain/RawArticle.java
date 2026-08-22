package dev.kalinaleksandrov.newsdigest.source.domain;

import java.time.Instant;

public record RawArticle(
        String externalId,
        String title,
        String url,
        String content,
        Instant publishedAt
) {
}
