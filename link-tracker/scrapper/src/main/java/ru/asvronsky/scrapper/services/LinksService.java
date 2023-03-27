package ru.asvronsky.scrapper.services;

import java.util.List;

import ru.asvronsky.scrapper.dto.AddLinkResponse;
import ru.asvronsky.scrapper.dto.DeleteLinkResponse;
import ru.asvronsky.scrapper.dto.GetAllLinksResponse;

public class LinksService {

    public List<GetAllLinksResponse> getAllLinks() {
        return List.of(new GetAllLinksResponse(123, "url"));
    }

    public AddLinkResponse addLink(String link) {
        return null;
    }

    public DeleteLinkResponse deleteLink(String link) {
        return null;
    }

}
