package ru.asvronsky.bot.dto;

import java.util.List;

public record UpdateLinksResponse(int id, String url, String description, List<Integer> ids) {

}
