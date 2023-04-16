package ru.asvronsky.bot.botApi.commands;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import ru.asvronsky.bot.clients.ScrapperClient;
import ru.asvronsky.bot.exceptions.ScrapperResponseException;
import ru.asvronsky.scrapper.dto.controller.AddLinkRequest;
import ru.asvronsky.scrapper.dto.controller.ApiErrorResponse;

@Component
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
                return new SendMessage(chatId, "Wrong url format, try again!");

            } catch (ScrapperResponseException e) {
                ApiErrorResponse response = e.getApiResponse();
                int code = Integer.parseInt(response.code());
                
                if (code == HttpStatus.BAD_REQUEST.value()) {
                    return new SendMessage(chatId, "Unsupported link type!");
                } else {
                    throw e;
                }
            }
        } 

        return new SendMessage(chatId, "Invalid message format, usage:\n"+getUsage());
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
