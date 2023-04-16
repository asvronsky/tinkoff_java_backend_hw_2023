package ru.asvronsky.bot.botApi;

import com.pengrad.telegrambot.request.SendMessage;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChatManager {
    private final long chatId;

    public SendMessage send(String message) {
        return new SendMessage(chatId, message);
    }
}
