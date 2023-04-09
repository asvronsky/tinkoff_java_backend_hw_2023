package ru.asvronsky.bot.botApi;

import com.pengrad.telegrambot.model.BotCommand;

public interface HelpSupportingCommand extends Command {
    
    public String command();
    
    public String description();

    public default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }

    public default String getUsage() {
        return "/" + command();
    }

    public default String getHelpDescription() {
        return getUsage() + " - " + description();
    }
}
