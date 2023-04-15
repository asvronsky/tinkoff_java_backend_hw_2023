package ru.asvronsky.scrapper.configuration;

import org.springframework.context.annotation.Bean;

import ru.asvronsky.linkparser.Parsers.GithubParser;
import ru.asvronsky.linkparser.Parsers.Parser;
import ru.asvronsky.linkparser.Parsers.StackOverflowParser;

public class ParserConfiguration {
    
    @Bean
    public Parser parser() {
        return new GithubParser()
            .appendNext(new StackOverflowParser());
    }

}
