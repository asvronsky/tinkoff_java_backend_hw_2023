package ru.asvronsky.bot.botApi.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;

public class StartCommand extends PatternMatchingCommand {

    public StartCommand() {
        super("start", "register new user");
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        User user = update.message().from();
        String username = user.firstName() + " " + user.lastName();
        return new SendMessage(chatId, "Hello, " + username + "!");
    }    
}
