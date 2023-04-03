package ru.asvronsky.bot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.asvronsky.bot.botApi.Bot;
import ru.asvronsky.bot.botApi.BotImpl;
import ru.asvronsky.bot.botApi.UserMessageProcessorImpl;

@Configuration
public class BotConfiguration {
    
    @Bean
    public Bot bot(@Value("${app.token}") String token) {
        Bot bot = new BotImpl(token, new UserMessageProcessorImpl());
        bot.start();
        return bot;
    }
}
