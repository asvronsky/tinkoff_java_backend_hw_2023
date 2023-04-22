package ru.asvronsky.bot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pengrad.telegrambot.TelegramBot;

@Configuration
public class BotConfiguration {

    @Bean 
    @Value("#{token}")
    public TelegramBot telegramBot(String token) {
        return new TelegramBot(token);
    }
}
