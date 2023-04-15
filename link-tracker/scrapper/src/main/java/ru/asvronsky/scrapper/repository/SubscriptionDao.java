package ru.asvronsky.scrapper.repository;

import java.util.List;
import java.util.Optional;

import ru.asvronsky.scrapper.model.Chat;
import ru.asvronsky.scrapper.model.Link;

public interface SubscriptionDao {

    public Link add(Chat chat, Link link);
    public Optional<Link> remove(Chat chat, Link link);
    public List<Link> findAll(Chat chat);
    
}
