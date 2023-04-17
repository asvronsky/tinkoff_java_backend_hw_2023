package ru.asvronsky.scrapper.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Link {

    private Long id;
    private String url;
    private LocalDateTime updatedAt;
    private String websiteData;

}
