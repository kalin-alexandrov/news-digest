package dev.kalinaleksandrov.newsdigest.source.application;

record SourceIngestionResult(
        int fetched,
        int saved,
        int duplicates
) {
}
