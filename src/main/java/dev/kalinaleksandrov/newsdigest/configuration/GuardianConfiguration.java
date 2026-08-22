package dev.kalinaleksandrov.newsdigest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class GuardianConfiguration {

    @Bean
    public RestClient guardianRestClient() {
        return RestClient.builder()
                .baseUrl("https://content.guardianapis.com")
                .build();
    }
}
