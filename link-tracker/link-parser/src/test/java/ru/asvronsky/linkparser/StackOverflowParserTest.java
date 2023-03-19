package ru.asvronsky.linkparser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ru.asvronsky.linkparser.ParserResults.StackOverflowParserResult;
import ru.asvronsky.linkparser.Parsers.Parser;
import ru.asvronsky.linkparser.Parsers.StackOverflowParser;

public class StackOverflowParserTest {
    private Parser stackOverflowParser = new StackOverflowParser(null);
    
    @Test
    public void NoSubdirectories()
    {
        String stackOverflowUrl = "https://stackoverflow.com/questions/1642028";
        assertEquals(
            new StackOverflowParserResult("1642028"), 
            stackOverflowParser.parse(stackOverflowUrl)
        );
    }

    @Test
    public void WithSubdirectories() {
        String stackOverflowUrl = "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c";
        assertEquals(
            new StackOverflowParserResult("1642028"), 
            stackOverflowParser.parse(stackOverflowUrl)
        );;
    }

    @Test
    public void TrailingSlash() {
        String stackOverflowUrl = "https://stackoverflow.com/questions/1642028/";
        assertEquals(
            new StackOverflowParserResult("1642028"), 
            stackOverflowParser.parse(stackOverflowUrl)
        );
    }

    @Test
    public void NoHttps() {
        String stackOverflowUrl = "stackoverflow.com/questions/1642028/";
        assertEquals(
            new StackOverflowParserResult("1642028"), 
            stackOverflowParser.parse(stackOverflowUrl)
        );
    } 
    
}
