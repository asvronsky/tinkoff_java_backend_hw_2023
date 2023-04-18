package ru.asvronsky.scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.asvronsky.scrapper.clients.BotClient;
import ru.asvronsky.scrapper.clients.GithubClient;
import ru.asvronsky.scrapper.clients.StackOverflowClient;

@Configuration
public class ClientConfiguration {
    
    @Bean
    public GithubClient githubClient() {
        return GithubClient.create();
    }

    @Bean
    public StackOverflowClient stackOverflowClient() {
        return StackOverflowClient.create();
    }

    @Bean
    public BotClient botClient() {
        return BotClient.create();
    }
}
