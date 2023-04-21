package ru.asvronsky.scrapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import jakarta.annotation.PostConstruct;
import ru.asvronsky.scrapper.model.Chat;
import ru.asvronsky.scrapper.model.Link;
import ru.asvronsky.scrapper.repository.ChatDao;
import ru.asvronsky.scrapper.repository.JdbcChatDao;
import ru.asvronsky.scrapper.repository.JdbcSubscriptionDao;
import ru.asvronsky.scrapper.repository.SubscriptionDao;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class JdbcSubscriptionTest extends IntegrationEnvironment{

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private SubscriptionDao subscriptionRepository;
    private ChatDao chatRepository;

    @PostConstruct
    public void setup() {
        subscriptionRepository = new JdbcSubscriptionDao(jdbcTemplate);
        chatRepository = new JdbcChatDao(jdbcTemplate);
    }

    @Test
    public void addAndRemoveOneChatTest() {
        long chatId = 0;

        chatRepository.add(chatId);
        List<Chat> chats = chatRepository.findAll();
        Optional<Chat> removed = chatRepository.remove(chatId);

        assertEquals(1, chats.size());
        assertTrue(removed.isPresent());
        assertEquals(chatId, removed.get().getChatId());
    }

    @Test
    public void subscribeOneChatTest() {
        long chatId = 0;
        Link link = new Link();
        link.setUrl("google.com");

        chatRepository.add(chatId);
        Link addedLink = subscriptionRepository.add(chatId, link);
        List<Link> subscriptions = subscriptionRepository.findLinksByChat(chatId);
        Optional<Link> removedLink = subscriptionRepository.remove(chatId, link);

        assertEquals("google.com", addedLink.getUrl());
        assertEquals(1, subscriptions.size());
        assertTrue(removedLink.isPresent());
        assertEquals("google.com", removedLink.get().getUrl());
    }

    @Test
    public void findChatsByLink() {
        Link link = new Link();
        link.setUrl("google.com");
        Link anotherLink = new Link();
        anotherLink.setUrl("twitter.com");

        chatRepository.add(0);
        chatRepository.add(1);
        chatRepository.add(2);
        subscriptionRepository.add(0, link);
        subscriptionRepository.add(1, link);
        subscriptionRepository.add(2, anotherLink);
        List<Long> subscriptionsLink = subscriptionRepository.findChatsByLink(link);
        List<Long> subscriptionsAnotherLink = subscriptionRepository.findChatsByLink(anotherLink);
        
        assertThat(subscriptionsLink).hasSameElementsAs(List.of(0L, 1L));
        assertThat(subscriptionsAnotherLink).hasSameElementsAs(List.of(2L));
    }

}
