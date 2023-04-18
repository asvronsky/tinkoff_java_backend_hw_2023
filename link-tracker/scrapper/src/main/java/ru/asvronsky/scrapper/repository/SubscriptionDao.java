package ru.asvronsky.scrapper.repository;

import java.util.List;
import java.util.Optional;

import ru.asvronsky.scrapper.model.Link;

public interface SubscriptionDao {

    public Link add(long chatId, Link link);
    public Optional<Link> remove(long chatId, Link link);
    public List<Link> findLinksByChat(long chatId);
    public List<Long> findChatsByLink(Link link);
    
}
