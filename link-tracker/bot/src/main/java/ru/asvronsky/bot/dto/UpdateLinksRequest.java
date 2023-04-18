package ru.asvronsky.bot.dto;

import java.net.URI;
import java.util.List;

public record UpdateLinksRequest(
    Long id, 
    URI url, 
    String description, 
    List<Long> ids) {

}
