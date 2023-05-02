package ru.asvronsky.bot.botApi;

import java.util.List;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface Bot extends AutoCloseable, UpdatesListener {
    
    @Override
    public int process(List<Update> updates);
    
    public void execute(SendMessage request);

    public void start();

    @Override
    public void close();
}
