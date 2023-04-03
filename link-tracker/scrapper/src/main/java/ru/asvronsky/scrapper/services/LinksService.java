package ru.asvronsky.scrapper.services;

import java.util.List;

import ru.asvronsky.scrapper.dto.LinkResponse;
import ru.asvronsky.scrapper.dto.ListLinkResponse;
import ru.asvronsky.scrapper.exceptions.NotFoundException;

public class LinksService {

    public ListLinkResponse getAllLinks() {
        return new ListLinkResponse(List.of(new LinkResponse(123, "stub")));
    }

    public LinkResponse addLink(String link) {
        return new LinkResponse(123, link);
    }

    public LinkResponse deleteLink(String link) {
        if (link.equals("abc.ru")) {
            throw new NotFoundException("Link not found");
        }
        return new LinkResponse(123, link);
    }

}
