package ru.asvronsky.linkparser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import ru.asvronsky.linkparser.ParserResults.GithubParserResult;
import ru.asvronsky.linkparser.ParserResults.StackOverflowParserResult;
import ru.asvronsky.linkparser.Parsers.GithubParser;
import ru.asvronsky.linkparser.Parsers.Parser;
import ru.asvronsky.linkparser.Parsers.StackOverflowParser;

public class LinkParserChainTest {

    
    private Parser mainParser; 

    {
        mainParser = new GithubParser(null);
        mainParser = new StackOverflowParser(mainParser);
    }    

    @Test
    public void GithubUrl() {
        String githubUrl = "https://github.com/tinkoff-ml-dl/ml-fall22/tree/main/00.%20Lab%20Work";  
        assertEquals(
            new GithubParserResult("tinkoff-ml-dl", "ml-fall22"), 
            mainParser.parse(githubUrl)
        );
    }
    
    @Test
    public void StackOverflowUrl() {
        String stackOverflowUrl = "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c";
        assertEquals(
            new StackOverflowParserResult("1642028"), 
            mainParser.parse(stackOverflowUrl)
        );
    }

    @Test
    public void NoMatch() {
        String googleUrl = "https://stackoverflow.com/search?q=unsupported%20link";
        assertNull(mainParser.parse(googleUrl));
    }

    

}
