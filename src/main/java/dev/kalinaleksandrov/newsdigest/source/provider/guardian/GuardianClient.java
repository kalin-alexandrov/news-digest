package dev.kalinaleksandrov.newsdigest.source.provider.guardian;

import dev.kalinaleksandrov.newsdigest.source.provider.guardian.dto.GuardianResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class GuardianClient {

    private final RestClient guardianRestClient;

    public GuardianResponse fetchArticles(String apiKey, int pageSize) {
        return guardianRestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("api-key", apiKey)
                        .queryParam("page-size", pageSize)
                        .queryParam("show-fields", "bodyText")
                        .build())
                .retrieve()
                .body(GuardianResponse.class);
    }
}
