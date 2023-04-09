package ru.asvronsky.bot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.asvronsky.bot.botApi.Bot;
import ru.asvronsky.bot.botApi.BotImpl;
import ru.asvronsky.bot.botApi.UserMessageProcessorImpl;
import ru.asvronsky.bot.clients.ScrapperClient;

@Configuration
public class BotConfiguration {
    
    @Bean
    public Bot bot(final String token, final ScrapperClient scrapperClient) {
        Bot bot = new BotImpl(token, new UserMessageProcessorImpl(scrapperClient));
        bot.start();
        return bot;
    }
}
