package ru.asvronsky.scrapper.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.asvronsky.scrapper.services.TgChatService;

@RestController
@RequestMapping("/tg-chat")
@RequiredArgsConstructor
public class TgChatController {
    private final TgChatService tgChatService;
    
    @PostMapping("/{id}")
    public void registerChat(@PathVariable long id) {
        tgChatService.registerChat(id);
    }

    @DeleteMapping("/{id}")
    public void deleteChat(@PathVariable long id) {
        tgChatService.deleteChat(id);
    }
}
