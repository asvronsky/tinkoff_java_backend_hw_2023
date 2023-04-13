package ru.asvronsky.bot.botApi;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.botcommandscope.BotCommandScopeAllPrivateChats;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.asvronsky.bot.exceptions.ScrapperResponseException;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotImpl implements Bot {

    private final UserMessageProcessor processor;
    private final TelegramBot bot;

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        bot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        log.info("incoming batch of updates: " + updates.size());

        int lastUpdate = UpdatesListener.CONFIRMED_UPDATES_NONE;
        for (Update update : updates) {
            log.info("incoming update #" + update.updateId());
            SendMessage msg;
            try {
                msg = processor.process(update);
            } catch (Exception e) {
                logException(e);
                msg = generateErrorMessage(update);
            }

            execute(msg);
            lastUpdate = update.updateId();
            log.info("served update #" + update.updateId());
        }
        return lastUpdate;
    }

    private void logException(Exception e) {
        if (e instanceof ScrapperResponseException) {
            log.error("Scrapper error: " + e.getMessage());
        } else {
            e.printStackTrace();
        }
    }

    private SendMessage generateErrorMessage(Update update) {
        long chatId = update.message().chat().id();
        return new SendMessage(chatId, "Oops! Error");
    } 

    @Override
    @PostConstruct
    public void start() {
        log.info("Bot up, token: " + bot.getToken());
        BaseResponse response = bot.execute(setCommands());
        log.info("Set commands response: " + response.toString());
        bot.setUpdatesListener(this);
    }

    private SetMyCommands setCommands() {
        BotCommand[] botCommands = processor.commands()
                                .stream()
                                .map(HelpSupportingCommand::toApiCommand)
                                .toArray(BotCommand[]::new);
        return new SetMyCommands(botCommands)
                        .scope(new BotCommandScopeAllPrivateChats());
    }

    @Override
    public void close() {
        bot.shutdown();
        log.info("Bot closed");
    }
}
