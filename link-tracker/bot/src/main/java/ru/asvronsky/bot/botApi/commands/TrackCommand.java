package ru.asvronsky.bot.botApi.commands;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import ru.asvronsky.bot.clients.ScrapperClient;
import ru.asvronsky.scrapper.dto.controller.AddLinkRequest;

public class TrackCommand extends RequiringClientCommand {
    private static final String command = "track";
    private static final String description = "start tracking given link";

    public TrackCommand(ScrapperClient client) {
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
            String urlString = matcher.group("url");
            try {
                URI url = new URI(urlString);
                client().addLink(chatId, new AddLinkRequest(url));
                return new SendMessage(chatId, "Link \""+url+"\" added!");
            } catch (URISyntaxException e) {
                return new SendMessage(chatId, "Wrong link format, try again!");
            }
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
