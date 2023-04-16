package ru.asvronsky.bot.botApi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import jakarta.annotation.PostConstruct;
import ru.asvronsky.bot.botApi.commands.UnknownCommand;

@Component
public class UserMessageProcessorImpl implements UserMessageProcessor {

    @Autowired
    private List<Command> commands;

    @PostConstruct
    public void init() {
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
