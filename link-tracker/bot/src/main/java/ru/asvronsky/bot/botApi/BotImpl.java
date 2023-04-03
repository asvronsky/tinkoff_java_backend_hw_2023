package ru.asvronsky.bot.botApi;

import java.util.List;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.botcommandscope.BotCommandScopeAllPrivateChats;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BotImpl implements Bot {
    private final UserMessageProcessor processor;
    private final String token;
    private final TelegramBot bot;

    public BotImpl(String token, UserMessageProcessor processor) {
        log.info("create new bot");
        this.processor = processor;
        this.token = token;
        bot = new TelegramBot(token);
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        bot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        log.info("incoming batch of updates: " + updates.size());
        for (Update update : updates) {
            log.info("incoming update: " + update.updateId());
            SendMessage msg = processor.process(update);
            execute(msg);
        }
        return lastUpdateId(updates);
    }

    @Override
    public void start() {
        log.info("Bot up, token: " + token);
        BotCommand[] botCommands = processor.commands()
                                .stream()
                                .map(Command::toApiCommand)
                                .toArray(BotCommand[]::new);
        SetMyCommands cmds = new SetMyCommands(botCommands)
                        .scope(new BotCommandScopeAllPrivateChats());
        BaseResponse response = bot.execute(cmds);
        log.info(response.toString());
        bot.setUpdatesListener(this);
    }

    @Override
    public void close() {
        bot.shutdown();
        log.info("Bot closed");
    }

    private int lastUpdateId(List<Update> updates) {
        return updates.get(updates.size() - 1).updateId();
    }
    
}
