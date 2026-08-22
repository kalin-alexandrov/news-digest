package dev.kalinaleksandrov.newsdigest.source.provider.guardian.dto;

import java.time.Instant;

public record GuardianArticleDto(
        String id,
        String webTitle,
        String webUrl,
        Instant webPublicationDate,
        Fields fields
) {
    public record Fields(
            String bodyText
    ) {
    }
}
