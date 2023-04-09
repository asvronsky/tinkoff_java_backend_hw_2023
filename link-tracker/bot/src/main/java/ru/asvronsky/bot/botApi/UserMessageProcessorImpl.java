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
import ru.asvronsky.bot.clients.ScrapperClient;

public class UserMessageProcessorImpl implements UserMessageProcessor {
    private List<Command> commands;

    public UserMessageProcessorImpl(ScrapperClient scrapperClient) {
        commands = new ArrayList<>();
        commands.add(new StartCommand(scrapperClient));
        commands.add(new TrackCommand(scrapperClient));
        commands.add(new UntrackCommand(scrapperClient));
        commands.add(new ListCommand(scrapperClient));
        commands.add(new HelpCommand(commands()));
        commands.add(new UnknownCommand());
    }


    @Override
    public List<HelpSupportingCommand> commands() {
        return commands.stream()
                .filter(command -> (command instanceof HelpSupportingCommand))
                .map(command -> (HelpSupportingCommand) command)
                .toList();
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
