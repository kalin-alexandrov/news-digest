package dev.kalinaleksandrov.newsdigest.source.provider;

import dev.kalinaleksandrov.newsdigest.source.domain.NewsSource;
import dev.kalinaleksandrov.newsdigest.source.domain.NewsSourceType;
import dev.kalinaleksandrov.newsdigest.source.domain.RawArticle;

import java.util.List;

public interface NewsProvider {
    NewsSourceType supports();

    List<RawArticle> fetch(NewsSource source);
}
