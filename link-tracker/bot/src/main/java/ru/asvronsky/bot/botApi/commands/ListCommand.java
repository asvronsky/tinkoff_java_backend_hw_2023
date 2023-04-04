package ru.asvronsky.bot.botApi.commands;

import java.util.List;
import java.util.stream.Collectors;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import ru.asvronsky.bot.clients.ScrapperClient;
import ru.asvronsky.scrapper.dto.LinkResponse;

public class ListCommand extends RequiringClientCommand {
    
    public ListCommand(ScrapperClient client) {
        super("list", "list tracked links", client);
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        List<LinkResponse> links = client().getAllLinks(chatId).links();
        String responseMessage = links.stream()
            .map(LinkResponse::url)
            .collect(Collectors.joining("\n"));
        return new SendMessage(chatId, responseMessage);
    }


}
