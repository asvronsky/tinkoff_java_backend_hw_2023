package ru.asvronsky.linkparser.Parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.asvronsky.linkparser.ParserResults.GithubParserResult;
import ru.asvronsky.linkparser.ParserResults.ParserResult;

public final class GithubParser extends Parser {
    /**
     * first group: user
     * second group: name of repository
     */
    private final static Pattern userAndRepoPattern = Pattern.compile(
        "^(?>https://)?github\\.com/(?<user>.+?)/(?<repo>.+?)(?>/.*)?$"
    );

    public GithubParser(Parser successor) {
        super(successor);
    }

    @Override
    public ParserResult parse(String url) {
        
        
        Matcher userAndRepoMatcher = userAndRepoPattern.matcher(url);
        if (userAndRepoMatcher.matches()) {
            String user = userAndRepoMatcher.group("user");
            String repo = userAndRepoMatcher.group("repo");
            return new GithubParserResult(user, repo);
        } else {
            return super.parse(url);
        }
    }
    
}
