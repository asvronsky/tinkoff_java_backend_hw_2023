package ru.asvronsky.bot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.asvronsky.bot.dto.UpdateLinksResponse;

@RestController
@RequestMapping("/updates")
public class BotController {
    
    @PostMapping
    public UpdateLinksResponse updateLinks() {

        return new UpdateLinksResponse(
            0, 
            "google.com", 
            "description", 
            null
        );
    }

}
