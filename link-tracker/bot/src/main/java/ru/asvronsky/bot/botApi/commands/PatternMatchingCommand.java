package ru.asvronsky.bot.botApi.commands;

import java.util.regex.Pattern;

import com.pengrad.telegrambot.model.Update;

import ru.asvronsky.bot.botApi.Command;

public abstract class PatternMatchingCommand implements Command  {
    private final String command;
    private final String description;
    private final Pattern pattern;

    public PatternMatchingCommand(String command, String description) {
        this.command = command;
        this.description = description;
        pattern = Pattern.compile("/" + command + "(?>\s+.*)?");
    }

    @Override
    public String command() {
        return command;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public boolean supports(Update update) {
        String text = update.message().text();
        return pattern.matcher(text).matches();
    }
}
