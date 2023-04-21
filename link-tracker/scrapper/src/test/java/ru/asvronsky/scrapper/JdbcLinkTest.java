package ru.asvronsky.scrapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import jakarta.annotation.PostConstruct;
import ru.asvronsky.scrapper.model.Link;
import ru.asvronsky.scrapper.repository.ChatDao;
import ru.asvronsky.scrapper.repository.JdbcChatDao;
import ru.asvronsky.scrapper.repository.JdbcLinkDao;
import ru.asvronsky.scrapper.repository.JdbcSubscriptionDao;
import ru.asvronsky.scrapper.repository.LinkDao;
import ru.asvronsky.scrapper.repository.SubscriptionDao;

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

        List<Link> outdated =  linkRepository.findOutdated(Duration.ofMillis(200));

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


}
