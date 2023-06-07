package ru.asvronsky.scrapper.repository.jpa;

import java.util.List;
import java.util.Optional;

import org.mapstruct.factory.Mappers;

import lombok.RequiredArgsConstructor;
import ru.asvronsky.scrapper.model.Chat;
import ru.asvronsky.scrapper.model.jpa.ChatEntity;
import ru.asvronsky.scrapper.model.jpa.ChatEntityMapper;
import ru.asvronsky.scrapper.repository.ChatDao;

@RequiredArgsConstructor
public class JpaChatDao implements ChatDao {

    private final JpaChatEntityRepository entityRepository;

    private final ChatEntityMapper mapper = Mappers.getMapper(ChatEntityMapper.class);

    @Override
    public void add(long chatId) {
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setChatId(chatId);
        entityRepository.saveAndFlush(chatEntity);
    }

    @Override
    public Optional<Chat> remove(long chatId) {
        entityRepository.deleteById(chatId);
        entityRepository.flush();

        return Optional.empty();
    }

    @Override
    public List<Chat> findAll() {
        return entityRepository.findAll()
            .stream()
            .map(mapper::toChat)
            .toList();
    }
    
}
