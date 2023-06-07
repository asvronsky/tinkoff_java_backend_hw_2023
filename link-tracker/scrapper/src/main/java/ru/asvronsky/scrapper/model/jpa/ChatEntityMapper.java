package ru.asvronsky.scrapper.model.jpa;

import org.mapstruct.Mapper;

import ru.asvronsky.scrapper.model.Chat;

@Mapper(componentModel = "spring")
public interface ChatEntityMapper {
    
    public Chat toChat(ChatEntity chatEntity);
    public ChatEntity toChatEntity(Chat chat);

}
