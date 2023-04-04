package ru.asvronsky.bot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.asvronsky.bot.clients.ScrapperClient;

@Configuration
public class ClientConfiguration {
    
    @Bean
    public ScrapperClient scrapperClient() {
        return ScrapperClient.create();
    }
}
