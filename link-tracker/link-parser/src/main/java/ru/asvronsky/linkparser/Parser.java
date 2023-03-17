package ru.asvronsky.linkparser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
