package ru.asvronsky.scrapper.services;

import java.net.URI;
import java.util.List;

import ru.asvronsky.scrapper.model.Link;

public interface LinkService {
    
    Link add(long chatId, URI url);
    Link remove(long chatId, URI url);
    List<Link> listAll(long chatId);
    
}
