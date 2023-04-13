package ru.asvronsky.scrapper.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.asvronsky.scrapper.services.TgChatService;

@RestController
@RequiredArgsConstructor
public class TgChatController implements TgChatControllerApi {
    private final TgChatService tgChatService;
    
    @Override
    public void registerChat(long chatId) {
        tgChatService.registerChat(chatId);
    }


    @Override
    public void deleteChat(long chatId) {
        tgChatService.deleteChat(chatId);
    }
}
