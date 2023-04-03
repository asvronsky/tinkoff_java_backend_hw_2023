package ru.asvronsky.bot.botApi.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import ru.asvronsky.bot.botApi.Command;

public class UnknownCommand implements Command {
    private String command = "unknown command";
    private String description = "unknown command";

    @Override
    public String command() {
        return command;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        return new SendMessage(chatId, "Sorry! Unknown command");
    }

    @Override
    public boolean supports(Update update) {
        return true;
    }
}
