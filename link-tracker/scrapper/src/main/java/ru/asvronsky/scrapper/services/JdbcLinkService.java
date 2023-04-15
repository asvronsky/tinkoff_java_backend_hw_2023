package ru.asvronsky.scrapper.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.asvronsky.scrapper.exceptions.NotFoundException;
import ru.asvronsky.scrapper.model.Link;
import ru.asvronsky.scrapper.repository.SubscriptionDao;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {

    private final SubscriptionDao subscriptionRepository;

    @Override
    public Link add(long chatId, URI url) {
        Link link = new Link();
        link.setUrl(url.toString());

        return subscriptionRepository.add(chatId, link);
        
    }

    @Override
    public Link remove(long chatId, URI url) {
        Link link = new Link();
        link.setUrl(url.toString());
        
        Optional<Link> removed = subscriptionRepository.remove(chatId, link);
        if (!removed.isPresent()) {
            throw new NotFoundException("Subscription \"chat: {}, link: \" was not found");
        } else {
            return removed.get();
        }
    }

    @Override
    public List<Link> listAll(long chatId) {
        return subscriptionRepository.findAll(chatId);
    }

}
