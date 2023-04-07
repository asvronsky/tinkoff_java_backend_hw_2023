package ru.asvronsky.bot.botApi;

import java.util.List;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface UserMessageProcessor {
    List<? extends HelpSupportingCommand> commands();

    SendMessage process(Update update);
}
