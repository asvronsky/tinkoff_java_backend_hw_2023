package ru.asvronsky.bot.botApi.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import ru.asvronsky.bot.botApi.Command;

public class UnknownCommand implements Command {
    private final String defaultMessage = "Sorry! Unknown command";

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        return new SendMessage(chatId, defaultMessage);
    }

    @Override
    public boolean supports(Update update) {
        return true;
    }
}
