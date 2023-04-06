package ru.asvronsky.linkparser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ru.asvronsky.linkparser.ParserResults.GithubParserResult;
import ru.asvronsky.linkparser.Parsers.GithubParser;
import ru.asvronsky.linkparser.Parsers.Parser;

public class GithubParserTest 
{
    private Parser githubParser = new GithubParser(null);

    @Test
    public void NoSubdirectories()
    {
        String githubUrl = "https://github.com/sanyarnd/tinkoff-java-course-2022";
        assertEquals(
            new GithubParserResult("sanyarnd", "tinkoff-java-course-2022"), 
            githubParser.parse(githubUrl)
        );
    }

    @Test
    public void Subdirectories() {
        String githubUrl = "https://github.com/tinkoff-ml-dl/ml-fall22/tree/main/00.%20Lab%20Work";
        assertEquals(
            new GithubParserResult("tinkoff-ml-dl", "ml-fall22"), 
            githubParser.parse(githubUrl)
        );
    }

    @Test
    public void TrailingSlash() {
        String githubUrl = "https://github.com/tinkoff-ml-dl/ml-fall22/";
        assertEquals(
            new GithubParserResult("tinkoff-ml-dl", "ml-fall22"), 
            githubParser.parse(githubUrl)
        );
    }    
    
    @Test
    public void NoHttps() {
        String githubUrl = "github.com/tinkoff-ml-dl/ml-fall22/";
        assertEquals(
            new GithubParserResult("tinkoff-ml-dl", "ml-fall22"), 
            githubParser.parse(githubUrl)
        );
    } 

}
