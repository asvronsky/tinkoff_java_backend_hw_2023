package ru.asvronsky.scrapper.configuration;

import java.time.Duration;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;


@Validated
@ConfigurationProperties(prefix = "app", ignoreInvalidFields = false)
public record ApplicationConfig(@NotNull Map<String, String> apiTokens, @NotNull Scheduler scheduler, 
        AccessType databaseAccessType) {

    @Bean
    public long schedulerIntervalMs(ApplicationConfig config) {
        return config.scheduler().interval().toMillis();
    }

    public final record Scheduler(Duration interval) {

    }

    public enum AccessType {
        JDBC, JPA
    }
}
