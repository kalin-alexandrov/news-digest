package dev.kalinaleksandrov.newsdigest.source.provider.guardian.dto;

import java.util.List;

public record GuardianResponse(
        Response response
) {
    public record Response(
            String status,
            int total,
            int startIndex,
            int pageSize,
            int currentPage,
            int pages,
            List<GuardianArticleDto> results
    ) {
    }
}
