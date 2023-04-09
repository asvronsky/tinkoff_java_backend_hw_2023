package ru.asvronsky.scrapper.services;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import ru.asvronsky.scrapper.dto.LinkResponse;
import ru.asvronsky.scrapper.dto.ListLinkResponse;
import ru.asvronsky.scrapper.exceptions.NotFoundException;

@Slf4j
public class LinksService {

    public ListLinkResponse getAllLinks() {
        log.info("Request: get links");
        return new ListLinkResponse(List.of(new LinkResponse(123, "stub")));
    }

    public LinkResponse addLink(String link) {
        log.info("Request: add link " + link);
        return new LinkResponse(123, link);
    }

    public LinkResponse deleteLink(String link) {
        log.info("Request: delete link" + link);
        if (link.equals("abc.ru")) {
            throw new NotFoundException("Link not found");
        }
        return new LinkResponse(123, link);
    }

}
