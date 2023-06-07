package ru.asvronsky.scrapper.model.jpa;

import org.mapstruct.Mapper;

import ru.asvronsky.scrapper.model.Link;

@Mapper(componentModel = "spring")
public interface LinkEntityMapper {
    
    public Link toLink(LinkEntity linkEntity);
    public LinkEntity toLinkEntity(Link link);

}
