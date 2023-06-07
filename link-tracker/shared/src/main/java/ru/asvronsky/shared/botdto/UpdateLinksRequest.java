package ru.asvronsky.shared.botdto;

import java.net.URI;
import java.util.List;

public record UpdateLinksRequest(
    Long id, 
    URI url, 
    String description, 
    List<Long> chatIds) {

}
