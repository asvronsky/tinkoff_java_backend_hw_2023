package ru.asvronsky.scrapper.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.asvronsky.scrapper.dto.AddLinkRequest;
import ru.asvronsky.scrapper.dto.AddLinkResponse;
import ru.asvronsky.scrapper.dto.DeleteLinkRequest;
import ru.asvronsky.scrapper.dto.DeleteLinkResponse;
import ru.asvronsky.scrapper.dto.GetAllLinksResponse;
import ru.asvronsky.scrapper.services.TgChatService;

@RestController
@RequestMapping("/links")
@RequiredArgsConstructor
public class LinksController {
    private final TgChatService tgChatService;

    @GetMapping
    public List<GetAllLinksResponse> getAllLinks(@RequestHeader("Tg-Chat-Id") long id) {
        return tgChatService.getLinksService(id).getAllLinks();
    }

    @PostMapping
    public AddLinkResponse addLink(@RequestHeader("Tg-Chat-Id") long id, @RequestBody AddLinkRequest request) {
        return tgChatService.getLinksService(id).addLink(request.link());
    }

    @DeleteMapping
    public DeleteLinkResponse deleteLink(@RequestHeader("Tg-Chat-Id") long id, @RequestBody DeleteLinkRequest request) {
        return tgChatService.getLinksService(id).deleteLink(request.link());
    }
}
