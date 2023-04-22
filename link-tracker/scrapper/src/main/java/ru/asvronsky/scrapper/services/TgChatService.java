package ru.asvronsky.scrapper.services;

public interface TgChatService {
    
    void register(long chatId);
    void unregister(long chatId);

}
