package ru.asvronsky.scrapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import ru.asvronsky.scrapper.model.Chat;
import ru.asvronsky.scrapper.model.Link;
import ru.asvronsky.scrapper.repository.ChatDao;
import ru.asvronsky.scrapper.repository.SubscriptionDao;

@SpringBootTest
public class JdbcSubscriptionTest extends IntegrationEnvironment{

    @Autowired
    private SubscriptionDao subscriptionRepository;
    @Autowired
    private ChatDao chatRepository;

    @Test
    @Transactional
    @Rollback
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
    @Transactional
    @Rollback
    public void subscribeOneChatTest() {
        long chatId = 0;
        Link link = new Link();
        link.setUrl("google.com");

        chatRepository.add(chatId);
        Link addedLink = subscriptionRepository.add(chatId, link);
        List<Link> subscriptions = subscriptionRepository.findAll(chatId);
        Optional<Link> removedLink = subscriptionRepository.remove(chatId, link);

        assertEquals("google.com", addedLink.getUrl());
        assertEquals(1, subscriptions.size());
        assertTrue(removedLink.isPresent());
        assertEquals("google.com", removedLink.get().getUrl());
    }


}
