package dev.kalinaleksandrov.newsdigest.source.application;

import dev.kalinaleksandrov.newsdigest.article.application.ArticleService;
import dev.kalinaleksandrov.newsdigest.article.domain.Article;
import dev.kalinaleksandrov.newsdigest.source.domain.NewsSource;
import dev.kalinaleksandrov.newsdigest.source.domain.RawArticle;
import dev.kalinaleksandrov.newsdigest.source.persistence.NewsSourceRepository;
import dev.kalinaleksandrov.newsdigest.source.provider.NewsProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsIngestionService {

    private final NewsSourceRepository newsSourceRepository;
    private final ArticleService articleService;
    private final List<NewsProvider> newsProviders;

    public IngestionResult ingestAll() {
        List<NewsSource> sources = newsSourceRepository.findAllByEnabledTrue();

        log.info("Starting ingestion for {} enabled news sources", sources.size());

        int successfulSources = 0;
        int articlesFetched = 0;
        int articlesSaved = 0;
        int duplicates = 0;
        int failedSources = 0;

        for (NewsSource source : sources) {
            try {
                SourceIngestionResult result = ingest(source);
                successfulSources++;
                articlesFetched += result.fetched();
                articlesSaved += result.saved();
                duplicates += result.duplicates();
            } catch (Exception e) {
                failedSources++;
                log.error("Failed to ingest source '{}'", source.getName(), e);
            }
        }

        IngestionResult result = new IngestionResult(successfulSources, articlesFetched, articlesSaved, duplicates, failedSources);
        log.info("Ingestion finished: processed={}, fetched={}, saved={}, duplicates={}, failed={}",
                result.sourcesProcessed(), result.articlesFetched(), result.articlesSaved(), result.duplicates(), result.failedSources());
        return result;
    }

    private SourceIngestionResult ingest(NewsSource source) {
        NewsProvider provider = newsProviders.stream()
                .filter(candidate -> candidate.supports() == source.getType())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "No provider found for source type " + source.getType()
                ));

        log.info("Fetching source '{}' using provider {}", source.getName(), provider.getClass().getSimpleName());

        List<RawArticle> rawArticles = provider.fetch(source);
        int saved = 0;
        int duplicates = 0;

        for (RawArticle rawArticle : rawArticles) {
            Article article = new Article(
                    rawArticle.title(),
                    rawArticle.url(),
                    rawArticle.content(),
                    source,
                    rawArticle.publishedAt(),
                    Instant.now()
            );
            if (articleService.saveIfNew(article).isPresent()) {
                saved++;
            } else {
                duplicates++;
            }
        }

        log.info("Finished source '{}': fetched={}, saved={}", source.getName(), rawArticles.size(), saved);

        return new SourceIngestionResult(rawArticles.size(), saved, duplicates);
    }
}
