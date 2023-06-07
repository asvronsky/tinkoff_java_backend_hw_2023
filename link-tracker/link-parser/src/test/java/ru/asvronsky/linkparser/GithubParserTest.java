package ru.asvronsky.linkparser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import org.junit.jupiter.api.Test;

import ru.asvronsky.linkparser.ParserResults.GithubParserResult;
import ru.asvronsky.linkparser.Parsers.GithubParser;
import ru.asvronsky.linkparser.Parsers.Parser;

public class GithubParserTest 
{
    private Parser githubParser = new GithubParser();

    @Test
    public void NoSubdirectories()
    {
        URI githubUrl = URI.create("https://github.com/sanyarnd/tinkoff-java-course-2022");
        assertEquals(
            new GithubParserResult("sanyarnd", "tinkoff-java-course-2022"), 
            githubParser.parse(githubUrl).get()
        );
    }

    @Test
    public void Subdirectories() {
        URI githubUrl = URI.create("https://github.com/tinkoff-ml-dl/ml-fall22/tree/main/00.%20Lab%20Work");
        assertEquals(
            new GithubParserResult("tinkoff-ml-dl", "ml-fall22"), 
            githubParser.parse(githubUrl).get()
        );
    }

    @Test
    public void TrailingSlash() {
        URI githubUrl = URI.create("https://github.com/tinkoff-ml-dl/ml-fall22/");
        assertEquals(
            new GithubParserResult("tinkoff-ml-dl", "ml-fall22"), 
            githubParser.parse(githubUrl).get()
        );
    }    
    
    @Test
    public void NoHttps() {
        URI githubUrl = URI.create("github.com/tinkoff-ml-dl/ml-fall22/");
        assertEquals(
            new GithubParserResult("tinkoff-ml-dl", "ml-fall22"), 
            githubParser.parse(githubUrl).get()
        );
    } 

    @Test
    public void doubleSlash() {
        URI githubUrl = URI.create("https:////github.com//////tinkoff-ml-dl/ml-fall22/");

        assertEquals(
            new GithubParserResult("tinkoff-ml-dl", "ml-fall22"), 
            githubParser.parse(githubUrl).get()
        );
    }

}
