package ru.asvronsky.bot.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.asvronsky.bot.services.BotService;
import ru.asvronsky.shared.botdto.UpdateLinksRequest;

@RestController
@RequiredArgsConstructor
public class BotController implements BotControllerApi {

    private final BotService botService;
    
    @Override
    public void updateLinks(UpdateLinksRequest request) {
        botService.sendUpdate(request.description(), request.chatIds());
    }

}
