package ru.asvronsky.bot.botApi;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface Command {

    public SendMessage handle(Update update);

    public boolean supports(Update update);
}
