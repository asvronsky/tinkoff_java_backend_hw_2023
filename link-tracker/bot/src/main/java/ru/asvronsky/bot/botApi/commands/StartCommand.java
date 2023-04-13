package ru.asvronsky.bot.botApi.commands;

import org.springframework.stereotype.Component;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;

import ru.asvronsky.bot.clients.ScrapperClient;

@Component
public class StartCommand extends RequiringClientCommand {

    public StartCommand(ScrapperClient client) {
        super("start", "register new user", client);
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        User user = update.message().from();
        String username = user.firstName() + " " + user.lastName();

        client().registerChat(chatId);
        return new SendMessage(chatId, "Hello, " + username + "!");
    }    
}
