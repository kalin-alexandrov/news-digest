package dev.kalinaleksandrov.newsdigest.source.provider.guardian;

import dev.kalinaleksandrov.newsdigest.source.domain.NewsSource;
import dev.kalinaleksandrov.newsdigest.source.domain.NewsSourceType;
import dev.kalinaleksandrov.newsdigest.source.domain.RawArticle;
import dev.kalinaleksandrov.newsdigest.source.provider.NewsProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GuardianNewsProvider implements NewsProvider {

    private final GuardianClient guardianClient;

    @Value("${guardian.api-key}")
    private String apiKey;

    @Value("${guardian.page-size:20}")
    private int pageSize;

    @Override
    public List<RawArticle> fetch(NewsSource source) {
        log.info("Fetching articles from Guardian source '{}'", source.getName());
        var response = guardianClient.fetchArticles(apiKey, pageSize);
        if (response == null || response.response() == null) {
            log.warn("Guardian returned an empty response");
            return List.of();
        }

        var results = response.response().results();
        if (results == null) {
            return List.of();
        }

        return results.stream()
                .map(article -> new RawArticle(
                        article.id(),
                        article.webTitle(),
                        article.webUrl(),
                        article.fields() != null
                                ? article.fields().bodyText()
                                : null,
                        article.webPublicationDate()
                ))
                .toList();
    }

    @Override
    public NewsSourceType supports() {
        return NewsSourceType.GUARDIAN;
    }
}
