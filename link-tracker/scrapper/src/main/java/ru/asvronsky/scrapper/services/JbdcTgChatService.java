package ru.asvronsky.scrapper.services;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import ru.asvronsky.scrapper.repository.ChatDao;

@Service
@AllArgsConstructor
public class JbdcTgChatService implements TgChatService {

    private final ChatDao chatRepository;

    @Override
    public void register(long chatId) {
        chatRepository.add(chatId);
    }

    @Override
    public void unregister(long chatId) {
        chatRepository.remove(chatId);
    }
}
