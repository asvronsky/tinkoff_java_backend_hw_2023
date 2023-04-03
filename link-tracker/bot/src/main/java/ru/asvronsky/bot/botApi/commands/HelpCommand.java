package ru.asvronsky.bot.botApi.commands;

import java.util.List;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import ru.asvronsky.bot.botApi.Command;

public class HelpCommand extends PatternMatchingCommand {
    private final String helpMessage;

    public HelpCommand(List<? extends Command> commands) {
        super("help", "display help");
        String msg = "";
        for (Command command : commands) {
            msg += command.getHelpDescription() + "\n";
        }
        helpMessage = msg + getHelpDescription();
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        return new SendMessage(chatId, helpMessage);
    }
}
