package ru.asvronsky.scrapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import ru.asvronsky.scrapper.model.Link;
import ru.asvronsky.scrapper.repository.ChatDao;
import ru.asvronsky.scrapper.repository.LinkDao;
import ru.asvronsky.scrapper.repository.SubscriptionDao;

@SpringBootTest
public class JdbcLinkTest extends IntegrationEnvironment{

    @Autowired
    private SubscriptionDao subscriptionRepository;
    @Autowired
    private ChatDao chatRepository;
    @Autowired
    private LinkDao linkRepository;

    @Test
    @Transactional
    @Rollback
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
    @Transactional
    @Rollback
    public void update_LinkWithNullWebsiteData() throws InterruptedException {
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
    @Transactional
    @Rollback
    public void update_ReplaceNonNullData() throws InterruptedException {
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
