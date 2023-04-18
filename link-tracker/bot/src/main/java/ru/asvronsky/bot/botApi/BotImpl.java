package ru.asvronsky.bot.botApi;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.botcommandscope.BotCommandScopeAllPrivateChats;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;

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
    public void execute(SendMessage request) {
        bot.execute(request, new Callback<SendMessage, SendResponse>() {

            @Override
            public void onResponse(SendMessage request, SendResponse response) {
                
            }

            @Override
            public void onFailure(SendMessage request, IOException e) {
                Object chatId = request.getParameters().get("chat_id");
                String text = (String) request.getParameters().get("text");
                String logMessage = "Error while sending message. "
                    + "chatid=%s, message=%s".formatted(chatId, text);
                log.error(logMessage);
            }
            
        });
    }

    @Override
    public int process(List<Update> updates) {
        log.info("incoming batch of updates: " + updates.size());

        int lastUpdate = UpdatesListener.CONFIRMED_UPDATES_NONE;
        for (Update update : updates) {
            log.info("incoming update #" + update.updateId());
            SendMessage msg = processHandler(update);
        
            execute(msg);
            lastUpdate = update.updateId();
            log.info("served update #" + update.updateId());
        }
        return lastUpdate;
    }

    private SendMessage processHandler(Update update) {
        try {
            return processor.process(update);
        } catch (ScrapperResponseException e) {
            log.error("Scrapper response (UNINTENDED exception):", e);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
