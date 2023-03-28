package ru.asvronsky.scrapper.configuration;

import org.springframework.context.annotation.Configuration;

import ru.asvronsky.scrapper.clients.GithubClient;
import ru.asvronsky.scrapper.clients.StackOverflowClient;

@Configuration
public class ClientConfiguration {
    
    public GithubClient githubClient() {
        return GithubClient.create();
    }

    public StackOverflowClient stackOverflowClient() {
        return StackOverflowClient.create();
    }
}
