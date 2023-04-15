package ru.asvronsky.linkparser.Parsers;

import java.net.URI;
import java.nio.file.Path;
import java.util.Optional;

import ru.asvronsky.linkparser.ParserResults.ParserResult;
import ru.asvronsky.linkparser.ParserResults.StackOverflowParserResult;

public class StackOverflowParser implements Parser {

    @Override
    public Optional<ParserResult> parse(URI url) {
        Path path = Path.of(url.getSchemeSpecificPart().toString());
        if (path.getName(0).toString().equals("stackoverflow.com")) {
            if (path.getNameCount() >= 3 && path.getName(1).toString().equals("questions")) {
                Long id = Long.parseLong(path.getName(2).toString());
                return Optional.of(new StackOverflowParserResult(id));
            }
        }
        return Optional.empty();
    }
    
}
