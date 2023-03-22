package ru.asvronsky.scrapper;

import org.springframework.boot.SpringApplication;

import ru.asvronsky.scrapper.configuration.ApplicationConfig;

public class ScrapperApplication {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(ScrapperApplication.class, args);
        ApplicationConfig config = ctx.getBean(ApplicationConfig.class);
        System.out.println(config);
    }
}
