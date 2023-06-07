package ru.asvronsky.scrapper.model;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class Link {

    private Long id;
    private String url;
    private OffsetDateTime updatedAt;
    private String websiteData;

}
