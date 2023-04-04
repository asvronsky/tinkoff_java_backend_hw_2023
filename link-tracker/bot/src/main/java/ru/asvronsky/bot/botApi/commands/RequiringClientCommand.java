package ru.asvronsky.bot.botApi.commands;

import ru.asvronsky.bot.clients.ScrapperClient;

public abstract class RequiringClientCommand extends PatternMatchingCommand{

    private final ScrapperClient client;

    public RequiringClientCommand(String command, String description, ScrapperClient client) {
        super(command, description);
        this.client = client;
    }
    
    public ScrapperClient client() {
        return client;
    }
}
