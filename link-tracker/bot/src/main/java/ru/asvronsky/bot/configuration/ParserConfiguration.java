package ru.asvronsky.bot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.asvronsky.linkparser.Parsers.GithubParser;
import ru.asvronsky.linkparser.Parsers.Parser;
import ru.asvronsky.linkparser.Parsers.StackOverflowParser;

@Configuration
public class ParserConfiguration {
    
    @Bean
    public Parser parser() {
        return new GithubParser()
            .appendNext(new StackOverflowParser());
    }

}
