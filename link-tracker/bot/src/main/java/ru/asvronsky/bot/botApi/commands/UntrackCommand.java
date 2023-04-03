package ru.asvronsky.bot.botApi.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class UntrackCommand extends PatternMatchingCommand {
    public UntrackCommand() {
        super("untrack", "stop tracking given link");
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        return new SendMessage(chatId, "Link deleted!");
    }

    @Override
    public String getHelpDescription() {
        return "/" + command() + " {url} - " + description();
    }
}
