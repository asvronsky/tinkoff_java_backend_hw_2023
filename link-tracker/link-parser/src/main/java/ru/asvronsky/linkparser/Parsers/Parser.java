package ru.asvronsky.linkparser.Parsers;

import java.net.URI;
import java.util.Optional;

import ru.asvronsky.linkparser.ParserResults.ParserResult;

@FunctionalInterface
public interface Parser {

    public Optional<ParserResult> parse(URI url);

    default public Parser appendNext(Parser nextParser) {
        return url -> {
            return parse(url).or(() -> nextParser.parse(url));
        };
    }

}
