package ru.asvronsky.linkparser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.net.URI;

import org.junit.jupiter.api.Test;

import ru.asvronsky.linkparser.ParserResults.GithubParserResult;
import ru.asvronsky.linkparser.ParserResults.StackOverflowParserResult;
import ru.asvronsky.linkparser.Parsers.GithubParser;
import ru.asvronsky.linkparser.Parsers.Parser;
import ru.asvronsky.linkparser.Parsers.StackOverflowParser;

public class LinkParserChainTest {

    private Parser mainParser = new GithubParser().appendNext(new StackOverflowParser());

    @Test
    public void GithubUrl() {
        URI githubUrl = URI.create("https://github.com/tinkoff-ml-dl/ml-fall22/tree/main/00.%20Lab%20Work");  
        assertEquals(
            new GithubParserResult("tinkoff-ml-dl", "ml-fall22"), 
            mainParser.parse(githubUrl).get()
        );
    }
    
    @Test
    public void StackOverflowUrl() {
        URI stackOverflowUrl = URI.create("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c");
        assertEquals(
            new StackOverflowParserResult(1642028), 
            mainParser.parse(stackOverflowUrl).get()
        );
    }

    @Test
    public void NoMatch() {
        URI googleUrl = URI.create("https://stackoverflow.com/search?q=unsupported%20link");
        assertFalse(mainParser.parse(googleUrl).isPresent());
    }

    

}
