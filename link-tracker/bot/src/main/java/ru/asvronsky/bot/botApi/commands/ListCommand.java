package ru.asvronsky.bot.botApi.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class ListCommand extends PatternMatchingCommand {
    
    public ListCommand() {
        super("list", "list tracked links");
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        return new SendMessage(chatId, "google.com");
    }
}
