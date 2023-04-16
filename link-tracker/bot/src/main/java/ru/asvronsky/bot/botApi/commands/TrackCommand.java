package ru.asvronsky.bot.botApi.commands;

import java.net.URI;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import ru.asvronsky.bot.botApi.ChatManager;
import ru.asvronsky.bot.botApi.ClientResponseHandler;
import ru.asvronsky.bot.clients.ScrapperClient;
import ru.asvronsky.linkparser.ParserResults.ParserResult;
import ru.asvronsky.linkparser.Parsers.Parser;
import ru.asvronsky.scrapper.dto.controller.AddLinkRequest;

@Component
public class TrackCommand extends RequiringClientCommand {
    private static final String command = "track";
    private static final String description = "start tracking given link";
    private final Parser parser;

    public TrackCommand(ScrapperClient client, Parser parser) {
        super(command, description, client);
        this.parser = parser;
    }

    private static final Pattern parsePattern;
    static {
        parsePattern = Pattern.compile("/"+command+" (?<url>\\S*)");
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        String text = update.message().text();
        ChatManager manager = new ChatManager(chatId);

        Matcher matcher = parsePattern.matcher(text);
        if (matcher.matches()) {
            String urlString = matcher.group("url");
            URI url;
            try {
                Optional<ParserResult> result = parser.parse(new URI(urlString));
                url = result.get().getNormalizedLink();
            } catch (Exception e) {
                return manager.send("Wrong link format, try again!");
            }

            String message = ClientResponseHandler.get()
                .onBadRequest(() -> "Unsupported link type!")
                .handle(() -> {
                    client().addLink(chatId, new AddLinkRequest(url));
                    return "Link \"%s\" added!".formatted(url);
                });
            
            return manager.send(message);
        } 

        return manager.send("Invalid message format, usage:\n"+getUsage());
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
