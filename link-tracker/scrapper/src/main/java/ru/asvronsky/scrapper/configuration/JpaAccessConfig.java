package ru.asvronsky.scrapper.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.asvronsky.scrapper.repository.jpa.JpaChatDao;
import ru.asvronsky.scrapper.repository.jpa.JpaChatEntityRepository;
import ru.asvronsky.scrapper.repository.jpa.JpaLinkDao;
import ru.asvronsky.scrapper.repository.jpa.JpaLinkEntityRepository;
import ru.asvronsky.scrapper.repository.jpa.JpaSubscriptionDao;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfig {

    @Bean
    public JpaChatDao chatDao(JpaChatEntityRepository jpaChatRepository) {
        return new JpaChatDao(jpaChatRepository);
    }

    @Bean
    public JpaLinkDao linkDao(JpaLinkEntityRepository jpaLinkRepository) {
        return new JpaLinkDao(jpaLinkRepository);
    }

    @Bean
    public JpaSubscriptionDao subscriptionDao(JpaChatEntityRepository jpaChatRepository,
            JpaLinkEntityRepository jpaLinkRepository) {
        return new JpaSubscriptionDao(jpaLinkRepository, jpaChatRepository);
    }
    
}
