package ru.asvronsky.bot.dto;

import java.util.List;

public record UpdateLinksRequest(
    int id, 
    String url, 
    String description, 
    List<Integer> ids) {

}
