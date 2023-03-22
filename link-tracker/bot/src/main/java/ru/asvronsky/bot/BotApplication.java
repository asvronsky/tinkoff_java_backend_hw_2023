package ru.asvronsky.bot;

import org.springframework.boot.SpringApplication;

import ru.asvronsky.bot.configuration.ApplicationConfig;

public class BotApplication {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(BotApplication.class, args);
        ApplicationConfig config = ctx.getBean(ApplicationConfig.class);
        System.out.println(config);
    }
}
