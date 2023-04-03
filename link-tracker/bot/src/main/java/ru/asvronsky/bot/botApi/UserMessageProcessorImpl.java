package ru.asvronsky.bot.botApi;

import java.util.ArrayList;
import java.util.List;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import ru.asvronsky.bot.botApi.commands.HelpCommand;
import ru.asvronsky.bot.botApi.commands.ListCommand;
import ru.asvronsky.bot.botApi.commands.StartCommand;
import ru.asvronsky.bot.botApi.commands.TrackCommand;
import ru.asvronsky.bot.botApi.commands.UnknownCommand;
import ru.asvronsky.bot.botApi.commands.UntrackCommand;

public class UserMessageProcessorImpl implements UserMessageProcessor {
    private List<Command> commands;
    {
        commands = new ArrayList<>();
        commands.add(new StartCommand());
        commands.add(new TrackCommand());
        commands.add(new UntrackCommand());
        commands.add(new ListCommand());
        commands.add(new HelpCommand(commands));
        commands.add(new UnknownCommand());
    }

    @Override
    public List<Command> commands() {
        return commands.subList(0, commands.size() - 1);
    }

    @Override
    public SendMessage process(Update update) {
        for(Command command : commands) {
            if (command.supports(update)) {
                return command.handle(update);
            }
        }
        return null;
    }
    
}
