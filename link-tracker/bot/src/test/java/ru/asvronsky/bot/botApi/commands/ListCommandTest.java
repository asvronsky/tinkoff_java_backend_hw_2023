package ru.asvronsky.bot.botApi.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pengrad.telegrambot.model.Update;

import ru.asvronsky.bot.botApi.Command;
import ru.asvronsky.bot.clients.ScrapperClient;
import ru.asvronsky.scrapper.dto.LinkResponse;
import ru.asvronsky.scrapper.dto.ListLinkResponse;

@ExtendWith(MockitoExtension.class)
public class ListCommandTest {
    
    @Mock
    private ScrapperClient client;

    private static String noLinksMessage = "Sorry! No links are being tracked";

    @ParameterizedTest(name = "{index}: {0} {1}")
    @MethodSource({"dataTwoLinks", "dataNullOrEmpty"})
    public void listCommandTest(String incomingMessage, List<String> links, long chatId, String expectedMessage) {
        // given
        Update update = mock(Update.class, Answers.RETURNS_DEEP_STUBS);
        Command listCommand = new ListCommand(client);

        when(client.getAllLinks(any()))
            .thenReturn(linksToResponse(links, chatId));
        when(update.message().chat().id())
            .thenReturn(chatId);
        when(update.message().text())
            .thenReturn(incomingMessage);
        
        // when
        String message = (String) listCommand.handle(update)
                .getParameters()
                .get("text");
        

        // then
        assertTrue(listCommand.supports(update));
        assertEquals(expectedMessage, message);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("dataForSupportsTest")
    public void supportsTest(String incomingMessage, boolean expected) {
        Update update = mock(Update.class, Answers.RETURNS_DEEP_STUBS);
        Command listCommand = new ListCommand(client);

        when(update.message().text())
            .thenReturn(incomingMessage);

        assertEquals(expected, listCommand.supports(update));
    }

    private static Stream<Arguments> dataForSupportsTest() {
        return Stream.of(
            Arguments.of("/list aaa", true),
            Arguments.of("/ list", false),
            Arguments.of("list", false)
        );
    }

    @DisplayName("dataTwoLinks")
    private static Stream<Arguments> dataTwoLinks() {
        String incomingmessage = "/list";
        long chatId = 101L;
        return Stream.of(
            Arguments.of(incomingmessage, 
                List.of(
                    "google.com",
                    "amazon.com"
                ),
                chatId,
                """
                google.com
                amazon.com"""
            )
        );
    }

    private static Stream<Arguments> dataNullOrEmpty() {
        String incomingmessage = "/list";
        long chatId = 101L;
        return Stream.of(
            Arguments.of(incomingmessage, 
                null,
                chatId,
                noLinksMessage
            ),
            Arguments.of(incomingmessage, 
                List.of(),
                chatId,
                noLinksMessage
            )
        );
    }

    private ListLinkResponse linksToResponse(List<String> links, long chatId) {
        if (links == null) {
            return new ListLinkResponse(null);
        }
        
        List<LinkResponse> linkResponses = links.stream()
                    .map(link -> new LinkResponse(chatId, link))
                    .toList();
        return new ListLinkResponse(linkResponses);
    }
}
