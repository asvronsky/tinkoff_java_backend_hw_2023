package ru.asvronsky.scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.asvronsky.scrapper.clients.BotClient;
import ru.asvronsky.scrapper.clients.GithubClient;
import ru.asvronsky.scrapper.clients.StackOverflowClient;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ClientConfiguration {
    private final ApplicationConfig config;
    
    @Bean
    public GithubClient githubClient() {
        String githubToken = config.apiTokens().getOrDefault("github", "");
        log.info("creating github client with token %s".formatted(githubToken));
        return GithubClient.create(githubToken);
    }

    @Bean
    public StackOverflowClient stackOverflowClient() {
        String stackoverflowToken = config.apiTokens().getOrDefault("stackoverflow", "");
        log.info("creating stackoverflow client with token %s".formatted(stackoverflowToken));
        return StackOverflowClient.create(stackoverflowToken);
    }

    @Bean
    public BotClient botClient() {
        return BotClient.create();
    }
}
