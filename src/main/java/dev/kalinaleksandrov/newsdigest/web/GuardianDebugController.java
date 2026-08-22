package dev.kalinaleksandrov.newsdigest.web;

import dev.kalinaleksandrov.newsdigest.source.application.IngestionResult;
import dev.kalinaleksandrov.newsdigest.source.application.NewsIngestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/debug")
@Slf4j
@RequiredArgsConstructor
public class GuardianDebugController {

    private final NewsIngestionService newsIngestionService;

    @PostMapping("/ingest")
    public IngestionResult ingest() {
        return newsIngestionService.ingestAll();
    }
}
