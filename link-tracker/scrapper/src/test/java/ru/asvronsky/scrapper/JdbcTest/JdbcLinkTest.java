package ru.asvronsky.scrapper.JdbcTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import jakarta.annotation.PostConstruct;
import ru.asvronsky.scrapper.IntegrationEnvironment;
import ru.asvronsky.scrapper.model.Link;
import ru.asvronsky.scrapper.repository.ChatDao;
import ru.asvronsky.scrapper.repository.LinkDao;
import ru.asvronsky.scrapper.repository.SubscriptionDao;
import ru.asvronsky.scrapper.repository.jdbc.JdbcChatDao;
import ru.asvronsky.scrapper.repository.jdbc.JdbcLinkDao;
import ru.asvronsky.scrapper.repository.jdbc.JdbcSubscriptionDao;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class JdbcLinkTest extends IntegrationEnvironment{

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private SubscriptionDao subscriptionRepository;
    private ChatDao chatRepository;
    private LinkDao linkRepository;

    @PostConstruct
    public void setup() {
        subscriptionRepository = new JdbcSubscriptionDao(jdbcTemplate);
        chatRepository = new JdbcChatDao(jdbcTemplate);
        linkRepository = new JdbcLinkDao(jdbcTemplate);
    }

    @Test
    public void findOutdated_OneLink() throws InterruptedException {
        long chatId = 0;
        Link link = new Link();
        link.setUrl("google.com");
        chatRepository.add(chatId);
        Link addedLink = subscriptionRepository.add(chatId, link);
        Thread.sleep(1000);

        List<Link> outdated = linkRepository.findOutdated(Duration.ofMillis(200));

        assertEquals(1, outdated.size());
        assertEquals(addedLink.getId(), outdated.get(0).getId());
    }

    @Test
    public void update_LinkWithNullWebsiteData() {
        long chatId = 0;
        Link link = new Link();
        link.setUrl("google.com");

        chatRepository.add(chatId);
        Link addedLink = subscriptionRepository.add(chatId, link);
        addedLink.setWebsiteData("{\"a\":\"aaa\", \"b\":\"bbb\", \"c\":\"ccc\"}");

        List<String> diff = linkRepository.update(addedLink);

        assertThat(List.of("a", "b", "c")).hasSameElementsAs(diff);
    }

    @Test
    public void update_ReplaceNonNullData() {
        long chatId = 0;
        Link link = new Link();
        link.setUrl("google.com");

        chatRepository.add(chatId);
        Link addedLink = subscriptionRepository.add(chatId, link);
        addedLink.setWebsiteData("{\"a\":\"aaa\", \"b\":\"bbb\", \"c\":\"ccc\"}");
        linkRepository.update(addedLink);

        addedLink.setWebsiteData("{\"a\":\"aaa\", \"b\":\"nnn\", \"c\":\"ccc\"}");
        List<String> diff = linkRepository.update(addedLink);


        assertThat(List.of("b")).hasSameElementsAs(diff);
    }

    @Test
    public void delete_Link() {
        long chatId = 0;
        Link link = new Link();
        link.setUrl("google.com");
        chatRepository.add(chatId);
        Link addedLink = subscriptionRepository.add(chatId, link);

        Optional<Link> deletedLink = linkRepository.remove(addedLink);

        assertTrue(deletedLink.isPresent());
        assertEquals("google.com", deletedLink.get().getUrl());
    }

    @Test
    public void delete_NotExistingLink() {
        long chatId = 0;
        Link link = new Link();
        link.setUrl("google.com");
        chatRepository.add(chatId);
        subscriptionRepository.add(chatId, link);
        Link notExistingLink = new Link();
        notExistingLink.setUrl("whassup.com");

        Optional<Link> deletedLink = linkRepository.remove(notExistingLink);

        assertFalse(deletedLink.isPresent());
    }
}
