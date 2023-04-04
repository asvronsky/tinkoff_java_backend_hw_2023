package ru.asvronsky.bot.botApi.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import ru.asvronsky.bot.clients.ScrapperClient;
import ru.asvronsky.scrapper.dto.RemoveLinkRequest;

public class UntrackCommand extends RequiringClientCommand {
    private static final String command = "untrack";
    private static final String description = "stop tracking given link";

    public UntrackCommand(ScrapperClient client) {
        super(command, description, client);
        
    }
    
    private static final Pattern parsePattern;
    static {
        parsePattern = Pattern.compile("/"+command+" (?<url>\\S*)");
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        String text = update.message().text();

        Matcher matcher = parsePattern.matcher(text);
        if (matcher.matches()) {
            String url = matcher.group("url");
            client().deleteLink(chatId, new RemoveLinkRequest(url));
            return new SendMessage(chatId, "Link \""+url+"\" removed!");
        } 

        return new SendMessage(chatId, "Invalid request, usage:\n"+getUsage());
    }

    @Override
    public String getUsage() {
        return "/"+command+" {url}";
    }

    @Override
    public String getHelpDescription() {
        return "/" + command() + " {url} - " + description();
    }
}
