package ru.asvronsky.linkparser.Parsers;

import java.net.URI;
import java.nio.file.Path;
import java.util.Optional;

import ru.asvronsky.linkparser.ParserResults.GithubParserResult;
import ru.asvronsky.linkparser.ParserResults.ParserResult;

public class GithubParser implements Parser {

    @Override
    public Optional<ParserResult> parse(URI url) {
        Path path = Path.of(url.getSchemeSpecificPart().toString());
        if (path.getName(0).toString().equals("github.com")) {
            if (path.getNameCount() >= 3) {
                String username = path.getName(1).toString();
                String repo = path.getName(2).toString();
                var result = new GithubParserResult(username, repo);
                result.setNormalizedLink(
                    URI.create("github.com/%s/%s".formatted(username, repo))
                );
                return Optional.of(result);
            }
        }
        return Optional.empty();
    }
    
}
