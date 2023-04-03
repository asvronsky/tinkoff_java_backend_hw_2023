package ru.asvronsky.scrapper.services;

import org.springframework.stereotype.Service;

import ru.asvronsky.scrapper.exceptions.NotFoundException;

@Service
public class TgChatService {

    public void registerChat(long id) {

    }

    public void deleteChat(long id) {
        if (id == 666) {
            throw new NotFoundException("Chat id not found");
        }
    }
    
    public LinksService getLinksService(long id) {
        
        return new LinksService();
    }
}
