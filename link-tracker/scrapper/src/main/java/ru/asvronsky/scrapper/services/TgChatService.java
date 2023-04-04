package ru.asvronsky.scrapper.services;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ru.asvronsky.scrapper.exceptions.NotFoundException;

@Service
@Slf4j
public class TgChatService {

    public void registerChat(long id) {
        log.info("Request: register chat #" + id);
    }

    public void deleteChat(long id) {
        log.info("Request: delete chat #" + id);
        if (id == 666) {
            throw new NotFoundException("Chat id not found");
        }
    }
    
    public LinksService getLinksService(long id) {
        
        return new LinksService();
    }
}
