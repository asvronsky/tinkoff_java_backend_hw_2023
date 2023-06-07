package ru.asvronsky.scrapper.repository.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.mapstruct.factory.Mappers;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.asvronsky.scrapper.model.Link;
import ru.asvronsky.scrapper.model.jpa.ChatEntity;
import ru.asvronsky.scrapper.model.jpa.LinkEntity;
import ru.asvronsky.scrapper.model.jpa.LinkEntityMapper;
import ru.asvronsky.scrapper.repository.SubscriptionDao;

@RequiredArgsConstructor
public class JpaSubscriptionDao implements SubscriptionDao {

    private final JpaLinkEntityRepository linkRepository;
    private final JpaChatEntityRepository chatRepository;

    private final LinkEntityMapper linkMapper = Mappers.getMapper(LinkEntityMapper.class);

    @Override
    @Transactional
    public Link add(long chatId, Link link) {
        Optional<LinkEntity> linkEntity = linkRepository.findByUrl(link.getUrl());
        Optional<ChatEntity> chatEntity = chatRepository.findById(chatId);
        if (linkEntity.isPresent() && chatEntity.isPresent()) {
            linkEntity.get().getSubscribers().add(chatEntity.get());
            linkRepository.saveAndFlush(linkEntity.get());

            return linkMapper.toLink(linkEntity.get());
        }

        return null;
    }

    @Override
    public Optional<Link> remove(long chatId, Link link) {
        Optional<LinkEntity> linkEntity = linkRepository.findByUrl(link.getUrl());
        Optional<ChatEntity> chatEntity = chatRepository.findById(chatId);
        if (linkEntity.isPresent() && chatEntity.isPresent()) {
            linkEntity.get().getSubscribers().remove(chatEntity.get());
            linkRepository.saveAndFlush(linkEntity.get());

            return Optional.of(linkMapper.toLink(linkEntity.get()));
        }

        return Optional.empty();
    }

    @Override
    public List<Link> findLinksByChat(long chatId) {
        Optional<ChatEntity> chatEntity = chatRepository.findById(chatId);
        
        if (chatEntity.isPresent()) {
            Set<LinkEntity> subscriptions = chatEntity.get().getSubscriptions();

            return subscriptions.stream()
                .map(linkMapper::toLink)
                .toList();
        }

        return new ArrayList<>();
    }

    @Override
    public List<Long> findChatsByLink(Link link) {
        Optional<LinkEntity> linkEntity = linkRepository.findByUrl(link.getUrl());
        
        if (linkEntity.isPresent()) {
            Set<ChatEntity> subscribers = linkEntity.get().getSubscribers();

            return subscribers.stream()
                .map(ChatEntity::getChatId)
                .toList();
        }

        return new ArrayList<>();
    }
    
}
