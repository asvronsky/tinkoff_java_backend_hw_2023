package ru.asvronsky.scrapper.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.asvronsky.scrapper.model.jpa.ChatEntity;

public interface JpaChatEntityRepository extends JpaRepository<ChatEntity, Long> {
    
}
