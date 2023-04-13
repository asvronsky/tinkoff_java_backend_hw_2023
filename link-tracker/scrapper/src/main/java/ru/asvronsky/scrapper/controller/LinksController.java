package ru.asvronsky.scrapper.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.asvronsky.scrapper.dto.controller.AddLinkRequest;
import ru.asvronsky.scrapper.dto.controller.LinkResponse;
import ru.asvronsky.scrapper.dto.controller.ListLinkResponse;
import ru.asvronsky.scrapper.dto.controller.RemoveLinkRequest;
import ru.asvronsky.scrapper.services.TgChatService;

@RestController
@RequiredArgsConstructor
public class LinksController implements LinksControllerApi {
    private final TgChatService tgChatService;

    @Override
    public ListLinkResponse getAllLinks(long chatId) {
        return tgChatService.getLinksService(chatId).getAllLinks();
    }

    @Override
    public LinkResponse addLink(long chatId, AddLinkRequest request) {
        return tgChatService.getLinksService(chatId).addLink(request.link());
    }


    @Override
    public LinkResponse deleteLink(long chatId, RemoveLinkRequest request) {
        return tgChatService.getLinksService(chatId).deleteLink(request.link());
    }
}
