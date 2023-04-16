package ru.asvronsky.bot.botApi.commands;

import java.util.List;

import org.springframework.stereotype.Component;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import ru.asvronsky.bot.botApi.HelpSupportingCommand;

@Component
public class HelpCommand extends PatternMatchingCommand {
    private final String helpMessage;

    public HelpCommand(List<? extends HelpSupportingCommand> commands) {
        super("help", "display help");
        String msg = "";
        for (HelpSupportingCommand command : commands) {
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
