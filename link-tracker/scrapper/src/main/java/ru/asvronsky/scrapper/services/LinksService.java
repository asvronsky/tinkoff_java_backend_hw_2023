package ru.asvronsky.scrapper.services;

import java.util.List;

import ru.asvronsky.scrapper.dto.AddLinkResponse;
import ru.asvronsky.scrapper.dto.DeleteLinkResponse;
import ru.asvronsky.scrapper.dto.GetAllLinksResponse;
import ru.asvronsky.scrapper.exceptions.NotFoundException;

public class LinksService {

    public List<GetAllLinksResponse> getAllLinks() {
        return List.of(new GetAllLinksResponse(123, "stub"));
    }

    public AddLinkResponse addLink(String link) {
        return new AddLinkResponse(123, link);
    }

    public DeleteLinkResponse deleteLink(String link) {
        if (link.equals("abc.ru")) {
            throw new NotFoundException("Link not found");
        }
        return new DeleteLinkResponse(123, link);
    }

}
