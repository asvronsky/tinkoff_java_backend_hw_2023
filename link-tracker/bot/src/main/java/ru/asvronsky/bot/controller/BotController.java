package ru.asvronsky.bot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.asvronsky.bot.dto.UpdateLinksResponse;
import ru.asvronsky.bot.services.BotService;

@RestController
@RequestMapping("/updates")
@RequiredArgsConstructor
public class BotController {
    private final BotService botService;
    
    @PostMapping
    public UpdateLinksResponse updateLinks() {
        return botService.updateLinks();
    }

}
