package ru.asvronsky.linkparser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import org.junit.jupiter.api.Test;

import ru.asvronsky.linkparser.ParserResults.StackOverflowParserResult;
import ru.asvronsky.linkparser.Parsers.Parser;
import ru.asvronsky.linkparser.Parsers.StackOverflowParser;

public class StackOverflowParserTest {
    private Parser stackOverflowParser = new StackOverflowParser();
    
    @Test
    public void NoSubdirectories()
    {
        URI stackOverflowUrl = URI.create("https://stackoverflow.com/questions/1642028");
        assertEquals(
            new StackOverflowParserResult(1642028), 
            stackOverflowParser.parse(stackOverflowUrl).get()
        );
    }

    @Test
    public void WithSubdirectories() {
        URI stackOverflowUrl = URI.create("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c");
        assertEquals(
            new StackOverflowParserResult(1642028), 
            stackOverflowParser.parse(stackOverflowUrl).get()
        );;
    }

    @Test
    public void TrailingSlash() {
        URI stackOverflowUrl = URI.create("https://stackoverflow.com/questions/1642028/");
        assertEquals(
            new StackOverflowParserResult(1642028), 
            stackOverflowParser.parse(stackOverflowUrl).get()
        );
    }

    @Test
    public void NoHttps() {
        URI stackOverflowUrl = URI.create("stackoverflow.com/questions/1642028/");
        assertEquals(
            new StackOverflowParserResult(1642028), 
            stackOverflowParser.parse(stackOverflowUrl).get()
        );
    } 
    
}
