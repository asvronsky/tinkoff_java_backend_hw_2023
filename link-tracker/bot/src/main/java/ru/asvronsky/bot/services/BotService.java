package ru.asvronsky.bot.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pengrad.telegrambot.request.SendMessage;

import lombok.RequiredArgsConstructor;
import ru.asvronsky.bot.botApi.Bot;

@Service
@RequiredArgsConstructor
public class BotService {
    
    private final Bot bot;

    public void sendUpdate(String message, List<Long> chatIds) {
        for(Long chatId : chatIds) {
            bot.execute(new SendMessage(chatId, message));
        }
    }
    
}
