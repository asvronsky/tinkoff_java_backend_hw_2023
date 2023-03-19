package ru.asvronsky.linkparser.Parsers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.asvronsky.linkparser.ParserResults.ParserResult;

@AllArgsConstructor
public abstract sealed class Parser permits GithubParser, StackOverflowParser {
    @Getter @Setter private Parser successor;

    public ParserResult parse(String url) {
        if (getSuccessor() != null) {
            return getSuccessor().parse(url);
        } else {
            // throw new IllegalArgumentException("Unable to find the correct parser");
            return null;
        }
    }

}
