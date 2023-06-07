package ru.asvronsky.scrapper.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import ru.asvronsky.scrapper.repository.jdbc.JdbcChatDao;
import ru.asvronsky.scrapper.repository.jdbc.JdbcLinkDao;
import ru.asvronsky.scrapper.repository.jdbc.JdbcSubscriptionDao;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfig {

    @Bean
    public JdbcChatDao chatDao(NamedParameterJdbcTemplate jdbcTemplate) {
        return new JdbcChatDao(jdbcTemplate);
    }

    @Bean
    public JdbcLinkDao linkDao(NamedParameterJdbcTemplate jdbcTemplate) {
        return new JdbcLinkDao(jdbcTemplate);
    }

    @Bean
    public JdbcSubscriptionDao subscriptionDao(NamedParameterJdbcTemplate jdbcTemplate) {
        return new JdbcSubscriptionDao(jdbcTemplate);
    }

}
