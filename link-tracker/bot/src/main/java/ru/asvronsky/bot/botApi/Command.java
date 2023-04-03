package ru.asvronsky.bot.botApi;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface Command {

    public String command();
    
    public String description();

    public SendMessage handle(Update update);

    public boolean supports(Update update);

    public default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }

    public default String getHelpDescription() {
        return "/" + command() + " - " + description();
    }
}
