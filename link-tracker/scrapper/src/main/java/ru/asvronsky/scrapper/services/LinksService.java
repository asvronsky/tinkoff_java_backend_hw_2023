package ru.asvronsky.scrapper.services;

import java.net.URI;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import ru.asvronsky.scrapper.dto.controller.LinkResponse;
import ru.asvronsky.scrapper.dto.controller.ListLinkResponse;
import ru.asvronsky.scrapper.exceptions.NotFoundException;

@Slf4j
public class LinksService {

    public ListLinkResponse getAllLinks() {
        log.info("Request: get links");
        return new ListLinkResponse(List.of(new LinkResponse(123, URI.create("stub.com"))));
    }

    public LinkResponse addLink(URI link) {
        log.info("Request: add link " + link);
        return new LinkResponse(123, link);
    }

    public LinkResponse deleteLink(URI link) {
        log.info("Request: delete link" + link);
        if (link.equals(URI.create("abc.ru"))) {
            throw new NotFoundException("Link not found");
        }
        return new LinkResponse(123, link);
    }

}
