package ru.asvronsky.scrapper.services;

import org.springframework.stereotype.Service;

import ru.asvronsky.scrapper.exceptions.ChatIdNotFoundException;
import ru.asvronsky.scrapper.exceptions.InvalidRequestFormatException;

@Service
public class TgChatService {


    public void registerChat(long id) {
        throw new InvalidRequestFormatException("Unable to register chat", "400");
    }

    public void deleteChat(long id) {
        throw new ChatIdNotFoundException("Unable to delete chat", "404");
    }
    
    public LinksService getLinksService(long id) {
        
        return new LinksService();
    }
}
