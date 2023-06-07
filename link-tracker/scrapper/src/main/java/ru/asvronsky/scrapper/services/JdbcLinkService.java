package ru.asvronsky.scrapper.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.asvronsky.linkparser.ParserResults.ParserResult;
import ru.asvronsky.linkparser.Parsers.Parser;
import ru.asvronsky.scrapper.exceptions.InvalidRequestFormatException;
import ru.asvronsky.scrapper.exceptions.NotFoundException;
import ru.asvronsky.scrapper.model.Link;
import ru.asvronsky.scrapper.repository.SubscriptionDao;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {

    private final SubscriptionDao subscriptionRepository;
    private final Parser linkParser;

    @Override
    public Link add(long chatId, URI url) {
        Optional<ParserResult> result;
        try {
            result = linkParser.parse(url);
        } catch (Exception e) {
            throw new InvalidRequestFormatException(
                "Exception while parsing link %s, cause: %s".formatted(url.toString(), e.getMessage()), 
                e
            );
        }
        result.orElseThrow(
            () -> new InvalidRequestFormatException("No appropriate parser found"));

        Link link = new Link();
        link.setUrl(url.toString());

        return subscriptionRepository.add(chatId, link);
        
    }

    @Override
    public Link remove(long chatId, URI url) {
        Link link = new Link();
        link.setUrl(url.toString());
        
        Optional<Link> removed = subscriptionRepository.remove(chatId, link);
        return removed.orElseThrow(
            () -> new NotFoundException(
                    "Subscription \"chat: %d, link: %s\" was not found".formatted(
                            chatId,
                            url.toString()
                        )
            )
        );
    }

    @Override
    public List<Link> listAll(long chatId) {
        return subscriptionRepository.findLinksByChat(chatId);
    }

}
