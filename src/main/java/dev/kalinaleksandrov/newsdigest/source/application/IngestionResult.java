package dev.kalinaleksandrov.newsdigest.source.application;

public record IngestionResult(
        int sourcesProcessed,
        int articlesFetched,
        int articlesSaved,
        int duplicates,
        int failedSources
) {
}
