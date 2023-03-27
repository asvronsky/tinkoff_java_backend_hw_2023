package ru.asvronsky.bot.services;

import ru.asvronsky.bot.dto.UpdateLinksResponse;

public class BotService {

    public UpdateLinksResponse updateLinks() {
        return new UpdateLinksResponse(
            0, 
            "google.com", 
            "description", 
            null
        );
    }
    
}
