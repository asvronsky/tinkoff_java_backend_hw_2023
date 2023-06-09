package ru.asvronsky.bot.configuration;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreInvalidFields = false)
public record ApplicationConfig(@NotNull String token) {
    
    @Bean
    public String token() {
        return token;
    }
}
