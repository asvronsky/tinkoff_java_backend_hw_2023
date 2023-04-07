package ru.asvronsky.bot.botApi.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Answers;

import com.pengrad.telegrambot.model.Update;

import ru.asvronsky.bot.botApi.Command;

public class UnknownCommandTest {
    private final String defaultMessage = "Sorry! Unknown command";

    @Test
    public void defaultMessageTest() {
        Update update = mock(Update.class, Answers.RETURNS_DEEP_STUBS);
        Command unknownCommand = new UnknownCommand();

        when(update.message().text())
            .thenReturn("/unlist");

        String message = (String) unknownCommand.handle(update)
            .getParameters()
            .get("text");
    

        // then
        assertTrue(unknownCommand.supports(update));
        assertEquals(defaultMessage, message);
    }

}
