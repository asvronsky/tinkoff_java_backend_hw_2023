package ru.asvronsky.scrapper.controller;

import java.net.URI;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.asvronsky.scrapper.model.Link;
import ru.asvronsky.scrapper.services.LinkService;
import ru.asvronsky.shared.scrapperdto.AddLinkRequest;
import ru.asvronsky.shared.scrapperdto.LinkResponse;
import ru.asvronsky.shared.scrapperdto.ListLinkResponse;
import ru.asvronsky.shared.scrapperdto.RemoveLinkRequest;

@RestController
@RequiredArgsConstructor
public class LinksController implements LinksControllerApi {

    private final LinkService linkService;

    @Override
    public ListLinkResponse getAllLinks(long chatId) {
        return new ListLinkResponse(
            linkService.listAll(chatId).stream()
                .map(this::convertToLinkResponse)
                .toList()
        );
    }

    @Override
    public LinkResponse addLink(long chatId, AddLinkRequest request) {
        return convertToLinkResponse(linkService.add(chatId, request.link()));
    }

    @Override
    public LinkResponse deleteLink(long chatId, RemoveLinkRequest request) {
        return convertToLinkResponse(linkService.remove(chatId, request.link()));
    }


    private LinkResponse convertToLinkResponse(Link link) {
        return new LinkResponse(link.getId(), URI.create(link.getUrl()));
    }

}
