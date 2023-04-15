package ru.asvronsky.scrapper.repository;

import java.util.List;
import java.util.Optional;

import ru.asvronsky.scrapper.model.Chat;

public interface ChatDao {
    
    public void add(Chat chat);
    public Optional<Chat> remove(Chat chat);
    public List<Chat> findAll();
}
